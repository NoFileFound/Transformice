#coding: utf-8
import math
import time

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.Time import Time

class ModoPwet:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        self.lastOpened = 0
        self.banhackreason = "$MessageTriche" # Hack. Your account will be permanently banned if you keep breaking the rules!
        self.banhackreasonperm = "$MessageTricheDef" # Hack. You have been warned.
        
# Modopwet helper functions

    def banHack(self, playerName, iban):
        info = self.server.cursor["sanctions"].find_one({"Username":playerName, 'Type':'Banned', 'State':'Expired', 'Reason':self.banhackreason}, sort=[("Duration", -1)])
        if info == None:
            hours = 24
        else:
            hours = Time.getHoursDiff(info) * 2
        result = 0
        
        if hours > 8766:
            reason = self.banhackreasonperm
            result = self.server.banPlayer(playerName, -1, reason, self.client.playerName, False, True)
        else:
            reason = self.banhackreason
            result = self.server.banPlayer(playerName, hours, reason, self.client.playerName, not iban, True)
        if result == 1:
            self.sendReportersKarma(playerName, hours, reason, self.client.playerName, "ban")
        elif result == 2:
            self.client.sendServerMessage(f"Player [{playerName}] is already banned, please wait.", True)

    def makeReport(self, playerName, type, reason, reporter):
        if playerName != reporter or self.server.isDebug:
            if reporter in self.server.modoReports:
                reporter_community = self.server.modoReports[reporter]['language'].upper()
                if reporter_community in self.client.modoCommunitiesNotification and self.client.isModoPwetNotifications:
                    self.sendModoNotification(reporter, reporter_community.lower(), "report", [playerName, f"{self.getReportType(type)}"])
        
            player = self.server.players.get(playerName)
            if player != None:
                if playerName in self.server.modoReports:
                    if reporter in self.server.modoReports[playerName]['reporters']:
                        r = self.server.modoReports[playerName]['reporters'][reporter]
                        if r[0] != type:
                            self.server.modoReports[playerName]['reporters'][reporter] = [type, reason, Time.getTime()]
                    else:
                        self.server.modoReports[playerName]['reporters'][reporter] = [type, reason, Time.getTime()]
                    self.server.modoReports[playerName]['status'] = 'connected' if self.server.checkConnectedPlayer(playerName) else 'disconnected'
                else:
                    self.server.modoReports[playerName] = {}
                    self.server.modoReports[playerName]['reporters'] = {reporter:[type, reason, Time.getTime()]}
                    self.server.modoReports[playerName]['status'] = 'connected' if self.server.checkConnectedPlayer(playerName) else 'disconnected'
                    self.server.modoReports[playerName]['language'] = player.playerLangue.upper()
                    self.server.modoReports[playerName]['isMuted'] = False
                    self.server.modoReports[playerName]['mutehours'] = 0
                    self.server.modoReports[playerName]['mutereason'] = ""
                    self.server.modoReports[playerName]['mutedby'] = ""
                    self.server.modoReports[playerName]["bannedby"] = ""
                    self.server.modoReports[playerName]["banhours"] = 0
                    self.server.modoReports[playerName]["banreason"] = ""
                self.client.sendBanConsideration()
                self.updateModoPwet()
                reported_community = self.server.modoReports[playerName]['language'].upper()
                if reported_community in self.client.modoCommunitiesNotification and self.client.isModoPwetNotifications:
                    self.sendModoNotification(reporter, "", "reported", [f"{self.getReportType(type)}", player.roomName])

    def _sortkey(self, array):
        for i in array[1]["reporters"]:
            return array[1]["reporters"][i][2]

    def openModoPwet(self, isOpen=False, modopwetOnlyPlayerReports=False, sortBy=False):
        communityCount = {}
        if len(self.server.modoReports) <= 0:
            self.client.sendPacket(Identifiers.send.Modopwet_Open, 0)
        else:
            if (time.time() - self.lastOpened) < 1.5:
                return
            self.lastOpened = time.time()
        
            modoReports = self.sortReports(sortBy)
            bannedList = {}
            deletedList = {}
            disconnectList = []
            cnt = 0
            cnt2 = 0
            
            p = ByteArray()
            for i in modoReports:
                state = False
                playerName = i[0]
                v = self.server.modoReports[playerName]
                if self.client.modoPwetLangue == 'ALL' or v["language"] == self.client.modoPwetLangue:
                    for name in v["reporters"]:
                        if int(time.time() - v["reporters"][name][2]) > 86400:
                            del self.server.modoReports[playerName]
                            state = True
                            break
                if state:
                    state = False
                    continue
            
                if modopwetOnlyPlayerReports:
                    if v['status'] == "connected" and (v["language"] == self.client.modoPwetLangue or self.client.modoPwetLangue == "ALL"):
                        cnt2 += 1
                        
                    if cnt2 > 1:
                        break
                        
                if not v["language"] in communityCount:
                    communityCount[v["language"]] = 1
                else:
                    communityCount[v["language"]] += 1
                
                if (v["language"] == self.client.modoPwetLangue or self.client.modoPwetLangue == "ALL"):                
                    cnt += 1
                    if cnt > 255: break
                    self.client.lastReportID += 1
                    player = self.server.players.get(playerName)
                    TimePlayed = math.floor(player.playerTime / 3600) if player != None else 0
                    playerNameRoom = player.roomName if player != None else "0"
                    playerRoomMods = self.getRoomMods(playerNameRoom)
                    p.writeByte(cnt)
                    p.writeShort(self.client.lastReportID)
                    p.writeUTF(v["language"])
                    p.writeUTF(playerName)
                    p.writeUTF(playerNameRoom)
                    p.writeByte(len(playerRoomMods))
                    for info in playerRoomMods:
                        p.writeUTF(info)
                    p.writeInt(TimePlayed)
                    p.writeByte(int(len(v["reporters"])))
                    for name in v["reporters"]:
                        r = v["reporters"][name]
                        p.writeUTF(name)
                        p.writeShort(self.getPlayerKarma(name))
                        p.writeUTF(r[1])
                        p.writeByte(r[0])
                        p.writeShort(int(Time.getSecondsDiff(r[2]) / 60))
                                
                    mute = v["isMuted"]
                    p.writeBoolean(mute)
                    if mute:
                        p.writeUTF(v["mutedby"])
                        p.writeShort(v["mutehours"])
                        p.writeUTF(v["mutereason"])
                        
                    if v['status'] == 'banned':
                        x = {}
                        x['banhours'] = v['banhours']
                        x['banreason'] = v['banreason']
                        x['bannedby'] = v['bannedby']
                        bannedList[playerName] = x
                    if v['status'] == 'deleted':
                        x = {}
                        x['deletedby'] = v['deletedby']
                        deletedList[playerName] = x
                    if v['status'] == 'disconnected':
                        disconnectList.append(playerName)

                self.client.sendPacket(Identifiers.send.Modopwet_Open, ByteArray().writeByte(cnt).toByteArray() + p.toByteArray())
                for user in disconnectList:
                    self.sendReportStatusDisconnect(user)

                for user in deletedList.keys():
                    self.sendReportStatusDeleted(user, deletedList[user]['deletedby'])

                for user in bannedList.keys():
                    self.sendReportStatusBanned(user, bannedList[user]['banhours'], bannedList[user]['banreason'], bannedList[user]['bannedby'])
                    
        self.sendModoCommunities()
        self.sendReportCommunityCount(communityCount)

    def sortReports(self, sort):  
        if sort:
            return sorted(self.server.modoReports.items(), key=self._sortkey,reverse=True)
        else:
            return sorted(self.server.modoReports.items(), key=lambda x: len(x[1]["reporters"]),reverse=True)

    def updateModoPwet(self):
        for player in self.server.players.values():
            if player.isModoPwetOpened and (player.privLevel >= 8 or player.isPrivMod):
                player.ModoPwet.openModoPwet(True)

    def getPlayerKarma(self, playerName):
        player = self.server.players.get(playerName)
        if player:
            return player.playerKarma
        else:
            return 0

    def getReportType(self, type):
        if type == 0: return "Hack"
        elif type == 1: return "Spam / Flood"
        elif type == 2: return "Insults"
        elif type == 3: return "Phishing"
        return "Other"

    def getRoomMods(self, room):
        s = []
        for player in self.server.players.values():
            if player.roomName == room and (player.privLevel >= 8 or player.isPrivMod):
                s.append(player.playerName)
        return s

# Modopwet packets

    def sendReportCommunityCount(self, info):
        p = ByteArray().writeUnsignedByte(len(info))
    
        for community in info:
            p.writeUTF(community).writeUnsignedByte(info[community])
            
        self.client.sendPacket(Identifiers.send.Modopwet_Reports_Community_Count, p.toByteArray())

    def sendReportResult(self, playerName, handled):
        if handled == 1:
            self.sendReportersKarma(playerName, 0, "", self.client.playerName, "")
            
        self.server.modoReports[playerName]["status"] = "deleted"
        self.server.modoReports[playerName]["deletedby"] = self.client.playerName
        self.updateModoPwet()

    def sendReportRoomMods(self, playerName, mods):
        p = ByteArray()
        p.writeUTF(playerName)
        p.writeUnsignedByte(len(mods))
        for mod in mods:
            p.writeUTF(mod)
        self.client.sendPacket(Identifiers.send.Modopwet_Room_Mods, p.toByteArray())

    def sendReportStatusBanned(self, playerName, banhours, banreason, bannedby, isTranslation=True):
        self.client.sendPacket(Identifiers.send.Modopwet_Banned, ByteArray().writeUTF(playerName).writeBoolean(isTranslation).writeUTF(bannedby).writeInt(int(banhours)).writeUTF(banreason).toByteArray())

    def sendReportStatusDeleted(self, playerName, deletedby):
        self.client.sendPacket(Identifiers.send.Modopwet_Deleted, ByteArray().writeUTF(playerName).writeUTF(deletedby).toByteArray())

    def sendReportStatusDisconnect(self, playerName):
        self.client.sendPacket(Identifiers.send.Modopwet_Disconnected, ByteArray().writeUTF(playerName).toByteArray())

    def sendReportersKarma(self, playerName, hours, reason, modName, state):
        if playerName in self.server.modoReports:
            if state == "mute":
                self.server.modoReports[playerName]["isMuted"] = True
                self.server.modoReports[playerName]["mutedby"] = modName
                self.server.modoReports[playerName]["mutehours"] = int(hours)
                self.server.modoReports[playerName]["mutereason"] = reason
                
            elif state == "ban":
                self.server.modoReports[playerName]["status"] = "banned"
                self.server.modoReports[playerName]["bannedby"] = modName
                self.server.modoReports[playerName]["banhours"] = hours
                self.server.modoReports[playerName]["banreason"] = reason
            self.updateModoPwet()
                
            for name in self.server.modoReports[playerName]["reporters"]:
                player = self.server.players.get(name) 
                if player != None:
                    player.playerKarma += 1
                    player.sendServerMessage(f"Your report regarding the player {playerName} has been handled. (karma: {player.playerKarma})", True)

    def sendModoCommunities(self):
        p = ByteArray().writeShort(len(self.client.modoCommunities))
        for community in self.client.modoCommunities:
            p.writeUTF(community.upper())
        self.client.sendPacket(Identifiers.send.Modopwet_Add_Language, p.toByteArray())

    def sendModoNotification(self, playerName, community, operation, arguments=[]):
        if operation == "disconnect":
            self.server.sendStaffMessage(f"<ROSE>[Modopwet]</ROSE> The player <N>{playerName}</N> from community {community} just left the game.", "PrivMod|Mod|Admin", True, True)
        elif operation == "connect":
            self.server.sendStaffMessage(f"<ROSE>[Modopwet]</ROSE> The player <N>{playerName}</N> from community {community} just connected in the game.", "PrivMod|Mod|Admin", True, True)
        elif operation == "changeroom":
            self.server.sendStaffMessage(f"<ROSE>[Modopwet]</ROSE> The player <BV>{playerName}</BV> (<N>{arguments[0]}</N>) left the room [{arguments[1]}] and came to the room [{arguments[2]}].", "PrivMod|Mod|Admin", True, True)
        elif operation == "report":
            self.server.sendStaffMessage(f"<ROSE>[Modopwet]</ROSE> The player <N>{playerName}</N> from community {community} reported the player {arguments[0]} for <N2>{arguments[1]}</N2>.", "PrivMod|Mod|Admin", True, True)
        elif operation == "reported":
            self.server.sendStaffMessage(f"<ROSE>[Modopwet]</ROSE> The player <BV>{playerName}</BV> has been reported for <N>{arguments[0]}</N> in room [{arguments[1]}].", "PrivMod|Mod|Admin", True, True)

    def sendUpdateModopwet(self, langue, modopwetOnlyPlayerReports, sortBy, reOpen):
        if self.client.modoPwetLangue != langue:
            self.client.modoPwetLangue = langue
            self.client.sendPacket(Identifiers.send.Modopwet_Update_Language, [])
            
        if reOpen:
            self.openModoPwet(self.client.isModoPwetOpened, modopwetOnlyPlayerReports, sortBy)

    def sendWatchPlayer(self, playerName, isFollowing):
        player = self.server.players.get(playerName)
        if player != None:
            if player.roomName != self.client.roomName:
                if isFollowing:
                    self.client.sendEnterRoom(player.roomName, player.playerLangue)
                else:
                    self.client.sendWatchPlayerPacket(playerName, True)
                    self.client.sendEnterRoom(player.roomName, player.playerLangue, True)

    def openChatLog(self, playerName):
        if playerName in self.server.whisperMessages:
            packet = ByteArray().writeUTF(playerName).writeByte(0)
            x = 0
            p = ByteArray()
            for room in self.server.whisperMessages[playerName]:
                if not '#' in room: continue
                x += 1
                p.writeUTF(room).writeByte(len(self.server.whisperMessages[playerName][room]))
                for message in self.server.whisperMessages[playerName][room]:
                    p.writeUTF(message[1]).writeUTF(message[0])
            packet.writeByte(x).writeBytes(p.toByteArray())
            self.client.sendPacket(Identifiers.send.Modopwet_Chat_Log, packet.toByteArray())
        self.client.sendBullePacket(Identifiers.bulle.BU_GetChatLog, self.client.playerID, playerName)
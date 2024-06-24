#coding: utf-8
import re
import time
from collections import deque

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.Langue import Langue
from Utils.Other import Other
from Utils.Time import Time

class Tribulle:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        self.GAME_MODE = 4 # TRANSFORMICE
        self.MAX_AMIS = 250
        self.MAX_NOIRES = 250
        self.MAX_TRIBU_MEMBRES = 500
        self.MAX_TRIBU_MESSAGE = 3600
        self.MAX_CANAL_LEN = 42
        self.MAX_MENSAGE_LEN = 90
        self.TRIBE_RANKS = "0|${trad#TG_0}|0;0|${trad#TG_1}|0;2|${trad#TG_2}|0;3|${trad#TG_3}|0;4|${trad#TG_4}|32;5|${trad#TG_5}|160;6|${trad#TG_6}|416;7|${trad#TG_7}|932;8|${trad#TG_8}|2044;9|${trad#TG_9}|2046"

    def checkTribePermisson(self, permId): # UNFINISHED
        if self.client.tribeRanks == "":
            return False
    
        return True

    def sendTribullePacketWholeChat(self, chatName, code, result, all=False):
        for player in self.server.players.copy().values():
            if player.playerCode != self.client.playerCode or all:
                if player.playerName in self.server.chats[chatName]:
                    player.sendTribullePacket(code, result)

    def sendTribullePacketWholeTribe(self, code, result, all=False):
        for player in self.server.players.copy().values():
            if player.playerCode != self.client.playerCode or all:
                if player.tribeCode == self.client.tribeCode:
                    player.sendTribullePacket(code, result)
             
    def sendPacketWholeTribe(self, identifiers, packet, all=False):
        for player in self.server.players.copy().values():
            if player.playerCode != self.client.playerCode or all:
                if player.tribeCode == self.client.tribeCode:
                    player.sendPacket(identifiers, packet)

    def parseTribulleCodeOld(self, code, packet):
        self.client.Logger.warn(f"[TRIBULLE][OLD]{self.client.ipAddress} The packet {code} was not implemented in the server.\n")

    def parseTribulleCode(self, code, packet):
        if code == Identifiers.tribulle.recv.ST_ChangerDeGenre:
            self.sendChangeGender(packet)
        elif code == Identifiers.tribulle.recv.ST_AjoutAmi:
            self.sendAddFriend(packet)
        elif code == Identifiers.tribulle.recv.ST_RetireAmi:
            self.sendRemoveFriend(packet)
        elif code == Identifiers.tribulle.recv.ST_DemandeEnMariage:
            self.sendMarriageInvitation(packet)
        elif code == Identifiers.tribulle.recv.ST_RepondDemandeEnMariage:
            self.sendMarriageAnswer(packet)
        elif code == Identifiers.tribulle.recv.ST_DemandeDivorce:
            self.sendMarriageDivorce(packet)
        elif code == Identifiers.tribulle.recv.ST_ListeAmis:
            self.sendFriendsList(False, packet)
        elif code == Identifiers.tribulle.recv.ST_FermeeListeAmis:
            self.sendCloseFriendsList(packet)
        elif code == Identifiers.tribulle.recv.ST_AjoutNoire:
            self.sendIgnorePlayer(packet)
        elif code == Identifiers.tribulle.recv.ST_EnvoitMessageCanal:
            self.sendServerChannelMessage(packet)
        elif code == Identifiers.tribulle.recv.ST_EnvoitMessageTribu:
            self.sendTribeChatMessage(packet, False)
        elif code == Identifiers.tribulle.recv.ST_EnvoitMessagePrive:
            self.sendWhisperMessage(packet)
        elif code == Identifiers.tribulle.recv.ST_DefinitModeSilence:
            self.sendDisabledWhisper(packet)
        elif code == Identifiers.tribulle.recv.ST_DemandeMembresCanal:
            self.sendServerChannelMembers(packet)
        elif code == Identifiers.tribulle.recv.ST_RejoindreCanal:
            self.sendCreateServerChannel(packet)
        elif code == Identifiers.tribulle.recv.ST_QuitterCanal:
            self.sendLeaveServerChannel(packet)
        elif code == Identifiers.tribulle.recv.ST_RetireListeNoire:
            self.sendRemoveIgnorePlayer(packet)
        elif code == Identifiers.tribulle.recv.ST_ListeNoire:
            self.sendIgnoredList(packet)
        elif code == Identifiers.tribulle.recv.ST_InviterMembre:
            self.sendTribeInvitation(packet)
        elif code == Identifiers.tribulle.recv.ST_RepondInvitationTribu:
            self.sendTribeInvitationAnswer(packet)
        elif code == Identifiers.tribulle.recv.ST_QuitterTribu:
            self.sendTribeMemberLeave(packet)
        elif code == Identifiers.tribulle.recv.ST_CreerTribu:
            self.sendCreateTribe(packet)
        elif code == Identifiers.tribulle.recv.ST_ChangerMessageJour:
            self.sendChangedTribeMessage(packet)
        elif code == Identifiers.tribulle.recv.ST_ExclureMembre:
            self.sendTribeMemberKicked(packet)
        elif code == Identifiers.tribulle.recv.ST_DemandeInformationsTribu:
            self.sendTribeInfo(packet)
        elif code == Identifiers.tribulle.recv.ST_AffecterRang:
            self.sendTribeRankPlayerChanged(packet)
        elif code == Identifiers.tribulle.recv.ST_FermeeTribu:
            self.sendCloseTribe(packet)
        elif code == Identifiers.tribulle.recv.ST_RenommerRang:
            self.sendTribeRankRename(packet)
        elif code == Identifiers.tribulle.recv.ST_AjouterRang:
            self.sendTribeNewRankCreation(packet)
        elif code == Identifiers.tribulle.recv.ST_ModifierDroitRang:
            self.sendTribeRankChangePermission(packet)
        elif code == Identifiers.tribulle.recv.ST_SupprimerRang:
            self.sendTribeRankDeletion(packet)
        elif code == Identifiers.tribulle.recv.ST_InverserOrdreRangs:
            self.sendTribeRankPositionChanged(packet)
        elif code == Identifiers.tribulle.recv.ST_DesignerChefSpirituel:
            self.sendTribeChangeLeader(packet)
        elif code == Identifiers.tribulle.recv.ST_DissoudreTribu:
            self.sendTribeDissolve(packet)
        elif code == Identifiers.tribulle.recv.ST_ListeHistoriqueTribu:
            self.sendTribeHistorique(packet)
        else:
            self.client.Logger.warn(f"[TRIBULLE][NEW]{self.client.ipAddress} The packet {code} was not implemented in the server.\n")

    # Platform Connection
    def sendPlatformCommunityConnection(self):
        isOnline, friendsOn, friendsOff, isOffline, infos = [], [], [], [], {}
        p = ByteArray().writeShort(3).writeByte(self.client.genderType).writeInt(self.client.playerID)        
        if self.client.playerSoulmate == "":
            p.writeInt(0).writeUTF("").writeByte(0).writeInt(0).writeBoolean(False).writeBoolean(False).writeInt(1).writeUTF("").writeInt(0)
        else:
            player = self.server.players.get(self.client.playerSoulmate)
            if player == None:
                rs = self.server.cursor['users'].find_one({'Username' : self.client.playerSoulmate})
            else:
                rs = {'Soulmate':self.client.playerSoulmate, 'PlayerID': player.playerID, 'PlayerGender': player.genderType, 'LastOn': player.lastOn}
            p.writeInt(rs['PlayerID']).writeUTF(rs['Soulmate']).writeByte(rs['PlayerGender']).writeInt(rs['PlayerID']).writeBoolean(True).writeBoolean(self.server.checkConnectedPlayer(rs['Soulmate'])).writeInt(self.GAME_MODE).writeUTF(player.roomName if player else "").writeInt(rs['LastOn'])
        
        for friend in self.client.friendList.copy():
            player = self.server.players.get(friend)
            if player != None:
                infos[friend] = [player.playerID, ",".join(player.friendList), player.playerSoulmate, player.genderType, player.lastOn]
                isFriend = self.client.playerName in player.friendList
                friendsOn.append(friend) if isFriend else isOnline.append(friend)
            else:
                rs = self.server.cursor['users'].find_one({'Username' : friend})
                infos[rs['Username']] = [rs['PlayerID'], rs['FriendsList'], rs['Soulmate'], rs['PlayerGender'], rs['LastOn']]
                isFriend = self.client.playerName in map(str, filter(None, rs['FriendsList'].split(",")))
                friendsOff.append(rs['Username']) if isFriend else isOffline.append(rs['Username'])
                
        playersNames = friendsOn + isOnline + friendsOff + isOffline
        p.writeShort(len(playersNames))
        for playerName in playersNames:
            info = infos[playerName]
            player = self.server.players.get(playerName)
            isFriend = self.client.playerName in player.friendList if player != None else self.client.playerName in map(str, filter(None, info[1].split(",")))
            genderID = player.genderType if player else int(info[3])
            isMarriage = self.client.playerName == player.playerSoulmate if player else self.client.playerName == info[2]
            p.writeInt(info[0]).writeUTF(playerName).writeByte(genderID).writeInt(info[0]).writeBoolean(isFriend).writeBoolean(self.server.checkConnectedPlayer(playerName)).writeInt(self.GAME_MODE).writeUTF(player.roomName if isFriend and player != None else "").writeInt(info[4] if isFriend else 0)
        
        p.writeShort(len(self.client.ignoredList))
        for playerName in self.client.ignoredList:
            p.writeUTF(playerName)
            
        p.writeUTF(self.client.tribeName)
        p.writeInt(self.client.tribeCode)
        p.writeUTF(self.client.tribeMessage)
        p.writeInt(self.client.tribeHouse)
        if not self.client.tribeRanks == "":
            rankName = self.client.tribeRanks.split(";")[self.client.tribeRank].split("|")
            p.writeUTF(rankName[1])
            p.writeInt(rankName[2])
        else:
            p.writeUTF("")
            p.writeInt(0)
        self.client.sendPacket(Identifiers.send.New_Tribulle, p.toByteArray())

    # Friend list
    def sendAddFriend(self, packet):
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        if playerName == self.client.playerName:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutAmi, ByteArray().writeInt(tribulleID).writeByte(15).toByteArray())
        elif playerName in self.client.friendList:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutAmi, ByteArray().writeInt(tribulleID).writeByte(4).toByteArray())
        elif len(self.client.friendList) >= self.MAX_AMIS:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutAmi, ByteArray().writeInt(tribulleID).writeByte(7).toByteArray())
        elif not self.server.checkAlreadyExistingAccount(playerName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutAmi, ByteArray().writeInt(tribulleID).writeByte(12).toByteArray())
        else:
            player = self.server.players.get(playerName)
            isFriend = self.checkFriend(playerName, self.client.playerName)
            if not player:
                rs = self.server.cursor['users'].find_one({'Username': playerName})
                info = [playerName, rs['PlayerID'], rs['PlayerGender'], rs['LastOn']]
            else:
                info = [playerName, player.playerID, player.genderType, player.lastOn]
            self.client.friendList.append(playerName)
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementAjoutAmi, ByteArray().writeInt(info[1]).writeUTF(playerName).writeByte(info[2]).writeInt(info[1]).writeBoolean(isFriend).writeBoolean(self.server.checkConnectedPlayer(playerName)).writeInt(self.GAME_MODE).writeUTF(player.roomName if isFriend and player != None else "").writeInt(info[3] if isFriend else 0).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutAmi, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
            if player != None:
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeBoolean(True).writeBoolean(self.server.checkConnectedPlayer(self.client.playerName)).writeInt(self.GAME_MODE).writeUTF(self.client.roomName if isFriend else "").writeInt(self.client.lastOn if isFriend else 0).toByteArray())

    def sendCloseFriendsList(self, packet):
        self.client.isFriendListOpen = False
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeFermetureListeAmis, ByteArray().writeBytes(packet.toByteArray()).writeByte(1).toByteArray())

    def sendFriendsList(self, isOpen=False, packet=""):
        isOnline, friendsOn, friendsOff, isOffline, infos = [], [], [], [], {}
        self.client.isFriendListOpen = True
        p = ByteArray().writeShort(Identifiers.tribulle.send.ET_ResultatListeAmis)        
        if self.client.playerSoulmate == "":
            p.writeInt(0).writeUTF("").writeByte(0).writeInt(0).writeByte(0).writeByte(0).writeInt(1).writeUTF("").writeInt(0)
        else:
            player = self.server.players.get(self.client.playerSoulmate)
            if player == None:
                rs = self.server.cursor['users'].find_one({'Username' : self.client.playerSoulmate})
            else:
                rs = {'Soulmate':self.client.playerSoulmate, 'PlayerID': player.playerID, 'PlayerGender': player.genderType, 'LastOn': player.lastOn}
            p.writeInt(rs['PlayerID']).writeUTF(rs['Soulmate']).writeByte(rs['PlayerGender']).writeInt(rs['PlayerID']).writeBoolean(True).writeBoolean(self.server.checkConnectedPlayer(rs['Soulmate'])).writeInt(self.GAME_MODE).writeUTF(player.roomName if player else "").writeInt(rs['LastOn'])
        
        for friend in self.client.friendList.copy():
            player = self.server.players.get(friend)
            if player != None:
                infos[friend] = [player.playerID, ",".join(player.friendList), player.playerSoulmate, player.genderType, player.lastOn]
                isFriend = self.client.playerName in player.friendList
                friendsOn.append(friend) if isFriend else isOnline.append(friend)
            else:
                rs = self.server.cursor['users'].find_one({'Username' : friend})
                infos[rs['Username']] = [rs['PlayerID'], rs['FriendsList'], rs['Soulmate'], rs['PlayerGender'], rs['LastOn']]
                isFriend = self.client.playerName in map(str, filter(None, rs['FriendsList'].split(",")))
                friendsOff.append(rs['Username']) if isFriend else isOffline.append(rs['Username'])
                
        playersNames = friendsOn + isOnline + friendsOff + isOffline
        p.writeShort(len(playersNames))
        for playerName in playersNames:
            info = infos[playerName]
            player = self.server.players.get(playerName)
            isFriend = self.client.playerName in player.friendList if player != None else self.client.playerName in map(str, filter(None, info[1].split(",")))
            genderID = player.genderType if player else int(info[3])
            isMarriage = self.client.playerName == player.playerSoulmate if player else self.client.playerName == info[2]
            p.writeInt(info[0]).writeUTF(playerName).writeByte(genderID).writeInt(info[0]).writeBoolean(isFriend).writeBoolean(self.server.checkConnectedPlayer(playerName)).writeInt(self.GAME_MODE).writeUTF(player.roomName if isFriend and player != None else "").writeInt(info[4] if isFriend else 0)
        
        self.client.sendPacket(Identifiers.send.New_Tribulle, p.toByteArray())
        if self.client.playerSoulmate != "":
            if not isOpen:
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeOuvertureListeAmis, ByteArray().writeInt(packet.readInt()).writeByte(1).toByteArray())

    def sendRemoveFriend(self, packet):
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        player = self.server.players.get(playerName)
        if playerName in self.client.friendList:
            self.client.friendList.remove(playerName)
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementSuppressionAmi, ByteArray().writeInt(self.server.getPlayerID(playerName)).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatSuppressionAmi, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
            if player != None:
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeBoolean(False).writeBoolean(self.server.checkConnectedPlayer(playerName)).writeInt(self.GAME_MODE).writeUTF("").writeInt(0).toByteArray())

    # Friend list Utilities
    def sendFriendConnected(self, playerName):
        player = self.server.players.get(Other.parsePlayerName(playerName))
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(player.playerID).writeUTF(playerName).writeByte(player.genderType).writeInt(player.playerID).writeBoolean(True).writeBoolean(True).writeInt(self.GAME_MODE).writeUTF(player.roomName).writeInt(player.lastOn).toByteArray())
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementConnexionAmi, ByteArray().writeUTF(player.playerName).toByteArray())

    def sendFriendDisconnected(self, playerName):
        player = self.server.players.get(Other.parsePlayerName(playerName))
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(player.playerID).writeUTF(playerName).writeByte(player.genderType).writeInt(player.playerID).writeByte(True).writeByte(False).writeInt(0).writeUTF("").writeInt(player.lastOn).toByteArray())
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementDeconnexionAmi, ByteArray().writeUTF(playerName).toByteArray())

    def sendFriendChangedRoom(self, playerName):
        player = self.server.players.get(Other.parsePlayerName(playerName))
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(player.playerID).writeUTF(player.playerName).writeByte(player.genderType).writeInt(player.playerID).writeBoolean(True).writeBoolean(True).writeInt(self.GAME_MODE).writeUTF(player.roomName).writeInt(player.lastOn).toByteArray())

    # Ignored List
    def sendIgnoredList(self, packet):
        tribulleID = packet.readInt()
        packet = ByteArray().writeInt(tribulleID).writeShort(len(self.client.ignoredList))
        for playerName in self.client.ignoredList:
            packet.writeUTF(playerName)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatConsultationListeNoire, packet.toByteArray())
    
    def sendIgnorePlayer(self, packet):
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        if playerName == self.client.playerName or playerName == self.client.playerSoulmate:
            self.client.sendServerMessage("You can't ignore yourself or your marriaged person.", True)
        elif len(self.client.ignoredList) >= self.MAX_NOIRES:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutListeNoire, ByteArray().writeInt(tribulleID).writeByte(7).toByteArray())
        elif not self.server.checkAlreadyExistingAccount(playerName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutListeNoire, ByteArray().writeInt(tribulleID).writeByte(12).toByteArray())
        elif playerName in self.client.ignoredList:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutListeNoire, ByteArray().writeInt(tribulleID).writeByte(4).toByteArray())
        else:
            self.client.ignoredList.append(playerName)
            if playerName in self.client.friendList:
                self.client.friendList.remove(playerName)
                player = self.server.players.get(playerName)
                if player != None:
                    player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementModificationAmi, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeBoolean(False).writeBoolean(self.server.checkConnectedPlayer(self.client.playerName)).writeInt(self.GAME_MODE).writeUTF("").writeInt(0).toByteArray())
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementSuppressionAmi, ByteArray().writeInt(self.server.getPlayerID(playerName)).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAjoutListeNoire, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        
    def sendRemoveIgnorePlayer(self, packet):
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        self.client.ignoredList.remove(playerName)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatSuppressionListeNoire, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        
    # Player Identification
    def sendChangeGender(self, packet):
        tribulleID, gender = packet.readInt(), packet.readByte()
        self.client.genderType = gender
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatChangementGenre, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleChangementGenre, ByteArray().writeByte(gender).toByteArray())
        for player in self.server.players.copy().values():
            if self.client.playerName in player.friendList and player.playerName in self.client.friendList:
                if player.isFriendListOpen:
                    player.Tribulle.sendFriendsList(True)
        
    # Marriage
    def sendMarriageAnswer(self, packet):
        tribulleID, playerName, answer = packet.readInt(), Other.parsePlayerName(packet.readUTF()), packet.readByte()
        player = self.server.players.get(playerName)
        self.client.marriageInvite = []
        if not self.server.checkAlreadyExistingAccount(playerName) or not self.server.checkConnectedPlayer(playerName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatReponseDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(11).toByteArray())
        else:
            if answer == 0:
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatReponseDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementRefusMariage, ByteArray().writeUTF(self.client.playerName).toByteArray())
            
            elif answer == 1:
                player.playerSoulmate = self.client.playerName
                self.client.playerSoulmate = player.playerName
                
                if not self.client.playerName in player.friendList:
                    player.friendList.append(self.client.playerName)
                    
                if not player.playerName in self.client.friendList:
                    self.client.friendList.append(player.playerName)
                    
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementMariage, ByteArray().writeUTF(player.playerName).toByteArray())
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementMariage, ByteArray().writeUTF(self.client.playerName).toByteArray())

                if self.client.isFriendListOpen:
                    self.sendFriendsList(True)
                    
                if player.isFriendListOpen:
                    player.Tribulle.sendFriendsList(True)

                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatReponseDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())

    def sendMarriageDivorce(self, packet):
        tribulleID = packet.readInt()
        time = Time.getTime() + 3600

        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementDivorce, ByteArray().writeUTF(self.client.playerSoulmate).writeByte(1).toByteArray())
        player = self.server.players.get(self.client.playerSoulmate)
        if player != None:
            player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementDivorce, ByteArray().writeUTF(player.playerSoulmate).writeByte(1).toByteArray())
            player.playerSoulmate = ""
            player.lastDivorceTime = time
        else:
            self.removeMarriage(self.client.playerSoulmate)

        self.client.playerSoulmate = ""
        self.client.lastDivorceTime = time

    def sendMarriageInvitation(self, packet):
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        player = self.server.players.get(playerName)
        if player.playerSoulmate != "":
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(15).toByteArray())
        elif not self.server.checkConnectedPlayer(playerName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(11).toByteArray())
        elif len(player.marriageInvite) > 0:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(6).toByteArray())
        elif not self.server.checkAlreadyExistingAccount(playerName) or (playerName in self.client.ignoredList or self.client.playerName in player.ignoredList):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(4).toByteArray())
        else:
            player.marriageInvite = [self.client.playerName, tribulleID]
            player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementDemandeEnMariage, ByteArray().writeUTF(self.client.playerName).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeEnMariage, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())


    # Tribe
    def sendChangedTribeMessage(self, packet):
        if not self.checkTribePermisson(4):
            return
    
        tribulleID, message = packet.readInt(), packet.readUTF()
        if len(message) > self.MAX_TRIBU_MESSAGE:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatChangerMessageJour, ByteArray().writeInt(tribulleID).writeByte(22).toByteArray())
        else:
            self.server.cursor['tribes'].update_one({'Code' : self.client.tribeCode},{'$set':{'Message': message}})
            self.client.tribeMessage = message
            self.setTribeHistorique(self.client.tribeCode, 6, self.getTime(), message, self.client.playerName)
            self.updateTribeData()
            self.sendTribeInfo()
            self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleChangementMessageJour, ByteArray().writeUTF(self.client.playerName).writeUTF(message).toByteArray(), True)

    def sendCloseTribe(self, packet):
        tribulleID = packet.readInt()
        self.client.isTribeOpened = False
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatFermetureInterfaceTribu, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())

    def sendCreateTribe(self, packet):
        tribulleID, tribeName = packet.readInt(), packet.readUTF()
    
        if self.client.tribeCode != 0 or self.client.tribeName != "": 
            return
            
        if tribeName == "" or not re.match("^[ a-zA-Z0-9]*$", tribeName) or "<" in tribeName or ">" in tribeName:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeCreerTribu, ByteArray().writeInt(tribulleID).writeByte(8).toByteArray())
        elif self.checkExistingTribe(tribeName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeCreerTribu, ByteArray().writeInt(tribulleID).writeByte(9).toByteArray())
        elif self.client.shopCheeses < 500:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeCreerTribu, ByteArray().writeInt(tribulleID).writeByte(14).toByteArray())
        else:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeCreerTribu, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
            creationTime = self.getTime()
            self.server.lastTribeID += 1
            self.server.cursor['tribes'].insert_one({'Code' : self.server.lastTribeID, 'Name': tribeName, 'Message': '', 'HouseMap': 0, 'Ranks': self.TRIBE_RANKS, 'Historique': '', 'Members': self.client.playerName, 'CreationTime': creationTime, 'isModerated': False})
            self.client.shopCheeses -= 500
            self.client.tribeCode = self.server.lastTribeID
            self.client.tribeRank = 9
            self.client.tribeName = tribeName
            self.client.tribeJoined = creationTime
            self.client.tribeMessage = ""
            self.client.tribeRanks = self.TRIBE_RANKS
            self.setTribeHistorique(self.client.tribeCode, 1, creationTime, self.client.playerName, tribeName)
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleInformationsMembreTribu, ByteArray().writeUTF(self.client.tribeName).writeInt(self.client.tribeCode).writeUTF(self.client.tribeMessage).writeInt(0).writeUTF(self.client.tribeRanks.split(";")[9].split("|")[1]).writeInt(2049).toByteArray())

    def sendTribeChangeLeader(self, packet):
        if not self.checkTribePermisson(2046):
            return
    
        tribulleID, playerName = packet.readInt(), packet.readUTF()
        rankInfo = self.client.tribeRanks.split(";")
        self.client.tribeRank = (len(rankInfo)-2)
        player = self.server.players.get(playerName)
        if player:
            player.tribeRank = (len(rankInfo)-1)
            info = [playerName, player.playerID, player.genderType, player.lastOn]
        else:
            self.server.cursor['users'].update_one({'Username': playerName}, {'$set':{'TribeRank':len(rankInfo)-1}})
            rs = self.server.cursor['users'].find_one({'Username': playerName})
            info = [rs['Username'], rs['PlayerID'], rs['PlayerGender'], rs['LastOn']]

        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(info[1]).writeUTF(playerName).writeByte(info[2]).writeInt(info[1]).writeInt(0 if self.server.checkConnectedPlayer(playerName) else info[3]).writeByte(len(rankInfo)-1).writeInt(self.GAME_MODE).writeUTF("" if player == None else player.roomName).toByteArray())
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeInt(0).writeByte(len(rankInfo)-2).writeInt(self.GAME_MODE).writeUTF(self.client.roomName).toByteArray())
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDesignerChef, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        members = self.getTribeMembers(self.client.tribeCode)
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeChatMessage(self, readPacket, isOld=False):
        tribulleID, message = readPacket.readInt(), readPacket.readUTF()
        if isOld:
            self.sendPacketWholeTribe(Identifiers.send.Tribe_Message, ByteArray().writeUTF(self.client.playerName).writeUTF(message).toByteArray(), True)
        else:
            self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignalementMessageTribu, ByteArray().writeUTF(self.client.playerName).writeUTF(message).toByteArray(), True)

    def sendTribeDissolve(self, packet):
        if not self.checkTribePermisson(2046):
            return
    
        tribulleID = packet.readInt()
        members = self.getTribeMembers(self.client.tribeCode)
        self.server.cursor['tribes'].delete_one({'Code':self.client.tribeCode})
        for member in members:
            player = self.server.players.get(member)
            if player:
                player.tribeCode = 0
                player.tribeRank = 0
                player.tribeJoined = 0
                player.tribeHouse = 0
                player.tribeChat = 0
                player.tribeMessage = ""
                player.tribeName = ""
                player.tribeRanks = ""
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleExclusionMembre, ByteArray().writeUTF(player.playerName).writeUTF(self.client.playerName).toByteArray())
            else:
                self.server.cursor['users'].update_one({'Username':member},{'$set':{'TribeCode':0,'TribeRank':0,'TribeJoined':0}})
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeDissoudreTribu, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())

    def sendTribeHistorique(self, packet):
        tribulleID = packet.readInt()
        historique = self.getTribeHistorique(self.client.tribeCode).split("|")
        
        p = ByteArray()
        p.writeInt(tribulleID)
        p.writeShort(len(historique) - 1 if historique == [''] else len(historique))
        for event in historique:
            event = event.split("/")
            if not historique == [''] and not event[1] == '':
                p.writeInt(event[1])
                p.writeInt(event[0])
                if int(event[0]) == 8:
                    p.writeUTF('{"code":"%s","auteur":"%s"}' % (event[3], event[2]))
                elif int(event[0]) == 6:
                    p.writeUTF('{"message":"%s","auteur":"%s"}' % (event[2], event[3]))
                elif int(event[0]) == 5:
                    p.writeUTF('{"cible":"%s","ordreRang":"%s","rang":"%s","auteur":"%s"}' % (event[2], event[3], event[4], event[5]))
                elif int(event[0]) == 4:
                    p.writeUTF('{"membreParti":"%s","auteur":"%s"}' % (event[2], event[2]))
                elif int(event[0]) == 3:
                    p.writeUTF('{"membreExclu":"%s","auteur":"%s"}' % (event[2], event[3]))
                elif int(event[0]) == 2:
                    p.writeUTF('{"membreAjoute":"%s","auteur":"%s"}' % (event[3], event[2]))
                elif int(event[0]) == 1:
                    p.writeUTF('{"tribu":"%s","auteur":"%s"}' % (event[3], event[2]))

        p.writeInt(len(historique))
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatHistoriqueTribu, p.toByteArray())
    
    def sendTribeInfo(self, packet=""):
        if packet != "":
            tribulleID, connected = packet.readInt(), packet.readByte()
        else:
            tribulleID = 0
            connected = 0
    
        if self.client.tribeName == "":
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatOuvertureInterfaceTribu, ByteArray().writeInt(tribulleID).writeByte(17).toByteArray())
        else:
            isOnline, isOffline, infos = [], [], {}
            members = self.getTribeMembers(self.client.tribeCode)
            packet = ByteArray()
            packet.writeInt(self.client.tribeCode)
            packet.writeUTF(self.client.tribeName)
            packet.writeUTF(self.client.tribeMessage)
            packet.writeInt(self.client.tribeHouse)
            for member in members:
                player = self.server.players.get(member)
                if player != None:
                    infos[member] = [player.playerID, player.genderType, player.lastOn, player.tribeRank, player.tribeJoined]
                    isOnline.append(member)
                else:
                    rs = self.server.cursor['users'].find_one({'Username':member})
                    infos[member] = [rs['PlayerID'], rs['PlayerGender'], rs['LastOn'], rs['TribeRank'], rs['TribeJoined']]
                    isOffline.append(member)

            if connected == 0:
                playersTribe = isOnline
            else:
                playersTribe = isOnline + isOffline

            packet.writeShort(len(playersTribe))
            for member in playersTribe:
                info = infos[member]
                player = self.server.players.get(member)
                packet.writeInt(info[0])
                packet.writeUTF(member)
                packet.writeByte(info[1])
                packet.writeInt(info[0])
                packet.writeInt(info[2] if not self.server.checkConnectedPlayer(member) else 0)
                packet.writeByte(info[3])
                packet.writeInt(4)
                packet.writeUTF(player.roomName if player != None else "")

            packet.writeShort(len(self.client.tribeRanks.split(";")))
            for rank in self.client.tribeRanks.split(";"):
                ranks = rank.split("|")
                packet.writeUTF(ranks[1]).writeInt(ranks[2])

            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleChangementParametresTribu, packet.toByteArray())
            self.client.isTribeOpened = True

    def sendTribeInvitation(self, packet):
        if not self.checkTribePermisson(32):
            return
    
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())

        player = self.server.players.get(playerName)
        if len(self.getTribeMembers(self.client.tribeCode)) >= self.MAX_TRIBU_MEMBRES:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(7).toByteArray())
        elif playerName.startswith("*") or player == None or playerName == self.client.playerName:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(11).toByteArray())
        elif player.tribeName != "":
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(18).toByteArray())
        elif len(player.tribeInvite) > 0:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(6).toByteArray())
        else:
            player.tribeInvite = [tribulleID, self.client]
            player.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleInvitationTribu, ByteArray().writeUTF(self.client.playerName).writeUTF(self.client.tribeName).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())

    def sendTribeInvitationAnswer(self, readPacket):
        tribulleID, playerName, answer = readPacket.readInt(), readPacket.readUTF(), readPacket.readByte()
        resultTribulleID = int(self.client.tribeInvite[0])
        player = self.client.tribeInvite[1]
        self.client.tribeInvite = []

        if self.client.tribeCode > 0:
           self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRepondsInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(18).toByteArray())
        elif len(self.getTribeMembers(player.tribeCode)) >= self.MAX_TRIBU_MEMBRES:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRepondsInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(7).toByteArray())
        elif player == None:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRepondsInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(4).toByteArray())
        else:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRepondsInvitationTribu, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
            if answer == 0:
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleReponseInvitationTribu, ByteArray().writeUTF(self.client.playerName).writeByte(0).toByteArray())
            elif answer == 1:
                members = self.getTribeMembers(player.tribeCode)
                members.append(self.client.playerName)
                self.setTribeMembers(player.tribeCode, members)

                self.client.tribeCode = player.tribeCode
                self.client.tribeRank = 0
                self.client.tribeName = player.tribeName
                self.client.tribeJoined = self.getTime()
                tribeInfo = self.getTribeInfo(self.client.tribeCode)
                self.client.tribeName = str(tribeInfo[0])
                self.client.tribeMessage = str(tribeInfo[1])
                self.client.tribeHouse = int(tribeInfo[2])
                self.client.tribeRanks = tribeInfo[3]

                self.setTribeHistorique(self.client.tribeCode, 2, self.getTime(), player.playerName, self.client.playerName)

                packet = ByteArray()
                packet.writeUTF(self.client.tribeName)
                packet.writeInt(self.client.tribeCode)
                packet.writeUTF(self.client.tribeMessage)
                packet.writeInt(self.client.tribeHouse)

                rankInfo = self.client.tribeRanks.split(";")
                rankName = rankInfo[self.client.tribeRank].split("|")
                packet.writeUTF(rankName[1])
                packet.writeInt(rankName[2])
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleInformationsMembreTribu, packet.toByteArray())
                player.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleReponseInvitationTribu, ByteArray().writeUTF(self.client.playerName).writeByte(1).toByteArray())
                self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleNouveauMembre, ByteArray().writeUTF(self.client.playerName).toByteArray(), True)
                for member in members:
                    player = self.server.players.get(member)
                    if player != None:
                        if player.isTribeOpened:
                            player.Tribulle.sendTribeInfo()

    def sendTribeNewRankCreation(self, packet):
        if not self.checkTribePermisson(8):
            return
    
        tribulleID, rankName = packet.readInt(), packet.readUTF()
        ranksID = self.client.tribeRanks.split(";")
        s = ranksID[1]
        f = ranksID[1:]
        f = ";".join(map(str, f))
        s = "%s|%s|%s" % ("0", rankName, "0")
        del ranksID[1:]
        ranksID.append(s)
        ranksID.append(f)
        self.client.tribeRanks = ";".join(map(str, ranksID))
        members = self.getTribeMembers(self.client.tribeCode)
        for playerName in members:
            player = self.server.players.get(playerName)
            tribeRank = self.getPlayerTribeRank(playerName)
            if player != None:
                if player.tribeRank >= 1:
                    player.tribeRank += 1
            else:
                if tribeRank >= 1:
                    self.server.cursor['users'].update_one({'Username':playerName}, {'$set':{'TribeRank':tribeRank+1}})

        self.setTribeRanks()
        self.updateTribeData()
        self.sendTribeInfo()
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeMemberKicked(self, packet):
        if not self.checkTribePermisson(64):
            return
    
        tribulleID, playerName = packet.readInt(), Other.parsePlayerName(packet.readUTF())
        p = ByteArray().writeInt(tribulleID)
        player = self.server.players.get(playerName)
        tribeCode = player.tribeCode if player != None else self.getPlayerTribeCode(playerName)

        if tribeCode != 0:
            p.writeByte(1)
            members = self.getTribeMembers(self.client.tribeCode)
            if playerName in members:
                members.remove(playerName)
                self.setTribeMembers(self.client.tribeCode, members)
                self.setTribeHistorique(self.client.tribeCode, 3, self.getTime(), playerName, self.client.playerName)
                self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleExclusionMembre, ByteArray().writeUTF(playerName).writeUTF(self.client.playerName).toByteArray(), True)
                if player != None:
                    player.tribeCode = 0
                    player.tribeName = ""
                    player.tribeRank = 0
                    player.tribeJoined = 0
                    player.tribeHouse = 0
                    player.tribeMessage = ""
                    player.tribeRanks = ""
                else:
                    self.server.cursor['users'].update_one({'Username':playerName},{'$set':{'TribeRank':0,'TribeCode':0,'TribeJoined':0}})

            members = self.getTribeMembers(self.client.tribeCode)
            for member in members:
                player = self.server.players.get(member)
                if player != None:
                    if player.isTribeOpened:
                        player.Tribulle.sendTribeInfo()
                        
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatExclureMembre, p.toByteArray())

    def sendTribeMemberLeave(self, packet):
        tribulleID = packet.readInt()
        p = ByteArray().writeInt(tribulleID)

        if self.client.tribeRank == (len(self.client.tribeRanks.split(";")) - 1):
            p.writeByte(4)
        else:
            p.writeByte(1)
            self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleDepartMembre, ByteArray().writeUTF(self.client.playerName).toByteArray(), True)
            members = self.getTribeMembers(self.client.tribeCode)
            if self.client.playerName in members:
                members.remove(self.client.playerName)
                self.setTribeMembers(self.client.tribeCode, members)
                self.setTribeHistorique(self.client.tribeCode, 4, self.getTime(), self.client.playerName)
                
                self.client.tribeCode = 0
                self.client.tribeName = ""
                self.client.tribeRank = 0
                self.client.tribeJoined = 0
                self.client.tribeHouse = 0
                self.client.tribeMessage = ""
                self.client.tribeRanks = ""
            for member in members:
                player = self.server.players.get(member)
                if player != None:
                    if player.isTribeOpened:
                        player.Tribulle.sendTribeInfo()
                        
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDemandeQuitterTribu, p.toByteArray())

    def sendTribeRankChangePermission(self, packet):
        if not self.checkTribePermisson(8):
            return
    
        tribulleID, rankID, permID, type = packet.readInt(), packet.readByte(), packet.readInt(), packet.readByte()
        rankInfo = self.client.tribeRanks.split(";")
        perms = rankInfo[rankID].split("|")
        suma = 0
        if type == 0:
            suma = int(perms[2]) + 2**permID
        elif type == 1:
            suma = int(perms[2]) - 2**permID
        perms[2] = str(suma)
        print(suma)
        join = "|".join(map(str, perms))
        rankInfo[rankID] = join
        self.client.tribeRanks = ";".join(map(str, rankInfo))
        self.setTribeRanks()
        self.updateTribeData()
        self.sendTribeInfo()
        members = self.getTribeMembers(self.client.tribeCode)
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeRankDeletion(self, readPacket):
        if not self.checkTribePermisson(8):
            return
    
        tribulleID, rankID = readPacket.readInt(), readPacket.readByte()

        rankInfo = self.client.tribeRanks.split(";")
        del rankInfo[rankID]
        self.client.tribeRanks = ";".join(map(str, rankInfo))

        self.setTribeRanks()
        self.updateTribeData()

        members = self.getTribeMembers(self.client.tribeCode)
        for playerName in members:
            player = self.server.players.get(playerName)
            if player != None:
                if player.tribeRank == rankID:
                    player.tribeRank = 0
                    break
            else:
                tribeRank = self.getPlayerTribeRank(playerName)
                if tribeRank == rankID:
                    self.server.cursor['users'].update_one({'Username':playerName},{'$set':{'TribeRank':0}})
                    break
                    
        for playerName in members:
            player = self.server.players.get(playerName)
            tribeRank = self.getPlayerTribeRank(playerName)
            if player != None:
                if player.tribeRank >= 1:
                    player.tribeRank -= 1
            else:
                if tribeRank >= 1:
                    self.server.cursor['users'].update_one({'Username':playerName},{'$set':{'TribeRank':tribeRank-1}})
                    
        self.sendTribeInfo()
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeRankPlayerChanged(self, packet):
        if not self.checkTribePermisson(16):
            return
    
        tribulleID, playerName, rankID = packet.readInt(), Other.parsePlayerName(packet.readUTF()), packet.readByte()
        rankInfo = self.client.tribeRanks.split(";")
        rankName = rankInfo[rankID].split("|")[1]
        player = self.server.players.get(playerName)
        if player == None:
            rs = self.server.cursor['users'].find_one({'Username':playerName})
            info = [rs['Username'], rs['PlayerID'], rs['PlayerGender'], rs['LastOn']]
            self.server.cursor['users'].update_one({'Username':playerName}, {'$set':{'TribeRank':rankID}})
        else:
            info = [playerName, player.playerID, player.genderType, player.lastOn]
            player.tribeRank = rankID

        self.setTribeHistorique(self.client.tribeCode, 5, self.getTime(), playerName, str(rankID), rankName, self.client.playerName)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(info[1]).writeUTF(playerName).writeByte(info[2]).writeInt(info[1]).writeInt(0 if self.server.checkConnectedPlayer(playerName) else info[3]).writeByte(rankID).writeInt(1).writeUTF("" if player == None else player.roomName).toByteArray())
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleChangementRang, ByteArray().writeUTF(self.client.playerName).writeUTF(playerName).writeUTF(rankName).toByteArray(), True)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatAffecterRang, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        members = self.getTribeMembers(self.client.tribeCode)
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeRankPositionChanged(self, packet):
        if not self.checkTribePermisson(8):
            return
    
        tribulleID, rankID, rankID2 = packet.readInt(), packet.readByte(), packet.readByte()

        ranks = self.client.tribeRanks.split(";")
        rank = ranks[rankID]
        rank2 = ranks[rankID2]
        ranks[rankID] = rank2
        ranks[rankID2] = rank
        self.client.tribeRanks = ";".join(map(str, ranks))
        self.setTribeRanks()
        self.updateTribeData()
        up = (rankID2 > rankID)
        down = (rankID > rankID2)
        members = self.getTribeMembers(self.client.tribeCode)
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.tribeRank == rankID:
                    player.tribeRank = rankID2
                if up:
                    if player.tribeRank == rankID2:
                        player.tribeRank -= 1
                if down:
                    if player.tribeRank == rankID2:
                        player.tribeRank += 1
            else:
                rankPlayer = self.server.cursor['users'].find_one({'Username':member})['TribeRank']

                if rankPlayer == rankID:
                    self.server.cursor['users'].update_one({'Username':member},{'$set':{'TribeRank':rankID2}})
                if up:
                    if rankPlayer == rankID2:
                        self.server.cursor['users'].update_one({'Username':member},{'$set':{'TribeRank':rankID2-1}})
                if down:
                    if rankPlayer == rankID2:
                        self.server.cursor['users'].update_one({'Username':member},{'$set':{'TribeRank':rankID2+1}})

        self.setTribeRanks()
        self.updateTribeData()
        self.sendTribeInfo()
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()

    def sendTribeRankRename(self, packet):
        if not self.checkTribePermisson(8):
            return
    
        tribulleID, rankID, rankName = packet.readInt(), packet.readByte(), packet.readUTF()
        rankInfo = self.client.tribeRanks.split(";")
        rank = rankInfo[rankID].split("|")
        rank[1] = rankName
        rankInfo[rankID] = "|".join(map(str, rank))
        self.client.tribeRanks = ";".join(map(str, rankInfo))
        self.setTribeRanks()
        self.updateTribeData()
        self.sendTribeInfo()
        members = self.getTribeMembers(self.client.tribeCode)
        for member in members:
            player = self.server.players.get(member)
            if player != None:
                if player.isTribeOpened:
                    player.Tribulle.sendTribeInfo()



    # Tribe Utilities
    def sendTribeMemberConnected(self):
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleConnexionMembre, ByteArray().writeUTF(self.client.playerName).toByteArray(), True)
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeInt(0).writeByte(self.client.tribeRank).writeInt(self.GAME_MODE).writeUTF("").toByteArray())
        
    def sendTribeMemberDisconnected(self):
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleDeconnexionMembre, ByteArray().writeUTF(self.client.playerName).toByteArray(), False)
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName).writeByte(self.client.genderType).writeInt(self.client.playerID).writeInt(self.client.lastOn).writeByte(self.client.tribeRank).writeInt(1).writeUTF("").toByteArray())
        
    def sendTribeMemberChangeRoom(self):
        self.sendTribullePacketWholeTribe(Identifiers.tribulle.send.ET_SignaleChangementParametresMembre, ByteArray().writeInt(self.client.playerID).writeUTF(self.client.playerName.lower()).writeByte(self.client.genderType).writeInt(self.client.playerID).writeInt(0).writeByte(self.client.tribeRank).writeInt(self.GAME_MODE).writeUTF(self.client.roomName).toByteArray())
        
        
    # Chat
    def sendCreateServerChannel(self, packet):
        tribulleID, chatName = packet.readInt(), packet.readUTF()

        if chatName in self.server.chats and len(self.server.chats[chatName]) >= self.MAX_CANAL_LEN:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRejoindreCanalPublique, ByteArray().writeInt(tribulleID).writeByte(7).toByteArray())
            return
        if re.match("^(%s=^(%s:(%s!.*_$).)*$)(%s=^(%s:(%s!_{2,}).)*$)[A-Za-z][A-Za-z0-9_]{2,11}$", chatName):
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRejoindreCanalPublique, ByteArray().writeInt(tribulleID).writeByte(8).toByteArray())
            return
        else:
            if chatName in self.server.chats and not self.client.playerName in self.server.chats[chatName]:
                self.server.chats[chatName].append(self.client.playerName)
            elif not chatName in self.server.chats:
                self.server.chats[chatName] = [self.client.playerName]

            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementRejoindreCanalPublique, ByteArray().writeUTF(chatName).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatRejoindreCanalPublique, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
            
    def sendServerChannelMembers(self, packet):
        tribulleID, chatName = packet.readInt(), packet.readUTF()
        p = ByteArray().writeInt(tribulleID).writeByte(1).writeShort(len(self.server.chats[chatName]))

        for player in self.server.players.copy().values():
            if self.client.playerName in self.server.chats[chatName]:
                p.writeUTF(player.playerName)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatListerCanalPublique, p.toByteArray())

    def sendLeaveServerChannel(self, packet):
        tribulleID, chatName = packet.readInt(), packet.readUTF()
        if self.client.playerName in self.server.chats[chatName]:
            self.server.chats[chatName].remove(self.client.playerName)
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementQuitterCanalPublique, ByteArray().writeUTF(chatName).toByteArray())
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatQuitterCanalPublique, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())

    def sendServerChannelMessage(self, packet):
        tribulleID, chatName, message = packet.readInt(), packet.readUTF(), packet.readUTF()
        self.sendTribullePacketWholeChat(chatName, Identifiers.tribulle.send.ET_SignalementMessageChat, ByteArray().writeUTF(self.client.playerName.lower()).writeInt(Langue.getLangueID(self.client.playerLangue.upper()) + 1).writeUTF(chatName).writeUTF(message).toByteArray(), True)
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessageChat, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        
    # Whisper (Private) messages
    def sendWhisperMessage(self, packet):
        tribulleID, playerName, message = packet.readInt(), Other.parsePlayerName(packet.readUTF()), packet.readUTF().replace("\n", "").replace("&amp;#", "&#").replace("<", "&lt;")
        isCheck = self.server.checkMessage(message)
        if self.client.isGuest:
            self.client.sendLangueMessage("", "$Créer_Compte_Parler")
            return

        packet = ByteArray().writeInt(tribulleID)
        if playerName.startswith("*") or not playerName in self.server.players:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(12).writeShort(0).toByteArray())
            return
            
        if playerName in self.client.ignoredList:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(27).writeShort(0).toByteArray())
            return
            
        if len(message) > self.MAX_MENSAGE_LEN:
            self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(22).writeShort(0).toByteArray())
            return
                
        if self.client.isMuted:
            muteInfo = self.server.getTempPunishmentInfo(self.client.playerName, 0)
            timeCalc = self.client.isMutedHours #Time.getHoursDiff(muteInfo[1])
            if timeCalc <= 0:
                self.server.removeModMute(self.client.playerName)
            else:
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(23).writeShort(0).toByteArray())
                return
                                                
        else:
            player = self.server.players.get(playerName)
            if player != None:
                if player.silenceType != 0:
                    if self.client.privLevel < 8 and (player.silenceType != 1 or not self.checkFriend(playerName, self.client.playerName)):
                        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(25).writeUTF(player.silenceMessage).toByteArray())
                        return
                        
                if self.client.playerTime < 3600 * 5 and not (self.client.playerName in player.friendList and not playerName == self.client.playerName):
                    self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatEnvoiMessagePrive, packet.writeByte(28).writeShort(0).toByteArray())
                    return
                        
                if not (self.client.playerName in player.ignoredList) and not isCheck and playerName != self.client.playerName:
                    player.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementMessagePrive, ByteArray().writeUTF(self.client.playerName).writeInt(Langue.getLangueID(self.client.playerLangue)+1).writeUTF(player.playerName).writeUTF(message).toByteArray())
                self.client.sendTribullePacket(Identifiers.tribulle.send.ET_SignalementMessagePrive, ByteArray().writeUTF(self.client.playerName).writeInt(Langue.getLangueID(player.playerLangue)+1).writeUTF(player.playerName).writeUTF(message).toByteArray())
    
                if not self.client.playerName in self.server.whisperMessages:
                     messages = deque([], 60)
                     messages.append([time.strftime("%Y/%m/%d %H:%M:%S"), message, self.client.roomName])
                     self.server.whisperMessages[self.client.playerName] = {}
                     self.server.whisperMessages[self.client.playerName][player.playerName] = messages
                elif not player.playerName in self.server.whisperMessages[self.client.playerName]:
                    messages = deque([], 60)
                    messages.append([time.strftime("%Y/%m/%d %H:%M:%S"), message, self.client.roomName])
                    self.server.whisperMessages[self.client.playerName][player.playerName] = messages
                else:
                     self.server.whisperMessages[self.client.playerName].append([time.strftime("%Y/%m/%d %H:%M:%S"), message, self.client.roomName])
    
    def sendDisabledWhisper(self, packet):
        tribulleID, type, message = packet.readInt(), packet.readByte(), packet.readUTF()
        self.client.sendTribullePacket(Identifiers.tribulle.send.ET_ResultatDefinirModeSilence, ByteArray().writeInt(tribulleID).writeByte(1).toByteArray())
        self.client.silenceType = type
        self.client.silenceMessage = "" if self.server.checkMessage(message) else message
        
    # Helper functions
    def checkExistingTribe(self, tribeName) -> bool:
        return self.server.cursor['tribes'].find_one({'Name':tribeName}) != None
    
    def checkFriend(self, playerName, playerNameToCheck) -> bool:
        if playerName in self.server.players:
            return playerNameToCheck in self.server.players[playerName].friendList
        else:
            rs = self.server.cursor['users'].find_one({'Username': playerName})['FriendsList'].split(",")
            return playerNameToCheck in rs

    def getPlayerTribeCode(self, playerName) -> int:
        player = self.server.players.get(playerName)
        if playerName:
            return player.tribeCode
        else:
            rs = self.Cursor['users'].find_one({'Username':playerName})
            return rs['TribeCode'] if rs else 0

    def getPlayerTribeRank(self, playerName) -> int:
        if playerName in self.server.players:
            return self.server.players[playerName].tribeRank
        else:
            rs = self.server.cursor['users'].find_one({'Username':playerName})
            return rs['TribeRank'] if rs else 0

    def getTime(self) -> int:
        return int(time.time() // 60)
        
    def getTribeHistorique(self, tribeCode) -> str:
        rs = self.server.cursor['tribes'].find_one({'Code':tribeCode})
        return rs['Historique'] if rs else ""
            
    def getTribeInfo(self, tribeCode):
        rs = self.server.cursor['tribes'].find_one({'Code':tribeCode})
        if rs:
            return [rs['Name'], rs['Message'], rs['HouseMap'], rs['Ranks'], tribeCode]
        else:
            return ["", "", 0, self.TRIBE_RANKS, 0]
            
    def getTribeMembers(self, tribeCode) -> list:
        rs = self.server.cursor['tribes'].find_one({'Code':tribeCode})
        return list(map(str, filter(None, rs['Members'].split(",")))) if rs else []
        
    def setTribeCache(self, tribeCode, historique) -> None:
        self.server.cursor['tribes'].update_one({'Code':tribeCode},{'$set':{'Historique':historique}})

    def setTribeHistorique(self, tribeCode, *data) -> None:
        historique = self.getTribeHistorique(tribeCode)
        if historique == "":
            historique = "/".join(map(str, data))
        else:
            historique = "/".join(map(str, data)) + "|" + historique
        
        self.setTribeCache(tribeCode, historique)
        
    def setTribeMembers(self, tribeCode, members) -> None:
        self.server.cursor['tribes'].update_one({'Code':tribeCode},{'$set':{'Members':",".join(map(str, [member for member in members]))}})
             
    def setTribeRanks(self) -> None:
        self.server.cursor['tribes'].update_one({'Code':self.client.tribeCode},{'$set':{'Ranks':self.client.tribeRanks}})
        

    # Other functions
    def updateTribeData(self):
        for player in self.server.players.copy().values():
            if player.tribeCode == self.client.tribeCode:
                player.tribeHouse = self.client.tribeHouse
                player.tribeMessage = self.client.tribeMessage
                player.tribeRanks = self.client.tribeRanks
    
    def removeMarriage(self, playerName):
        self.server.cursor['users'].update_one({'Username': playerName}, {'$set':{'Soulmate': ''}})
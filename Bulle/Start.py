#coding: utf-8
import sys
sys.dont_write_bytecode = True
#sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(sys.argv[0]))))

import asyncio
import base64
import socket
import sqlite3
import threading
import os

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils import Config
from Utils.Time import Time

# Other
from Protocol import *
from Room import Room

class Bulle:
    def __init__(self):
        # Config
        self.bulleInfo = Config.Json().load_file("./Include/bulle.json")
        self.mapsInfo = Config.Json().load_file("./Include/map_db.json")
        self.chatEmojies = Config.Json().load_file("./Include/chat_emoji.json", encoding='utf-8')
    
        # Boolean
        self.isDebug = False
    
        # Dictionary
        self.bulle_players = {}
        self.bulle_verification = {}
        self.bulle_rooms = {}
        self.chatMessages = {}
        self.cachedmaps = {}
        self.vanillaMaps = {}
    
        # Loops
        self.loop = asyncio.get_event_loop()

        # Logger
        self.Logger = Logger()
        
        # Other
        self.CursorMaps = None
        
    # Socket functions
    def send_packet(self, data):
        data += b';'
        while data:
            n = self.main_server_socket.send(data)    
            data = data[n:]
        
    def connect_to_main_server(self):
        try:
            self.main_server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.main_server_socket.connect((self.bulleInfo['main_ip'], self.bulleInfo['main_port']))
            self.send_packet(self.bulleInfo['bulle_auth_code'].encode())
            self.Logger.info(f"Connected to main server at {self.bulleInfo['main_ip']}:{self.bulleInfo['main_port']}.\n")
            threading.Thread(target=self.receive_from_main_server).start()
        except socket.error as e:
            self.Logger.error(f"Failed to connect to main server: {e}.\n")
            sys.exit(1)
        
    def receive_from_main_server(self):
        while True:
            try:
                data = self.main_server_socket.recv(1024)
                if data:
                    more = data.count(b';') > 1
                    if more: 
                        packets = data.split(b';')
                        for i in packets:
                            self.handle_data(i)
                    else:    
                        self.handle_data(data.decode()[:-1])
            except socket.error as e:
                self.Logger.error(f"Error receiving data from main server: {e}.\n")
                break

    def handle_data(self, data):
        if data == b'':
            return
    
        code = int(data[:4])
        args = str(data[4:]).split('|')
        
        if code == Identifiers.bulle.BU_InitConnection:
            self.main_server_socket.send(b'1000')
                        
        elif code == Identifiers.bulle.BU_ConnectToGivenRoom:
            playerID = int(args[0])
            playerName = args[1]
            playerCode = int(args[2])
            playerLangue = args[3]
            playerLook = args[4]
            staffRoles = args[5]
            isMuted = bool(args[6] == 'True')
            playerGender = int(args[7])
            roomName = args[8]
            isHidden = bool(args[9] == 'True')
            isReported = bool(args[10] == 'True')
            titleNumber = int(args[11])
            titleStars = int(args[12])
            isMutedHours = int(args[13])
            isMutedReason = args[14]
            shamanType = int(args[15])
            shamanLevel = int(args[16])
            shamanItems = args[17]
            shamanBadge = int(args[18])
            shamanColor = args[19]
            verification_code = int(args[20])
            self.bulle_verification[verification_code] = [playerName, playerCode, playerLangue, playerLook, staffRoles, isMuted, playerGender, roomName, isHidden, isReported, titleNumber, titleStars, isMutedHours, isMutedReason, shamanType, shamanLevel, shamanItems, shamanBadge, shamanColor]
                
        elif code == Identifiers.bulle.BU_SendAnimZelda:
            playerID = int(args[0])
            playerCode = int(args[1])
            type = int(args[2])
            item = int(args[3])
            id = int(args[4])
            case = args[5]
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                packet = ByteArray().writeInt(playerCode).writeByte(type)
                if type == 7:
                    packet.writeUTF(case).writeUnsignedByte(id)
                elif type == 5:
                    packet.writeUTF(case)
                else:
                    packet.writeInt(item)
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Anim_Zelda, packet.toByteArray())
                
        elif code == Identifiers.bulle.BU_SendMuMute:
            playerID = int(args[0])
            isMumuted = bool(args[1] == 'True')
            if playerID in self.bulle_players:
                self.bulle_players[playerID].isMumuted = isMumuted
        
        elif code == Identifiers.bulle.BU_SendModerationMesage:
            playerID = int(args[0])
            info = args[1].replace("b'", '').replace("'", '')
            msg = base64.b64decode(info.encode() + b'=' * (-len(info) % 4)).decode()
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Send_Staff_Chat_Message, ByteArray().writeByte(0).writeUTF("").writeUTF(msg).writeShort(0).writeByte(0).toByteArray())
        
        elif code == Identifiers.bulle.BU_LoadMapEditor_Map:
            mapCode = int(args[0])
            playerID = int(args[1])
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                client = self.bulle_players[playerID]
                client.room.CursorMaps.execute("select * from Maps where Code = ?", [mapCode])
                rs = client.room.CursorMaps.fetchone()
                if rs:
                    if client.playerName == rs["Name"] or client.checkStaffPermission(["MC", "Admin", "Owner"]):
                        client.sendPacket(Identifiers.old.send.Load_Map, [rs["XML"], rs["YesVotes"], rs["NoVotes"], rs["Perma"]])
                        client.room.editeurMapXML = rs["XML"]
                        client.room.editeurMapCode = mapCode
                    else:
                        client.sendPacket(Identifiers.old.send.Load_Map_Result, [])
                else:
                    client.sendPacket(Identifiers.old.send.Load_Map_Result, [])
        
        elif code == Identifiers.bulle.BU_DrawingClear:
            roomName = args[0]
            if roomName in self.bulle_rooms:
                self.bulle_rooms[roomName].sendAll(Identifiers.old.send.Drawing_Clear, [])
                
        elif code == Identifiers.bulle.BU_DrawingPoint:
            roomName = args[0]
            info = args[1].replace("b'", '').replace("'", '')
            playerID = int(args[2])
            if info == b'': values = []
            else: values = list(map(int, base64.b64decode(info.encode() + b'=' * (-len(info) % 4)).decode().split(',')))
            if roomName in self.bulle_rooms:
                self.bulle_rooms[roomName].sendAllOthers(self.bulle_players[playerID], Identifiers.old.send.Drawing_Point, values)
                
        elif code == Identifiers.bulle.BU_DrawingStart:
            roomName = args[0]
            info = args[1].replace("b'", '').replace("'", '')
            playerID = int(args[2])
            
            if info == b'': values = []
            else: values = list(map(int, base64.b64decode(info.encode() + b'=' * (-len(info) % 4)).decode().split(',')))
            if roomName in self.bulle_rooms:
                self.bulle_rooms[roomName].sendAllOthers(self.bulle_players[playerID], Identifiers.old.send.Drawing_Start, values)
        
        elif code == Identifiers.bulle.BU_GetChatLog:
            playerID = int(args[0])
            playerName = args[1]
            if playerID in self.bulle_players:
                if playerName in self.chatMessages:
                    packet = ByteArray()
                    x = 0
                    for room in self.chatMessages[playerName]:
                        if '#' in room: continue
                        for message in self.chatMessages[playerName][room]:
                            x+=1
                            packet.writeUTF(room).writeUTF(message[1]).writeUTF(message[0])
                    packet = ByteArray().writeUTF(playerName).writeByte(x).writeBytes(packet.toByteArray()).writeByte(0)
                    self.bulle_players[playerID].sendPacket(Identifiers.send.Modopwet_Chat_Log, packet.toByteArray())
        
        elif code == Identifiers.bulle.BU_ReceiveTitleID:
            playerID = int(args[0])
            titleID = int(args[1])
            titleStars = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].titleNumber = titleID
                self.bulle_players[playerID].titleStars = titleStars

        elif code == Identifiers.bulle.BU_RespawnPlayer:
            playerID = int(args[0])
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                self.bulle_players[playerID].room.respawnSpecific(self.bulle_players[playerID].playerName)
                
        elif code == Identifiers.bulle.BU_ChangeRoomTime:
            playerID = int(args[0])
            time = int(args[1])
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                roomClient = self.bulle_players[playerID].room
                for player in roomClient.players.copy().values():
                    player.sendRoundTime(time)
                    
                roomClient.changeMapTimers(time)
                
        elif code == Identifiers.bulle.BU_Clear_Room_Chat:
            playerID = int(args[0])
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Message, ByteArray().writeUTF("\n" * 10000).toByteArray())
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Message, ByteArray().writeUTF(f"The chat was cleared by {self.bulle_players[playerID].playerName}").toByteArray())
        
        elif code == Identifiers.bulle.BU_SendRoomCreator:
            playerID = int(args[0])
            if playerID in self.bulle_players and self.bulle_players[playerID].room != None:
                self.bulle_players[playerID].sendServerMessage(f"Room [<J>{self.bulle_players[playerID].room.roomName}</J>]'s creator: <BV>{self.bulle_players[playerID].room.roomCreator}</BV>", True)
        
        elif code == Identifiers.bulle.BU_SendBanMessage:
            roomName = args[0]
            playerID = int(args[1])
            playerName = args[2]
            hours = args[3]
            msg_type = args[4]
            reason = args[5:]
            if msg_type == "$MessageBanDefSalon":
                hours = reason
                reason = ""
            
            if roomName in self.bulle_rooms:
                for player in self.bulle_rooms[roomName].players.copy().values():
                    player.sendLangueMessage("", f"<ROSE>• [Moderation] {msg_type}", playerName, hours, ' '.join(reason))
        
        elif code == Identifiers.bulle.BU_SendMute:
            playerID = int(args[0])
            isMuted = bool(args[1] == 'True')
            hours = int(args[2])
            reason = args[3:]
            if playerID in self.bulle_players:
                self.bulle_players[playerID].isMuted = isMuted
                if not isMuted:
                    self.bulle_players[playerID].isMutedHours = 0
                    self.bulle_players[playerID].isMutedReason = ""
                else:
                    self.bulle_players[playerID].isMutedHours = Time.getTime() + (int(hours) * 3600)
                    self.bulle_players[playerID].isMutedReason = reason
        
        elif code == Identifiers.bulle.BU_DeleteMap:
            self.CursorMaps.execute("delete from Maps where Code = ?", [mapCode])
        
        elif code == Identifiers.bulle.BU_ChangePlayerLook:
            playerID = int(args[0])
            playerLook = base64.b64decode(args[1].encode()).decode('utf-8')
            playerMouseColor = args[2]
            if playerID in self.bulle_players:
                self.bulle_players[playerID].playerLook = playerLook
                self.bulle_players[playerID].mouseColor = playerMouseColor
        
        elif code == Identifiers.bulle.BU_ChangeShamanLook:
            playerID = int(args[0])
            shamanLook = base64.b64decode(args[1].encode()).decode('utf-8')
            if playerID in self.bulle_players:
                self.bulle_players[playerID].shamanItems = shamanLook
        
        elif code == Identifiers.bulle.BU_Interrupt_Connection:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].transport.close()
        
        elif code == Identifiers.bulle.BU_ChangeShamanBadge:
            playerID = int(args[0])
            badge = int(args[1])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].equipedShamanBadge = badge
        
        elif code == Identifiers.bulle.BU_ChangeShamanType:
            playerID = int(args[0])
            _id = int(args[1])
            withoutSkills = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].shamanType = _id
                self.bulle_players[playerID].isUsingShamanSkills = withoutSkills
        
        elif code == Identifiers.bulle.BU_ChangeShamanColor:
            playerID = int(args[0])
            color = args[1]
            if playerID in self.bulle_players:
                self.bulle_players[playerID].shamanColor = color
        
        elif code == Identifiers.bulle.BU_ManageFunCorpRoom:
            roomName = args[0]
            if roomName in self.bulle_rooms:
                self.bulle_rooms[roomName].sendRoomFunCorp()
        
        elif code == Identifiers.bulle.BU_FunCorpGiveTransformationPowers:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                    if players == ['*']:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            player.sendPacket(Identifiers.send.Can_Transformation, 1 if option == "set" else 0)
                            player.hasFunCorpTransformations = (True if option == "set" else False)
                            
                        if option == "set":
                            self.bulle_players[playerID].sendServerMessage("Transformations powers given to all players in the room.", True)
                        else:
                            self.bulle_players[playerID].sendServerMessage("All the transformations powers have been removed.", True)
                    else:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.playerName in players:
                                player.sendPacket(Identifiers.send.Can_Transformation, 1 if option == "set" else 0)
                                player.hasFunCorpTransformations = (True if option == "set" else False)
                                
                        if option == "set":
                            self.bulle_players[playerID].sendServerMessage(f"Transformations powers given to players: <BV>{', '.join(map(str, players))}</BV>", True)
                        else:
                            players.remove("off")
                            self.bulle_players[playerID].sendServerMessage(f"Transformations powers removed to players: <BV>{', '.join(map(str, players))}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_FunCorpGiveMeepPowers:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                    if players == ['*']:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            player.sendPacket(Identifiers.send.Can_Meep, 1 if option == "set" else 0)
                            player.canMeep = (True if option == "set" else False)
                            
                        if option == "set":
                            self.bulle_players[playerID].sendServerMessage("Meep powers given to all players in the room.", True)
                        else:
                            self.bulle_players[playerID].sendServerMessage("All the meep powers have been removed.", True)
                    else:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.playerName in players:
                                player.sendPacket(Identifiers.send.Can_Meep, 1 if option == "set" else 0)
                                player.canMeep = (True if option == "set" else False)
                                
                        if option == "set":
                            self.bulle_players[playerID].sendServerMessage(f"Meep powers given to players: <BV>{', '.join(map(str, players))}</BV>", True)
                        else:
                            players.remove("off")
                            self.bulle_players[playerID].sendServerMessage(f"Meep powers removed from players: <BV>{', '.join(map(str, players))}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_FunCorpRoomEvent: # UNFINISHED
            roomName = args[0]
            playerID = int(args[1])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp:
                    self.bulle_rooms[roomName].isMarkFuncorpRoom = not self.bulle_rooms[roomName].isMarkFuncorpRoom
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_FunCorpChangePlayerSize:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if not option.isdigit() and not option == "off":
                self.bulle_players[playerID].sendServerMessage("Invalid size", True)
            else:
                if roomName in self.bulle_rooms:
                    if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                        if players == ['*']:
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                self.bulle_rooms[roomName].sendAll(Identifiers.send.Mouse_Size, ByteArray().writeInt(player.playerCode).writeUnsignedShort(100 if option == "off" else int(option)).writeBoolean(False).toByteArray())

                            if option != "off":
                                self.bulle_players[playerID].sendServerMessage(f"All players now have the same size: <BV>{option}</BV>.", True)
                            else:
                                self.bulle_players[playerID].sendServerMessage("All players now have their regular size.", True)
                        else:
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                if player.playerName in players:
                                    self.bulle_rooms[roomName].sendAll(Identifiers.send.Mouse_Size, ByteArray().writeInt(player.playerCode).writeUnsignedShort(100 if option == "off" else int(option)).writeBoolean(False).toByteArray())
                                    
                            if option != "off":
                                players.remove(option)
                                self.bulle_players[playerID].sendServerMessage(f"The following players now have the size {option}: <BV>{', '.join(map(str, players))}</BV>", True)
                            else:
                                players.remove("off")
                                self.bulle_players[playerID].sendServerMessage(f"The following players now have their regular size: <BV>{', '.join(map(str, players))}</BV>", True)
                    else:
                        self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_FunCorpLinkMices:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                    if players == ['*']:
                        _list = []
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            _list.append(player.playerCode)
                            
                        for info in _list:
                            self.bulle_rooms[roomName].sendAll(Identifiers.send.Soulmate, ByteArray().writeBoolean(bool(option != "off")).writeInt(info).writeInt(_list[-1]).toByteArray())
                            
                        if option == "":
                            self.bulle_players[playerID].sendServerMessage("All the players are now linked.", True)
                        else:
                            self.bulle_players[playerID].sendServerMessage("All the links have been removed.", True)
                    else:
                        _list = []
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.playerName in players:
                                _list.append(player.playerCode)
                                
                        for info in _list:
                            self.bulle_rooms[roomName].sendAll(Identifiers.send.Soulmate, ByteArray().writeBoolean(bool(option != "off")).writeInt(info).writeInt(_list[-1]).toByteArray())
                            
                        if option == "":
                            self.bulle_players[playerID].sendServerMessage(f"The following players are now linked: <BV>{', '.join(map(str, players))}</BV>", True)
                        else:
                            players.remove("off")
                            self.bulle_players[playerID].sendServerMessage(f"The links involving the following players have been removed: <BV>{', '.join(map(str, players))}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_ChangeRoomMaximumPlayers:
            roomName = args[0]
            players = int(args[1])
            isFunCorp = bool(args[2] == 'True')
            playerID = int(args[3])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isFunCorp:
                    self.bulle_rooms[roomName].maximumPlayers = players
                    self.bulle_players[playerID].sendServerMessage(f"Maximum number of players in the room is set to: <BV>{players}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        else:
            self.Logger.warn(f"Unregisted packet id {code} with data {args}.\n")

    def ConnectMAPDatabase(self):
        db = sqlite3.connect("./Database/Maps.db", check_same_thread=False)
        db.text_factory = str
        db.isolation_level = None
        db.row_factory = sqlite3.Row
        cursor = db.cursor()
        cursor.execute("select * from maps")
        self.Logger.info(f"Loaded total {len(cursor.fetchall())} maps.\n")
        return cursor
    
    def LoadVanillaMaps(self):
        cnt = 0
        for fileName in os.listdir("./Include/maps/vanilla/"):
            with open("./Include/maps/vanilla/"+fileName) as f:
                self.vanillaMaps[int(fileName[:-4])] = f.read()
            cnt += 1
        self.Logger.info(f"Loaded {cnt} total vanilla maps.\n")
            
    def Main(self):
        self.CursorMaps = self.ConnectMAPDatabase()
        self.LoadVanillaMaps()
        self.isDebug = self.bulleInfo["debug"]
        self.connect_to_main_server()

        try:
            for port in self.bulleInfo["port"]:
                self.loop.run_until_complete(self.loop.create_server(lambda: BulleProtocol(self, self.CursorMaps), self.bulleInfo["ip_address"], port))
            self.Logger.info(f"Bulle connected on {self.bulleInfo['ip_address']}:{'-'.join(map(str, self.bulleInfo['port']))}.\n")
        except OSError:
            self.Logger.error("The bulle is already running.\n")
            return
        
        self.loop.run_forever()
        
    # Bulle Functions
    async def addClientToRoom(self, player, roomName):
        if roomName in self.bulle_rooms:
            return self.bulle_rooms[roomName].addClient(player)
        else:
            room = Room(player, roomName, player.playerName)
            self.bulle_rooms[roomName] = room
            room.addClient(player, True)
            await room.mapChange()
            
    def getRecommendedRoom(self, langue, prefix="") -> str:
        count = 1
        result = ""
        while result == "":
            roomName = f"{langue}-{count}" if prefix == "" else f"{langue}-{prefix}{count}"
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].getPlayerCount() < 30:
                    result = str(count)
            else:
                result = str(count)
            count += 1
            
        if not prefix:
            result = f"{langue}-{result}"
        else:
            result = f"{langue}-{prefix}{result}"
        
        return result
        
    def getShamanBadge(self, playerCode):
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.Skills.getShamanBadge()
        return 0

    def getShamanLevel(self, playerCode):
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.shamanLevel
        return 0

    def getShamanType(self, playerCode): 
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.shamanType
        return 0
        
        
        
        
    def checkMessage(self, message):
        return False
        
if __name__ == "__main__":
    _Bulle = Bulle()
    _Bulle.Main()

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
        self.npcs = Config.Json().load_file("./Include/npcs.json")
    
        # Boolean
        self.isDebug = False
        
        # Dictionary
        self.bulle_players = {}
        self.bulle_verification = {}
        self.bulle_rooms = {}
        self.chatMessages = {}
        self.cachedmaps = {}
        self.vanillaMaps = {}
        self.eventMaps = {}
    
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
            petType = int(args[20])
            petEnd = int(args[21])
            furType = int(args[22])
            furEnd = int(args[23])
            mapCheeses = int(args[24])
            shopCheeses = int(args[25])
            cheeseCount = int(args[26])
            playerSkills = args[27]
            verification_code = int(args[28])
            self.bulle_verification[verification_code] = [playerName, playerCode, playerLangue, playerLook, staffRoles, isMuted, playerGender, roomName, isHidden, isReported, titleNumber, titleStars, isMutedHours, isMutedReason, shamanType, shamanLevel, shamanItems, shamanBadge, shamanColor, petType, petEnd, furType, furEnd, mapCheeses, shopCheeses, cheeseCount, playerSkills]
                
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
                    if players == ['']:
                        info = 0
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.hasFunCorpTransformations:
                                info += 1
                    
                        self.bulle_players[playerID].sendServerMessage(f"Players with transformations: <BV>{info}</BV>", True)
                
                    elif players == ['*']:
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
                    if players == ['']:
                        info = 0
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.canMeep:
                                info += 1
                    
                        self.bulle_players[playerID].sendServerMessage(f"Players with meep: <BV>{info}</BV>", True)
                
                    elif players == ['*']:
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
                
        elif code == Identifiers.bulle.BU_SendPlayerPet:
            roomName = args[0]
            playerID = int(args[1])
            petType = int(args[2])
            petEnd = int(args[3])
            playerCode = int(args[4])
            if playerID in self.bulle_players:
                if not self.bulle_players[playerID].isDead:
                    self.bulle_players[playerID].petType = petType
                    self.bulle_players[playerID].petEnd = petEnd
                
                    if roomName in self.bulle_rooms:
                        self.bulle_rooms[roomName].sendAll(Identifiers.send.Pet, ByteArray().writeInt(playerCode).writeByte(petType).toByteArray())
                        
        elif code == Identifiers.bulle.BU_SendPlayerFur:
            playerID = int(args[0])
            furType = int(args[1])
            furEnd = int(args[2])
            if playerID in self.bulle_players:
                if not self.bulle_players[playerID].isDead:
                    self.bulle_players[playerID].furType = furType
                    self.bulle_players[playerID].furEnd = furEnd
        
        elif code == Identifiers.bulle.BU_SendPlayerPencil:
            playerID = int(args[0])
            pencilColor = int(args[1])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].drawingColor = pencilColor
                self.bulle_players[playerID].sendPacket(Identifiers.send.Crazzy_Packet, ByteArray().writeByte(1).writeShort(650).writeInt(pencilColor).toByteArray())
        
        elif code == Identifiers.bulle.BU_SendTrowableObject:
            playerID = int(args[0])
            objectCode = int(args[1])
            consumableID = int(args[2])
            if playerID in self.bulle_players:
                if objectCode == 11:
                    self.bulle_players[playerID].room.objectID += 2
                self.bulle_players[playerID].sendPlaceObject(self.bulle_players[playerID].room.objectID if consumableID == 11 else 0, objectCode, self.bulle_players[playerID].posX + 28 if self.bulle_players[playerID].isFacingRight else self.bulle_players[playerID].posX - 28, self.bulle_players[playerID].posY, 0, 0 if consumableID == 11 or objectCode in [24, 63] else 10 if self.bulle_players[playerID].isFacingRight else 40, 50, True, True)
       
        elif code == Identifiers.bulle.BU_SendPlayerEmote:
            playerID = int(args[0])
            emoteID= int(args[1])
            flag = args[2]
            others = bool(args[3] == 'True')
            if playerID in self.bulle_players:
                self.bulle_players[playerID].sendPlayerEmote(emoteID, flag, others, False)
                
        elif code == Identifiers.bulle.BU_SendBallonBadge:
            playerID = int(args[0])
            playerCode = int(args[1])
            code = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Baloon_Badge, ByteArray().writeInt(playerCode).writeShort(code).toByteArray())
                
        elif code == Identifiers.bulle.BU_SendPlayerPlayedTime:
            playerID = int(args[0])
            playerCode = int(args[1])
            _local1 = int(args[2]) # days
            _local2 = int(args[3]) # hours ?
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Crazzy_Packet, ByteArray().writeByte(5).writeInt(playerCode).writeShort(_local1).writeByte(_local2).toByteArray())
        
        elif code == Identifiers.bulle.BU_SendPlayerCheeses:
            playerID = int(args[0])
            playerCode = int(args[1])
            _local1 = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Crazzy_Packet, ByteArray().writeByte(4).writeInt(playerCode).writeInt(_local1).toByteArray())
       
        elif code == Identifiers.bulle.BU_SendPlayerMicrophone:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].sendPlayerEmote(20, "", False, False)
                if len(self.bulle_players[playerID].room.players) > 5:
                    for player in self.bulle_players[playerID].room.players.copy().values():
                        if player != self.bulle_players[playerID]:
                            if player.posX >= self.bulle_players[playerID].posX - 400 and player.posX <= self.bulle_players[playerID].posX + 400:
                                if player.posY >= self.bulle_players[playerID].posY - 300 and player.posY <= self.bulle_players[playerID].posY + 300:
                                    player.sendPlayerEmote(6, "", False, False)
       
        elif code == Identifiers.bulle.BU_SendPlayerBonfire:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].Skills.sendBonfireSkill(self.bulle_players[playerID].posX, self.bulle_players[playerID].posY, 15)
        
        elif code == Identifiers.bulle.BU_UseInventoryConsumable:
            playerID = int(args[0])
            playerCode = int(args[1])
            _id = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Use_Inventory_Consumable, ByteArray().writeInt(playerCode).writeShort(_id).toByteArray())
        
        elif code == Identifiers.bulle.BU_SendMistletoe:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                if len(self.bulle_players[playerID].room.players) > 5:
                    for player in self.bulle_players[playerID].room.players.copy().values():
                        if player != self.bulle_players[playerID]:
                            if player.posX >= self.bulle_players[playerID].posX - 400 and player.posX <= self.bulle_players[playerID].posX + 400:
                                if player.posY >= self.bulle_players[playerID].posY - 300 and player.posY <= self.bulle_players[playerID].posY + 300:
                                    player.sendPlayerEmote(3, "", False, False)
        
        elif code == Identifiers.bulle.BU_SendShopBadge:
            playerID = int(args[0])
            item2 = int(args[1])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].sendUnlockedBadge(item2)
        
        elif code == Identifiers.bulle.BU_SendUnlockTitle:
            playerID = int(args[0])
            title_id = int(args[1])
            stars = int(args[2])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].sendUnlockedTitle(title_id, stars)
        
        elif code == Identifiers.bulle.BU_Change_Map:
            roomName = args[0]
            mapChange = args[1]
            fakeMap = args[2]
            command_name = args[3]
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if command_name == "np":
                    if mapChange == '0':
                        self.loop.create_task(self.bulle_rooms[roomName].mapChange())
                    else:
                        mapInfo = self.bulle_rooms[roomName].getMapInfo(int(mapChange[1:]) if mapChange.startswith('@') else int(mapChange))
                        if mapInfo[0] != '':
                            if fakeMap != '0':
                                self.bulle_rooms[roomName].forceNextMap = fakeMap
                                #self.bulle_rooms[roomName].forceNextMapCode = mapChange
                            else:
                                self.bulle_rooms[roomName].forceNextMap = mapChange
                            if self.bulle_rooms[roomName].changeMapTimer != None:
                                try:self.bulle_rooms[roomName].changeMapTimer.cancel()
                                except:self.bulle_rooms[roomName].changeMapTimer = None
                            self.loop.create_task(self.bulle_rooms[roomName].mapChange())
                        else:
                            self.bulle_players[playerID].sendLangueMessage("", "$CarteIntrouvable")
                else:
                    mapInfo = self.bulle_rooms[roomName].getMapInfo(int(mapChange[1:]) if mapChange.startswith('@') else int(mapChange))
                    if mapInfo[0] == '':
                        self.bulle_players[playerID].sendLangueMessage("", "$CarteIntrouvable")
                    else:
                        self.bulle_rooms[roomName].forceNextMap = f"{mapChange}"
                        self.bulle_players[playerID].sendLangueMessage("", f"$ProchaineCarte : Vanilla - {mapChange}")
        
        elif code == Identifiers.bulle.BU_FunCorpChangeMouseColor:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                    if players == ['']:
                        info = 0
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.tempMouseColor != "":
                                info += 1
                        self.bulle_players[playerID].sendServerMessage(f"Colored furs: <BV>{info}</BV>", True)
                        
                    elif players == ['*']:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            player.tempMouseColor = str(option)
                            
                        if option != "off":
                            option = int(option)
                            self.bulle_players[playerID].sendServerMessage(f"All the players now have the fur color <font color='#{hex(option)[2:]}'>{hex(option)}</font>.", True)
                        else:
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                if player.tempMouseColor != "":
                                    player.tempMouseColor = ""
                            self.bulle_players[playerID].sendServerMessage("All the fur colors have been removed.", True)
                    else:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.playerName in players:
                                player.tempMouseColor = str(option)
                                
                        if option != "off":
                            players.remove(option)
                            option = int(option)
                            self.bulle_players[playerID].sendServerMessage(f"New fur color (<font color='#{hex(option)[2:]}'>{hex(option)}</font>) for players: <BV>{', '.join(map(str, players))}</BV>", True)
                        else:
                            players.remove("off")
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                if player.playerName in players:
                                    player.tempMouseColor = ""
                            self.bulle_players[playerID].sendServerMessage(f"Fur colors removed from players: <BV>{', '.join(map(str, players))}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_FunCorpChangeNickColor:
            roomName = args[0]
            players = list(map(str, base64.b64decode(args[1].encode() + b'=' * (-len(args[1]) % 4)).decode().split(',')))
            option = args[2]
            isSkippingFunCorpRoom = bool(args[3] == 'True')
            playerID = int(args[4])
            if roomName in self.bulle_rooms:
                if self.bulle_rooms[roomName].isFuncorp or isSkippingFunCorpRoom:
                    if players == ['']:
                        info = 0
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.tempNickColor != "":
                                info += 1
                        self.bulle_players[playerID].sendServerMessage(f"Colored nicknames: <BV>{info}</BV>", True)
                        
                    elif players == ['*']:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            player.tempNickColor = str(option)
                            
                        if option != "off":
                            option = int(option)
                            self.bulle_players[playerID].sendServerMessage(f"All the players now have the nickname color <font color='#{hex(option)[2:]}'>{hex(option)}</font>.", True)
                        else:
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                if player.tempNickColor != "":
                                    player.tempNickColor = ""
                            self.bulle_players[playerID].sendServerMessage("All the nickname colors have been removed.", True)
                    else:
                        for player in self.bulle_rooms[roomName].players.copy().values():
                            if player.playerName in players:
                                player.tempNickColor = str(option)
                                
                        if option != "off":
                            players.remove(option)
                            option = int(option)
                            self.bulle_players[playerID].sendServerMessage(f"New nickname color (<font color='#{hex(option)[2:]}'>{hex(option)}</font>) for players: <BV>{', '.join(map(str, players))}</BV>", True)
                        else:
                            players.remove("off")
                            for player in self.bulle_rooms[roomName].players.copy().values():
                                if player.playerName in players:
                                    player.tempNickColor = ""
                            self.bulle_players[playerID].sendServerMessage(f"Nickname colors removed from players: <BV>{', '.join(map(str, players))}</BV>", True)
                else:
                    self.bulle_players[playerID].sendServerMessage("FunCorp commands only work when the room is in FunCorp mode.", True)
        
        elif code == Identifiers.bulle.BU_ReceiveBulleInformation:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].sendServerMessage(f"[bulle{self.bulleInfo['id']}] {len(self.bulle_players)} / {len(self.bulle_rooms)} rooms", True)
        
        elif code == Identifiers.bulle.BU_UpdateShamanSkill:
            playerID = int(args[0])
            if playerID in self.bulle_players:
                for skill in list(map(str, filter(None, base64.b64decode(args[1]).decode('utf-8').split(";")))):
                    values = skill.split(":")
                    self.bulle_players[playerID].playerSkills[int(values[0])] = int(values[1])
        
        elif code == Identifiers.bulle.BU_AnimPacket:
            playerID = int(args[0])
            anim = args[1]
            frame = int(args[2])
            playerCode = int(args[3])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Add_Anim, ByteArray().writeInt(playerCode).writeUTF(anim).writeShort(frame).toByteArray())
                
        elif code == Identifiers.bulle.BU_FramePacket:
            playerID = int(args[0])
            frame = args[1]
            xPosition = int(args[2])
            yPosition = int(args[3])
            playerCode = int(args[4])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Add_Frame, ByteArray().writeInt(playerCode).writeUTF(frame).writeInt(xPosition).writeInt(yPosition).toByteArray())
        
        elif code == Identifiers.bulle.BU_ChangePlayerScore:
            playerID = int(args[0])
            score = int(args[1])
            if playerID in self.bulle_players:
                self.bulle_players[playerID].playerScore = score
                self.bulle_players[playerID].room.sendAll(Identifiers.send.Set_Player_Score, ByteArray().writeInt(self.bulle_players[playerID].playerCode).writeShort(self.bulle_players[playerID].playerScore).toByteArray())
        
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
    
    def LoadEventMaps(self):
        cnt = 0
        for fileName in os.listdir("./Include/maps/event/"):
            with open("./Include/maps/event/"+fileName) as f:
                self.eventMaps[fileName[:-4]] = f.read()
            cnt += 1
        self.Logger.info(f"Loaded {cnt} total event maps.\n")
    
    def LoadVanillaMaps(self):
        cnt = 0
        for fileName in os.listdir("./Include/maps/vanilla/"):
            with open("./Include/maps/vanilla/"+fileName) as f:
                self.vanillaMaps[int(fileName[:-4])] = f.read()
            cnt += 1
        self.Logger.info(f"Loaded {cnt} total vanilla maps.\n")
                    
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
        
    def getShamanBadge(self, playerCode) -> int:
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.Skills.getShamanBadge()
        return 0

    def getShamanLevel(self, playerCode) -> int:
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.shamanLevel
        return 0

    def getShamanNoSkillChallenge(self, playerCode) -> bool:
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.isNoSkill
        return False

    def getShamanType(self, playerCode) -> int: 
        for player in self.bulle_players.copy().values():
            if player.playerCode == playerCode:
                return player.shamanType
        return 0
        
        
    def checkMessage(self, message) -> bool:
        return False
        
        
    def Main(self):
        self.CursorMaps = self.ConnectMAPDatabase()
        self.LoadEventMaps()
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
        
        
if __name__ == "__main__":
    _Bulle = Bulle()
    _Bulle.Main()

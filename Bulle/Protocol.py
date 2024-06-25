#coding: utf-8
import asyncio
import zlib

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers
from Modules.Packets import Packets
from Modules.Commands import Commands
from Modules.Utilities import Utilities
from Modules.Skills import Skills

# Utils
from Utils.Logger import Logger
from Utils.Time import Time

class BulleProtocol(asyncio.Protocol):
    def __init__(self, _server, _cursor):
        self.Logger = Logger()
        self.clientPacket = ByteArray()
        self.server = _server
        self.cursor = _cursor
        
        # Bulle
        self.bulle_id = 0
        
        # Integer
        self.bubblesCount = 0
        self.cheeseCount = 0
        self.defilantePoints = 0
        self.equipedShamanBadge = 0
        self.furType = 0
        self.furEnd = 0
        self.iceCount = 0
        self.isMutedHours = 0
        self.playerCode = 0
        self.playerID = 0
        self.playerScore = 0
        self.posX = 0
        self.posY = 0
        self.playerGender = 0
        self.playerStartTimeMillis = 0
        self.shamanType = 0
        self.shamanLevel = 0
        self.velocityX = 0
        self.velocityY = 0
        self.petType = 0
        self.petEnd = 0
        self.PInfo = 0
        self.titleNumber = 0
        self.titleStars = 0
                
        # Boolean
        self.canMeep = False
        self.desintegration = False
        self.hasLuaTransformations = False
        self.hasFunCorpTransformations = False
        self.isAfk = False
        self.isClosed = False
        self.isDead = False
        self.isFacingRight = False
        self.isFacingLeft = False
        self.isJumping = False
        self.isMovingRight = False
        self.isMovingLeft = False
        self.isMumuted = False
        self.isMuted = False
        self.isNoSkill = False
        self.isShaman = False
        self.isHidden = False
        self.isNewPlayer = False
        self.isReported = False
        self.isVampire = False
        self.isUsedTotem = False
        self.resetTotem = False
        
        # String
        self.ipAddress = ""
        self.isMutedReason = ""
        self.tempPlayerName = ""
        self.playerLangue = ""
        self.playerName = ""
        self.playerLook = "1;0,0,0,0,0,0,0,0,0,0,0,0"
        self.roomName = ""
        self.shamanColor = "95d9d6"
        self.lastMessage = ""
        self.lastRoomName = ""
        self.mouseColor = "78583a"
        self.shamanItems = ""
        self.tempMouseColor = ""
        self.tempNickColor = ""
        
        # List
        self.mulodromePos = []
        self.staffRoles = []
        self.tempTotem = [0, ""]
        self.totemInfo = [0, ""]
        
        # Dictionary
        
        # Nonetype
        self.awakeTimer = None
        self.transport = None
        self.room = None
        self.killafktimer = None
        self.skipMusicTimer = None
        
        # Loop
        self.loop = asyncio.get_event_loop()
        
    def getnewlen(self,b):
        var_2068 = 0
        var_2053 = 0
        var_176 = b
        while var_2053 < 10:
            var_56 = var_176.readByte() & 255
            var_2068 = var_2068 | (var_56 & 127) << 7 * var_2053
            var_2053 += 1
            if not ((var_56 & 128) == 128 and var_2053 < 5):
                return var_2068+1, var_2053
        
    def connection_made(self, transport):
        self.transport = transport
        self.ipAddress = transport.get_extra_info("peername")[0]
        
        self.Packets = Packets(self)
        self.ParseCommands = Commands(self)
        self.Utilities = Utilities(self)
        self.Skills = Skills(self)
        
    def data_received(self, packet: bytes) -> None:
        if self.isClosed:
            return

        self.clientPacket.write(packet)
        old_packet = self.clientPacket.copy()
        while self.clientPacket.getLength() > 0:
            packet_length, lenlen = self.getnewlen(self.clientPacket)
            if self.clientPacket.getLength() >= packet_length:
                read = ByteArray(self.clientPacket._bytes[:packet_length])
                old_packet._bytes = old_packet._bytes[packet_length:]
                self.clientPacket._bytes = self.clientPacket._bytes[packet_length:]
                self.loop.create_task(self.parsePacket(read))
            else:
                self.clientPacket = old_packet
                
    def connection_lost(self, *args) -> None:
        self.isClosed = True
    
        if self.room != None:
            self.server.loop.create_task(self.room.removeClient(self))
            
        if self.playerID in self.server.bulle_players:
            del self.server.bulle_players[self.playerID]
    
        self.transport.close()
                
    async def parsePacket(self, packet) -> None:
        if self.isClosed:
            return
            
        packet_id, C, CC = packet.readByte(), packet.readByte(), packet.readByte()
        self.lastPacketID = packet_id
        await self.Packets.parsePacket(packet_id, C, CC, packet)
        
    def sendPacket(self, identifiers, data=b''): 
        self.loop.create_task(self._sendPacket(identifiers, data))

    async def _sendPacket(self, identifiers, data=b''):
        if self.isClosed:
            return

        if isinstance(data, list):
            data = ByteArray().writeUTF(chr(1).join(map(str, ["".join(map(chr, identifiers))] + data))).toByteArray()
            identifiers = [1, 1]

        elif isinstance(data, int):
            data = chr(data)

        if isinstance(data, str):
            data = data.encode(encoding='Windows-1252')

        self.lastPacketID = (self.lastPacketID + 1) % 255
        packet = ByteArray()
        length = len(data) + 2
        packet2 = ByteArray()
        calc1 = length >> 7
        while calc1 != 0:
            packet2.writeByte(((length & 127) | 128))
            length = calc1
            calc1 = calc1 >> 7
        packet2.writeByte((length & 127))
        
        packet.writeBytes(packet2.toByteArray()).writeByte(identifiers[0]).writeByte(identifiers[1]).writeBytes(data)
        self.transport.write(packet.toByteArray())
                
    def checkStaffPermission(self, levels):
        return any(element in self.staffRoles for element in levels)
        
        
    # Client Packets
    def sendEnterRoom(self):
        found = False
        rooms = self.roomName[3:]
        count = "".join(i for i in rooms if i.isdigit())
        for room in ["vanilla", "survivor", "racing", "music", "bootcamp", "defilante", "village"]:
            if rooms.startswith(room) and not count == "" or rooms.isdigit():
                found = not (int(count) < 1 or int(count) > 1000000000 or rooms == room)
        self.sendPacket(Identifiers.send.Enter_Room, ByteArray().writeBoolean(found).writeUTF(self.roomName).writeUTF("int" if self.roomName.startswith("*") else self.playerLangue).toByteArray())

    def sendEmotion(self, emotion):
        self.room.sendAllOthers(self, Identifiers.send.Emotion, ByteArray().writeInt(self.playerCode).writeShort(emotion).toByteArray())

    def sendGameType(self, gameType, serverType):
        self.sendPacket(Identifiers.send.Room_Type, gameType)
        self.sendPacket(Identifiers.send.Room_Server, serverType)

    async def sendGiveCheese(self, cheeseX, cheeseY, x, distance):        
        self.room.canChangeMap = False
        if not self.cheeseCount > 0 or (not self.room.isRacing and not self.room.isBootcamp and not self.room.isSurvivor and not self.room.isDefilante):
            self.room.sendAll(Identifiers.send.Collectible_Action, ByteArray().writeByte(3).writeInt(self.playerCode).toByteArray())
            self.room.sendAll(Identifiers.send.Collectible_Action, ByteArray().writeByte(2).writeInt(self.playerCode).writeUTF(f"x_transformice/x_aventure/x_recoltables/x_{59 + self.cheeseCount}.png").writeShort(-32).writeShort(-45 if self.cheeseCount == 2 else -30).writeBoolean(False).writeShort(100).writeShort(0).toByteArray())
            self.room.sendAll(Identifiers.send.Player_Get_Cheese, ByteArray().writeInt(self.playerCode).writeBoolean(True).toByteArray())
            self.cheeseCount += 1
            
            if self.room.currentMap in range(108, 114):
                if self.room.checkDeadPlayersPercentage(70):
                    await self.room.killShaman()

            if self.room.isTutorial:
                self.sendPacket(Identifiers.send.Tutorial, 1)
        self.room.canChangeMap = True

    def sendLangueMessage(self, community, message, *args):
        packet = ByteArray().writeUTF(community).writeUTF(message).writeByte(len(args))
        for arg in args:
            packet.writeUTF(arg)
        self.sendPacket(Identifiers.send.Message_Langue, packet.toByteArray())

    def sendServerMessage(self, message, tab=False, *args):
        packet = ByteArray().writeBoolean(tab).writeUTF(message).writeByte(len(args))
        for arg in args:
            packet.writeUTF(args)
        self.sendPacket(Identifiers.send.Recv_Message, packet.toByteArray())

    def sendMapStartTimer(self, startMap):
        self.sendPacket(Identifiers.send.Map_Start_Timer, ByteArray().writeBoolean(startMap).toByteArray())

    def sendMusicVideo(self):
        self.sendPacket(Identifiers.send.Music_Video, ByteArray().writeUTF(self.room.musicVideos[0]["VideoID"]).writeUTF(self.room.musicVideos[0]["Title"]).writeShort(self.room.musicVideos[0]["Duration"]).writeUTF(self.room.musicVideos[0]["By"]).toByteArray())

    def sendPlaceObject(self, objectID, code, px, py, angle, vx, vy, isVisible, isCollidable, _local1=False):
        packet = ByteArray()
        packet.writeInt(objectID)
        packet.writeInt128(code)
        packet.writeInt128(px)
        packet.writeInt128(py)
        packet.writeInt128(angle)
        packet.writeInt128(vx)
        packet.writeInt128(vy)
        packet.writeBoolean(isVisible)
        packet.writeByte(isCollidable)
        if not self.checkStaffPermission(["Guest"]):
            info = self.Utilities.getShamanItemCustom(code)
            packet.writeBytes(info)
        else:
            packet.writeByte(0)

        if not _local1:
            self.room.sendAll(Identifiers.send.Spawn_Object, packet.toByteArray())
        else:
            self.room.sendAllOthers(self, Identifiers.send.Spawn_Object, packet.toByteArray())
            self.room.objectID = objectID

    def sendPlayerDisconnect(self):
        self.room.sendAll(Identifiers.old.send.Player_Disconnect, [self.playerCode])

    def sendPlayerEmote(self, emoteID, flag, others, lua):
        p = ByteArray().writeInt(self.playerCode).writeByte(emoteID)
        if not flag == '':
            p.writeUTF(flag)
        p.writeBoolean(lua)
        if others:
            self.room.sendAllOthers(self, Identifiers.send.Player_Action, p.toByteArray())
        else:
            self.room.sendAll(Identifiers.send.Player_Action, p.toByteArray())

    def sendPlayerList(self):
        info = self.room.getPlayerList()
        self.sendPacket(Identifiers.send.Player_List, ByteArray().writeShort(info[0]).writeBytes(info[1]).toByteArray())

    def sendRoundTime(self, time):
        self.sendPacket(Identifiers.send.Round_Time, ByteArray().writeShort(0 if time < 0 or time > 32767 else time).toByteArray())

    def sendSync(self, playerCode):
        self.sendPacket(Identifiers.old.send.Sync, [playerCode, ""] if (self.room.mapCode != 1 or self.room.editeurMapCode != 0) else [playerCode])

    def sendTotem(self, totem, x, y, playerCode):
        self.sendPacket(Identifiers.old.send.Totem, ["%s#%s#%s#%s" %(playerCode, x, y, totem)])

    def sendTotemItemCount(self, number):
        if self.room.isTotemEditor:
            self.sendPacket(Identifiers.send.Totem_Item_Count, ByteArray().writeShort(number * 2).toByteArray())



    # Other Functions
    def initTotemEditor(self):
        if self.resetTotem:
            self.sendTotemItemCount(0)
            self.resetTotem = False
        else:
            if not self.totemInfo[1] == "":
                self.tempTotem[0] = self.totemInfo[0]
                self.tempTotem[1] = self.totemInfo[1]
                self.sendTotemItemCount(self.tempTotem[0])
                self.sendTotem(self.tempTotem[1], 400, 204, self.playerCode)
            else:
                self.sendTotemItemCount(0)


    def sendShamanCode(self, shamanCode, shamanCode2):
        self.sendPacket(Identifiers.send.Shaman_Info, ByteArray().writeInt(shamanCode).writeInt(shamanCode2).writeByte(self.server.getShamanType(shamanCode)).writeByte(self.server.getShamanType(shamanCode2)).writeShort(self.server.getShamanLevel(shamanCode)).writeShort(self.server.getShamanLevel(shamanCode2)).writeShort(self.server.getShamanBadge(shamanCode)).writeShort(self.server.getShamanBadge(shamanCode2)).writeByte(0).writeByte(0).toByteArray())


    def sendPlayerDied(self, showPacket=True):
        if showPacket:
            self.room.sendAll(Identifiers.old.send.Player_Died, [self.playerCode, self.playerScore])
        self.cheeseCount = 0
        
        #for player in self.room.clients.copy().values():
        #    if player.isShaman:
        #        if 15 in player.playerSkills and (self.room.isNormRoom or self.room.isVanilla or self.room.isMusic):
        #            self.room.sendPacket(Identifiers.send.Dead_Bubble, ByteArray().writeShort(0).toByteArray())
        #            break

        #if self.room.getPlayerCountAlive() < 1 or self.room.catchTheCheeseMap or self.isAfk:
        #    self.canShamanRespawn = False

        #if ((self.room.checkIfTooFewRemaining() and not self.canShamanRespawn) or (self.room.checkIfShamanIsDead() and not self.canShamanRespawn) or (self.room.checkIfDoubleShamansAreDead())):
        #    self.room.send20SecRemainingTimer()

        #if self.canShamanRespawn:
        #    self.isDead = False
        #    self.isAfk = False
        #    self.hasCheese = False
        #    self.hasEnter = False
        #    self.canShamanRespawn = False
        #    self.playerStartTimeMillis = time.time()
        #    self.room.sendAll(Identifiers.send.Player_Respawn, ByteArray().writeBytes(self.getPlayerData()).writeBoolean(False).writeBoolean(True).toByteArray())
        #    for player in self.room.clients.copy().values():
        #        player.sendShamanCode(self.playerCode, 0)

    def ResetAfkKillTimer(self):
        self.isAfk = False
        if self.killafktimer != None:
            self.killafktimer.cancel()
        #self.killafktimer = call_later(3600, self.transport.loseConnection)

        

    async def enterRoom(self):
        roomName = self.roomName.replace("<", "&lt;")
        if len(roomName) == 0 or roomName in ["\x03racing", "\x03survivor", "\x03vanilla", "\x03bootcamp"]:
            if len(roomName) > 0:
                roomName = roomName[1:]
            roomName = self.server.getRecommendedRoom(self.playerLangue, roomName)

        elif not roomName.startswith(("*", "@")) and not (len(roomName) > 3 and roomName[2] == "-"):
            roomName = f"{self.playerLangue}-{roomName}"
        
        elif (len(roomName) > 3 and roomName[2] == "-"):
            community = roomName[:2].lower()
            roomName = roomName[3:]
            if not community == self.playerLangue and not self.checkStaffPermission(["PrivMod", "Mod", "Admin", "Owner"]): 
                roomName = f"{self.playerLangue}-{roomName}"
            else:
                self.playerLangue = community
                roomName = f"{community}-{roomName}"
            
        if self.room != None:
            await self.room.removeClient(self)

        print(f"Room name --> {roomName}")
        self.roomName = roomName
        self.sendGameType(11 if "music" in roomName else 0, 0)
        self.sendEnterRoom()
        await self.server.addClientToRoom(self, self.roomName)
        
        self.sendPacket(Identifiers.old.send.Anchors, self.room.anchors)
        self.sendPacket(Identifiers.send.Initialize_Lua_Scripting, "")
        
        if self.isReported:
            self.sendPacket(Identifiers.send.Modopwet_Room_Password_Protected, ByteArray().writeUTF(self.playerName).writeUTF(self.roomName).writeBoolean(self.room.roomPassword != "").toByteArray())

        if self.room.isMusic:
            if self.room.isPlayingMusic:
                self.sendMusicVideo()
                
            self.canSkipMusic = False
            if self.skipMusicTimer != None:
                self.skipMusicTimer.cancel()
            self.skipMusicTimer = self.loop.call_later(15, setattr, self, "canSkipMusic", True)

        if self.room.isFuncorp:
            self.sendLangueMessage("", "<FC>$FunCorpActiveAvecMembres</FC>", ', '.join(map(str, self.room.roomFuncorps)))
        
        self.lastRoomName = self.roomName
            

            
            
    def sendVampireMode(self, others):
        self.isVampire = True
        p = ByteArray().writeInt(self.playerCode).writeInt(-1)
        if others:
            self.room.sendAllOthers(self, Identifiers.send.Vampire_Mode, p.toByteArray())
        else:
            self.room.sendAll(Identifiers.send.Vampire_Mode, p.toByteArray())
            
            
    def startPlay(self):
        self.playerStartTimeMillis = self.room.gameStartTimeMillis
        self.isNewPlayer = self.isDead
        self.sendMap(newMapCustom=True) if self.room.mapCode != -1 else self.sendMap() if self.room.isEditeur and self.room.editeurMapCode != 0 else self.sendMap(True)
        
        shamanCode, shamanCode2 = 0, 0
        if self.room.isDoubleMap:
            shamans = self.room.getDoubleShamanCode()
            shamanCode = shamans[0]
            shamanCode2 = shamans[1]
        else:
            shamanCode = self.room.getShamanCode()
            
        if self.playerCode == shamanCode or self.playerCode == shamanCode2:
            self.isShaman = True

        #if self.isShaman and not self.room.isUsingShamanSkills:
        #    self.Skills.getSkills()

        #if self.room.currentShamanName != "" and not self.room.roomDetails[1]:
        #    self.Skills.getPlayerSkills(self.room.currentShamanSkills)

        #if self.room.currentSecondShamanName != "" and not self.room.roomDetails[1]:
        #    self.Skills.getPlayerSkills(self.room.currentSecondShamanSkills)
        
        self.sendPlayerList()
        if self.room.catchTheCheeseMap:
            self.sendPacket(Identifiers.old.send.Catch_The_Cheese_Map, [shamanCode])
            self.sendPacket(Identifiers.send.Player_Get_Cheese, ByteArray().writeInt(shamanCode).writeBoolean(True).toByteArray())
            if self.room.currentMap in [111, 110, 112, 113, 114]:
                self.sendShamanCode(shamanCode, shamanCode2)
        else:
            self.sendShamanCode(shamanCode, shamanCode2)
            
        self.sendSync(self.room.getSyncCode())
        self.sendRoundTime(self.room.roundTime + (self.room.gameStartTime - Time.getTime()) + self.room.addTime)
        self.sendMapStartTimer(False) if self.isDead or self.room.isTutorial or self.room.isTotemEditor or self.room.isBootcamp or self.room.isDefilante or self.room.getPlayerCountUnique() < 2 else self.sendMapStartTimer(True)
    
        if self.room.isTotemEditor:
            self.initTotemEditor()

        #if self.room.isVillage:
        #    self.server.loop.call_later(0.2, self.sendNPCS)

        if self.room.isMulodrome:
            if not self.playerName in self.room.mulodromeRedTeam and not self.playerName in self.room.mulodromeBlueTeam:
                if not self.isDead:
                    self.isDead = True
                    self.sendPlayerDied(False)

        if self.room.isSurvivor and self.isShaman:
            self.canMeep = True
            self.sendPacket(Identifiers.send.Can_Meep, 1)

        if self.room.currentMap in range(200, 211) and not self.isShaman:
            self.sendPacket(Identifiers.send.Can_Transformation, 1)
    
    def resetPlay(self):
        self.iceCount = 2
        self.bubblesCount = 0
        #self.currentPlace = 0
        #self.ambulanceCount = 0
        #self.defilantePoints = 0
        self.posY = 0
        self.posX = 0
        #self.artefactID = 0
        self.cheeseCount = 0
        
        self.isAfk = True
        self.isDead = False
        self.isUsedTotem = False
        #self.hasEnter = False
        self.isShaman = False
        self.isVampire = False
        #self.canRespawn = False
        self.isNewPlayer = False
        #self.isOpportunist = False
        self.desintegration = False
        #self.canShamanRespawn = False


    async def playerWin(self, holeType, monde, distance, holeX, holeY):
        print(holeType, monde, distance, holeX, holeY)
        pass

    def getPlayerData(self):
        data = ByteArray()
        data.writeUTF(self.playerName if self.tempPlayerName == "" else self.tempPlayerName)
        data.writeInt(self.playerCode)
        data.writeBoolean(self.isShaman)
        data.writeBoolean(self.isDead)
        if not self.isHidden:
            data.writeShort(self.playerScore)
        data.writeByte(self.cheeseCount) # New
        data.writeShort(self.titleNumber)
        data.writeByte(self.titleStars)
        data.writeByte(self.playerGender) 
        data.writeUTF("")
        data.writeUTF("1;0,0,0,0,0,0,0,0,0,0" if self.room.isBootcamp else self.playerLook) # "1;0,0,0,0,0,0,0,0,0,0" if self.room.isBootcamp else (str(self.fur) + ";" + self.playerLook.split(";")[1] if self.fur != 0 else self.playerLook)
        data.writeBoolean(self.isHidden)
        data.writeInt(int(self.tempMouseColor if not self.tempMouseColor == "" else self.mouseColor, 16))
        data.writeInt(int(self.shamanColor, 16))
        data.writeInt(0)
        data.writeInt(int(self.tempNickColor.lower() if self.tempNickColor != "" else "95d9d6", 16))
        data.writeByte(0)
        return data.toByteArray()
        
    def sendMap(self, newMap=False, newMapCustom=False): ######
        self.room.notUpdatedScore = True
        
        if self.room.editeurMapXML != "":
            xml = self.room.editeurMapXML.encode()
        else:
            xml = b"" if newMap else self.room.mapXML.encode() if isinstance(self.room.mapXML, str) else self.room.mapXML if newMapCustom else self.room.editeurMapXML.encode() if isinstance(self.room.editeurMapXML, str) else self.room.editeurMapXML
        xml = zlib.compress(xml)
        self.sendPacket([5, 2], ByteArray().writeInt(self.room.currentMap if newMap else self.room.mapCode if newMapCustom else -1).writeShort(self.room.getPlayerCount()).writeByte(self.room.lastRoundCode).writeInt(len(xml)).writeBytes(xml).writeUTF("" if newMap else self.room.mapName if newMapCustom else "-").writeByte(0 if newMap else self.room.mapPerma if newMapCustom else 100).writeBoolean(self.room.mapInverted if newMapCustom else False).writeBoolean(False).writeBoolean(self.room.isDisabledMiceCollision).writeBoolean(self.room.isDisabledFallDamage).writeInt(self.room.miceWeight).toByteArray())
        

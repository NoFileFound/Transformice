#coding: utf-8
import base64
import json
import random
import time
import urllib.request
from collections import deque

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers
from Modules.Lua import Lua

# Utils
from Utils.Other import Other
from Utils.Time import Time

class Packets:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        self.packets = {}
        self.__init_2()
        self.__local1 = True
        self.msgTime = time.time()
        
    def packet(self,func=None,args=[],decrypt=False):
        if not func: return lambda x: self.packet(x,args,decrypt)
        else: 
            if func.__name__ in dir(Identifiers.recv):
                exec(f"self.ccc = Identifiers.recv.{func.__name__}")
                self.packets[self.ccc[0] << 8 | self.ccc[1]] = [args,func,decrypt]

    async def parsePacket(self, packetID, C, CC, packet):
        ccc = C << 8 | CC
        args = []
        self.packet = packet
        self.packetID = packetID
        if ccc in self.packets:
            if self.packets[ccc][2]:
                self.packet.decrypt(self.server.bulleInfo["packet_keys"], packetID)
            self.originalpacket = packet.copy()
            for i in self.packets[ccc][0]:
                exec(f"self.value = self.packet.{i}()")
                args.append(self.value)
            await self.packets[ccc][1](self, *args)
            
            if (self.packet.bytesAvailable()):
                if (C, CC) == (1, 1):
                    self.client.Logger.warn(f"[SATELLITE][{self.client.ipAddress}][OLD] The bulle did not property send the packet {C}:{CC}. Res: {repr(packet.toByteArray())}\n")
                else:
                    self.client.Logger.warn(f"[SATELLITE][{self.client.ipAddress}] The bulle did not property send the packet {C}:{CC}. Res: {repr(packet.toByteArray())}\n")
                
        else:
            self.client.Logger.warn(f"[SATELLITE][{self.client.ipAddress}] The packet {C}:{CC} is not registered in the bulle.\n")
            
    def __init_2(self):
        @self.packet(args=['readInt'])
        async def Antigravity_Skill(self, objectID):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendAntigravitySkill(objectID)
    
        @self.packet(args=['readInt'])
        async def Attach_Ballon_To_Player(self, playerCode):
            self.client.room.sendAll(Identifiers.send.Play_Shaman_Invocation_Sound, ByteArray().writeByte(-1).toByteArray())
            self.client.room.sendAll(Identifiers.send.Attach_Ballon_Player, ByteArray().writeInt(playerCode).writeInt(0).writeInt(1 * 1000).toByteArray())
    
        @self.packet(args=['readInt'])
        async def Convert_Skill(self, objectID):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendConvertSkill(objectID)
    
        @self.packet(args=['readUnsignedByte'])
        async def Crazzy_Packet(self, code):
            p = ByteArray().writeByte(code)
            if code == 2:
                posX = self.packet.readInt()
                posY = self.packet.readInt()
                lineX = self.packet.readInt()
                lineY = self.packet.readInt()
                p.writeInt(self.client.playerCode).writeInt(self.client.drawingColor).writeInt(posX).writeInt(posY).writeInt(lineX).writeInt(lineY).toByteArray()
            
            elif code == 3:
                p.writeUTF(self.client.playerName).toByteArray()
            self.client.room.sendAllOthers(self.client, Identifiers.send.Crazzy_Packet, p.toByteArray())
    
        @self.packet(args=['readByte'])
        async def Crouch(self, crouch_type):
            self.client.room.sendAll(Identifiers.send.Crouch, ByteArray().writeInt(self.client.playerCode).writeByte(crouch_type).writeByte(0).toByteArray())

        @self.packet(args=[])
        async def Defilante_Points(self):
            self.client.defilantePoints += 1
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("PlayerBonusGrabbed", (self.client.playerName, something))

        @self.packet(args=['readInt'])
        async def Demolition_Skill(self, objectID):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendDemolitionSkill(objectID)

        @self.packet(args=[])
        async def Detach_Ballon_To_Player(self):
            self.client.room.sendAll(Identifiers.send.Play_Shaman_Invocation_Sound, ByteArray().writeByte(-1).toByteArray())
            self.client.room.sendAll(Identifiers.send.Detach_Ballon_Player, ByteArray().writeInt(self.client.playerCode).toByteArray())

        @self.packet(args=['readByte', 'readInt', 'readInt', 'readShort', 'readShort', 'readShort'])
        async def Enter_Hole(self, holeType, roundCode, monde, distance, holeX, holeY):
            if roundCode == self.client.room.lastRoundCode and (self.client.room.currentMap == -1 or monde == self.client.room.currentMap or self.client.room.editeurMapCode != 0):
                await self.client.playerWin(holeType, monde, distance, holeX, holeY)

        @self.packet(args=['readUTF'], decrypt=True)
        async def Execute_Command(self, command):
            await self.client.ParseCommands.parseCommand(command)

        @self.packet(args=['readInt', 'readShort', 'readShort', 'readShort', 'readShort'])
        async def Get_Cheese(self, roundCode, cheeseX, cheeseY, x, distance):
            if roundCode == self.client.room.lastRoundCode:
                await self.client.sendGiveCheese(cheeseX, cheeseY, x, distance)

        @self.packet(args=['readInt', 'readInt'])
        async def Gravitational_Skill(self, velX, velY):
            if self.client.isShaman and self.client.room.isUsingShamanSkills and 63 in self.client.playerSkills:
                self.client.Skills.sendGravitationalSkill(0, velX, velY)

        @self.packet(args=['readByte', 'readInt'])
        async def Handymouse_Skill(self, handyMouseByte, objectID):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                if self.client.room.lastHandymouse[0] == -1:
                    self.client.room.lastHandymouse = [objectID, handyMouseByte]
                else:
                    self.client.Skills.sendHandymouseSkill(handyMouseByte, objectID)
                    self.client.room.sendAll(Identifiers.send.Skill, 'M\x01')
                    self.client.room.lastHandymouse = [-1, -1]

        @self.packet(args=['readInt', 'readShort', 'readShort'])
        async def Ice_Cube(self, playerCode, px, py):
            if self.client.isShaman and not self.client.isDead and not self.client.room.isSurvivor and self.client.room.numCompleted > 1:
                if self.client.iceCount != 0 and playerCode != self.client.playerCode:
                    for player in self.client.room.players.copy().values():
                        if player.playerCode == playerCode and not player.isShaman:
                            player.isDead = True
                            if self.client.room.isAutoScore: 
                                self.client.playerScore += 1
                            player.sendPlayerDied(True)
                            self.client.sendPlaceObject(self.client.room.objectID + 2, 54, px, py, 0, 0, 0, True, True)
                            self.client.iceCount -= 1
                            await self.client.room.checkChangeMap()

        @self.packet(args=['readInt128', 'readInt128', 'readInt128', 'readInt128', 'readUTF', 'readBoolean'])
        async def Invocation(self, objectCode, posX, posY, angle, offset, isSpawing):
            if self.client.isShaman:
                self.client.room.sendAllOthers(self.client, Identifiers.send.Invocation, ByteArray().writeInt(self.client.playerCode).writeShort(objectCode).writeShort(posX).writeShort(posY).writeShort(angle).writeUTF(offset).writeBoolean(isSpawing).toByteArray())
                if self.client.room.luaRuntime != None:
                    self.client.room.luaRuntime.emit("SummoningStart", (self.client.playerName, objectCode, posX, posY, rotation))

        @self.packet(args=['readInt', 'readInt'])
        async def Lua_Color_Picked(self, colorPickerId, color):
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("ColorPicked", (colorPickerId, self.client.playerName, color))
                
        @self.packet(args=['readShort', 'readBoolean', 'readShort', 'readShort', 'readShort', 'readShort'])
        async def Lua_Key_Board(self, key, down, posX, posY, xPlayerVelocity, yPlayerVelocity):
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("Keyboard", (self.client.playerName, key, down, posX, posY))
                
        @self.packet(args=['readShort', 'readShort'])
        async def Lua_Mouse_Click(self, posX, posY):
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("Mouse", (self.client.playerName, posX, posY))

        @self.packet(args=['readInt', 'readUTF'])
        async def Lua_Popup_Answer(self, popupID, answer):
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("PopupAnswer", (popupID, self.client.playerName, answer))

        @self.packet(args=['readInt', 'readUTF'])
        async def Lua_Text_Area_Callback(self, textAreaID, event): 
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("TextAreaCallback", (textAreaID, self.client.playerName, event))

        @self.packet(args=['readByte'])
        async def Map_Info(self, cheesesCount):
            self.client.room.cheesesList = []
            i = 0
            while i < cheesesCount // 2:
                cheeseX, cheeseY = self.packet.readShort(), self.packet.readShort()
                self.client.room.cheesesList.append([cheeseX, cheeseY])
                i += 1
            
            self.client.room.holesList = []
            holesCount = self.packet.readByte()
            i = 0
            while i < holesCount // 3:
                holeType, holeX, holeY = self.packet.readShort(), self.packet.readShort(), self.packet.readShort()
                self.client.room.holesList.append([holeType, holeX, holeY])
                i += 1

        @self.packet(args=['readInt', 'readByte'])
        async def Mort(self, roundCode, loc_1):
            if roundCode == self.client.room.lastRoundCode:
                self.client.isDead = True
                if self.client.room.isAutoScore: 
                    self.client.playerScore += 1
                self.client.sendPlayerDied(True)

                if not self.client.room.currentShamanName == "":
                    player = self.client.room.players.get(self.client.room.currentShamanName)
                    if player != None and self.client.room.isUsingShamanSkills:
                        if player.bubblesCount > 0:
                            if self.client.room.getPlayerCountAlive() > 1:
                                self.client.sendPlaceObject(self.client.room.objectID + 2, 59, self.client.posX, 450, 0, 0, 0, True, True)
                        
                        if player.desintegration:
                            self.client.Skills.sendSkillObject(6, self.client.posX, 395, 0, 6, True)
                            
                await self.client.room.checkChangeMap()

        @self.packet(args=[])
        async def Mulodrome_Close(self):
            self.client.room.sendAll(Identifiers.send.Mulodrome_End)

        @self.packet(args=['readByte', 'readByte'])
        async def Mulodrome_Join(self, team, position):
            if len(self.client.mulodromePos) != 0:
                self.client.room.sendAll(Identifiers.send.Mulodrome_Leave, chr(self.client.mulodromePos[0]) + chr(self.client.mulodromePos[1]))

            self.client.mulodromePos = [team, position]
            self.client.room.sendAll(Identifiers.send.Mulodrome_Join, ByteArray().writeByte(team).writeByte(position).writeInt(self.client.playerID).writeUTF(self.client.playerName).writeUTF(self.client.tribeName).toByteArray())
            if self.client.playerName in self.client.room.mulodromeRedTeam: 
                self.client.room.mulodromeRedTeam.remove(self.client.playerName)
                
            if self.client.playerName in self.client.room.mulodromeBlueTeam: 
                self.client.room.mulodromeBlueTeam.remove(self.client.playerName)
                
            self.client.room.mulodromeRedTeam.append(self.client.playerName) if team == 1 else self.client.room.mulodromeBlueTeam.append(self.client.playerName)

        @self.packet(args=['readByte', 'readByte'])
        async def Mulodrome_Leave(self, team, position):
            self.client.room.sendAll(Identifiers.send.Mulodrome_Leave, ByteArray().writeByte(team).writeByte(position).toByteArray())
            if team == 1:
                for playerName in self.client.room.mulodromeRedTeam:
                    if self.client.room.players[playerName].mulodromePos[1] == position:
                        self.client.room.mulodromeRedTeam.remove(playerName)
                        break
            else:
                for playerName in self.client.room.mulodromeBlueTeam:
                    if self.client.room.players[playerName].mulodromePos[1] == position:
                        self.client.room.mulodromeBlueTeam.remove(playerName)
                        break

        @self.packet(args=[])
        async def Mulodrome_Play(self):
            if not len(self.client.room.mulodromeRedTeam) == 0 or not len(self.client.room.mulodromeBlueTeam) == 0:
                self.client.room.isMulodrome = True
                self.client.room.isRacing = True
                self.client.room.noShaman = True
                self.client.room.mulodromeRoundCount = 0
                self.client.room.never20secTimer = True
                self.client.room.sendAll(Identifiers.send.Mulodrome_End)
                await self.client.room.mapChange()

        @self.packet(args=['readShort'])
        async def Old_Protocol(self, length):
            data = self.packet.readUTFBytes(length)
            if isinstance(data, (bytes, bytearray)):
                data = data.decode()
                
            values = data.split('\x01')
            C = ord(values[0][0])
            CC = ord(values[0][1])
            values = values[1:]
            if (C, CC) == Identifiers.old.recv.Player_Bomb_Explode:
                self.client.room.sendAll(Identifiers.old.send.Player_Bomb_Explode, values)
                
            elif (C, CC) == Identifiers.old.recv.Player_Conjure_Start:
                self.client.room.sendAll(Identifiers.old.send.Player_Conjure_Start, values)

            elif (C, CC) == Identifiers.old.recv.Player_Conjure_End:
                self.client.room.sendAll(Identifiers.old.send.Player_Conjure_End, values)
                
            elif (C, CC) == Identifiers.old.recv.Player_Conjuration:
                self.server.loop.call_later(10, self.client.sendConjurationDestroy, int(values[0]), int(values[1]))
                self.client.room.sendAll(Identifiers.old.send.Player_Add_Conjuration, values)
                
            elif (C, CC) == Identifiers.old.recv.Room_Anchors:
                self.client.room.sendAll(Identifiers.old.send.Anchors, values)
                self.client.room.anchors.extend(values)
                
            elif (C, CC) == Identifiers.old.recv.Totem_Anchors:
                if self.client.room.isTotemEditor:
                    if self.client.tempTotem[0] < 20:
                        self.client.tempTotem[0] = int(self.client.tempTotem[0]) + 1
                        self.client.sendTotemItemCount(self.client.tempTotem[0])
                        self.client.tempTotem[1] += "#3#" + chr(1).join(map(str, [values[0], values[1], values[2]]))
                
            elif (C, CC) == Identifiers.old.recv.Room_Bombs:
                self.client.room.sendAll(Identifiers.old.send.Bombs, values)
                
            elif (C, CC) == Identifiers.old.recv.Vote_Map:
                if len(values) == 0:
                    self.client.room.receivedNo += 1
                else:
                    self.client.room.receivedYes += 1
                return
                                
            elif (C, CC) == Identifiers.old.recv.Map_Editor_Validate_Map:
                if self.client.room.isEditeur:
                    self.client.sendPacket(Identifiers.old.send.Map_Editor, [""])
                    self.client.room.EMapValidated = False
                    self.client.room.editeurMapCode = 1
                    self.client.room.editeurMapXML = values[0]
                    await self.client.room.mapChange()
            
            elif (C, CC) == Identifiers.old.recv.Map_Editor_Map_Xml:
                if self.client.room.isEditeur:
                    self.client.room.editeurMapXML = values[0]
                
            elif (C, CC) == Identifiers.old.recv.Return_To_Map_Editor:
                if self.client.room.isEditeur:
                    self.client.room.editeurMapCode = 0
                    self.client.sendPacket(Identifiers.old.send.Map_Editor, ["", ""])
                                
            elif (C, CC) == Identifiers.old.recv.Map_Editor_Export_Map:
                isTribeHouse = len(values) != 0
                isSpecial = self.client.checkStaffPermission(['MC', 'PrivMod', 'Mod', 'Admin', 'Owner'])
                if self.client.cheeseCountDB < 1500 and not isSpecial:
                    self.client.sendPacket(Identifiers.old.send.Editor_Message, [""])
                    return
                
                elif self.client.shopCheeses < (40 if isTribeHouse else self.client.minimumCheesesMapEditor) and not isSpecial:
                    self.client.sendPacket(Identifiers.old.send.Editor_Message, ["", ""])
                    return
                    
                if self.client.room.EMapValidated or isTribeHouse:
                    if not isSpecial:
                        self.client.shopCheeses -= 40 if isTribeHouse else self.client.minimumCheesesMapEditor

                    self.client.room.CursorMaps.execute(f"SELECT MAX(Code) FROM Maps")
                    last_row_id = self.client.room.CursorMaps.fetchone()[0]
                    code = last_row_id + 1
                    self.client.room.CursorMaps.execute("insert into Maps (Code, Name, XML, YesVotes, NoVotes, Perma, Del) values (?, ?, ?, ?, ?, ?, ?)", [code, self.client.playerName, self.client.room.editeurMapXML, 0, 0, 22 if isTribeHouse else 0, 0])
                    self.client.sendPacket(Identifiers.old.send.Map_Editor, ["0"])
                    self.client.sendPacket(Identifiers.old.send.Map_Exported, [code])
                    self.client.roomName = self.client.server.getRecommendedRoom(self.client.playerLangue)
                    await self.client.enterRoom()
                                
            elif (C,CC) == Identifiers.old.recv.Map_Editor_Reset_Map:
                if self.client.room.isEditeur:
                    self.client.room.editeurMapCode = 0
            
            else:
                self.client.Logger.warn(f"[SATELLITE][{self.client.ipAddress}][OLD] The packet {C}:{CC} is not registered in the bulle.\n")

        @self.packet(args=['readInt'])
        async def Object_Sync(self, roundCode): # WARNING: Possible crash the bulle!
            if roundCode == self.client.room.lastRoundCode:
                p = ByteArray()
                while self.packet.bytesAvailable():
                    objectID = self.packet.readInt()
                    objectCode = self.packet.readShort()
                    p.writeInt(objectID)
                    p.writeShort(objectCode)
                    if objectCode != -1:
                        posX = self.packet.readInt()
                        posY = self.packet.readInt()
                        velocityX = self.packet.readShort()
                        velocityY = self.packet.readShort()
                        rotation = self.packet.readShort()
                        rotationSpeed = self.packet.readShort()
                        isGhost = self.packet.readBoolean()
                        isStationary = self.packet.readBoolean()

                        p.writeInt(posX).writeInt(posY).writeShort(velocityX).writeShort(velocityY).writeShort(rotation).writeShort(rotationSpeed).writeBoolean(isGhost).writeBoolean(isStationary).writeByte(0)                
                
                self.client.room.sendAllOthers(self.client, Identifiers.send.Sync, p.toByteArray())

        @self.packet(args=['readByte', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readBoolean', 'readByte', 'readInt128'])
        async def Place_Object(self, roundCode, objectID, shamanCode, px, py, angle, velx, vely, isCollidable, isSpawnedByPlayer, session_id):
            if roundCode == self.client.room.lastRoundCode:
                if not self.client.isShaman and isSpawnedByPlayer == 1:
                    return
            
                if self.client.room.isTotemEditor:
                    if self.client.tempTotem[0] < 20:
                        self.client.tempTotem[0] = int(self.client.tempTotem[0]) + 1
                        self.client.sendTotemItemCount(self.client.tempTotem[0])
                        self.client.tempTotem[1] += "#2#" + chr(1).join(map(str, [shamanCode, px, py, angle, velx, vely, int(isCollidable)]))
                        
                elif shamanCode == 44: # Totem
                    if not self.client.isUsedTotem:
                        self.client.sendTotem(self.client.totemInfo[1], px, py, self.client.playerCode)
                        self.client.isUsedTotem = True
                self.client.sendPlaceObject(objectID, shamanCode, px, py, angle, velx, vely, True, isCollidable, True)
                self.client.Skills.placeSkill(objectID, shamanCode, px, py, angle)
                
            if self.client.room.luaRuntime != None:
                data = self.client.room.luaRuntime.runtime.table()
                data["id"] = objectID
                data["type"] = code
                data["x"] = px
                data["y"] = py
                data["angle"] = angle
                data["ghost"] = not dur
                self.client.room.luaRuntime.emit("SummoningEnd", (self.client.playerName, code, px, py, angle, data))

        @self.packet(args=['readByte', 'readInt', 'readUTF'])
        async def Player_Action(self, emoteID, playerCode, flag=''):
            if emoteID == 10:
                if flag == '':
                    self.client.sendPlayerEmote(10, self.client.playerLangue, False, False)
                else:
                    self.client.sendPlayerEmote(10, flag, False, False)
            
            elif emoteID == 14:
                self.client.sendPlayerEmote(14, flag, False, False)
                self.client.sendPlayerEmote(15, flag, False, False)
                player = list(filter(lambda p: p.playerCode == playerCode, self.client.room.players.copy().values()))
                if len(player) > 0:
                    player = player[0]
                    player.sendPlayerEmote(14, flag, False, False)
                    player.sendPlayerEmote(15, flag, False, False)
                    
            elif emoteID == 18:
                self.client.sendPlayerEmote(18, flag, False, False)
                self.client.sendPlayerEmote(19, flag, False, False)
                player = list(filter(lambda p: p.playerCode == playerCode, self.client.room.players.copy().values()))
                if len(player) > 0:
                    player = player[0]
                    player.sendPlayerEmote(18, flag, False, False)
                    player.sendPlayerEmote(19, flag, False, False)
                    
            elif emoteID == 22:
                self.client.sendPlayerEmote(22, flag, False, False)
                self.client.sendPlayerEmote(23, flag, False, False)
                player = list(filter(lambda p: p.playerCode == playerCode, self.client.room.players.copy().values()))
                if len(player) > 0:
                    player = player[0]
                    player.sendPlayerEmote(22, flag, False, False)
                    player.sendPlayerEmote(23, flag, False, False)
                    
            elif emoteID == 26: 
                self.client.sendPlayerEmote(26, flag, False, False)
                self.client.sendPlayerEmote(27, flag, False, False)
                p1 = random.randint(0, 2)
                player = list(filter(lambda p: p.playerCode == playerCode, self.client.room.players.copy().values()))
                if len(player) > 0:
                    player = player[0]
                    player.sendPlayerEmote(26, flag, False, False)
                    player.sendPlayerEmote(27, flag, False, False)
                    p2 = random.randint(0, 2)
                    self.client.room.sendAll(Identifiers.send.Jankenpon, ByteArray().writeInt(self.client.playerCode).writeByte(p1).writeInt(player.playerCode).writeByte(p2).toByteArray())
            
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.parseEmoteSkill(emoteID)
  
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("EmotePlayed", (self.client.playerName, emoteID, flag))

        @self.packet(args=[])
        async def Player_Attack(self):
            self.client.sendPacket(Identifiers.send.Player_Attack, ByteArray().writeInt(self.client.playerCode).toByteArray())

        @self.packet(args=[])
        async def Player_Damaged(self):
            if self.client.playerLifes <= 0:
                self.client.playerLifes = 0
                self.client.isDead = True
                self.client.sendPlayerDied(True)
            else:
                self.client.playerLifes -= 1
            self.client.sendPacket(Identifiers.send.Player_Damaged, ByteArray().writeInt(self.client.playerCode).toByteArray())

        @self.packet(args=['readShort'])
        async def Player_Emotions(self, emotionID):
            self.client.sendEmotion(emotionID)

        @self.packet(args=['readInt', 'readByte'])
        async def Player_Hit_Monster(self, monster_id, isRight):
            if self.client.room.monsterLifes[monster_id] <= 0:
                self.client.room.monsterLifes[monster_id] = 0
                
                
                if self.client.room.isEventTime and self.client.room.eventType == "halloween__mapid_monsterboss" and monster_id == 0:
                    for player in self.client.room.players.copy().values():
                        player.cheeseCount = 1
                        await player.playerWin(0, 0, 0, 0, 0, True)
                
                self.client.sendPacket(Identifiers.send.Remove_Monster, ByteArray().writeInt(monster_id).toByteArray())
            else:
                self.client.room.monsterLifes[monster_id] -= 1
            
            self.client.room.sendAll(Identifiers.send.Player_Hit_Monster, ByteArray().writeInt(monster_id).writeByte(isRight).toByteArray())

        @self.packet(args=['readBoolean'])
        async def Player_Shaman_Fly(self, fly):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendShamanFly(fly)

        @self.packet(args=['readUTF'])
        async def Player_IPS_Info(self, info):
            self.client.sendPacket(Identifiers.send.Player_IPS_Info, ByteArray().writeUTF(info).toByteArray())

        @self.packet(args=['readShort', 'readShort'])
        async def Player_Meep(self, posX, posY):
            if not self.client.canMeep and not self.client.hasShamanMeep:
                return
                
            self.client.room.sendAll(Identifiers.send.Meep, ByteArray().writeInt(self.client.playerCode).writeShort(posX).writeShort(posY).writeInt(20 if self.client.isShaman else 5).toByteArray())
            if self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("PlayerMeep", (self.client.playerName, posX, posY))

        @self.packet(args=['readInt128', 'readBoolean', 'readBoolean', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readInt128', 'readBoolean', 'readInt128', 'readInt128'], decrypt=True)
        async def Player_Movement(self, roundCode, move_right, move_left, posX, posY, velX, velY, sticky, slippery, jumping, jumping_frame_index, entered_portal):
            if roundCode == self.client.room.lastRoundCode:
                if (move_right, move_left) != (False,False):
                    self.client.isFacingRight = self.client.isMovingRight = move_right
                    self.client.isMovingLeft = move_left
                else:
                    self.client.isMovingRight = move_right
                    self.client.isMovingLeft = move_left
                
                lasty = self.client.posY
                lastx = self.client.posX
                self.client.posX, self.client.posY = posX * 800 // 2700, posY * 800 // 2700
                if not lasty == 0 and not lastx == 0:
                    if not lasty == self.client.posY or not lastx == self.client.posX:
                        self.client.resetAfkKillTimer()
                            
                self.client.velocityX = velX
                self.client.velocityY = velY
                self.client.isJumping = jumping
                
                p = ByteArray()
                p.writeInt128(self.client.playerCode)
                self.originalpacket.readInt128()
                p.write(self.originalpacket.toByteArray())

                self.client.room.sendAllOthers(self.client, Identifiers.send.Player_Movement, p.toByteArray())

        @self.packet(args=[])
        async def Player_MS_Info(self):
            self.client.sendPacket(Identifiers.send.Player_MS_Info)

        @self.packet(args=['readShort', 'readShort', 'readShort'])
        async def Projection_Skill(self, posX, posY, _dir):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendProjectionSkill(posX, posY, _dir)

        @self.packet(args=['readInt','readInt','readInt'])
        async def Receive_Bulle_Info(self, bulle_id, verification_code, playerID):
            self.client.bulle_id = bulle_id
            if verification_code in self.server.bulle_verification:
                player = self.server.bulle_verification[verification_code]
                self.client.playerID = playerID
                self.client.playerName = player[0]
                self.client.playerCode = player[1]
                self.client.playerLangue = player[2]
                self.client.playerLook = base64.b64decode(player[3]).decode('utf-8')
                self.client.staffRoles = player[4].split(',')
                self.client.isMuted = player[5]
                self.client.playerGender = player[6]
                self.client.roomName = player[7]
                self.client.isHidden = player[8]
                self.client.isReported = player[9]
                self.client.titleNumber = player[10]
                self.client.titleStars = player[11]
                self.client.isMutedHours = player[12]
                self.client.isMutedReason = player[13]
                self.client.shamanType = player[14]
                self.client.shamanLevel = player[15]
                self.client.shamanItems = base64.b64decode(player[16]).decode('utf-8')
                self.client.equipedShamanBadge = player[17]
                self.client.shamanColor = player[18]
                self.client.petType = player[19]
                self.client.petEnd = player[20]
                self.client.furType = player[21]
                self.client.furEnd = player[22]
                self.client.minimumCheesesMapEditor = player[23]
                self.client.shopCheeses = player[24]
                self.client.cheeseCountDB = player[25]
                for skill in list(map(str, filter(None, base64.b64decode(player[26]).decode('utf-8').split(";")))):
                    values = skill.split(":")
                    self.client.playerSkills[int(values[0])] = int(values[1])
                self.client.isGuest = True if player[27] == "True" else False
                self.server.bulle_players[playerID] = self.client
                del self.server.bulle_verification[verification_code]        
                if self.server.isDebug:
                    self.client.Logger.debug(f"[SATELLITE][{self.client.ipAddress}] a new connection was made by the player {self.client.playerName}. Bulle : bulle{bulle_id}\n")
                    
                await self.client.enterRoom()
            else:
                return self.client.transport.close()

        @self.packet(args=['readShort'])
        async def Recycling_Skill(self, id):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendRecyclingSkill(id)

        @self.packet(args=[])
        async def Remove_Invocation(self):
            if self.client.isShaman:
                self.client.room.sendAllOthers(self.client, Identifiers.send.Remove_Invocation, ByteArray().writeInt(self.client.playerCode).toByteArray())
                if self.client.room.luaRuntime != None:
                    self.client.room.luaRuntime.emit("SummoningCancel", (self.client.playerName))

        @self.packet(args=['readInt', 'readInt'])
        async def Restorative_Skill(self, objectID, id):
            if self.client.isShaman and self.client.room.isUsingShamanSkills:
                self.client.Skills.sendRestorativeSkill(objectID, id)

        @self.packet(args=['readUTF'], decrypt=True)
        async def Send_Chat_Message(self, message):
            message = message.replace("&amp;#", "&#").replace("<", "&lt;")
            if self.client.checkStaffPermission(["Guest"]):
                self.client.sendLangueMessage("", "$Créer_Compte_Parler")
                return
                
            elif self.client.isHidden:
                self.client.sendServerMessage("You can't speak while you are watching somebody.", True)
                return
                
            elif message.startswith("!") and self.client.room.luaRuntime != None:
                self.client.room.luaRuntime.emit("ChatCommand", (self.client.playerName, message[1:]))
                if message[1:] in self.client.room.luaRuntime.HiddenCommands:
                    return
            
            elif self.client.isMuted:
                timeCalc = Time.getHoursDiff(self.client.isMutedHours)
                if timeCalc <= 0:
                    self.client.isMuted = False
                    self.client.isMutedHours = 0
                    self.client.isMutedReason = ""
                else:
                    self.client.sendLangueMessage("", "<ROSE>$MuteInfo1", str(timeCalc), " ".join(self.client.isMutedReason) if isinstance(self.client.isMutedReason, list) else self.client.isMutedReason)
                    return
            else:
                if time.time() - self.msgTime > 1.5:
                    if message != self.client.lastMessage:
                        self.client.lastMessage = message
                        cfm = message.split(' ')
                        for word in cfm:
                            if word in self.server.chatEmojies:
                                message = message.replace(word, self.server.chatEmojies[word])
                    
                        if self.client.isMumuted:
                            self.client.room.sendAllChat(self.client.playerName if self.client.tempPlayerName == "" else self.client.tempPlayerName, message, 2)
                        else:
                            self.client.room.sendAllChat(self.client.playerName if self.client.tempPlayerName == "" else self.client.tempPlayerName, message, self.server.checkMessage(message))
                    else:
                        self.client.sendLangueMessage("", "$Message_Identique")
                    self.msgTime = time.time()
                else:
                    self.client.sendLangueMessage("", "$Doucement")
                    
                if not self.client.playerName in self.server.chatMessages:
                    messages = deque([], 60)
                    messages.append([time.strftime("%Y/%m/%d %H:%M:%S"), message])
                    self.server.chatMessages[self.client.playerName] = {}
                    self.server.chatMessages[self.client.playerName][self.client.roomName] = messages
                elif not self.client.roomName in self.server.chatMessages[self.client.playerName]:
                    messages = deque([], 60)
                    messages.append([time.strftime("%Y/%m/%d %H:%M:%S"), message])
                    self.server.chatMessages[self.client.playerName][self.client.roomName] = messages
                else:
                    self.server.chatMessages[self.client.playerName][self.client.roomName].append([time.strftime("%Y/%m/%d %H:%M:%S"), message, self.client.roomName])

                if self.client.room.luaRuntime != None:
                    self.client.room.luaRuntime.emit("ChatMessage", (self.client.playerName, message))

        @self.packet(args=['readUTF'])
        async def Send_Music(self, video_url):
            info = Other.getVideoID(video_url)
            if len(info) == 0:
                self.client.sendLangueMessage("", "$ModeMusic_ErreurVideo")
            else:
                url = f"https://www.googleapis.com/youtube/v3/videos?id={info}&key={self.server.bulleInfo['yt_key']}&part=snippet,contentDetails"
                with urllib.request.urlopen(url) as response:
                    data = json.loads(response.read().decode())
            
                if not data["pageInfo"]["totalResults"] == 0:
                    duration = Other.VideoDuration(data["items"][0]["contentDetails"]["duration"])
                    duration = 32767 if duration > 32767 else duration
                    title = data["items"][0]["snippet"]["title"]
                    if any(filter(lambda music: music["By"] == self.client.playerName, self.client.room.musicVideos)):
                        self.client.sendLangueMessage("", "$ModeMusic_VideoEnAttente")
                    elif any(filter(lambda music: music["Title"] == title, self.client.room.musicVideos)):
                        self.client.sendLangueMessage("", "$DejaPlaylist")
                    else:
                        self.client.sendLangueMessage("", "$ModeMusic_AjoutVideo", "<BL>" + str(len(self.client.room.musicVideos) + 1))
                        self.client.room.musicVideos.append({"By": self.client.playerName, "Title": title, "Duration": duration, "VideoID": info})
                        if len(self.client.room.musicVideos) == 1:
                            self.client.room.sendMusicVideo()
                            self.client.room.isPlayingMusic = True
                            self.client.room.musicSkipVotes = 0

        @self.packet(args=[])
        async def Send_PlayList(self):
            p = ByteArray().writeShort(len(self.client.room.musicVideos))
            for music in self.client.room.musicVideos:
                p.writeUTF(music["Title"]).writeUTF(music["By"])
            self.client.sendPacket(Identifiers.send.Music_Playlist, p.toByteArray())

        @self.packet(args=['readByte', 'readShort', 'readShort'])
        async def Shaman_Message(self, _type, x, y):
            self.client.room.sendAll(Identifiers.send.Shaman_Message, ByteArray().writeByte(_type).writeShort(x).writeShort(y).toByteArray())

        @self.packet(args=['readByte'])
        async def Shaman_Position(self, direction):
            self.client.room.sendAll(Identifiers.send.Shaman_Position, ByteArray().writeInt(self.client.playerCode).writeByte(direction).toByteArray())

        @self.packet(args=['readBoolean'])
        async def Strm_Force_Next_Shaman(self, canShaman):
            if canShaman:
                self.client.room.forceNextShaman = self.client.playerCode
            else:
                self.client.room.forceNextShaman = -1

        @self.packet(args=['readShort'])
        async def Transformation_Object(self, objectID):
            if not self.client.isDead and (self.client.room.currentMap in self.client.room.transformationMaps or self.client.hasFunCorpTransformations or self.client.hasLuaTransformations or self.client.hasShamanTransformations):
                self.client.room.sendAll(Identifiers.send.Transformation, ByteArray().writeInt(self.client.playerCode).writeShort(objectID).toByteArray())
                
        @self.packet
        async def Execute_Lua_Script(self):
            script = self.packet.readUTFBytes(int.from_bytes(self.packet.read(3), 'big')).decode()
            #if False:
            #if(self.client.privLevel in [9, 10] or self.client.isLuaCrew) or ((self.client.privLevel == 5 or self.client.isFunCorpPlayer) and self.client.room.isFuncorp) or self.client.room.isTribeHouse:
            if self.client.room.luaRuntime == None:
                self.client.room.luaRuntime = Lua(self.client.room, self.server)
            self.client.room.luaRuntime.owner = self.client
            self.client.room.luaRuntime.RunCode(script)
                            
        @self.packet(args=['readUnsignedShort'])
        async def Monster_Synchronization(self, monsters):
            pass
            #x = 0
            #while x < monsters:
            
            #    monster_id = self.packet.readInt()
            #    x = self.packet.readInt()
            #    y = self.packet.readInt()
                
            #    if monster_id == 0:
            #        self.client.room.sendEventMapAction(10)
                
                #self.client.sendPacket([26, 8], ByteArray().writeInt(monster_id).writeInt(0).toByteArray())
            
            #    x += 1
                
            
            
#coding: utf-8
import asyncio

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

class Skills:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        
    # Check
    def checkQualifiedPlayer(self, px, py, player):
        if not player.playerName == self.client.playerName and not player.isShaman:
            if player.posX >= px - 85 and player.posX <= px + 85:
                if player.posY >= py - 85 and player.posY <= py + 85:
                    return True
        return False
        
    # Get
        
    def getShamanBadge(self):
        if self.client.equipedShamanBadge != 0:
            return self.client.equipedShamanBadge

        badgesCount = [0, 0, 0, 0, 0]

        for skill in self.client.playerSkills.items():
            if skill[0] > -1 and skill[0] < 14:
                badgesCount[0] += skill[1]
            elif skill[0] > 19 and skill[0] < 35:
                badgesCount[1] += skill[1]
            elif skill[0] > 39 and skill[0] < 55:
                badgesCount[2] += skill[1]
            elif skill[0] > 59 and skill[0] < 75:
                badgesCount[4] += skill[1]
            elif skill[0] > 79 and skill[0] < 95:
                badgesCount[3] += skill[1]

        return -(badgesCount.index(max(badgesCount)))
        
    def getSkills(self):
        if self.client.isShaman:
            if 4 in self.client.playerSkills and not self.client.room.isDoubleMap:
                self.client.canShamanRespawn = True

            for skill in [5, 8, 9, 11, 12, 26, 28, 29, 31, 41, 46, 48, 51, 52, 53, 60, 62, 65, 66, 67, 69, 71, 74, 80, 81, 83, 85, 88, 90, 93]:
                if skill in self.client.playerSkills and not (self.client.room.isSurvivor and skill == 81):
                    self.sendEnableSkill(skill, self.client.playerSkills[skill] * 2 if skill in [28, 65, 74] else self.client.playerSkills[skill])

            for skill in [6, 30, 33, 34, 44, 47, 50, 63, 64, 70, 73, 82, 84, 92]:
                if skill in self.client.playerSkills:
                    if skill == 6: self.client.ambulanceCount = skill
                    self.sendEnableSkill(skill, 1)

            for skill in [7, 14, 27, 86, 87, 94]:
                if skill in self.client.playerSkills:
                    self.sendEnableSkill(skill, 100)

            for skill in [10, 13]:
                if skill in self.client.playerSkills:
                    self.sendEnableSkill(skill, 3)

            if 20 in self.client.playerSkills:
                count = self.client.playerSkills[20]            
                self.sendEnableSkill(20, [114, 118, 120, 122, 126][(5 if count > 5 else count) - 1])

            if 21 in self.client.playerSkills:
                self.bubblesCount = self.client.playerSkills[21]

            if 22 in self.client.playerSkills and not self.client.room.currentMap in [108, 109]:
                count = self.client.playerSkills[22]
                self.sendEnableSkill(22, [25, 30, 35, 40, 45][(5 if count > 5 else count) - 1])

            if 23 in self.client.playerSkills:
                count = self.client.playerSkills[23]            
                self.sendEnableSkill(23, [40, 50, 60, 70, 80][(5 if count > 5 else count) - 1])

            if 24 in self.client.playerSkills:
                self.client.isOpportunist = True

            if 32 in self.client.playerSkills:
                self.client.iceCount += self.client.playerSkills[32]

            if 40 in self.client.playerSkills:
                count = self.client.playerSkills[40]            
                self.sendEnableSkill(40, [30, 40, 50, 60, 70][(5 if count > 5 else count) - 1])

            if 42 in self.client.playerSkills:
                count = self.client.playerSkills[42]            
                self.sendEnableSkill(42, [240, 230, 220, 210, 200][(5 if count > 5 else count) - 1])

            if 43 in self.client.playerSkills:
                count = self.client.playerSkills[43]            
                self.sendEnableSkill(43, [240, 230, 220, 210, 200][(5 if count > 5 else count) - 1])

            if 45 in self.client.playerSkills:
                count = self.client.playerSkills[45]
                self.sendEnableSkill(45, [110, 120, 130, 140, 150][(5 if count > 5 else count) - 1])

            if 49 in self.client.playerSkills:
                count = self.client.playerSkills[49]
                self.sendEnableSkill(49, [80, 70, 60, 50, 40][(5 if count > 5 else count) - 1])

            if 54 in self.client.playerSkills:
                self.sendEnableSkill(54, 130)

            if 72 in self.client.playerSkills:
                count = self.client.playerSkills[72]            
                self.sendEnableSkill(72, [25, 30, 35, 40, 45][(5 if count > 5 else count) - 1])

            if 89 in self.client.playerSkills and not self.client.room.isSurvivor:
                count = self.client.playerSkills[89]
                self.sendEnableSkill(49, [80, 70, 60, 50, 40][(5 if count > 5 else count) - 1])
                self.sendEnableSkill(49, [56, 52, 48, 44, 40][(5 if count > 5 else count) - 1])
                self.sendEnableSkill(54, [96, 92, 88, 84, 80][(5 if count > 5 else count) - 1])

            if 91 in self.client.playerSkills:
                self.client.desintegration = True
        
    # Send
        
    def sendAntigravitySkill(self, objectID):
        self.client.room.sendAll(Identifiers.send.Antigravity_Skill, ByteArray().writeInt(objectID).writeShort(0).toByteArray())
        
    def sendBonfireSkill(self, px, py, seconds):
        self.client.room.sendAll(Identifiers.send.Bonfire_Skill, ByteArray().writeShort(px).writeShort(py).writeByte(seconds).toByteArray())

    def sendConvertSkill(self, objectID):
        self.client.room.sendAll(Identifiers.send.Convert_Skill, ByteArray().writeInt(objectID).writeByte(0).toByteArray())

    def sendDecreaseMouseSkill(self, playerCode):
        self.client.room.sendAll(Identifiers.send.Mouse_Size, ByteArray().writeInt(playerCode).writeShort(70).writeBoolean(True).toByteArray())

    def sendDemolitionSkill(self, objectID):
        self.client.room.sendAll(Identifiers.send.Demolition_Skill, ByteArray().writeInt(objectID).toByteArray())

    def sendEnableSkill(self, id, count):
        self.client.sendPacket(Identifiers.send.Enable_Skill, ByteArray().writeUnsignedByte(id).writeUnsignedByte(count).toByteArray())

    def sendEvolutionSkill(self, playerCode):
        self.client.room.sendAll(Identifiers.send.Evolution_Skill, ByteArray().writeInt(playerCode).writeByte(100).toByteArray())

    def sendGatmanSkill(self, playerCode):
        self.client.room.sendAll(Identifiers.send.Gatman_Skill, ByteArray().writeInt(playerCode).writeByte(1).toByteArray())

    def sendGrapnelSkill(self, playerCode, px, py):
        self.client.room.sendAll(Identifiers.send.Grapnel_Mouse_Skill, ByteArray().writeInt(playerCode).writeShort(px).writeShort(py).toByteArray())

    def sendGravitationalSkill(self, seconds, velX, velY):
        self.client.room.sendAll(Identifiers.send.Gravitation_Skill, ByteArray().writeInt(seconds).writeInt(velX).writeInt(velY).toByteArray())

    def sendHandymouseSkill(self, handyMouseByte, objectID):
        self.client.room.sendAll(Identifiers.send.Handymouse_Skill, ByteArray().writeByte(handyMouseByte).writeInt(objectID).writeByte(self.client.room.lastHandymouse[1]).writeInt(self.client.room.lastHandymouse[0]).toByteArray())

    def sendIceMouseSkill(self, playerCode, iced):
        self.client.room.sendAll(Identifiers.send.Iced_Mouse_Skill, ByteArray().writeInt(playerCode).writeBoolean(iced).toByteArray())

    def sendLeafMouseSkill(self, playerCode):
        self.client.room.sendAll(Identifiers.send.Leaf_Mouse_Skill, ByteArray().writeByte(1).writeInt(playerCode).toByteArray())

    def sendProjectionSkill(self, posX, posY, dir):
        self.client.room.sendAllOthers(self.client, Identifiers.send.Projection_Skill, ByteArray().writeShort(posX).writeShort(posY).writeShort(dir).toByteArray())

    def sendRecyclingSkill(self, id):
        self.client.room.sendAll(Identifiers.send.Recycling_Skill, ByteArray().writeShort(id).toByteArray())

    def sendRestorativeSkill(self, objectID, id):
        self.client.room.sendAll(Identifiers.send.Restorative_Skill, ByteArray().writeInt(objectID).writeInt(id).toByteArray())

    def sendRolloutMouseSkill(self, playerCode):
        self.client.room.sendAll(Identifiers.send.Rollout_Mouse_Skill, ByteArray().writeInt(playerCode).toByteArray())
    
    def sendShamanFly(self, fly):
        self.client.room.sendAllOthers(self.client, Identifiers.send.Shaman_Fly, ByteArray().writeInt(self.client.playerCode).writeBoolean(fly).toByteArray())

    def sendSpiderMouseSkill(self, px, py):
        self.client.room.sendAll(Identifiers.send.Spider_Mouse_Skill, ByteArray().writeShort(px).writeShort(py).toByteArray())

    def sendSkillObject(self, objectID, posX, posY, angle, bonusType, isVisible=True):
        self.client.room.sendAll(Identifiers.send.Skill_Object, ByteArray().writeInt128(posX).writeInt128(posY).writeInt128(bonusType).writeInt128(angle).writeInt128(objectID).writeBoolean(isVisible).toByteArray())
        
    def sendTeleport(self, type, posX, posY):
        self.client.room.sendAll(Identifiers.send.Teleport, ByteArray().writeByte(type).writeShort(posX).writeShort(posY).toByteArray())
        
    # EXP
        
    def earnExp(self, isShaman, exp):
        pass
        
    def placeSkill(self, objectID, code, px, py, angle):        
        if code == 36:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    player.sendPacket(Identifiers.send.Can_Transformation, 1)
                    player.hasShamanTransformations = True
                    break
                    
        elif code == 37:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    self.sendTeleport(36, player.posX, player.posY)
                    player.room.movePlayer(player.playerName, self.client.posX, self.client.posY, False, 0, 0, True)
                    self.sendTeleport(37, self.client.posX, self.client.posY)
                    break
                    
        elif code == 38:
            for player in self.client.room.players.values():
                if player.isDead and not player.hasEnter and not player.isAfk and not player.isShaman and not player.isNewPlayer:
                    if self.client.ambulanceCount > 0:
                        self.client.ambulanceCount -= 1
                        self.client.room.respawnSpecific(player.playerName)
                        player.isDead = False
                        player.hasCheese = False
                        player.room.movePlayer(player.playerName, self.client.posX, self.client.posY, False, 0, 0, True)
                        self.sendTeleport(37, self.client.posX, self.client.posY)
                    else:
                        break
            self.client.room.sendAll(Identifiers.send.Skill, '&\x01')
                    
        elif code == 42: # Spring
            self.sendSkillObject(3, px, py, 0, 3)

        elif code == 43: # Speed
            self.sendSkillObject(1, px, py, 0, 1)
            
        elif code == 47:
            if self.client.room.numCompleted > 1:
                for player in self.client.room.players.values():
                    if player.cheeseCount > 0 and self.checkQualifiedPlayer(px, py, player):
                        player.server.loop.create_task(player.playerWin(0, 0, 0, 0, 0, True))
                        break

        elif code == 55:
            for player in self.client.room.players.values():
                if not player.cheeseCount > 0 and self.client.cheeseCount > 0 and self.checkQualifiedPlayer(px, py, player):
                    player.server.loop.create_task(player.sendGiveCheese(0, 0, 0, 0))
                    self.client.sendRemoveCheese()
                    self.client.cheeseCount = 0
                    break
            
        elif code == 56:
            self.sendTeleport(36, self.client.posX, self.client.posY)
            self.client.room.movePlayer(self.client.playerName, px, py, False, 0, 0, False)
            self.sendTeleport(37, px, py)
            
        elif code == 57:
            if self.client.room.cloudID == -1:
                self.client.room.cloudID = objectID
            else:
                self.client.room.removeObject(self.client.room.cloudID)
                self.client.room.cloudID = objectID

        elif code == 61:
            if self.client.room.companionBox == -1:
                self.client.room.companionBox = objectID
            else:
                self.client.room.removeObject(self.client.room.companionBox)
                self.client.room.companionBox = objectID
            
        elif code == 70:
            self.sendSpiderMouseSkill(px, py)
            
        elif code == 71:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    self.sendRolloutMouseSkill(player.playerCode)
                    self.client.room.sendAll(Identifiers.send.Skill, 'G\x01')
                    break

        elif code == 73:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    self.sendDecreaseMouseSkill(player.playerCode)
                    break

        elif code == 74:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    self.sendLeafMouseSkill(player.playerCode)
                    break
            
        elif code == 75:
            self.client.room.sendAll(Identifiers.send.Remove_All_Objects_Skill)
            
        elif code == 76: # Boost
            self.sendSkillObject(5, px, py, angle, 5)
            
        elif code == 79:
            if not self.client.room.isSurvivor:
                for player in self.client.room.players.values():
                    if self.checkQualifiedPlayer(px, py, player):
                        self.sendIceMouseSkill(player.playerCode, True)
                self.client.room.sendAll(Identifiers.send.Skill, 'O\x01')
                self.server.loop.call_later(self.client.playerSkills[82] * 2, lambda: self.sendIceMouseSkill(player.playerCode, False))
            
        elif code == 81:
            self.sendGravitationalSkill(self.client.playerSkills[63] * 2, 0, 0)
            
        elif code == 83:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    player.sendPacket(Identifiers.send.Can_Meep, 1)
                    player.hasShamanMeep = True
                    break
            
        elif code == 84:
            self.sendGrapnelSkill(self.client.playerCode, px, py)
            
        elif code == 86:
            self.sendBonfireSkill(px, py, self.client.playerSkills[86] * 4)
         
        elif code == 92:
            self.getSkills()
            self.client.room.sendAll(Identifiers.send.Reset_Shaman_Skills)
         
        elif code == 93:
            for player in self.client.room.players.values():
                if self.checkQualifiedPlayer(px, py, player):
                    self.sendEvolutionSkill(player.playerCode)
                    break
         
        elif code == 94:
            self.sendGatmanSkill(self.client.playerCode)
            
        else:
            self.server.Logger.warn(f"Not implemented skill: {code}\n")
                    
    def parseEmoteSkill(self, emote):
        count = 0
        if emote == 0 and 3 in self.client.playerSkills:
            for player in self.client.room.players.values():
                if self.client.playerSkills[3] >= count and player != self.client:
                    if player.posX >= self.client.posX - 400 and player.posX <= self.client.posX + 400:
                        if player.posY >= self.client.posY - 300 and player.posY <= self.client.posY + 300:
                            player.sendPlayerEmote(0, "", False, False)
                            count += 1

        elif emote == 4 and 61 in self.client.playerSkills:
            for player in self.client.room.players.values():
                if self.client.playerSkills[61] >= count and player != self.client:
                    if player.posX >= self.client.posX - 400 and player.posX <= self.client.posX + 400:
                        if player.posY >= self.client.posY - 300 and player.posY <= self.client.posY + 300:
                            player.sendPlayerEmote(2, "", False, False)
                            count += 1

        elif emote == 8 and 25 in self.client.playerSkills:
            for player in self.client.room.players.values():
                if self.client.playerSkills[25] >= count and player != self.client:
                    if player.posX >= self.client.posX - 400 and player.posX <= self.client.posX + 400:
                        if player.posY >= self.client.posY - 300 and player.posY <= self.client.posY + 300:
                            player.sendPlayerEmote(3, "", False, False)
                            count += 1
                    
    def getPlayerSkills(self, playerName):
        pass
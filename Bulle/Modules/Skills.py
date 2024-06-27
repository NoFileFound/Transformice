#coding: utf-8
import asyncio

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

class Skills:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        
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
        
        
    def sendBonfireSkill(self, px, py, seconds):
        self.client.room.sendAll(Identifiers.send.Bonfire_Skill, ByteArray().writeShort(px).writeShort(py).writeByte(seconds).toByteArray())

    def sendSkillObject(self, objectID, posX, posY, angle, bonusType, isVisible=True):
        self.client.room.sendAll(Identifiers.send.Skill_Object, ByteArray().writeInt128(posX).writeInt128(posY).writeInt128(bonusType).writeInt128(angle).writeInt128(objectID).writeBoolean(isVisible).toByteArray())
        
    # Skills
        
    def earnExp(self, isShaman, exp):
        pass
        
    def placeSkill(self, objectID, code, px, py, angle):
        pass
        
    def getSkills(self):
        pass
        
    def getPlayerSkills(self, playerName):
        pass
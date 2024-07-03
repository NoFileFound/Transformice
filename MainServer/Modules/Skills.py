#coding: utf-8
import base64

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

class Skills:
    def __init__(self, player):
        self.client = player
        self.server = player.server

    def sendExp(self, level, exp, nextLevel):
        self.client.sendPacket(Identifiers.send.Shaman_Exp, ByteArray().writeUnsignedShort(0 if level - 1 < 0 else level - 1).writeInt(exp).writeInt(nextLevel).toByteArray())

    def sendPurchaseSkill(self, skill):
        if self.client.shamanLevel > len(self.client.playerSkills):
            if skill in self.client.playerSkills:
                self.client.playerSkills[skill] += 1
            else:
                self.client.playerSkills[skill] = 1
            self.sendShamanSkills(True)
            
            self.client.sendBullePacket(Identifiers.bulle.BU_UpdateShamanSkill, self.client.playerID, base64.b64encode(";".join(map(lambda skill: "%s:%s" %(skill[0], skill[1]), self.client.playerSkills.items())).encode()).decode('utf-8'))
            

    def sendRedistributeSkills(self):
        if self.client.shopCheeses >= self.client.shamanLevel:
            if len(self.client.playerSkills) >=  1:
                if self.client.canRedistributeSkills:
                    self.client.shopCheeses -= self.client.shamanLevel
                    self.client.playerSkills = {}
                    self.sendShamanSkills(True)
                    self.client.canRedistributeSkills = False
                    self.client.totemInfo = [0, ""]
                    if self.client.resSkillsTimer != None: self.client.resSkillsTimer.cancel()
                    self.client.resSkillsTimer = self.server.loop.call_later(600, setattr, self, "canRedistributeSkills", True)
                else:
                    self.client.sendPacket(Identifiers.send.Redistribute_Error_Time)
        else:
            self.client.sendPacket(Identifiers.send.Redistribute_Error_Cheeses)

    def sendShamanSkills(self, refresh):
        packet = ByteArray().writeByte(len(self.client.playerSkills))
        for skill in self.client.playerSkills.items():
            packet.writeByte(skill[0]).writeByte(5 if self.client.playerSkills[skill[0]] >= 6 else skill[1])
        packet.writeBoolean(refresh)
        self.client.sendPacket(Identifiers.send.Shaman_Skills, packet.toByteArray())
        
#coding: utf-8
import json
import random

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

class DailyQuests:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        
        # Get
    def getMissions(self):
        rs = self.server.cursor['DailyQuests'].find_one({'PlayerID' : self.client.playerID})
        if rs:
            self.client.playerMissions = json.loads(rs['Quests'])
            self.client.missionsCompleted = rs['totalfinished_missions']
        else:
            i = 0
            while i < 3:
                self.randomMission()
                i += 1
            self.server.cursor['DailyQuests'].insert_one({'PlayerID' : self.client.playerID, 'Quests':json.dumps(self.client.playerMissions), 'totalfinished_missions':0})
        
        # Load
    def loadMissions(self):
        if self.client.isGuest:
            return
            
        self.getMissions()
        self.sendMissionsMark()
        
        # Send
    def sendMissionsMark(self):
    	self.client.sendPacket(Identifiers.send.DailyQuests_Mark, ByteArray().writeBoolean(True).toByteArray())
        
        # Other
    def changeMission(self, missionID):
        if self.client.canChangeMission:
            can = False
            mission = self.randomMission(True)
            self.client.playerMissions[mission[0]] = [mission[0], mission[1], mission[2], mission[3], mission[4], True]

            if int(missionID) in [2]:
                for missionID in ['2_1','2_2','2_3']: 
                    if missionID in self.client.playerMissions:
                        del self.client.playerMissions[missionID]
                        can = True
                        break

            elif missionID in self.client.playerMissions:
                del self.client.playerMissions[missionID]
                can = True
                
            if not can:
                self.client.Logger.warn(f"[{self.client.playerName}] Can't delete the mission: {missionID}.")

            self.client.canChangeMission = False
            self.server.loop.call_later(3600, setattr, self.client, "canChangeMission", True)
            self.sendMissions()
        
    def increaseMissionProgress(self, missionID, missionType=0):
        if missionID in self.client.playerMissions:
            mission = self.client.playerMissions[missionID]
            mission[2] += 1
            if mission[2] >= mission[3]:
                self.completeMission(missionID)
            else:
                self.client.sendPacket(Identifiers.send.Complete_Mission, ByteArray().writeShort(missionID).writeByte(0).writeShort(mission[2]).writeShort(mission[3]).writeShort(mission[4]).writeShort(0).toByteArray())
        
    def randomMission(self, isTrue=False): 
        missionID,missionType = random.randint(1, 7), 0
        while str(missionID) in self.client.playerMissions:
            missionID = random.randint(1, 7)

        if missionID == 2:
            missionType = random.randint(1, 3)

        collect = random.choice({10:[20,40,60],21:[50],22:[70],23:[90],30:[50,70,90],40:[20,40,60],50:[20,40,60],60:[1],70:[3,5,7]}[int(f'{missionID}{missionType}')])
        reward = {20:20,40:35,60:50,50:20,70:35,90:50,3:20,5:35,7:50,1:25}[collect]
        
        if missionType != 0: missionID = f'{missionID}_{missionType}'
        else: missionID = str(missionID)
        if isTrue:
            return [missionID, missionType, 0, collect, reward, True]
        else:
            self.client.playerMissions[missionID] = [missionID, missionType, 0, collect, reward, True]
               
    def updateMissions(self):
        self.server.cursor['missions'].update_one({'PlayerID' : self.client.playerID}, {'$set':{'Quests':json.dumps(self.client.playerMissions),'totalfinished_missions':self.client.missionsCompleted}})
               
               
    def sendMissions(self):
        p = ByteArray()
        p.writeByte(len(self.client.playerMissions) + 1)
        for id, mission in self.client.playerMissions.items():
            p.writeShort(mission[0].split('_')[0]) # langues -> $QJTFM_% (short)%
            p.writeByte(mission[1])
            p.writeShort(mission[2])
            p.writeShort(mission[3])
            p.writeShort(mission[4])
            p.writeShort(0)
            p.writeBoolean(True)
            
        # 4
        p.writeByte(237)
        p.writeByte(129)
        p.writeByte(0)
        p.writeShort(int(self.client.missionsCompleted))
        p.writeShort(20)
        p.writeInt(20)
        p.writeBoolean(False)
        self.client.sendPacket(Identifiers.send.Send_Missions, p.toByteArray())
            
    def completeMission(self, missionID):
        pass
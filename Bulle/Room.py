#coding: utf-8
import asyncio
import random
import time

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.Time import Time

loop = asyncio.get_event_loop()
def call_later(time, function, *args, **kwargs):
    async def async_call():
        try:
            if asyncio.iscoroutinefunction(function):
                result = await function(*args, **kwargs)
            else:
                result = function(*args, **kwargs)
            return result
        except Exception as e:
            print(f"Error in call_later {function} : {e}")
            return None
    
    # Schedule async_call to run after `time` seconds
    def schedule_async_call():
        asyncio.create_task(async_call())

    return loop.call_later(time, schedule_async_call)

class Room:
    def __init__(self, _client, _roomName, _roomCreator):
        self.client = _client
        self.server = _client.server
        self.roomCreator = _roomCreator
        self.CursorMaps = self.server.CursorMaps
        
        self.isUsingShamanSkills = True
        #self.musicTime = 0
        self.forceNextShaman = -1
        #self.musicSkipVotes = 0
        self.isPlayingMusic = False
        self.musicSkipVotes = 0
        self.maximumPlayers = 20
        
        # Integer
        self.addTime = 0
        self.changeMapAttemps = 0
        self.companionBox = -1
        self.currentMap = 0
        self.currentMonsterId = 0
        self.currentShamanCode = -1
        self.currentShamanType = -1
        self.currentSecondShamanCode = -1
        self.currentSecondShamanType = -1
        self.currentSyncCode = -1
        self.cloudID = -1
        self.editeurMapCode = 0
        self.FSnumCompleted = 0
        self.gameStartTime = 0
        self.gameStartTimeMillis = 0
        self.lastImageID = 0
        self.lastRoundCode = 0
        self.lastPhysicalObjectId = 0
        self.mapCode = -1
        self.mapStatus = -1
        self.mapPerma = -1
        self.mapYesVotes = 0
        self.mapNoVotes = 0
        self.miceWeight = 200
        self.mulodromeBlueCount = 0
        self.mulodromeRedCount = 0
        self.mulodromeRoundCount = 0
        self.numCompleted = 0
        self.objectID = 0
        self.receivedNo = 0
        self.receivedYes = 0
        self.roundsCount = -1
        self.roundTime = 120
        self.SSnumCompleted = 0
        
        self.deathRemaining = 0
        
        # Boolean
        self.canChangeMap = True
        self.canChangeMusic = True
        self.changed20secTimer = False
        self.catchTheCheeseMap = False
        self.countStats = False
        self.EMapValidated = False
        self.initVotingMode = True
        self.isAutoMapFlipMode = True
        self.isAutoRespawn = False
        self.isAutoScore = True
        self.isBootcamp = False
        self.isDefilante = False
        self.isDisabledAfkKill = False
        self.isDoubleMap = False
        self.isEditeur = False
        self.isEventTime = False
        self.isFixedMap = False
        self.isFuncorp = False
        self.isNoShaman = False
        self.isNoShamanMap = False
        self.isNormal = False
        self.isMulodrome = False
        self.isMusic = False
        self.isNoAdventureMaps = False
        self.isRacing = False
        self.isSpecificMap = False
        self.isSurvivor = False
        self.isTotemEditor = False
        self.isTribeHouse = False
        self.isTribeHouseMap = False
        self.isTutorial = False
        self.isVanilla = False
        self.isVillage = False
        self.mapInverted = False
        self.never20secTimer = False
        self.notUpdatedScore = False
        self.isVotingBox = False
        self.isVotingMode = False
        
        self.isDisabledMiceCollision = False
        self.isDisabledFallDamage = True
        
        # String
        self.currentShamanName = ""
        self.currentSecondShamanName = ""
        self.currentSyncName = ""
        self.editeurMapXML = ""
        self.eventType = ""
        self.forceNextMap = "-1"
        self.mapName = ""
        self.mapXML = ""
        self.roomCommunity = ""
        self.roomFullName = _roomName
        self.roomName = ""
        
        # List
        self.anchors = []
        self.lastHandymouse = [-1, -1]
        self.mulodromeRedTeam = []
        self.mulodromeBlueTeam = []
        self.roomFuncorps = []
        self.roomTimers = []
        self.holesList = []
        self.musicVideos = []
        self.cheesesList = []
        
        # Dictionary
        self.currentShamanSkills = {}
        self.currentSecondShamanSkills = {}
        self.players = {}
        self.monsterLifes = {}
        
        # Timers
        self.autoRespawnTimer = None
        self.changeMapTimer = None
        self.endSnowTimer = None
        self.killAfkTimer = None
        self.voteCloseTimer = None
        self.startTimerLeft = None
        self.eventHallowenInvasionTimer = None
        
        # Maps
        self.doubleShamanMaps = self.server.mapsInfo["doubleShamanMaps"]
        self.noShamanMaps = self.server.mapsInfo["noShamanMaps"]
        self.catchCheeseMaps = self.server.mapsInfo["catchCheeseMaps"]
        self.mapList = self.server.mapsInfo["normalMaps"]
        self.transformationMaps = self.server.mapsInfo["transformationMaps"]
        
        self.roomCommunity = _roomName.split("-")[0].lower()
        self.roomName = _roomName.split("-")[1]
        
        self.checkRoomName()

    def checkIfShamanIsDead(self) -> bool:
        player = self.players.get(self.currentShamanName)
        if player == None:
            return False
        return player.isDead

    async def checkChangeMap(self):
        if (not (self.isBootcamp or self.isAutoRespawn or self.isTribeHouse and self.isTribeHouseMap or self.isFixedMap)):
            alivePeople = self.getPlayerCountAlive()
            if not alivePeople:
                self.canChangeMap = True
                await self.mapChange()

    def checkMapXML(self):
        if int(self.currentMap) in self.server.vanillaMaps:
            self.mapCode = int(self.currentMap)
            self.mapName = '_Atelier 801' if self.mapCode == 801 else 'Transformice'
            self.mapXML = str(self.server.vanillaMaps[int(self.currentMap)])
            self.mapYesVotes = 0
            self.mapNoVotes = 0
            self.mapPerma = -1
            self.currentMap = -1
            self.mapInverted = False
            
        elif self.isEventTime:
            x = random.randint(0, 3)
        
            self.mapCode = 100002
            self.mapName = self.server.bulleInfo["event_info"]["mapname"]
            self.mapXML = str(self.server.eventMaps[self.server.bulleInfo["event_info"]["name"] + "_" + str(self.server.bulleInfo["event_info"]["maps"][x]["id"])])
            self.mapYesVotes = 0
            self.mapNoVotes = 0
            self.mapPerma = -1
            self.currentMap = -1
            self.mapInverted = False
            self.eventType = str(self.server.bulleInfo["event_info"]["maps"][x]["type"])

    def checkRoomName(self):
        roomNameCheck = self.roomName[1:] if self.roomName.startswith("*") or self.roomName.startswith("@") else self.roomName
        if self.roomName.startswith("\x03[Editeur] "):
            self.countStats = False
            self.isEditeur = True
            self.never20secTimer = True

        elif self.roomName.startswith("\x03[Tutorial] "):
            self.countStats = False
            self.isTutorial = True
            self.currentMap = 900
            self.isSpecificMap = True
            self.isNoShaman = True
            self.never20secTimer = True
            
        elif self.roomName.startswith("\x03[Totem] "):
            self.countStats = False
            self.isTotemEditor = True
            self.currentMap = 444
            self.isSpecificMap = True
            self.never20secTimer = True
            
        elif self.roomName.startswith("*\x03"):
            # Tribe house map
            self.countStats = False
            self.isTribeHouse = True
            self.isNoShaman = True
            self.isAutoRespawn = True
            self.isDisabledAfkKill = True
            self.isFixedMap = True
            self.roundTime = 0
            self.never20secTimer = True

        elif roomNameCheck.startswith("801") or roomNameCheck.startswith("village"):
            self.countStats = False
            self.isVillage = True
            self.isNoShaman = True
            self.isAutoRespawn = True
            self.isDisabledAfkKill = True
            self.isFixedMap = True
            self.roundTime = 0
            self.never20secTimer = True
            
        elif "music" in roomNameCheck.lower():
            self.countStats = True
            self.isMusic = True

        elif "racing" in roomNameCheck.lower():
            self.countStats = True
            self.isRacing = True
            self.isNoShaman = True
            self.roundTime = 63

        elif "bootcamp" in roomNameCheck.lower():
            self.countStats = True
            self.isBootcamp = True
            self.isNoShaman = True
            self.isAutoRespawn = True
            self.roundTime = 360
            self.never20secTimer = True

        elif "vanilla" in roomNameCheck.lower():
            self.countStats = True
            self.isVanilla = True

        elif "survivor" in roomNameCheck.lower():
            self.countStats = True
            self.isSurvivor = True
            self.roundTime = 90

        elif "defilante" in roomNameCheck.lower():
            self.countStats = False
            self.isDefilante = True
            self.isNoShaman = True
            
        elif roomNameCheck.startswith("*strm_"):
            self.countStats = False
            
        else:
            self.isNormal = True

    def checkDeadPlayersPercentage(self, percentage) -> bool:
        dead_people = self.getDeathCountNoShaman()
        all_people = self.getPlayerCountNotNew()
        p = (percentage * 100) / all_people
        return dead_people >= p

    def checkIfDoubleShamansAreDead(self) -> bool:
        player1 = self.players.get(self.currentShamanName)
        player2 = self.players.get(self.currentSecondShamanName)
        return (False if player1 == None else player1.isDead) and (False if player2 == None else player2.isDead)

    def checkIfShamanCanGoIn(self) -> bool:
        for player in self.players.copy().values():
            if player.playerCode != self.currentShamanCode and player.playerCode != self.currentSecondShamanCode and not player.isDead:
                return False
        return True

    def getDeathCountNoShaman(self) -> int:
        return len(list(filter(lambda player: not player.isShaman and not player.isNewPlayer, self.players.copy().values())))

    def getDoubleShamanCode(self) -> int:
        if self.currentShamanCode == -1 and self.currentSecondShamanCode == -1:
            if self.forceNextShaman > 0:
                self.currentShamanCode = self.forceNextShaman
                self.forceNextShaman = 0
            else:
                self.currentShamanCode = self.getHighestScore()

            if self.currentSecondShamanCode == -1:
                self.currentSecondShamanCode = self.getSecondHighestScore()

            if self.currentSecondShamanCode == self.currentShamanCode:
                tempClient = random.choice(list(self.players.copy().values()))
                self.currentSecondShamanCode = tempClient.playerCode

            for player in self.players.copy().values():
                if player.playerCode == self.currentShamanCode:
                    self.currentShamanName = player.playerName
                    self.currentShamanType = player.shamanType
                    if not player.isNoSkill and not self.isUsingShamanSkills:
                        self.currentShamanSkills = player.playerSkills
                    break

                if player.playerCode == self.currentSecondShamanCode:
                    self.currentSecondShamanName = player.playerName
                    self.currentSecondShamanType = player.shamanType
                    if not player.isNoSkill and not self.isUsingShamanSkills:
                        self.currentSecondShamanSkills = player.playerSkills
                    break

        return [self.currentShamanCode, self.currentSecondShamanCode]

    def getHighestScore(self) -> int:
        playerScores = []
        playerID = 0
        for player in self.players.copy().values():
            playerScores.append(player.playerScore)
                    
        for player in self.players.copy().values():
            if player.playerScore == max(playerScores):
                playerID = player.playerCode
        return playerID

    def getMapInfo(self, mapCode) -> list:
        if mapCode in self.server.cachedmaps: 
            return self.server.cachedmaps[mapCode]
        mapInfo = ["", "", 0, 0, 0]
        self.CursorMaps.execute("SELECT * from Maps where Code = ?", [mapCode])
        rs = self.CursorMaps.fetchone()
        if rs:
            mapInfo = rs["Name"], rs["XML"], rs["YesVotes"], rs["NoVotes"], rs["Perma"]
            self.server.cachedmaps[mapCode] = mapInfo
        return mapInfo

    def getPlayerCount(self) -> int:
        return len(list(filter(lambda player: not player.isHidden, self.players.copy().values())))

    def getPlayerCountAlive(self) -> int:
        return len(list(filter(lambda player: not player.isDead and not player.isNewPlayer, self.players.values())))

    def getPlayerCountNotNew(self) -> int:
        return len(list(filter(lambda player: not player.isHidden and not player.isNewPlayer, self.players.copy().values())))

    def getPlayerCountUnique(self) -> int:
        return len(list({player.ipAddress for player in self.players.copy().values()}))

    def getPlayerList(self) -> list:
        result = b""
        i = 0
        for player in self.players.copy().values():
            if not player.isHidden:
                result += player.getPlayerData()
                i += 1

        return [i, result]

    def getSecondHighestScore(self) -> int:
        playerScores = []
        playerID = 0
        for player in self.players.copy().values():
            playerScores.append(player.playerScore)
        playerScores.remove(max(playerScores))

        if len(playerScores) >= 1:
            for player in self.players.copy().values():
                if player.playerScore == max(playerScores):
                    playerID = player.playerCode
        return playerID

    def getShamanCode(self) -> int:
        if self.isNoShamanMap or self.isNoShaman or self.currentMap in self.noShamanMaps:
            return
    
        if self.currentShamanCode == -1:
            if self.forceNextShaman > 0:
                self.currentShamanCode = self.forceNextShaman
                self.forceNextShaman = 0
            else:
                self.currentShamanCode = self.getHighestScore()

            if self.currentShamanCode == -1:
                self.currentShamanName = ""
            else:
                for player in self.players.copy().values():
                    if player.playerCode == self.currentShamanCode:
                        self.currentShamanName = player.playerName
                        self.currentShamanType = player.shamanType
                        if not player.isNoSkill and not self.isUsingShamanSkills:
                            self.currentShamanSkills = player.playerSkills
                        break
        return self.currentShamanCode

    def getSyncCode(self) -> int:
        if self.getPlayerCount() > 0:
            if self.currentSyncCode == -1:
                player = random.choice(list(self.players.values()))
                self.currentSyncCode = player.playerCode
                self.currentSyncName = player.playerName
        else:
            if self.currentSyncCode == -1:
                self.currentSyncCode = 0
                self.currentSyncName = ""
        
        return self.currentSyncCode



    def send20SecRemainingTimer(self):
        if not self.changed20secTimer:
            if not self.never20secTimer and self.roundTime + (self.gameStartTime - Time.getTime()) > 21:
                self.changed20secTimer = True
                self.changeMapTimers(20)
                for player in self.players.copy().values():
                    player.sendRoundTime(20)

    def sendAll(self, identifiers, packet=""):
        for player in self.players.copy().values():
            player.sendPacket(identifiers, packet)

    def sendAllChat(self, playerName, message, isOnly):
        p = ByteArray().writeUTF(playerName).writeUTF(message).writeBoolean(True)
        if isOnly != 2:
            for client in self.players.copy().values():
                client.sendPacket(Identifiers.send.Chat_Message, p.toByteArray())
        else:
            player = self.players.get(playerName)
            if player != None:
                player.sendPacket(Identifiers.send.Chat_Message, p.toByteArray())

    def sendAllOthers(self, senderClient, identifiers, packet=""):
        for player in self.players.copy().values():
            if player != senderClient:
                player.sendPacket(identifiers, packet)

    def sendEventMapAction(self, action):
        self.sendAll(Identifiers.send.Event_Map_Action, ByteArray().writeShort(action).toByteArray())

    def sendMulodromeRound(self):
        self.sendAll(Identifiers.send.Mulodrome_Result, ByteArray().writeByte(self.mulodromeRoundCount).writeShort(self.mulodromeBlueCount).writeShort(self.mulodromeRedCount).toByteArray())
        if self.mulodromeRoundCount > 10:
            self.sendAll(Identifiers.send.Mulodrome_End)
            self.sendAll(Identifiers.send.Mulodrome_Winner, ByteArray().writeByte(2 if self.mulodromeBlueCount == self.mulodromeRedCount else (1 if self.mulodromeBlueCount < self.mulodromeRedCount else 0)).writeShort(self.mulodromeBlueCount).writeShort(self.mulodromeRedCount).toByteArray())
            self.isMulodrome = False
            self.mulodromeRoundCount = 0
            self.mulodromeRedCount = 0
            self.mulodromeRedTeam = []
            self.mulodromeBlueCount = 0
            self.mulodromeBlueTeam = []
            
            self.isRacing = False
            self.never20secTimer = False
            self.isNoShaman = False

    def sendMusicVideo(self):
        self.sendAll(Identifiers.send.Music_Video, ByteArray().writeUTF(self.musicVideos[0]["VideoID"]).writeUTF(self.musicVideos[0]["Title"]).writeShort(self.musicVideos[0]["Duration"]).writeUTF(self.musicVideos[0]["By"]).toByteArray())

    def sendNewMonster(self, x, y, monstyp):
        self.monsterLifes[self.currentMonsterId] = (10 * self.getPlayerCountAlive() if monstyp == "chat" else 5)
        self.sendAll(Identifiers.send.Spawn_Halloween_Monster, ByteArray().writeInt(self.currentMonsterId).writeInt(x).writeInt(y).writeUTF(monstyp).toByteArray())
        if monstyp != "chat":
            self.client.sendPacket(Identifiers.send.Halloween_Monster_Speed, ByteArray().writeInt(self.currentMonsterId).writeInt(-2).toByteArray())
            
        self.currentMonsterId += 1

    def sendNPC(self, npcName, data={}, interface=11):
        p = ByteArray().writeInt(data["id"])
        p.writeUTF(npcName)
        p.writeShort(data["title"])
        p.writeBoolean(data["isgirl"])
        p.writeUTF(data["look"])
        p.writeInt128(int(data["x"]))
        p.writeInt128(int(data["y"]))
        p.writeInt128(int(data["emotie"]) if "emotie" in data else -1)
        p.writeBoolean(bool(data["lookLeft"]))
        p.writeBoolean(bool(data["starePlayer"]))
        p.writeInt128(int(data["interface"]) if "interface" in data else interface)
        p.writeUTF(data["message"])
        self.sendAll(Identifiers.send.NPC, p.toByteArray())

    def sendRoomFunCorp(self):
        self.isFuncorp = not self.isFuncorp
        for player in self.players.copy().values():
            if self.isFuncorp:
                player.sendLangueMessage("", "<FC>$FunCorpActive</FC>")
                if player.checkStaffPermission(["FC", "Admin"]):
                    self.roomFuncorps.append(player.playerName)
            else:
                player.sendLangueMessage("", "<FC>$FunCorpDesactive</FC>")
                
        if not self.isFuncorp:
            self.roomFuncorps = []

    def sendRoomStartTimer(self):
        for player in self.players.copy().values():
            player.sendMapStartTimer(False)

    def sendVampireMode(self, others=False):
        player = self.players.get(self.currentSyncName)
        if player != None:
            player.sendVampireMode(others)


    # Other functions

    def addClient(self, player, newRoom=False, local_3=False, local_4=True) -> bool:
        self.players[player.playerName] = player
        player.room = self
        
        if not newRoom:
            player.isDead = True
            if not player.isHidden:
                self.sendAllOthers(player, Identifiers.send.Player_Respawn, ByteArray().writeBytes(player.getPlayerData()).writeBoolean(local_3).writeBoolean(local_4).toByteArray())
            player.startPlay()
        else:
            player.room.roomCreator = player.playerName

    def changeMapTimers(self, seconds):
        if self.changeMapTimer != None: self.changeMapTimer.cancel()
        self.canChangeMap = True
        self.changeMapTimer = self.server.loop.call_later(seconds, self.mapChange)

    async def closeVoting(self):
        self.initVotingMode = False
        self.isVotingBox = False
        if self.voteCloseTimer != None: 
            self.voteCloseTimer.cancel()
        await self.mapChange()

    async def killAfkPlayers(self):
        if self.isEditeur or self.isTotemEditor or self.isBootcamp or self.isTribeHouseMap or self.isDisabledAfkKill:
            return
            
        if ((Time.getTime() - self.gameStartTime) < 32 and (Time.getTime() - self.gameStartTime) > 28):
            for player in self.players.copy().values():
                if not player.isDead and player.isAfk:
                    player.isDead = True
                    if self.isAutoScore: 
                        player.playerScore += 1
                    player.sendPlayerDied(True)
            await self.checkChangeMap()

    async def mapChange(self):
        if self.changeMapTimer != None: 
            self.changeMapTimer.cancel()
            
        if not self.canChangeMap:
            self.changeMapAttemps += 1
            if self.changeMapAttemps < 5:
                await asyncio.sleep(1)
                return await self.mapChange()
            
        for timer in self.roomTimers:
            timer.cancel()
        self.roomTimers = []
        
        for timer in [self.autoRespawnTimer, self.killAfkTimer, self.startTimerLeft, self.voteCloseTimer, self.eventHallowenInvasionTimer]:
            if timer != None:
                timer.cancel()
    
        if self.initVotingMode:
            if not self.isVotingBox and (self.mapPerma == 0 and self.mapCode != -1) and self.getPlayerCount() >= self.server.bulleInfo["minimum_players"]:
                self.isVotingMode = True
                self.isVotingBox = True
                self.voteCloseTimer = call_later(8, self.closeVoting())
                for player in self.players.copy().values():
                    player.sendPacket(Identifiers.old.send.Vote_Box, [self.mapName, self.mapYesVotes, self.mapNoVotes])
                return
            else:
                self.votingMode = False
                return await self.closeVoting()
        elif self.isTribeHouse and self.isTribeHouseMap:
            pass
        else:
            if self.isVotingMode:
                TotalYes = self.mapYesVotes + self.receivedYes
                TotalNo = self.mapNoVotes + self.receivedNo
                isDel = False

                if TotalYes + TotalNo >= 100:
                    TotalVotes = TotalYes + TotalNo
                    Rating = (1.0 * TotalYes / TotalNo) * 100
                    rate = str(Rating).split(".")
                    if int(rate[0]) < 50:
                        isDel = True
                self.CursorMaps.execute("update Maps set YesVotes = ?, NoVotes = ?, Perma = 44 where Code = ?" if isDel else "update Maps set YesVotes = ?, NoVotes = ? where Code = ?", [TotalYes, TotalNo, self.mapCode])
                self.isVotingMode = False
                self.receivedNo = 0
                self.receivedYes = 0
            
            self.initVotingMode = True
            self.lastRoundCode = (self.lastRoundCode + 1) % 127
            
            if self.getPlayerCount() >= self.server.bulleInfo["minimum_players"]:
                self.giveStats(0 if self.isRacing else 1 if self.isSurvivor else 2)
            
            if self.isSurvivor:
                for player in self.players.values():
                    if not player.isDead and (not player.isVampire if self.mapPerma == 11 else not player.isShaman):
                        if self.isAutoScore: 
                            player.playerScore += 10
            
            if self.catchTheCheeseMap:
                self.catchTheCheeseMap = False
            else:
                if self.getPlayerCount() >= self.server.bulleInfo["minimum_players"]:
                    numCom = self.FSnumCompleted - 1 if self.isDoubleMap else self.numCompleted - 1
                    numCom2 = self.SSnumCompleted - 1 if self.isDoubleMap else 0
                    if numCom < 0: numCom = 0
                    if numCom2 < 0: numCom2 = 0
                    
                    player = self.players.get(self.currentShamanName)
                    if player != None:
                        self.sendAll(Identifiers.old.send.Shaman_Perfomance, [self.currentShamanName, numCom])
                        if self.isAutoScore: 
                            player.playerScore = numCom
                        if numCom > 0:
                            player.Skills.earnExp(True, numCom)

                    player2 = self.players.get(self.currentSecondShamanName)
                    if player2 != None:
                        self.sendAll(Identifiers.old.send.Shaman_Perfomance, [self.currentSecondShamanName, numCom2])
                        if self.isAutoScore: 
                            player2.playerScore = numCom2
                        if numCom2 > 0:
                            player2.Skills.earnExp(True, numCom2)
                       
            self.currentSyncCode = -1
            self.currentShamanCode = -1
            self.currentShamanType = -1
            self.currentSecondShamanCode = -1
            self.currentSecondShamanType = -1

            self.currentSyncName = ""
            self.currentShamanName = ""
            self.currentSecondShamanName = ""
            
            self.currentShamanSkills = {}
            self.currentSecondShamanSkills = {}
            
            self.changed20secTimer = False
            self.isDoubleMap = False
            self.isNoShamanMap = False
            self.isTribeHouseMap = False
            self.canChangeMusic = True
            self.canChangeMap = True
            
            self.FSnumCompleted = 0
            self.SSnumCompleted = 0
            self.objectID = 0
            self.addTime = 0
            self.cloudID = -1
            self.companionBox = -1
            self.changeMapAttemps = 0
            self.numCompleted = 0
            
            self.anchors = []
            self.lastHandymouse = [-1, -1]
            
            if self.getPlayerCount() > 1:
                self.mapStatus = (self.mapStatus + 1) % 15
                if self.mapStatus == 0:
                    self.isEventTime = True
            else:
                self.mapStatus = -1

            self.currentMap = await self.selectMap()
            self.checkMapXML()
            
            if (self.currentMap in self.doubleShamanMaps) or (self.mapPerma == 8 and self.getPlayerCount() >= 3):
                self.isDoubleMap = True

            if self.mapPerma in [7, 17, 42] or (self.isSurvivor and self.mapStatus == 0) or (self.currentMap in self.noShamanMaps) or (self.isEventTime and self.mapStatus == 0):
                self.isNoShamanMap = True

            if self.currentMap in self.catchCheeseMaps:
                self.catchTheCheeseMap = True
            
            self.gameStartTime = Time.getTime()
            self.gameStartTimeMillis = time.time()

            for player in self.players.copy().values():
                player.resetPlay()

            for player in self.players.copy().values():
                player.startPlay()
                
                if player.isHidden:
                    player.isDead = True
                    player.sendPlayerDied(False)
                    
            for player in self.players.copy().values():
                if player.petType != 0:
                    if Time.getSecondsDiff(player.petEnd) >= 0:
                        self.sendAll(Identifiers.send.Pet, ByteArray().writeInt(player.playerCode).writeUnsignedByte(player.petType).toByteArray())
                    else:
                        player.petType = 0
                        player.petEnd = 0
                if player.furType != 0:
                    if Time.getSecondsDiff(player.furEnd) <= 0:
                        player.furType = 0
                        player.furEnd = 0
                    
            if self.isMulodrome:
                self.mulodromeRoundCount += 1
                self.sendMulodromeRound()
                if self.mulodromeRoundCount <= 10:
                    for player in self.players.copy().values():
                        if player.playerName in self.mulodromeBlueTeam:
                            self.setNameColor(player.playerName, 0x979EFF)
                        elif player.playerName in self.mulodromeRedTeam:
                            self.setNameColor(player.playerName, 0xFF9396)
                else:
                    self.sendAll(Identifiers.send.Mulodrome_End)
            
            if (self.isRacing or self.isDefilante) and self.notUpdatedScore:
                self.roundsCount = (self.roundsCount + 1) % 10
                self.notUpdatedScore = False
                self.sendAll(Identifiers.send.Rounds_Count, ByteArray().writeByte(self.roundsCount).writeInt(self.getHighestScore()).toByteArray())
            
            if self.mapStatus == 0 and not self.isNoAdventureMaps:
                self.isEventTime = True
            else:
                self.isEventTime = False
            
            if self.isSurvivor and self.mapStatus == 0 and self.getPlayerCountAlive() > 1 and not self.isEventTime:
                self.server.loop.call_later(5, self.sendVampireMode)
                            
            self.startTimerLeft = call_later(3, self.sendRoomStartTimer)
            if not self.isFixedMap and not self.isTribeHouse and not self.isTribeHouseMap:
                self.changeMapTimer = call_later(self.roundTime + self.addTime, self.mapChange)
            
            self.killAfkTimer = call_later(30, self.killAfkPlayers)
            if self.isAutoRespawn or self.isTribeHouseMap:
                self.autoRespawnTimer = call_later(2, self.respawnMice)
                
            self.sendAll(Identifiers.send.Add_Collectible_Packet, ByteArray().writeUnsignedByte(26).writeUnsignedShort(0).writeUnsignedByte(random.randint(33, 37)).writeShort(random.randint(0, 800 - 1)).writeShort(random.randint(0, 600 - 1)).toByteArray())
                
            if self.isEventTime:
                if self.eventType == "halloween__mapid_monsterboss":          
                    self.sendNewMonster(700, 280, "chat")
                
                    for player in self.players.copy().values():
                        player.sendPlayerLifes(4)
                        
                    self.sendEventMapAction(4)
                    self.eventHallowenInvasionTimer = call_later(4, self.spawnInvasion, 400, 280, "f")
                    
                elif self.eventType == "halloween__mapid_room":
                    for player in self.players.copy().values():
                        player.sendPlayerLifes(4)
                        await player.sendGiveCheese(0, 0, 0, 0)
                        
                    self.eventHallowenInvasionTimer = call_later(4, self.spawnInvasion, 1916, 332, "f", 799, 328, "sq")
                    
                elif self.eventType == "halloween__mapid_broombloommap":
                    ground_info = [
                        [False, 12, 300, 57, 26, 113, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 300, 339, 26, 119, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 443, 33, 26, 65, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 443, 312, 26, 173, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 622, 52, 26, 103, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 622, 342, 26, 113, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 760, 86, 26, 170, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 760, 367, 26, 64, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 900, 87, 26, 173, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 900, 367, 26, 63, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1066, 86, 26, 170, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1066, 364, 26, 70, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1196, 73, 26, 144, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1196, 358, 26, 82, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1365, 96, 26, 191, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1365, 369, 26, 59, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1543, 88, 26, 175, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1543, 369, 26, 59, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1690, 76, 26, 150, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1690, 353, 26, 92, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 1867, 44, 26, 87, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 1867, 322, 26, 153, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2018, 74, 26, 146, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2018, 339, 26, 120, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2197, 95, 26, 188, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2197, 361, 26, 76, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2338, 52, 26, 103, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2338, 316, 26, 165, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2503, 100, 26, 198, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2503, 361, 26, 76, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2646, 37, 26, 72, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2646, 296, 26, 206, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2777, 61, 26, 120, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2777, 312, 26, 174, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 2933, 101, 26, 201, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 2933, 350, 26, 97, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 3063, 52, 26, 103, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 3063, 303, 26, 191, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 3227, 110, 26, 218, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 3227, 359, 26, 80, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 3393, 93, 26, 185, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 3393, 339, 26, 119, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 3552, 75, 26, 149, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 3552, 327, 26, 143, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        
                        [False, 12, 3690, 87, 26, 172, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False],
                        [False, 12, 3690, 333, 26, 132, False, 0.3, 0.2, 0, False, 0, True, True, True, 0, 0.0, 0.0, True, '', False]
                    ]
                    random.shuffle(ground_info)

                    for info in ground_info:
                        self.sendPhysicalObject(self.lastPhysicalObjectId, info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9], info[10], info[11], info[12], info[13], info[14], info[15], info[16], info[17], info[18], info[19], info[20])
                        self.sendImage('x_transformice/x_maps/x_halloween2015/ronces1.png', False, info[2], info[3], info[4], info[5], 0, False, False, False, '')
                        self.sendEventMapAction(2)
                    
                elif self.eventType == "halloween__mapid_casino":
                    pass
                    
            else:
                if self.currentMonsterId > 0:
                    for x in range(0, self.currentMonsterId):
                        self.client.sendPacket(Identifiers.send.Remove_Monster, ByteArray().writeInt(self.currentMonsterId).toByteArray())
                    self.currentMonsterId = 0

    async def removeClient(self, player):
        if player.playerName in self.players:
            # Player left the room
            
            del self.players[player.playerName]
            player.resetPlay()
            player.isDead = True
            player.playerScore = 0
            player.sendPlayerDisconnect()
            
            if self.isMulodrome:
                if player.playerName in self.mulodromeRedTeam: self.mulodromeRedTeam.remove(player.playerName)
                if player.playerName in self.mulodromeBlueTeam: self.mulodromeBlueTeam.remove(player.playerName)

                if len(self.mulodromeRedTeam) == 0 and len(self.mulodromeBlueTeam) == 0:
                    self.mulodromeRoundCount = 10
                    self.sendMulodromeRound()
            
            if len(self.players) == 0:
                # There are no players in current room
                
                for timer in [self.autoRespawnTimer, self.changeMapTimer, self.endSnowTimer, self.killAfkTimer, self.voteCloseTimer, self.eventHallowenInvasionTimer]:
                    if timer != None:
                        timer.cancel()
                        
                del self.server.bulle_rooms[self.roomFullName]
            else:
                if player.playerCode == self.currentSyncCode:
                    self.currentSyncCode = -1
                    self.currentSyncName = ""
                    self.getSyncCode()
                await self.checkChangeMap()

    def respawnMice(self):
        for player in self.players.copy().values():
            self.respawnSpecific(player.playerName)
            
        if self.isAutoRespawn or self.isTribeHouseMap:
            self.autoRespawnTimer = call_later(2, self.respawnMice)

    def respawnSpecific(self, playerName):
        player = self.players.get(playerName)
        if player != None and player.isDead:
            player.resetPlay()
            player.isAfk = False
            player.playerStartTimeMillis = time.time()
            self.sendAll(Identifiers.send.Player_Respawn, ByteArray().writeBytes(player.getPlayerData()).writeBoolean(False).writeBoolean(True).toByteArray())

    async def selectMap(self):
        if not self.forceNextMap == "-1":
            force = self.forceNextMap
            self.forceNextMap = "-1"
            self.mapCode = -1

            if force.isdigit():
                return await self.selectMapSpecificic(force, "Vanilla")
            elif force.startswith("@"):
                return await self.selectMapSpecificic(force[1:], "Custom")
            elif force.startswith("#"):
                return await self.selectMapSpecificic(force[1:], "Perm")
            elif force.startswith("<"):
                return await self.selectMapSpecificic(force, "Xml")
            else:
                return 0
        else:
            if self.isEditeur:
                return self.editeurMapCode

            elif self.isTribeHouse:
                pass

            elif self.isVillage and not self.roomName[:1] == "#":
                return 801
                
            elif self.isTutorial:
                return 900
                
            elif self.isTotemEditor:
                return 444

            elif self.isVanilla:
                self.mapCode = -1
                self.mapName = "Invalid";
                self.mapXML = "<C><P /><Z><S /><D /><O /></Z></C>"
                self.mapYesVotes = 0
                self.mapNoVotes = 0
                self.mapPerma = -1
                self.mapInverted = False
                mp = random.choice(self.mapList)
                return mp
                
            else:
                self.mapCode = -1
                self.mapName = "Invalid";
                self.mapXML = "<C><P /><Z><S /><D /><O /></Z></C>"
                self.mapYesVotes = 0
                self.mapNoVotes = 0
                self.mapPerma = -1
                self.mapInverted = False
                return await self.selectMapStatus()
        return -1

    async def selectMapSpecificic(self, code, type):
        if type == "Vanilla":
            return int(code)

        elif type == "Custom":
            mapInfo = self.getMapInfo(int(code))
            if mapInfo[0] == None:
                return 0
            else:
                self.mapCode = code
                self.mapName = mapInfo[0]
                self.mapXML = mapInfo[1]
                self.mapYesVotes = mapInfo[2]
                self.mapNoVotes = mapInfo[3]
                self.mapPerma = mapInfo[4]
                self.mapInverted = False
                return -1

        elif type == "Perm":
            self.CursorMaps.execute("select Code from Maps where Perma = ? and Code != ? order by random() limit 1", [code, self.currentMap])
            runMap = self.CursorMaps.fetchone()
            runMap = 0 if runMap == None else runMap[0]

            if runMap == 0:
                map = random.choice(self.mapList)
                while map == self.currentMap:
                    map = random.choice(self.mapList)
                return map
            else:
                mapInfo = self.getMapInfo(runMap)
                self.mapCode = runMap
                self.mapName = mapInfo[0]
                self.mapXML = mapInfo[1]
                self.mapYesVotes = mapInfo[2]
                self.mapNoVotes = mapInfo[3]
                self.mapPerma = mapInfo[4]
                self.mapInverted = False
                return -1

        elif type == "Xml":
            self.mapCode = 0
            self.mapName = "#Module"
            self.mapXML = str(code)
            self.mapYesVotes = 0
            self.mapNoVotes = 0
            self.mapPerma = 22
            self.mapInverted = False
            return -1

    async def selectMapStatus(self):
        maps = [0, -1, 4, 9, 5, 0, -1, 8, 6, 7]
        selectPerma = 17 if (self.isRacing) else (13 if self.mapStatus % 2 == 0 else 3) if self.isBootcamp else 18 if self.isDefilante else (11 if self.mapStatus == 0 else 10) if self.isSurvivor else 19 if self.isMusic and self.mapStatus % 2 == 0 else 0
        isMultiple = False

        if self.isNormal:
            if self.mapStatus < len(maps) and maps[self.mapStatus] != -1:
                isMultiple = maps[self.mapStatus] == 0
                selectPerma = maps[self.mapStatus]
            else:
                mp = random.choice(self.mapList)
                while mp == self.currentMap:
                    mp = random.choice(self.mapList)
                return mp

        elif self.isVanilla or (self.isMusic and self.mapStatus % 2 != 0):
            mp = random.choice(self.mapList)
            while mp == self.currentMap:
                mp = random.choice(self.mapList)
            return mp
            
        self.CursorMaps.execute("select * FROM maps WHERE Perma = ? ORDER BY RANDOM() LIMIT 1", [selectPerma])
        rs = self.CursorMaps.fetchone()
        if rs:
            self.mapCode = rs["Code"]
            self.mapName = rs["Name"]
            self.mapXML = rs["XML"]
            self.mapYesVotes = rs["YesVotes"]
            self.mapNoVotes = rs["NoVotes"]
            self.mapPerma = selectedPerma = rs["Perma"]
            self.mapInverted = self.isAutoMapFlipMode and random.randint(0, 100) > 75
        else:
            mp = random.choice(self.mapList)
            while mp == self.currentMap:
                mp = random.choice(self.mapList)
            return mp
        return -1

    def movePlayer(self, playerName, xPosition, yPosition, pOffSet=False, xSpeed=0, ySpeed=0, sOffSet=False, angle=0, angleOffset=False):
        player = self.players.get(playerName)
        if player != None:
            player.sendPacket(Identifiers.send.Move_Player, ByteArray().writeShort(xPosition).writeShort(yPosition).writeBoolean(pOffSet).writeShort(xSpeed).writeShort(ySpeed).writeBoolean(sOffSet).writeShort(angle).writeBoolean(angleOffset).toByteArray())


    def giveShamanSave(self, shamanName, type):
        if not self.countStats:
            return
        pass

    def newConsumableTimer(self, code):
        self.roomTimers.append(self.server.loop.call_later(10, lambda: self.sendAll(Identifiers.send.Remove_Object, ByteArray().writeInt(code).writeBoolean(False).toByteArray())))
                
                
                
    def setNameColor(self, playerName, color):
        if playerName in self.players:
            self.sendAll(Identifiers.send.Set_Name_Color, ByteArray().writeInt(self.players.get(playerName).playerCode).writeInt(color).toByteArray())
            
    def giveStats(self, typ):
        pass
        
    def removeObject(self, objectId):
        self.sendAll(Identifiers.send.Remove_Object, ByteArray().writeInt(objectId).writeBoolean(True).toByteArray())
            
    async def spawnInvasion(self, x, y, typ, x1=0, y1=0, typ1=0):
        while True:
            if self.eventHallowenInvasionTimer.cancelled():
                return
                
            if self.eventType == "halloween__mapid_monsterboss":
                self.sendEventMapAction(10)
                self.sendEventMapAction(7)
        
                self.sendNewMonster(x, y, typ)
            else:
                self.sendNewMonster(x, y, typ)
                self.sendNewMonster(x1, y1, typ1)
            
            await asyncio.sleep(5)
            
    def sendPhysicalObject(self, physic_id, dynamic, ground_id, x, y, width, height, foreground, friction, restitution, angle, has_color, color, mice_collidable, ground_collidable, fixed_rotation, mass, linear_damping, angular_damping, invisible, image_description, has_contact_listener):
        p = ByteArray()
        p.writeInt128(physic_id)
        p.writeBoolean(dynamic)
        p.writeByte(ground_id)
        p.writeInt128(x)
        p.writeInt128(y)
        p.writeInt128(width)
        p.writeInt128(height)
        p.writeBoolean(foreground)
        p.writeInt128(friction)
        p.writeInt128(restitution)
        p.writeInt128(angle)
        p.writeBoolean(has_color)
        p.writeInt(color)
        p.writeBoolean(mice_collidable)
        p.writeBoolean(ground_collidable)
        p.writeBoolean(fixed_rotation)
        p.writeInt128(mass)
        p.writeInt128(linear_damping)
        p.writeInt128(angular_damping)
        p.writeBoolean(invisible)
        p.writeUTF(image_description)
        p.writeBoolean(has_contact_listener)
        self.sendAll(Identifiers.send.Add_Physical_Object, p.toByteArray())
        self.lastPhysicalObjectId += 1
        
    def sendImage(self, image_path, _local2, x, y, width, height, target, tile_info, disappear_on_click, hidden, name, _local1=0, x_step=0, y_step=0):
        p = ByteArray()
        p.writeUnsignedByte(self.lastImageID)
        p.writeUTF(image_path)
        p.writeBoolean(_local2)
        p.writeShort(x)
        p.writeShort(y)
        p.writeShort(width)
        p.writeShort(height)
        p.writeByte(target)
        p.writeBoolean(tile_info)
        if tile_info:
            p.writeShort(_local1)
            p.writeInt(x_step)
            p.writeInt(y_step)
        
        p.writeBoolean(disappear_on_click)
        p.writeBoolean(hidden)
        p.writeUTF(name)
        self.sendAll(Identifiers.send.Add_Image, p.toByteArray())
        self.lastImageID += 1
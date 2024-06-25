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
        #self.EMapLoaded = 0
        self.forceNextShaman = -1
        #self.musicSkipVotes = 0
        self.isPlayingMusic = False
        self.musicSkipVotes = 0
        self.isMarkFuncorpRoom = False
        self.maximumPlayers = 20
        
        # Integer
        self.addTime = 0
        self.changeMapAttemps = 0
        self.companionBox = -1
        self.currentMap = 0
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
        self.catchTheCheeseMap = False
        self.countStats = False
        self.initVotingMode = True
        self.isAutoMapFlipMode = True
        self.isAutoRespawn = False
        self.isAutoScore = True
        self.isBootcamp = False
        self.isDefilante = False
        self.isDisabledAfkKill = False
        self.isDoubleMap = False
        self.isEditeur = False
        self.isFixedMap = False
        self.isFuncorp = False
        self.isNoShaman = False
        self.isNoShamanMap = False
        self.isNormal = False
        self.isMulodrome = False
        self.isMusic = False
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
        self.forceNextMap = "-1"
        self.mapName = ""
        self.mapXML = ""
        self.roomCommunity = ""
        self.roomFullName = _roomName
        self.roomName = ""
        self.roomPassword = ""
        
        # List
        self.anchors = []
        self.lastHandymouse = [-1, -1]
        self.mulodromeRedTeam = []
        self.mulodromeBlueTeam = []
        self.roomFuncorps = []
        self.roomTimers = []
        self.holesList = []
        self.cheesesList = []
        
        # Dictionary
        self.currentShamanSkills = {}
        self.currentSecondShamanSkills = {}
        self.players = {}
        self.musicVideos = []
        
        # Timers
        self.autoRespawnTimer = None
        self.changeMapTimer = None
        self.endSnowTimer = None
        self.killAfkTimer = None
        self.voteCloseTimer = None
        self.startTimerLeft = None
        
        # Maps
        self.doubleShamanMaps = self.server.mapsInfo["doubleShamanMaps"]
        self.noShamanMaps = self.server.mapsInfo["noShamanMaps"]
        self.catchCheeseMaps = self.server.mapsInfo["catchCheeseMaps"]
        self.mapList = self.server.mapsInfo["normalMaps"]
        self.transformationMaps = self.server.mapsInfo["transformationMaps"]
        
        if _roomName.startswith("*"):
            self.roomCommunity = "int"
            self.roomName = _roomName
            
        elif _roomName.startswith("@"):
            self.roomCommunity = self.client.playerLangue
            self.roomName = _roomName
            
        else:
            self.roomCommunity = _roomName.split("-")[0].lower()
            self.roomName = _roomName.split("-")[1]
        
        self.checkRoomName()

    def checkIfShamanIsDead(self):
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
        else:
            self.isNormal = True

    def checkDeadPlayersPercentage(self, percentage):
        dead_people = self.getDeathCountNoShaman()
        all_people = self.getPlayerCountNotNew()
        p = (percentage * 100) / all_people
        return dead_people >= p

    def getDeathCountNoShaman(self):
        return len(list(filter(lambda player: not player.isShaman and not player.isNewPlayer, self.players.copy().values())))

    def getHighestScore(self):
        playerScores = []
        playerID = 0
        for player in self.players.copy().values():
            playerScores.append(player.playerScore)
                    
        for player in self.players.copy().values():
            if player.playerScore == max(playerScores):
                playerID = player.playerCode
        return playerID

    def getMapInfo(self, mapCode):
        if mapCode in self.server.cachedmaps: 
            return self.server.cachedmaps[mapCode]
        mapInfo = ["", "", 0, 0, 0]
        self.CursorMaps.execute("SELECT * from Maps where Code = ?", [mapCode])
        rs = self.CursorMaps.fetchone()
        if rs:
            mapInfo = rs["Name"], rs["XML"], rs["YesVotes"], rs["NoVotes"], rs["Perma"]
            self.server.cachedmaps[mapCode] = mapInfo
        return mapInfo

    def getPlayerCount(self):
        return len(list(filter(lambda player: not player.isHidden, self.players.copy().values())))

    def getPlayerCountAlive(self):
        return len(list(filter(lambda player: not player.isDead and not player.isNewPlayer, self.players.values())))

    def getPlayerCountNotNew(self):
        return len(list(filter(lambda player: not player.isHidden and not player.isNewPlayer, self.players.copy().values())))

    def getPlayerCountUnique(self):
        return len(list({player.ipAddress for player in self.players.copy().values()}))

    def getPlayerList(self):
        result = b""
        i = 0
        for player in self.players.copy().values():
            if not player.isHidden:
                result += player.getPlayerData()
                i += 1

        return [i, result]

    def getSecondHighestScore(self):
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

    def getSyncCode(self):
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


    def sendAll(self, identifiers, packet=""):
        for player in self.players.copy().values():
            player.sendPacket(identifiers, packet)

    def sendAllOthers(self, senderClient, identifiers, packet=""):
        for player in self.players.copy().values():
            if player != senderClient:
                player.sendPacket(identifiers, packet)

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

    def sendRoomFunCorp(self):
        self.isFuncorp = not self.isFuncorp
        self.isMarkFuncorpRoom = self.isFuncorp
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




    def addClient(self, player, newRoom=False, local_3=False, local_4=True) -> bool:
        self.players[player.playerName] = player
        player.room = self
        
        if not newRoom:
            player.isDead = True
            self.sendAllOthers(player, Identifiers.send.Player_Respawn, ByteArray().writeBytes(player.getPlayerData()).writeBoolean(local_3).writeBoolean(local_4).toByteArray())
            player.startPlay()
        else:
            player.room.roomCreator = player.playerName

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
                
                for timer in [self.autoRespawnTimer, self.changeMapTimer, self.endSnowTimer, self.killAfkTimer, self.voteCloseTimer]:
                    if timer != None:
                        timer.cancel()
                        
                del self.server.bulle_rooms[self.roomFullName]
            else:
                if player.playerCode == self.currentSyncCode:
                    self.currentSyncCode = -1
                    self.currentSyncName = ""
                    self.getSyncCode()
                await self.checkChangeMap()

    def getShamanCode(self):
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
                        #self.currentShamanSkills = player.playerSkills
                        break
        return self.currentShamanCode


    def getDoubleShamanCode(self):
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
                    #self.currentShamanSkills = player.playerSkills
                    break

                if player.playerCode == self.currentSecondShamanCode:
                    self.currentSecondShamanName = player.playerName
                    self.currentSecondShamanType = player.shamanType
                    #self.currentSecondShamanSkills = player.playerSkills
                    break

        return [self.currentShamanCode, self.currentSecondShamanCode]


    def sendAllChat(self, playerName, message, isOnly):
        p = ByteArray().writeUTF(playerName).writeUTF(message).writeBoolean(True)
        if isOnly == 0:
            for client in self.players.copy().values():
                client.sendPacket(Identifiers.send.Chat_Message, p.toByteArray())
        else:
            player = self.players.get(playerName)
            if player != None:
                player.sendPacket(Identifiers.send.Chat_Message, p.toByteArray())
                #if isOnly == 1:
                    #player.sendServerMessage("The player <BV>"+player.playerName+"</BV> has sent a filtered text: [<J>" + str(message) + "</J>].")


    def changeMapTimers(self, seconds):
        if self.changeMapTimer != None: self.changeMapTimer.cancel()
        self.canChangeMap = True
        self.changeMapTimer = self.server.loop.call_later(seconds, self.mapChange)

    def respawnSpecific(self, playerName):
        player = self.players.get(playerName)
        if player != None and player.isDead:
            player.resetPlay()
            player.isAfk = False
            player.playerStartTimeMillis = time.time()
            self.sendAll(Identifiers.send.Player_Respawn, ByteArray().writeBytes(player.getPlayerData()).writeBoolean(False).writeBoolean(True).toByteArray())

    def respawnMice(self):
        for player in self.players.copy().values():
            if player.isDead:
                player.isDead = False
                player.playerStartTimeMillis = time.time()
                self.sendAll(Identifiers.send.Player_Respawn, ByteArray().writeBytes(player.getPlayerData()).writeBoolean(False).writeBoolean(True).toByteArray())
                    
        if self.isAutoRespawn or self.isTribeHouseMap:
            self.autoRespawnTimer = call_later(2, self.respawnMice)

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
        
        for timer in [self.autoRespawnTimer, self.killAfkTimer, self.startTimerLeft, self.voteCloseTimer]:
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
                numCom = self.FSnumCompleted - 1 if self.isDoubleMap else self.numCompleted - 1
                numCom2 = self.SSnumCompleted - 1 if self.isDoubleMap else 0
                if numCom < 0: numCom = 0
                if numCom2 < 0: numCom2 = 0
                
                player = self.players.get(self.currentShamanName)
                if player != None:
                    self.sendAll(Identifiers.old.send.Shaman_Perfomance, [self.currentShamanName, numCom])
                    if self.isAutoScore: 
                        player.playerScore = numCom
                #    if numCom > 0:
                #        player.Skills.earnExp(True, numCom)

                player2 = self.players.get(self.currentSecondShamanName)
                if player2 != None:
                    self.sendAll(Identifiers.old.send.Shaman_Perfomance, [self.currentSecondShamanName, numCom2])
                    if self.isAutoScore: 
                        player2.playerScore = numCom2
                #    if numCom2 > 0:
                #        player2.Skills.earnExp(True, numCom2)
                       
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
                self.mapStatus = (self.mapStatus + 1) % 10
            else:
                self.mapStatus = 0

            self.currentMap = await self.selectMap()
            self.checkMapXML()
            
            if (self.currentMap in self.doubleShamanMaps) or (self.mapPerma == 8 and self.getPlayerCount() >= 3):
                self.isDoubleMap = True

            if self.mapPerma in [7, 17, 42] or (self.isSurvivor and self.mapStatus == 0) or (self.currentMap in self.noShamanMaps):
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
                    if Utils.getSecondsDiff(player.petEnd) >= 0:
                        player.petType = 0
                        player.petEnd = 0
                    else:
                        self.sendAll(Identifiers.send.Pet, ByteArray().writeInt(player.playerCode).writeUnsignedByte(player.pet).toByteArray())
                if player.furType != 0:
                    if Utils.getSecondsDiff(player.furEnd) >= 0:
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
            
            if self.isSurvivor and self.mapStatus == 0 and self.getPlayerCountAlive() > 1:
                self.server.loop.call_later(5, self.sendVampireMode)
            
            self.startTimerLeft = call_later(3, self.sendRoomStartTimer)
            if not self.isFixedMap and not self.isTribeHouse and not self.isTribeHouseMap:
                self.changeMapTimer = call_later(self.roundTime + self.addTime, self.mapChange)
            
            self.killAfkTimer = call_later(30, self.killAfkPlayers)
            if self.isAutoRespawn or self.isTribeHouseMap:
                self.autoRespawnTimer = call_later(2, self.respawnMice)
                
                
    def setNameColor(self, playerName, color):
        if playerName in self.players:
            self.sendAll(Identifiers.send.Set_Name_Color, ByteArray().writeInt(self.players.get(playerName).playerCode).writeInt(color).toByteArray())
            
    def giveStats(self, typ):
        pass
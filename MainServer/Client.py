#coding: utf-8
import asyncio
import base64
import random
import string
import time

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers
from Modules.Packets import Packets
from Modules.Cafe import Cafe
from Modules.Shop import Shop
from Modules.ChannelCommands import ChannelCommands
from Modules.Commands import Commands
from Modules.ModoPwet import ModoPwet
from Modules.Tribulle import Tribulle

# Utils
from Utils import Config, Logger
from Utils.Langue import Langue
from Utils.Time import Time
from Utils.IPTools import IPTools
from Utils.Other import Other

class Client:
    def __init__(self, _server, _cursor):
        self.server = _server
        self.cursor = _cursor
        self.clientPacket = ByteArray()
        self.Logger = Logger.Logger()
                        
        # Integer
        self.adventurePoints = 0
        self.bootcampCount = 0
        self.cheeseCount = 0
        self.firstCount = 0
        self.furType = 0
        self.furEnd = 0
        self.equipedShamanBadge = 0
        self.genderType = 0
        self.isMutedHours = 0
        self.lastDivorceTime = 0
        self.lastOn = 0
        self.lastPacketID = 0
        self.lastReportID = 0
        self.lastSonarTime = 0
        self.loginTime = 0
        self.loginWrongAttemps = 0
        self.mapEditorCheese = 40
        self.verifycoder = 0
        self.petType = 0
        self.petEnd = 0
        self.playerCode = 0
        self.playerID = 0
        self.playerKarma = 0
        self.playerRegDate = 0
        self.playerTime = 0
        self.privLevel = 0
        self.silenceType = 0
        self.shamanNormalSaves = 0
        self.shamanNormalSavesNoSkill = 0
        self.shamanHardSaves = 0
        self.shamanHardSavesNoSkill = 0
        self.shamanDivineSaves = 0
        self.shamanDivineSavesNoSkill = 0
        self.shamanCheeses = 0
        self.shamanLevel = 0
        self.shamanType = 0
        self.shopCheeses = 0
        self.shopFraises = 0
        self.titleNumber = 0
        self.titleStars = 0
        self.tribeCode = 0
        self.tribeHouse = 0
        self.tribeJoined = 0
        self.tribeRank = 0

        
        # Boolean
        self.isCafeOpened = False
        self.isClosed = False
        self.isEmailAddressVerified = False
        self.isEnterRoom = False
        self.isFriendListOpen = False
        self.isGuest = False
        self.isFashionSquad = False
        self.isFunCorp = False
        self.isLoggedIn = False
        self.isLuaAdmin = False
        self.isLuaCrew = False
        self.isMapCrew = False
        self.isModoPwetNotifications = False
        self.isModoPwetOpened = False
        self.isMumuted = False
        self.isMuted = False
        self.isNewAccount = False
        self.isPacketLogging = False
        self.isPrisoned = False
        self.isPrivMod = False
        self.isReloadCafe = False
        self.isServerErrorLogging = False
        self.isTrade = False
        self.isTribeOpened = False
        self.isVerifiedClientVersion = False
        self.tradeConfirm = False
                
        # String
        self.computerInformation = ""
        self.currentCaptcha = ""
        self.playerLangue = ""
        self.flashVersion = ""
        self.mouseColor = "78583A"
        self.ipCountry = "Brazil" # UNFINISHED
        self.isMutedReason = ""
        self.lastNpcName = ""
        self.modoPwetLangue = "ALL"
        self.playerEmail = ""
        self.playerLook = "1;0,0,0,0,0,0,0,0,0,0,0,0"
        self.playerName = ""
        self.playerSoulmate = ""
        self.roomName = ""
        self.silenceMessage = ""
        self.shamanColor = "95d9d6"
        self.shamanLook = "0,0,0,0,0,0,0,0,0,0"
        self.shopItems = ""
        self.shopFavoriteItems = ""
        self.shopClothes = ""
        self.shopShamanItems = ""
        self.shopEmotes = ""
        self.tradeName = ""
        self.tribeName = ""
        self.tribeMessage = ""
        self.tribeRanks = ""
        
        # List
        self.banVotes = []
        self.equipedConsumables = []
        self.friendList = []
        self.invitedTribeHouses = []
        self.ignoredList = []
        self.loggedPackets = []
        self.marriageInvite = []
        self.modoCommunities = []
        self.modoCommunitiesNotification = []
        self.shamanBadges = []
        self.PInfo = [0, 0, 0]
        self.playerBadges = []
        self.playerStats = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        self.privRoles = []
        self.totemInfo = [0, ""]
        self.titleList = []
        self.tribeInvite = []
        
        # Dictionary
        self.adventureInfo = {}
        self.bulleID = {}
        self.shopGifts = {}
        self.playerConsumables = {}
        self.tradeConsumables = {}
        
        # Nonetype
        self.awakeTimer = None
        self.transport = None
        self.ipAddress = None
        
        # Loops
        self.loop = asyncio.get_event_loop()
        
        # Other
        self.CAPTime = time.time()
        self.CMDTime = time.time()
       
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
        
    def connection_made(self, transport: asyncio.Transport) -> None:
        """
        Make connection between client and server.
        """
        self.transport = transport
        self.ipAddress = transport.get_extra_info("peername")[0]
        
        self.Packets = Packets(self)
        self.Cafe = Cafe(self)
        self.Shop = Shop(self)
        self.ChannelCommands = ChannelCommands(self)
        self.ParseCommands = Commands(self)
        self.ModoPwet = ModoPwet(self)
        self.Tribulle = Tribulle(self)

    def data_received(self, packet: bytes) -> None:
        if self.isClosed or len(packet) < 2:
            return
            
        if self.ipAddress in self.server.IPPermaBanCache:
            self.transport.close()
            return

        if packet == b'<policy-file-request/>\x00':
            self.server.Logger.warn(f"{self.ipAddress} -> Policy File Request\n")
            self.transport.write(b'<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\x00')
            self.transport.close()
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
                
    async def parsePacket(self, packet) -> None:
        if self.isClosed:
            return
            
        packet_id, C, CC = packet.readByte(), packet.readByte(), packet.readByte()
        self.lastPacketID = packet_id
        packet_code = (C << 8) | (CC & 0xFF)
        if self.isPacketLogging and not packet_code in self.loggedPackets:
            self.sendServerMessage(f"Packet: [{packet_id}:{C}|{CC} ({packet_code})] --> {str(packet._bytes)}", True)
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
            data = bytes([data])

        data = data.encode() if isinstance(data, str) else data

        self.lastPacketID = (self.lastPacketID + 1) % 255

        packet = bytearray()
        length = len(data) + 2
        while length >= 128:
            packet.append((length & 127) | 128)
            length >>= 7
        packet.append(length)
        packet.append(identifiers[0])
        packet.append(identifiers[1])

        self.transport.write(bytes(packet+data))
        
    def eof_received(self) -> None:
        if self.server.isDebug:
            self.Logger.debug(f"[CLIENT] {self.ipAddress} -> EOF Received\n")
        return
            
    def connection_lost(self, *args) -> None: # UNFINISHED
        self.isClosed = True
        
        if self.playerName in self.server.players:
            # Friend disconnect
            for player in self.server.players.copy().values():
                if self.playerName in player.friendList and player.playerName in self.friendList:
                    player.Tribulle.sendFriendDisconnected(self.playerName)
                
            # Tribe disconnect
            if self.tribeCode != 0:
                self.Tribulle.sendTribeMemberDisconnected()
                
            # modopwet notification
            if self.playerName in self.server.modoReports:
                self.ModoPwet.sendModoNotification(self.playerName, self.playerLangue, "disconnect")
                if not self.server.modoReports[self.playerName]["status"] in ["banned", "deleted"]:
                    self.server.modoReports[self.playerName]["status"] = "disconnected"
            
            # database
            self.updateDatabase()
            
            del self.server.players[self.playerName]
        self.transport.close()
        
    async def createAccount(self, playerName, email, password): # UNFINISHED
        playerName = self.server.genPlayerTag(playerName)
        self.server.lastPlayerID = self.cursor['users'].count_documents({}) + 1
    
        self.cursor['users'].insert_one({
            # Identification
            "Username" :                        playerName,
            "Password" :                        password,
            "Email" :                           email,
            "PlayerID" :                        self.server.lastPlayerID,
            "PlayerGender" :                    0,
            "PlayerKarma":                      0,
            "PlayerTime" :                      0,
            "PlayerLook" :                      self.playerLook,
            "PrivLevel" :                       1,
            "PrivRoles" :                       "", # ["FS", "LUA", "FC", "MC", "PrivMod"]
            
            # Profile
            "ShamanCheeses":                    self.server.gameInfo["initial_shaman_cheeses"],
            "FirstCount" :                      self.server.gameInfo["initial_firsts"],
            "CheeseCount" :                     self.server.gameInfo["initial_cheeses"],
            "BootcampCount" :                   self.server.gameInfo["initial_bootcamps"],
            "NormalSavesCount" :                self.server.gameInfo["initial_saves"]["normal"],
            "NormalSavesCountNS" :              self.server.gameInfo["initial_saves"]["normal_no_skill"],
            "HardSavesCount" :                  self.server.gameInfo["initial_saves"]["hard"],
            "HardSavesCountNS" :                self.server.gameInfo["initial_saves"]["hard_no_skill"],
            "DivineSavesCount" :                self.server.gameInfo["initial_saves"]["divine"],
            "DivineSavesCountNS" :              self.server.gameInfo["initial_saves"]["divine_no_skill"],
            "PlayerBadges":                     "",
            "PlayerStats":                      "0,0,0,0,0,0,0,0,0,0,0",
            
            # Title
            "TitleNumber":                      0,
            "TitlesList":                       "",
            
            # Shaman
            "ShamanLevel":                      0,
            "CurrentShamanBadge":               0,
            "ShamanBadges":                     "",
            "ShamanLook":                       self.shamanLook,
            "ShamanType":                       0,
            "ShamanColor":                      "95d9d6",
            
            # Shop
            "ShopCheeseCount" :                 self.server.gameInfo["initial_shop"]["cheese"],
            "ShopFraiseCount" :                 self.server.gameInfo["initial_shop"]["strawberry"],
            "ShopItems"       :                 "",
            "ShopFavoriteItems":                "",
            "ShopClothes"     :                 "",
            "ShopShamanItems" :                 "",
            "ShopEmotes"      :                 "",
            
            # Tribulle
            "FriendsList" :                     "",
            "IgnoredList" :                     "",
            "Soulmate" :                        "",
            "TribeCode":                        0,
            "TribeRank":                        0,
            "TribeJoined":                      0,
            "LastDivorceTime":                  0,
            
            # Other
            "EmailVerified":                    0,
            "RegDate":                          Time.getTime(),
            "EquipedConsumables":               "",
            "Inventory":                        "",
            "AdventurePoints":                  0,
            "AdventureInfo":                    "",
            "ModoCommunities":                  "",
            "LastOn":                           0,
            "FurType":                          0,
            "PetType":                          0,
            "FurEnd":                           0,
            "PetEnd":                           0,
            "TotemInfo":                        ""
            
        })
        self.isNewAccount = True
        await self.loginPlayer(playerName, password, f"\x03[Tutorial] {playerName}")
                
    async def loginPlayer(self, playerName, password, startRoom):  # UNFINISHED
        if self.ipAddress in self.server.IPTempBanCache:
            info = self.server.getTempIPBanInfo(self.ipAddress)
            hours = Time.getHoursDiff(info[0])
            if hours > 0:
                self.sendPacket(Identifiers.old.send.Player_Ban_Login, [hours * 3600000, info[1]])
                self.isLoggedIn = False
                return
            else:
                self.server.removeTempIPBan(self.ipAddress)
    
    
        if password == "":
            # player is guest
            self.playerName = self.server.checkAlreadyExistingGuest(playerName)
            startRoom = f"\x03[Tutorial] {self.playerName}"
            self.isGuest = True
            self.isLoggedIn = True
            
        elif "@" in playerName:
            # email address linked with more than 1 player.
            rss = self.cursor['users'].find({'Email':playerName,'Password':password})
            players = []
            for rs in rss:
                players.append(rs['Username'])
                
            if len(players) == 0:
                self.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(2).writeUTF("").writeUTF("").toByteArray())
            else:
                i = 0
                p = ByteArray()
                while i < len(players):
                    p.writeBytes(players[i]).writeShort(-15708)
                    i += 1
                self.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(11).writeShort(len(p.toByteArray())).writeBytes(p.toByteArray()).writeShort(0).toByteArray())

        else:
            rs = self.cursor['users'].find_one({('Username'):playerName, 'Password':password})
            if rs:
                banInfo = self.server.getTempPunishmentInfo(playerName, 1)
                if len(banInfo) > 0:
                    time = banInfo[1]
                    if time == -1:
                        self.sendPacket(Identifiers.old.send.Player_Ban_Login, [banInfo[0]])
                        self.isLoggedIn = False
                        return
                    else:
                        time = Time.getHoursDiff(time)
                        if time > 0:
                            self.sendPacket(Identifiers.old.send.Player_Ban_Login, [time * 3600000, banInfo[0]])
                            self.isLoggedIn = False
                            return
                        else:
                            self.server.removeTempUserBan(playerName, False)
            
                muteInfo = self.server.getTempPunishmentInfo(playerName, 0)
                if len(muteInfo) > 0:
                    time = Time.getHoursDiff(muteInfo[1])
                    if time > 0:
                        self.isMuted = True
                        self.isMutedHours = muteInfo[1]
                        self.isMutedReason = muteInfo[0]
                    else:
                        self.server.removeModMute(playerName, False)
                    
            
                # Identification
                self.playerName = playerName
                self.playerEmail = rs["Email"]
                self.playerID = rs["PlayerID"]
                self.genderType = rs["PlayerGender"]
                self.playerKarma = rs["PlayerKarma"]
                self.playerTime = rs["PlayerTime"]
                self.playerLook = rs["PlayerLook"]
                self.playerRegDate = rs["RegDate"]
                self.privLevel = rs["PrivLevel"]
                self.privRoles = list(map(str, filter(None, rs['PrivRoles'].split(","))))
                
                # Stats
                self.firstCount = rs["FirstCount"]
                self.cheeseCount = rs["CheeseCount"]
                self.shamanCheeses = rs["ShamanCheeses"]
                self.bootcampCount = rs["BootcampCount"]
                self.shamanNormalSaves = rs["NormalSavesCount"]
                self.shamanNormalSavesNoSkill = rs["NormalSavesCountNS"]
                self.shamanHardSaves = rs["HardSavesCount"]
                self.shamanHardSavesNoSkill = rs["HardSavesCountNS"]
                self.shamanDivineSaves = rs["DivineSavesCount"]
                self.shamanDivineSavesNoSkill = rs["DivineSavesCountNS"]
                self.playerBadges = list(map(int, filter(None, rs['PlayerBadges'].split(","))))
                self.playerStats = list(map(int, filter(None, rs['PlayerStats'].split(","))))
                
                # Titles
                self.titleNumber = rs["TitleNumber"]
                self.titleList = list(map(float, filter(None, base64.b64decode(rs['TitlesList'].encode()).decode().split(","))))
                for title in self.titleList:
                    if str(title).split(".")[0] == str(self.titleNumber):
                        self.titleStars = int(str(title).split(".")[1])
                        break 
                
                # Shaman
                self.shamanLevel = rs["ShamanLevel"]
                self.equipedShamanBadge = rs["CurrentShamanBadge"]
                self.shamanBadges = list(map(int, filter(None, rs['ShamanBadges'].split(","))))
                self.shamanLook = rs["ShamanLook"]
                self.shamanType = rs["ShamanType"]
                self.shamanColor = rs["ShamanColor"]
                
                # Shop
                self.shopCheeses = rs["ShopCheeseCount"]
                self.shopFraises = rs["ShopFraiseCount"]
                self.shopItems = rs["ShopItems"]
                self.shopFavoriteItems = rs["ShopFavoriteItems"]
                self.shopClothes = rs["ShopClothes"]
                self.shopShamanItems = rs["ShopShamanItems"]
                self.shopEmotes = rs["ShopEmotes"]
                
                # Tribulle
                self.friendList = list(map(str, filter(None, rs['FriendsList'].split(","))))
                self.ignoredList = list(map(str, filter(None, rs['IgnoredList'].split(","))))
                self.playerSoulmate = rs["Soulmate"]
                self.tribeCode = rs["TribeCode"]
                self.tribeJoined = rs["TribeJoined"]
                self.tribeRank = rs["TribeRank"]
                self.lastDivorceTime = rs["LastDivorceTime"]
                if self.tribeCode != 0:
                    self.tribeInfo = self.Tribulle.getTribeInfo(self.tribeCode)
                    self.tribeName = str(self.tribeInfo[0])
                    self.tribeMessage = str(self.tribeInfo[1])
                    self.tribeHouse = int(self.tribeInfo[2])
                    self.tribeRanks = str(self.tribeInfo[3])
                
                # Other
                self.isEmailAddressVerified = bool(rs["EmailVerified"])
                self.equipedConsumables = list(map(int, filter(None, rs['EquipedConsumables'].split(","))))
                for info in list(map(str, filter(None, rs['Inventory'].split(";")))):
                    values = info.split(":")
                    self.playerConsumables[int(values[0])] = int(values[1])
                self.adventurePoints = rs["AdventurePoints"]
                #self.adventureInfo = base.b64decode(rs["AdventureInfo"])
                self.modoCommunities = list(map(str, filter(None, rs['ModoCommunities'].split(","))))
                self.lastOn = rs["LastOn"]
                self.furType = rs["FurType"]
                self.furEnd = rs["FurEnd"]
                self.petType = rs["PetType"]
                self.petEnd = rs["PetEnd"]
                self.totemInfo = list(map(lambda x: int(x) if x.isdigit() else x, rs["TotemInfo"].split("|")))
                                
                self.isLoggedIn = True
            else:
                self.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(2).writeUTF("").writeUTF("").toByteArray())
    
        if self.isLoggedIn:
            self.loginTime = Time.getTime()
            self.server.lastPlayerCode += 1
            self.playerCode = self.server.lastPlayerCode
            self.server.players[self.playerName] = self
            self.makeStaffRoleInfo()
            self.Cafe.makeCafePermissions()
            self.sendPlayerIdentification()
            self.logConnection()
            self.sendSourisLogin()
            self.sendPlayerTime()
            self.sendEmailVerifiedPacket(self.isEmailAddressVerified) ########
            self.sendTribulleProtocol()
            self.sendTribulleInitialization()
            self.sendCommunityPartners()
            self.sendTotalCheeseToExportMap()
            self.Shop.sendPromotions()
            self.Shop.sendShamanItems()
            self.Shop.sendShopCache()
            self.Shop.sendShopShamanCache()
            self.Shop.checkShopGifts()
            self.sendPlayerEmotes()
            self.sendPlayerInventory()
            self.sendModopwetNotification()
            self.sendDefaultGlobalChat()
            self.sendEnterRoom(startRoom)
            self.sendRegisteredAccountConsumable()
            if self.shamanNormalSaves >= 1500:
                self.sendShamanType(self.shamanType, (self.shamanNormalSaves >= 5000 and self.shamanHardSaves >= 2000), False)
            self.Shop.sendPromotionPopup()
            self.sendMessage("<VP>[SYSTEM]</VP> Be sure to wish happy birthday to Raiden Shogun today ❤️ or you will be banned forever.")

    async def connectToBulle(self, roomName, community="", isHidden=False, sendPacket=True): # UNFINISHED
        if self.isPrisoned:
            return
    
        if self.isTrade:
            self.cancelTrade(self.tradeName, False)
            
        if self.tribeCode != 0:
            self.Tribulle.sendTribeMemberChangeRoom()
            
        for player in self.server.players.values():
            if self.playerName and player.playerName in self.friendList and player.friendList:
                player.Tribulle.sendFriendChangedRoom(self.playerName)
                
        if self.playerName in self.server.modoReports:
            for player in self.server.players.values():
                if player.isModoPwetNotifications and roomName != self.roomName:
                    player.ModoPwet.sendModoNotification(self.playerName, self.playerLangue, "changeroom", ["TEST", self.roomName, f"{self.playerLangue}-{roomName}"])

        community = (community if len(community) > 0 else self.playerLangue)
        if len(roomName) > 0 and (roomName[0] == '*' or roomName[0] == '@'):
            self.roomName = f"{roomName}"
        else:
            self.roomName = f"{community}-{roomName}"
            
        self.bulleID = random.choice(list(self.server.bullesInfo))
        bullePorts = '-'.join(map(str, self.bulleID["port"]))
        isReported = self.playerName in self.server.modoReports

        temp_code = Other.randomGen()
        try:
            self.server.bulles[self.bulleID["id"]].send_packet(Identifiers.bulle.BU_ConnectToGivenRoom, self.playerID, self.playerName, self.playerCode, community, base64.b64encode(self.playerLook.encode()).decode('utf-8'), self.getStaffPermissions(), self.isMuted, self.genderType, roomName, isHidden, isReported, self.titleNumber, self.titleStars, self.isMutedHours, self.isMutedReason, self.shamanType, self.shamanLevel, base64.b64encode(self.shopShamanItems.encode()).decode('utf-8'), self.equipedShamanBadge, self.shamanColor, self.petType, self.petEnd, self.furType, self.furEnd, self.mapEditorCheese, self.shopCheeses, self.cheeseCount, temp_code)
            self.Logger.debug(f"[{self.ipAddress}] Established connection to bulle{self.bulleID['id']} : {self.bulleID['ip_address']}:{bullePorts}.\n")
        except KeyError as e:
            self.Logger.error("Unable to connect to bulle. Refreshing in 10 seconds.\n")
            await asyncio.sleep(10)
            return await self.connectToBulle(roomName, community, isHidden, False)
            
        if sendPacket:
            self.sendPacket(Identifiers.send.Init_Bulle_Connection, ByteArray().writeInt(self.bulleID["id"]).writeInt(temp_code).writeInt(self.playerID).writeUTF(self.bulleID["ip_address"]).writeUTF(bullePorts).toByteArray())
        
    def cancelTrade(self, playerName, isDone=False):
        player = self.server.players.get(playerName)
        if player != None:
            self.tradeName = ""
            self.isTrade = False
            self.tradeConsumables = {}
            self.tradeConfirm = False
            if not isDone:
                self.sendTradeResult(self.playerName, 2)
            else:
                self.sendPacket(Identifiers.send.Trade_Close)
            player.tradeName = ""
            player.isTrade = False
            player.tradeConsumables = {}
            player.tradeConfirm = False
            if not isDone:
                player.sendTradeResult(self.playerName, 2)
            else:
                player.sendPacket(Identifiers.send.Trade_Close)

    def giveTitle(self, _id, changenow=True):
        self.titleStars = 1
        self.titleNumber = _id
        self.titleList.append(_id + 0.1)
        self.sendBullePacket(Identifiers.bulle.BU_SendUnlockTitle, self.playerID, _id, 1)
        if changenow:
            self.sendPacket(Identifiers.send.Change_Title, ByteArray().writeByte(self.genderType).writeShort(_id).toByteArray())
            self.sendBullePacket(Identifiers.bulle.BU_ReceiveTitleID, self.playerID, self.titleNumber, self.titleStars)

    def tradeAddConsumable(self, __id, isAdd):
        player = self.server.players.get(self.tradeName)
        if player != None and player.isTrade and player.tradeName == self.playerName and str(__id) in self.server.inventoryConsumables and not "blockTrade" in self.server.inventoryConsumables[str(__id)]:
            if isAdd:
                if __id in self.tradeConsumables:
                    self.tradeConsumables[__id] += 1
                else:
                    self.tradeConsumables[__id] = 1
            else:
                count = self.tradeConsumables[__id] - 1
                if count > 0:
                    self.tradeConsumables[__id] = count
                else:
                    del self.tradeConsumables[__id]

            player.sendPacket(Identifiers.send.Trade_Add_Consumable, ByteArray().writeBoolean(False).writeShort(__id).writeBoolean(isAdd).writeByte(1).writeBoolean(False).toByteArray())
            self.sendPacket(Identifiers.send.Trade_Add_Consumable, ByteArray().writeBoolean(True).writeShort(__id).writeBoolean(isAdd).writeByte(1).writeBoolean(False).toByteArray())

    def tradeInvite(self, playerName):
        player = self.server.players.get(playerName)
        if player != None and (self.isGuest == False and player.isGuest == False): #  and ((self.ipAddress != player.ipAddress) or self.server.isDebug)
            if not player.isTrade:
                if not player.roomName == self.roomName:
                    self.sendTradeResult(playerName, 3)
                elif player.isTrade:
                    self.sendTradeResult(playerName, 0)
                else:
                    self.sendLangueMessage("", "$Demande_Envoyée")
                    player.sendTradeInvite(self.playerCode)

                self.tradeName = playerName
                self.isTrade = True
            else:
                self.tradeName = playerName
                self.isTrade = True
                self.sendTradeStart(player.playerCode)
                player.sendTradeStart(self.playerCode)

    def tradeResult(self, isAccept):
        player = self.server.players.get(self.tradeName)
        if player != None and player.isTrade and player.tradeName == self.playerName:
            self.tradeConfirm = isAccept
            player.sendPacket(Identifiers.send.Trade_Confirm, ByteArray().writeBoolean(False).writeBoolean(isAccept).toByteArray())
            self.sendPacket(Identifiers.send.Trade_Confirm, ByteArray().writeBoolean(True).writeBoolean(isAccept).toByteArray())
            if self.tradeConfirm and player.tradeConfirm:
                for consumable in player.tradeConsumables.items():
                    self.sendNewConsumable(consumable[0], consumable[1])
                    if consumable[0] in self.playerConsumables:
                        self.playerConsumables[consumable[0]] += consumable[1]
                    else:
                        self.playerConsumables[consumable[0]] = consumable[1]

                    count = player.playerConsumables[consumable[0]] - consumable[1]
                    if count <= 0:
                        del player.playerConsumables[consumable[0]]
                        if consumable[0] in player.equipedConsumables:
                            player.equipedConsumables.remove(consumable[0])
                    else:
                        player.playerConsumables[consumable[0]] = count

                for consumable in self.tradeConsumables.items():
                    player.sendNewConsumable(consumable[0], consumable[1])
                    if consumable[0] in player.playerConsumables:
                        player.playerConsumables[consumable[0]] += consumable[1]
                    else:
                        player.playerConsumables[consumable[0]] = consumable[1]

                    count = self.playerConsumables[consumable[0]] - consumable[1]
                    if count <= 0:
                        del self.playerConsumables[consumable[0]]
                        if consumable[0] in self.equipedConsumables:
                            self.equipedConsumables.remove(consumable[0])
                    else:
                        self.playerConsumables[consumable[0]] = count

                self.cancelTrade(self.tradeName, True)
                self.sendPlayerInventory()
                player.sendPlayerInventory()

    def sendModopwetNotification(self):
        if self.playerName in self.server.modoReports and not self.isGuest:
            self.ModoPwet.sendModoNotification(self.playerName, self.playerLangue, "connect")

    def checkPlayerTitle(self, titleID) -> bool:
        for title in self.titleList:
            if int(title - (title % 1)) == titleID:
                return True
        return False

    def checkStaffPermission(self, staff_positions) -> bool:
        if "Guest" in staff_positions:
            return self.isGuest
            
        res = 0
        if "FS" in staff_positions:
            res += (self.privLevel in [4, 10] or self.isFashionSquad)
    
        if "LT" in staff_positions:
            res += (self.privLevel in [5, 10] or self.isLuaCrew)
    
        if "FC" in staff_positions:
            res += (self.privLevel in [6, 10] or self.isFunCorp)
            
        if "MC" in staff_positions:
            res += (self.privLevel in [7, 10] or self.isMapCrew)
            
        if "PrivMod" in staff_positions:
            res += (self.privLevel in [8, 9, 10] or self.isPrivMod)
            
        if "Mod" in staff_positions:
            res += (self.privLevel >= 8)
            
        if "Admin" in staff_positions:
            res += (self.privLevel == 10)
            
        if "Owner" in staff_positions:
            res += (self.playerName in self.server.serverInfo["owners"])
        return res > 0

    def getProfileColor(self, isHacked=False) -> list:
        if self.privLevel == 4 or self.isFashionSquad:
            return ["009D9D" if isHacked else 1, "FFB6C1"]
    
        elif self.privLevel == 5 or self.isLuaCrew:
            return ["009D9D" if isHacked else 1, "8FE2D1"]
    
        elif self.privLevel == 6 or self.isFunCorp:
            return ["F89F4B" if isHacked else 13, "F89F4B"]
            
        elif self.privLevel == 7 or self.isMapCrew:
            return ["2F7FCC" if isHacked else 11, "2F7FCC"]
            
        elif self.isPrivMod:
            return ["009D9D" if isHacked else 1, "9F54CC"]
            
        elif self.privLevel in [8, 9]:
            return ["BABD2F" if isHacked else 5, "9F54CC"]
            
        elif self.privLevel == 10 or self.playerName in self.server.serverInfo["owners"]:
            return ["EB1D51" if isHacked else 10, "EB1D51"]
            
        return ["009D9D" if isHacked else 1, ""]

    def getStaffPermissions(self) -> str:
        perms = []
        if self.isGuest:
            perms.append("Guest")
            
        elif self.privLevel == 1:
            perms.append("Player")
    
        if self.privLevel in [4, 10] or self.isFashionSquad:
            perms.append("FS")
    
        if self.privLevel in [5, 10] or self.isLuaCrew:
            perms.append("LU")
    
        if self.privLevel in [6, 10] or self.isFunCorp:
            perms.append("FC")
            
        if self.privLevel in [7, 10] or self.isMapCrew:
            perms.append("MC")
            
        if self.privLevel in [8, 9, 10] or self.isPrivMod:
            perms.append("PrivMod")
            
        if self.privLevel >= 8:
            perms.append("Mod")
            
        if self.privLevel == 10:
            perms.append("Admin")
            
        if self.playerName in self.server.serverInfo["owners"]:
            perms.append("Owner")
        return ','.join(perms)

    def giveConsumable(self, _id, amount, flag=False):
        limit = 80
        if _id in [800, 801, 2257, 2472]:
            limit = 250
            
        elif _id in [2253, 2254, 2260, 2261, 2504, 2505, 2506, 2507, 2508, 2509, 2497, 2343]:
            limit = 200
            
        if flag:
            self.sendAnimZelda(4,_id)
        self.sendNewConsumable(_id, amount)
        sum = (self.playerConsumables[_id] if _id in self.playerConsumables else 0) + amount
        if sum > limit: sum = limit
        
        self.playerConsumables[_id] = sum
        self.sendUpdateInventoryConsumable(_id, sum, limit)

    def logConnection(self):
        if self.isGuest:
            return
            
        self.server.cursor['loginlog'].insert_one({
            'Username':self.playerName,
            'IP':IPTools.EncodeIP(self.ipAddress), 
            'Country':IPTools.GetCountry(self.ipAddress), 
            'Time': Time.getDate(), 
            'Community': self.playerLangue, 
            'ConnectionID':self.server.serverInfo["name"]
        })

    def makeStaffRoleInfo(self):
        if "FS" in self.privRoles:
            self.isFashionSquad = True
            
        if "FC" in self.privRoles:
            self.isFunCorp = True
            
        if "MC" in self.privRoles:
            self.isMapCrew = True
            
        if "LU" in self.privRoles:
            self.isLuaCrew = True
            
        if "PrivMod" in self.privRoles:
            self.isPrivMod = True

    def sendAnimZelda(self, type, item=0, case="", id=0):
        self.sendBullePacket(Identifiers.bulle.BU_SendAnimZelda, self.playerID, self.playerCode, type, item, id, case)

    def sendBanConsideration(self):
        self.sendPacket(Identifiers.old.send.Ban_Consideration, ["0"])

    def sendBotVerification(self):
        self.verifycoder = random.choice(range(0, 563432))
        self.sendPacket(Identifiers.send.PreLogin_Verification, ByteArray().writeInt(self.verifycoder).toByteArray())

    def sendBullePacket(self, packet_id, *args):
        self.server.bulles[self.bulleID["id"]].send_packet(packet_id, *args)

    def sendCodePrize(self, _local_1 : str, amount : int):
        if _local_1 == "cheese":
            self.sendPacket(Identifiers.send.Gain_Give, ByteArray().writeInt(amount).writeInt(0).toByteArray())
            self.sendPacket(Identifiers.send.Anim_Donation, ByteArray().writeByte(0).writeInt(amount).toByteArray())
            self.shopCheeses += amount
        elif _local_1 == "fraise":
            self.sendPacket(Identifiers.send.Gain_Give, ByteArray().writeInt(0).writeInt(amount).toByteArray())
            self.sendPacket(Identifiers.send.Anim_Donation, ByteArray().writeByte(1).writeInt(amount).toByteArray())
            self.shopFraises += amount
        elif _local_1 == "consumable":
            self.giveConsumable(amount, random.randint(10, 100))

    def sendCommunityPartners(self):
        packet = ByteArray()
        packet.writeShort(len(self.server.communityPartners))
        for partner in self.server.communityPartners:
            packet.writeUTF(partner["Name"]).writeUTF(partner["Icon"])
        self.sendPacket(Identifiers.send.Community_Partners, packet.toByteArray())

    def sendCorrectVersion(self, language, stand_type): 
        self.playerLangue = language.lower()
        self.sendPacket(Identifiers.send.Correct_Version, ByteArray().writeInt(len(self.server.players)).writeUTF(self.playerLangue).writeUTF('').writeInt(self.server.swfInfo["authkey"]).writeBoolean(self.server.serverInfo['streaming']).toByteArray())
        self.sendPacket(Identifiers.send.Image_Login, ByteArray().writeUTF(f'{self.server.serverInfo["event"]["adventure_banner"]}').toByteArray()) # #null#0#0
        self.sendPacket(Identifiers.send.Banner_Login, ByteArray().writeByte(1).writeByte(self.server.serverInfo["event"]["adventure_id"]).writeBoolean(True).writeBoolean(False).toByteArray())
        self.sendPacket(Identifiers.send.Set_News_Popup_Flyer, ByteArray().writeUTF(self.server.serverInfo["event"]["adventure_flyer"]).toByteArray())
        self.sendPacket(Identifiers.send.Set_Allow_Email_Address, ByteArray().writeBoolean(True).toByteArray())
        #self.sendPacket([20, 4], [])
        self.sendBotVerification()
        self.isVerifiedClientVersion = True
        
    def sendDefaultGlobalChat(self):
        if self.isGuest:
            return
            
        self.sendPacket(Identifiers.send.Rejoindre_Canal_Publique, ByteArray().writeUTF(self.playerLangue).toByteArray())

    def sendEmailVerifiedPacket(self, isVerified=True):
        if self.isGuest or not self.isEmailAddressVerified:
            return

        if not isVerified:
            self.isEmailAddressVerified = False

        self.sendPacket(Identifiers.send.Email_Address_Verified, ByteArray().writeByte(isVerified).toByteArray())

    def sendEnterRoom(self, roomName, community="", isHidden=False):
        if not self.isEnterRoom:
            self.isEnterRoom = True
            self.server.loop.call_later(0.8, asyncio.create_task, self.connectToBulle(roomName, community, isHidden))
            self.server.loop.call_later(1.9, setattr, self, "isEnterRoom", False)

    def sendGiveCurrency(self, type, count):
        self.sendPacket(Identifiers.send.Give_Currency, ByteArray().writeByte(type).writeByte(count).toByteArray())

    def sendLangueMessage(self, community, message, *args):
        packet = ByteArray().writeUTF(community).writeUTF(message).writeByte(len(args))
        for arg in args:
            packet.writeUTF(arg)
        self.sendPacket(Identifiers.send.Message_Langue, packet.toByteArray())

    def sendLogMessage(self, message):
        self.sendPacket(Identifiers.send.Log_Message, ByteArray().writeByte(0).writeUTF("").writeUnsignedByte((len(message) >> 16) & 0xFF).writeUnsignedByte((len(message) >> 8) & 0xFF).writeUnsignedByte(len(message) & 0xFF).writeBytes(message).toByteArray())

    def sendMessage(self, message):
        self.sendPacket(Identifiers.send.Chat_Message, ByteArray().writeUTF(message).toByteArray())

    def sendNewConsumable(self, consumable, count):
        self.sendPacket(Identifiers.send.New_Consumable, ByteArray().writeByte(0).writeShort(consumable).writeShort(count).toByteArray())

    def sendNPCBuyItem(self, itemID):
        item = self.server.npcs["Shop"][self.lastNpcName][itemID]
        type, _id, amount, four, priceItem, priceAmount = item
        if priceItem in self.playerConsumables and self.playerConsumables[priceItem] >= priceAmount:
            count = self.playerConsumables[priceItem] - priceAmount
            if count <= 0:
                del self.playerConsumables[priceItem]
            else:
                self.playerConsumables[priceItem] = count
            self.sendUpdateInventoryConsumable(priceItem, count)
            
            if type == 1:
                self.sendAnimZelda(3, _id)
                self.sendUnlockShopBadge(_id)
                
            elif type == 2:
                self.sendAnimZelda(6, _id)
                self.shamanBadges.append(_id)
                
            elif type == 3:
                self.giveTitle(_id, 1)
                
            elif type == 4:
                self.giveConsumable(_id, amount)
                
            self.sendNpcShop(self.lastNpcName)

    def sendNpcShop(self, npcName):
        npcShop = self.server.npcs["Shop"].get(npcName)
        self.lastNpcName = npcName
            
        data = ByteArray()
        data.writeUTF(npcName)
        data.writeByte(len(npcShop))
        
        for item in npcShop:
            type, id, amount, four, priceItem, priceAmount = item
            if (type == 1 and id in self.playerBadges) or (type == 2 and id in self.shamanBadges) or (type == 3 and self.checkPlayerTitle(id)) or (type == 4 and id in self.playerConsumables and self.playerConsumables.get(id) + amount > 256):
                data.writeByte(2)
            elif not priceItem in self.playerConsumables or self.playerConsumables.get(priceItem) < priceAmount:
                data.writeByte(1)
            else:
                data.writeByte(0)

            data.writeByte(type).writeInt(id).writeShort(amount).writeByte(four).writeInt(priceItem).writeShort(priceAmount).writeInt(0)
        self.sendPacket(Identifiers.send.NPC_Shop, data.toByteArray())

    def sendRestartPacket(self, seconds):
        self.sendPacket(Identifiers.send.Server_Restart, ByteArray().writeInt(seconds * 1000).toByteArray())

    def sendPlayerBan(self, hours, reason, silent=False):
        if not hours == -1:
            self.sendPacket(Identifiers.old.send.Player_Ban_Login, [hours * 3600000, reason])
        if not silent:
            if hours == -1:
                msg_type = "$MessageBanDefSalon"
            else:
                msg_type = "$Message_Ban"
                
            self.sendBullePacket(Identifiers.bulle.BU_SendBanMessage, self.roomName, self.playerID, self.playerName, str(hours), msg_type, str(reason))

    def sendPlayerEmotes(self):
        emotes = [] if len(self.shopEmotes) == 0 else self.shopEmotes.split(',')
        p = ByteArray().writeInt128(len(emotes))
        for emote in emotes: 
            p.writeInt128(int(emote))
        self.sendPacket(Identifiers.send.Emote_Panel, p.toByteArray())

    def sendPlayerIdentification(self):
        perms = ByteArray()
        permsList = []

        if self.isMapCrew or self.privLevel in [7, 10]:
            permsList.append(11)
            
        if self.isFashionSquad or self.privLevel in [4, 10]:
            permsList.append(15)
            
        if self.isLuaCrew or self.privLevel in [5, 10]:
            permsList.append(12)
            
        if self.isFunCorp or self.privLevel in [6, 10]:
            permsList.append(13)
                
        if self.isPrivMod or self.privLevel >= 8:
            if not self.playerLangue in self.modoCommunities:
                self.modoCommunities.append(self.playerLangue)
            # Cafe permissions
            permsList.insert(1, 3) 
            permsList.append(5)
            
        if self.privLevel >= 10:
            permsList.append(10)

        for i in permsList:
            perms.writeByte(i)
    
        data = ByteArray()
        data.writeInt(self.playerID)
        data.writeUTF(self.playerName)
        data.writeInt(self.playerTime)
        data.writeByte(Langue.getLangueID(self.playerLangue))
        data.writeInt(self.playerCode)
        data.writeBoolean(not self.isGuest)
        data.writeByte(len(permsList))
        data.writeBytes(perms.toByteArray())
        data.writeBoolean(self.privLevel >= 10)
        data.writeShort(255)
        data.writeShort(len(self.server.gameLanguages))
        for lang in self.server.gameLanguages:
            data.writeUTF(lang).writeUTF(self.server.gameLanguages[lang][1])
        self.sendPacket(Identifiers.send.Player_Identification, data.toByteArray())

    def sendPlayerInventory(self):
        inventory = []
        for consumable in self.playerConsumables.items():
            if str(consumable[0]) in self.server.inventoryConsumables:
                obj = self.server.inventoryConsumables[str(consumable[0])]
                if not "hide" in obj:
                    inventory.append([consumable[0], consumable[1], obj["sort"], not "blockUse" in obj, not "launchlable" in obj, obj["img"] if "img" in obj else "", self.equipedConsumables.index(consumable[0]) + 1 if consumable[0] in self.equipedConsumables else 0, self.server.getInventoryCategory(obj, consumable[0])])
            else:
                inventory.append([consumable[0], consumable[1], True, False, True, "", self.equipedConsumables.index(consumable[0]) + 1 if consumable[0] in self.equipedConsumables else 0,self.server.getInventoryCategory('', consumable[0])])

        data = ByteArray()
        data.writeShort(len(inventory))
        for info in inventory:
            data.writeShort(int(info[0]))
            data.writeUnsignedShort(int(info[1]))
            data.writeUnsignedByte(0)   # Unused but exist, wtf tfm
            data.writeBoolean(True)
            data.writeBoolean(info[3])              # use?
            data.writeBoolean(info[3])              # equip?
            data.writeBoolean(not info[3])
            data.writeByte(info[7])                 # Item category
            data.writeByte(info[2])
            data.writeBoolean(info[4])
            data.writeBoolean(len(info[5]) > 0)
            if len(info[5]) > 0:
                data.writeUTF(info[5])              # Image
            data.writeByte(info[6])                 # Consumable index
        self.sendPacket(Identifiers.send.Inventory, data.toByteArray())

    def sendPlayerMute(self, hours, reason):
        self.sendBullePacket(Identifiers.bulle.BU_SendMute, self.playerID, self.isMuted, hours, reason)

    def sendPlayerMuteMessage(self, hours, reason, isOnly=False):
        self.sendLangueMessage("", "<ROSE>$MuteInfo1", str(hours), reason)
        if isOnly:
            return
        self.sendBullePacket(Identifiers.bulle.BU_SendBanMessage, self.roomName, self.playerID, self.playerName, str(hours), "$MuteInfo2", str(reason))

    def sendPlayerTime(self):
        self.sendPacket(Identifiers.send.Time_Stamp, ByteArray().writeInt(self.loginTime / 1000).toByteArray())

    def sendProfile(self, playerName, isHacked=False):
        player = self.server.players.get(playerName)
        if player == None:
            return
            
        if player.isGuest:
            return
            
        color = player.getProfileColor(isHacked)[0]
        packet = ByteArray()
        
        packet.writeUTF(player.playerName)
        packet.writeInt(player.playerID)
        packet.writeInt(str(player.playerRegDate)[:10])
        if isHacked:
            packet.writeInt(int(color, 16))
        else:
            packet.writeByte(color)
        packet.writeByte(player.genderType)
        packet.writeUTF(player.tribeName)
        packet.writeUTF(player.playerSoulmate)
        
        packet.writeInt(player.shamanNormalSaves)
        packet.writeInt(player.shamanCheeses)
        packet.writeInt(player.firstCount)
        packet.writeInt(player.cheeseCount)
        packet.writeInt(player.shamanHardSaves)
        packet.writeInt(player.bootcampCount)
        packet.writeInt(player.shamanDivineSaves)
        packet.writeInt(player.shamanNormalSavesNoSkill)
        packet.writeInt(player.shamanHardSavesNoSkill)
        packet.writeInt(player.shamanDivineSavesNoSkill)
        
        packet.writeShort(player.titleNumber).writeShort(len(self.titleList))
        for title in self.titleList:
            packet.writeShort(int(title - (title % 1)))
            packet.writeByte(int(round((title % 1) * 10)))
        
        packet.writeUTF(((str(player.furType) + ";" + player.playerLook.split(";")[1]) if player.furType != 0 else player.playerLook) + ";" + player.mouseColor)
        packet.writeShort(player.shamanLevel)
        
        uniqueBadges = self.server.getPlayerBadgesUnique(player.playerBadges)
        packet.writeUnsignedShort(len(uniqueBadges) * 2)
        for badge in uniqueBadges:
            packet.writeUnsignedShort(badge).writeUnsignedShort(uniqueBadges.count(badge))

        packet.writeByte(len(self.server.profileStats))
        x = 0
        for stat in self.server.profileStats:
            packet.writeByte(stat[0]).writeInt(player.playerStats[x]).writeInt(stat[1]).writeShort(stat[2])
            x += 1

        packet.writeUnsignedByte(player.equipedShamanBadge).writeUnsignedByte(len(player.shamanBadges))
        for badge in player.shamanBadges:
            packet.writeUnsignedByte(badge)

        packet.writeBoolean(True) # appear adventure 
        packet.writeInt(self.adventurePoints)
        self.sendPacket(Identifiers.send.Profile, packet.toByteArray())

    def sendRegisteredAccountConsumable(self):
        if self.isNewAccount:
            self.giveConsumable(0, 10)
            self.isNewAccount = False

    def sendServerMessage(self, message, tab=False, *args):
        packet = ByteArray().writeBoolean(tab).writeUTF(message).writeByte(len(args))
        for arg in args:
            packet.writeUTF(args)
        self.sendPacket(Identifiers.send.Recv_Message, packet.toByteArray())

    def sendShamanType(self, mode, canDivine, isNoSkills):
        self.sendPacket(Identifiers.send.Shaman_Type, ByteArray().writeByte(mode).writeBoolean(canDivine).writeInt(int(self.shamanColor, 16)).writeBoolean(isNoSkills).toByteArray())

    def sendSourisLogin(self):
        if not self.isGuest:
            return
    
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(1).writeByte(10).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(2).writeByte(5).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(3).writeByte(15).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(4).writeByte(200).toByteArray())

    async def sendStaffChannelMessage(self, _id, message, tab=False, isTranslation=False, *args):
        packet = ByteArray().writeByte(_id).writeUTF(self.playerName).writeUTF(message).writeBoolean(tab).writeBoolean(isTranslation).writeByte(len(args))
        for arg in args:
            packet.writeUTF(arg)
        self.server.sendStaffChannelMessage(_id, self.playerLangue, Identifiers.send.Send_Staff_Chat_Message, packet.toByteArray())
        if self.privLevel >= 8 or self.isPrivMod and message.startswith('.'):
            await self.ChannelCommands.parseCommand(message[1:], _id)

    def sendTotalCheeseToExportMap(self, amount=40):
        self.mapEditorCheese = amount
        self.sendPacket(Identifiers.send.Amount_To_Export_Map, ByteArray().writeUnsignedShort(amount).toByteArray())

    def sendTradeInvite(self, playerCode):
        self.sendPacket(Identifiers.send.Trade_Invite, ByteArray().writeInt(playerCode).toByteArray())
            
    def sendTradeResult(self, playerName, result):
        self.sendPacket(Identifiers.send.Trade_Result, ByteArray().writeUTF(playerName).writeByte(result).toByteArray())

    def sendTradeStart(self, playerCode):
        self.sendPacket(Identifiers.send.Trade_Start, ByteArray().writeInt(playerCode).toByteArray())

    def sendTribulleInitialization(self):
        if self.isGuest:
            return
    
        self.Tribulle.sendPlatformCommunityConnection()
        
        for player in self.server.players.values():
            if self.playerName in player.friendList and player.playerName in self.friendList:
                player.Tribulle.sendFriendConnected(self.playerName)
                
        if self.tribeCode != 0:
            self.Tribulle.sendTribeMemberConnected()

    def sendTribullePacket(self, code, result):
        if self.isGuest:
            return
        self.sendPacket(Identifiers.send.New_Tribulle, ByteArray().writeShort(code).writeBytes(result).toByteArray())

    def sendTribullePacketOld(self, code, result):
        if self.isGuest:
            return
        self.sendPacket(Identifiers.send.Old_Tribulle, ByteArray().writeShort(code).writeBytes(result).toByteArray())

    def sendTribulleProtocol(self, isNew=True):
        self.sendPacket(Identifiers.send.Switch_Tribulle, ByteArray().writeBoolean(isNew).toByteArray())
           
    def sendUnlockShopBadge(self, badge):
        if badge in self.playerBadges:
            return
    
        self.sendBullePacket(Identifiers.bulle.BU_SendShopBadge, self.playerID, badge)
        self.playerBadges.append(badge)
           
    def sendUpdateInventoryConsumable(self, _id, count, limit=65535):
        self.sendPacket(Identifiers.send.Update_Inventory_Consumable, ByteArray().writeShort(_id).writeUnsignedShort(limit if count > limit else count).toByteArray())
           
    def sendWatchPlayerPacket(self, playerName, status):
        self.sendPacket(Identifiers.send.Watch_Player, ByteArray().writeUTF(playerName).writeBoolean(status).toByteArray())

    def useConsumable(self, _id):
        if _id in self.playerConsumables:
            if str(_id) in self.server.inventoryConsumables:
                obj = self.server.inventoryConsumables.get(str(_id))
                if "launchObject" in obj:                
                    objectCode = obj["launchObject"]
                    self.sendBullePacket(Identifiers.bulle.BU_SendTrowableObject, self.playerID, objectCode, _id)
               
                elif "pet" in obj:
                    if not self.petType > 0:
                        self.petType = obj["pet"]
                        self.petEnd = Time.getTime() + 3600
                        self.sendBullePacket(Identifiers.bulle.BU_SendPlayerPet, self.roomName, self.playerID, self.petType, self.petEnd, self.playerCode)
                        
                elif "fur" in obj:
                    self.furType = obj["fur"]
                    self.furEnd = Time.getTime() + 3600
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerFur, self.playerID, self.furType, self.furEnd)
        
                elif "pencil" in obj:
                    pencilColor = int(obj["pencil"], 16)
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerPencil, self.playerID, pencilColor)
                
                elif _id == 10:
                    self.sendBullePacket(Identifiers.bulle.BU_SendMistletoe, self.playerID)
                
                elif _id == 11: #UNFINISHED
                    pass
                
                elif _id == 21:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerEmote, self.playerID, 12, "", False)
        
                elif _id == 28:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerBonfire, self.playerID)
        
                elif _id == 33:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerEmote, self.playerID, 16, "", False)
        
                elif _id == 35:
                    if len(self.playerBadges) == 0:
                        return
                    badge = random.choice(self.playerBadges)
                    self.sendBullePacket(Identifiers.bulle.BU_SendBallonBadge, self.playerID, self.playerCode, badge)
        
                elif _id == 800:
                    self.shopCheeses += 1
                    self.sendAnimZelda(2, 0)
                    self.sendGiveCurrency(0, 1)
                    
                elif _id == 801:
                    self.shopFraises += 1
                    self.sendAnimZelda(2, 2)
        
                elif _id == 2234:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerMicrophone, self.playerID)
        
                elif _id == 2239:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerCheeses, self.playerID, self.playerCode, self.shopCheeses)
        
                elif _id == 2246:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerEmote, self.playerID, 24, "", False)
   
                elif _id == 2255:
                    self.sendAnimZelda(7, 0, "$De6", random.randint(0, 6))
   
                elif _id == 2259:
                    self.sendBullePacket(Identifiers.bulle.BU_SendPlayerPlayedTime, self.playerID, self.playerCode, int(self.playerTime // 86400), int(self.playerTime // 3600) % 24)

                elif _id > 2473 and _id < 2493: # Chests
                    pass
                    
                if not "letter" in obj:
                    count = self.playerConsumables[_id] - 1
                    if count <= 0:
                        del self.playerConsumables[_id]
                    else:
                        self.playerConsumables[_id] = count
                    self.sendBullePacket(Identifiers.bulle.BU_UseInventoryConsumable, self.playerID, self.playerCode, _id)
                    self.sendUpdateInventoryConsumable(_id, count)


    def buyItemResult(self, fullitem, isShopShamanItem=False):
        self.sendAnimZelda(int(isShopShamanItem), fullitem)
        self.sendUnlockTitle("shop")
        self.sendUnlockShopBadge(self.server.getShopBadge(fullitem))
        #self.client.missions.upMission('6')
                
                
    def sendUnlockTitle(self, typ): # Shop
        pass
                
    def updateDatabase(self): # UNFINISHED
        if self.isGuest or self.server.disableDatabase:
            return
 
        self.server.cursor['users'].update_one({'Username':self.playerName},{'$set':{
            # Identification
            "PlayerGender": self.genderType,
            "PlayerKarma": self.playerKarma,
            "PlayerTime": self.playerTime + abs(Time.getSecondsDiff(self.loginTime)),
            "PlayerLook": self.playerLook,

            # Stats
            "FirstCount": self.firstCount,
            "CheeseCount": self.cheeseCount,
            "ShamanCheeses": self.shamanCheeses,
            "BootcampCount": self.bootcampCount,
            "NormalSavesCount": self.shamanNormalSaves,
            "NormalSavesCountNS": self.shamanNormalSavesNoSkill,
            "HardSavesCount": self.shamanHardSaves,
            "HardSavesCountNS": self.shamanHardSavesNoSkill,
            "DivineSavesCount": self.shamanDivineSaves,
            "DivineSavesCountNS": self.shamanDivineSavesNoSkill,
            "PlayerBadges": ",".join(map(str, filter(None, [badge for badge in self.playerBadges]))),
            "PlayerStats": ",".join(map(str, self.playerStats)),
            "TitleNumber": self.titleNumber,
            # Title list
            
            # Shaman
            "ShamanLevel": self.shamanLevel,
            "CurrentShamanBadge": self.equipedShamanBadge,
            "ShamanBadges": (",".join(map(str, filter(None, [badge for badge in self.shamanBadges])))),
            "ShamanLook": self.shamanLook,
            "ShamanType": self.shamanType,
            "ShamanColor": self.shamanColor,
            
            # Shop
            "ShopCheeseCount": self.shopCheeses,
            "ShopFraiseCount": self.shopFraises,
            "ShopItems": self.shopItems,
            "ShopFavoriteItems": self.shopFavoriteItems,
            "ShopClothes": self.shopClothes,
            "ShopShamanItems": self.shopShamanItems,
            "ShopEmotes": self.shopEmotes,
            
            # Tribulle
            "FriendsList": (",".join(map(str, filter(None, [player for player in self.friendList])))),
            "IgnoredList": (",".join(map(str, filter(None, [player for player in self.ignoredList])))),
            "Soulmate": self.playerSoulmate,
            "TribeCode": self.tribeCode,
            "TribeJoined": self.tribeJoined,
            "TribeRank": self.tribeRank,
            "LastDivorceTime": self.lastDivorceTime,
            
            # Other
            "EmailVerified": int(self.isEmailAddressVerified),
            "EquipedConsumables": (",".join(map(str, filter(None, [consumable for consumable in self.equipedConsumables])))),
            "Inventory": ";".join(map(lambda consumable: "%s:%s" %(consumable[0], consumable[1]), self.playerConsumables.items())),
            "AdventurePoints": self.adventurePoints,
            "ModoCommunities": ",".join(map(str, filter(None, [community for community in self.modoCommunities]))),
            # adventure info
            "LastOn": self.Tribulle.getTime(),
            "FurType": self.furType,
            "FurEnd": self.furEnd,
            "PetType": self.petType,
            "PetEnd": self.petEnd
            # Toteminfo
        }})
                
    def checkAccountCreationTime(self) -> bool:
        return True
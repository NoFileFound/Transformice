import asyncio
import random
import string
import time
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers
from Modules.Packets import Packets
from Modules.Cafe import Cafe
from Modules.Shop import Shop
from Utils import Config, Logger
from Utils.Langue import Langue
from Utils.Time import Time

class Client:
    def __init__(self, _server, _cursor):
        self.server = _server
        self.cursor = _cursor
        self.clientPacket = ByteArray()
        self.Logger = Logger.Logger()
        
        # Boolean
        self.isCafeOpened = False
        self.isClosed = False
        self.isEmailAddressVerified = False
        self.isEnterRoom = False
        self.isGuest = False
        self.isFashionSquad = False
        self.isFunCorp = False
        self.isLoggedIn = False
        self.isLuaCrew = False
        self.isMapCrew = False
        self.isPrivMod = False
        self.isReloadCafe = False
        self.isVerifiedClientVersion = False
        
        # Integer
        self.cheeseCount = 0
        self.genderType = 0
        self.lastPacketID = 0
        self.loginTime = 0
        self.loginWrongAttemps = 0
        self.verifycoder = 0
        self.playerCode = 0
        self.playerID = 0
        self.playerTime = 0
        self.privLevel = 0
        self.shamanNormalSaves = 0
        self.shopCheeses = 0
        self.shopFraises = 0
        
        # String
        self.computerInformation = ""
        self.currentCaptcha = ""
        self.gameLanguage = ""
        self.flashVersion = ""
        self.mouseColor = "78583a"
        self.playerName = ""
        self.playerEmail = ""
        self.playerLook = "1;0,0,0,0,0,0,0,0,0,0,0"
        self.playerSoulmate = ""
        self.shamanColor = "95d9d6"
        self.shamanLook = "0,0,0,0,0,0,0,0,0,0"
        
        self.shopItems = ""
        self.shopFavoriteItems = ""
        self.shopClothes = ""
        self.shopShamanItems = ""
        self.shopEmotes = ""

        # Nonetype
        self.awakeTimer = None
        self.transport = None
        self.ipAddress = None
        
        # List
        self.friendList = []
        self.ignoredList = []
        self.privRoles = []
        
        # Dictionary
        self.shopGifts = {}
        
        # Loops
        self.loop = asyncio.get_event_loop()
        
        # Other
        self.CAPTime = time.time()
        
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
        
        self.Packets = Packets(self, self.server)
        self.Cafe = Cafe(self, self.server)
        self.Shop = Shop(self, self.server)

    def data_received(self, packet: bytes) -> None:
        if self.isClosed or len(packet) < 2:
            return
    
        if self.server.isDebug:
            self.Logger.debug(f"[CLIENT] {self.ipAddress} -> Data Received\n")
    
        if packet == b'<policy-file-request/>\x00':
            self.server.Logger.warn(f"{self.ipAddress} -> Policy File Request")
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
        
    def eof_received(self) -> None:
        if self.server.isDebug:
            self.Logger.debug(f"[CLIENT] {self.ipAddress} -> EOF Received\n")
        return
            
    def connection_lost(self, *args) -> None: #############
        self.isClosed = True
        if self.playerName in self.server.players:
            del self.server.players[self.playerName]
            
        self.transport.close()
        
        
        
    async def createAccount(self, playerName, email, password): #############
        playerName = self.server.genPlayerTag(playerName)
        self.server.lastPlayerID = self.cursor['users'].count_documents({}) + 1
    
        self.cursor['users'].insert_one({
            # Identification
            "Username" :                        playerName,
            "Password" :                        password,
            "Email" :                           email,
            "PlayerID" :                        self.server.lastPlayerID,
            "PlayerGender" :                    0,
            "PlayerTime" :                      0,
            "PlayerLook" :                      self.playerLook,
            "PrivLevel" :                       1,
            "PrivRoles" :                       "", # ["FS", "LUA", "FC", "MC", "PrivMod"]
            
            # Profile
            "FirstCount" :                      self.server.gameInfo["initial_firsts"],
            "CheeseCount" :                     self.server.gameInfo["initial_cheeses"],
            "BootcampCount" :                   self.server.gameInfo["initial_bootcamps"],
            "NormalSavesCount" :                self.server.gameInfo["initial_saves"]["normal"],
            "NormalSavesCountNS" :              self.server.gameInfo["initial_saves"]["normal_no_skill"],
            "HardSavesCount" :                  self.server.gameInfo["initial_saves"]["hard"],
            "HardSavesCountNS" :                self.server.gameInfo["initial_saves"]["hard_no_skill"],
            "DivineSavesCount" :                self.server.gameInfo["initial_saves"]["divine"],
            "DivineSavesCountNS" :              self.server.gameInfo["initial_saves"]["divine_no_skill"],
            
            # Shop
            "ShopCheeseCount" :                 self.server.gameInfo["initial_shop"]["cheese"],
            "ShopFraiseCount" :                 self.server.gameInfo["initial_shop"]["strawberry"],
            "ShopItems"       :                 "",
            "ShopFavoriteItems":                "",
            "ShopClothes"     :                 "",
            "ShopShamanItems" :                 "",
            "ShopEmotes"      :                 "",
            
            # Friends & Ignored
            "FriendsList" :                     "",
            "IgnoredList" :                     "",
            
            # Other
            "EmailVerified":                    0
            
        })
        await self.loginPlayer(playerName, password, f"\x03[Tutorial] {playerName}")
        
    def checkAccountCreationTime(self) -> bool:
        return True
        
    async def loginPlayer(self, playerName, password, startRoom): ###########
        if password == "":
            # player is guest
            self.playerName = self.server.checkAlreadyExistingGuest(playerName)
            startRoom = f"\x03[Tutorial] {playerName}"
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
                # Identification
                self.playerName = playerName
                self.playerEmail = rs["Email"]
                self.playerID = rs["PlayerID"]
                self.genderType = rs["PlayerGender"]
                self.playerTime = rs["PlayerTime"]
                self.playerLook = rs["PlayerLook"]
                self.privLevel = rs["PrivLevel"]
                self.privRoles = rs["PrivRoles"].split(",")
                
                # Stats
                self.cheeseCount = rs["CheeseCount"]
                self.shamanNormalSaves = rs["NormalSavesCount"]
                
                # Shop
                self.shopCheeses = rs["ShopCheeseCount"]
                self.shopFraises = rs["ShopFraiseCount"]
                self.shopItems = rs["ShopItems"]
                self.shopFavoriteItems = rs["ShopFavoriteItems"]
                self.shopClothes = rs["ShopClothes"]
                self.shopShamanItems = rs["ShopShamanItems"]
                self.shopEmotes = rs["ShopEmotes"]
                
                # Tribulle
                self.friendList = rs["FriendsList"].split(",")
                self.ignoredList = rs["IgnoredList"].split(",")
                
                # Other
                self.isEmailAddressVerified = bool(rs["EmailVerified"])
                
                playerName = self.playerName
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
            self.sendSourisLogin() ########
            self.sendPlayerTime()
            self.sendEmailVerifiedPacket(self.isEmailAddressVerified) ########
            self.sendTribulleProtocol()
            self.sendCommunityPartners()
            self.Shop.sendPromotions()
            self.Shop.sendShamanItems()
            self.sendPlayerEmotes()
            
            self.sendDefaultGlobalChat() ########
            self.sendBulle(startRoom)
            self.Shop.sendPromotionPopup() #######

    def sendDefaultGlobalChat(self):
        if self.isGuest:
            return
            
        self.sendTribullePacket(62, ByteArray().writeUTF(self.gameLanguage).toByteArray())
        
    def sendSourisLogin(self):
        if not self.isGuest:
            return
    
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(1).writeByte(10).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(2).writeByte(5).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(3).writeByte(15).toByteArray())
        self.sendPacket(Identifiers.send.Login_Souris, ByteArray().writeByte(4).writeByte(200).toByteArray())

    def buyItemResult(self, fullitem, isShopShamanItem=False):
        #self.client.sendAnimZelda(0, fullItem)
        #self.checkUnlockShopTitle()
        #self.checkUnlockShopBadge(fullItem)
        #self.client.missions.upMission('6')
        pass
        
    def connectToBulle(self, roomName): ########
        bulleInfo = random.choice(list(self.server.bullesInfo))
        bullePorts = '-'.join(map(str, bulleInfo["port"]))
        
        self.sendPacket(Identifiers.send.Init_Bulle_Connection, ByteArray().writeInt(bulleInfo["id"]).writeInt(int(time.time() / 100)).writeInt(self.playerID).writeUTF(bulleInfo["ip_address"]).writeUTF(bullePorts).toByteArray())
        self.server.bulles[bulleInfo["id"]].send_packet(1, self.playerID, self.playerName, self.playerCode, roomName, self.gameLanguage)
        if self.server.isDebug:
            self.Logger.debug(f"[{self.ipAddress}] Connected to bulle{bulleInfo['id']} : {bulleInfo['ip_address']}:{bullePorts}.\n")

    def makeStaffRoleInfo(self):
        if "FS" in self.privRoles:
            self.isFashionSquad = True
            
        if "FC" in self.privRoles:
            self.isFunCorp = True
            
        if "MC" in self.privRoles:
            self.isMapCrew = True
            
        if "LUA" in self.privRoles:
            self.isLuaCrew = True
            
        if "PMOD" in self.privRoles:
            self.isPrivMod = True

    def sendBulle(self, roomName):
        if not self.isEnterRoom:
            self.isEnterRoom = True
            self.server.loop.call_later(0.8, lambda: self.connectToBulle(roomName))
            self.server.loop.call_later(5, setattr, self, "isEnterRoom", False)

    def sendBotVerification(self):
        self.verifycoder = random.choice(range(0, 563432))
        self.sendPacket(Identifiers.send.PreLogin_Verification, ByteArray().writeInt(self.verifycoder).toByteArray())
 
    def sendCommunityPartners(self):
        packet = ByteArray()
        packet.writeShort(len(self.server.communityPartners))
        for partner in self.server.communityPartners:
            packet.writeUTF(partner["Name"]).writeUTF(partner["Icon"])
        self.sendPacket(Identifiers.send.Community_Partners, packet.toByteArray())
 
    def sendCorrectVersion(self, language, stand_type): ########
        self.gameLanguage = language
        self.sendPacket(Identifiers.send.Correct_Version, ByteArray().writeInt(len(self.server.players)).writeUTF(self.gameLanguage).writeUTF('').writeInt(self.server.swfInfo["authkey"]).writeBoolean(self.server.serverInfo['streaming']).toByteArray())
        self.sendPacket(Identifiers.send.Banner_Login, ByteArray().writeByte(1).writeByte(self.server.serverInfo["event"]["adventure_id"]).writeBoolean(True).writeBoolean(True).toByteArray())
        self.sendPacket(Identifiers.send.Image_Login, ByteArray().writeUTF(self.server.serverInfo["event"]["adventure_banner"]).toByteArray())
        self.sendBotVerification()
        self.isVerifiedClientVersion = True
        
    def sendEmailVerifiedPacket(self, isVerified=True):
        if self.isGuest:
            return
    
        if not self.isEmailAddressVerified:
            return
    
        if not isVerified :
            self.isEmailAddressVerified = False

        self.sendPacket(Identifiers.send.Email_Address_Verified, ByteArray().writeBoolean(isVerified).toByteArray())
        
    def sendLangueMessage(self, community, message, *args):
        packet = ByteArray().writeUTF(community).writeUTF(message).writeByte(len(args))
        for arg in args:
            packet.writeUTF(arg)
        self.sendPacket(Identifiers.send.Message_Langue, packet.toByteArray())
                  
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
        data.writeByte(Langue.getLangueID(self.gameLanguage))
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
        
    def sendPlayerTime(self):
        self.sendPacket(Identifiers.send.Time_Stamp, ByteArray().writeInt(self.loginTime / 1000).toByteArray())
        
    def sendServerMessage(self, message, tab=False):
        self.sendPacket(Identifiers.send.Recv_Message, ByteArray().writeBoolean(tab).writeUTF(message).writeByte(0).toByteArray())
        
    def sendTribullePacket(self, code, result):
        if self.isGuest:
            return
        self.sendPacket(Identifiers.send.New_Tribulle, ByteArray().writeShort(code).writeBytes(result).toByteArray())
        
    def sendTribullePacketOld(self, code, result):
        if self.isGuest:
            return
        self.sendPacket(Identifiers.send.Old_Tribulle, ByteArray().writeShort(code).writeBytes(result).toByteArray())
        
    def sendTribulleProtocol(self, isNew=True):
        if self.isGuest:
            return
        self.sendPacket(Identifiers.send.Switch_Tribulle, ByteArray().writeBoolean(isNew).toByteArray())
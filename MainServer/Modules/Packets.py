import base64
import datetime
import hashlib
import random
import re
import string
import time
import traceback
import uuid
import zlib

# Modules
from Modules.Identifiers import Identifiers
from Modules.ByteArray import ByteArray

# Utils
from Utils.IPTools import IPTools
from Utils.Time import Time

class Packets:
    def __init__(self, player):
        self.client = player
        self.server = player.server
        self.packets = {}
        self.__init_2()
        
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
                if ccc in [45103, 6664]:
                    self.packet.decryptIdentification(self.server.swfInfo["packet_keys"], (str(self.client.verifycoder).encode() if ccc == 45103 else b"identification"))
                else:
                    self.packet.decrypt(self.server.swfInfo["packet_keys"], packetID)
            for i in self.packets[ccc][0]:
                exec(f"self.value = self.packet.{i}()")
                args.append(self.value)
            await self.packets[ccc][1](self, *args)
            
            if (self.packet.bytesAvailable()):
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] The server did not property send the packet {C}:{CC} to client. Res: {repr(packet.toByteArray())}\n")
                
        else:
            self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] The packet {C}:{CC} is not registered in the server.\n")

    def __init_2(self):
        @self.packet(args=['readUTF', 'readShort', 'readUTF', 'readUTF'])
        async def Add_Outfit(self, name, bg, date, look):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
            if name != "name" and date != "date" and look != "look":
                date = int(date)
                if date > 2147483647:
                    self.client.sendServerMessage("Invalid arguments.", True)
                    return await self.parsePacket(self.client.lastPacketID + 1, 149, 12, ByteArray())
                else:
                    bg = int(bg)
                    self.server.shopInfo["fullLooks"].append({"id":self.server.getLastShopOutfitID(), "look":look, "bg":bg, "discount":0, "start":date, "perm":False, "name":name, "addedBy":self.client.playerName})
                    for item in self.server.shopOutfits:
                        self.server.shopOutfitsCheck[str(item["id"])] = [item["look"], item["bg"], item["discount"], item["start"], item["perm"], item["name"], item["addedBy"]]
            else:
                self.client.sendServerMessage("Invalid arguments.", True)
            return await self.parsePacket(self.client.lastPacketID + 1, 149, 12, ByteArray())

        @self.packet(args=['readUTF', 'readUTF', 'readUTF', 'readByte'])
        async def Add_Sale(self, item_id, starting_date, ending_date, amount):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
                
            if item_id != "item id" and starting_date != "starting date" and ending_date != "ending date":
                cat =  int(item_id.split(',')[0])
                item_id = int(item_id.split(',')[1])
                isShamanItem = False
                if cat == 0:
                    cat = -1
                    isShamanItem = True
            
                self.server.shopPromotions.append({"Id": self.server.getLastShopPromotionID(), "Category": cat, "Item": item_id, "Discount": amount, "Time": (int(ending_date) - int(starting_date)) + self.server.serverTime, "isShamanItem":isShamanItem, "Perm":False, "Collector": (amount == 0), "AddedBy": self.client.playerName})
            else:
                self.client.sendServerMessage("Invalid arguments", True)
            return await self.parsePacket(1, 149, 16, ByteArray())

        @self.packet(args=['readShort', 'readUTF'])
        async def Buy_Full_Look_Confirm(self, _id, status):
            self.client.Shop.buyFullLookConfirm(_id, status)

        @self.packet(args=['readByte', 'readBoolean'])
        async def Buy_Shop_Clothe(self, _id, withFraises):
            self.client.Shop.buyClothe(_id, withFraises)

        @self.packet(args=['readInt', 'readBoolean'])
        async def Buy_Shop_Item(self, _id, withFraises):
            original_fullitem = _id
        
            # How is this working?
            if _id > 10999 and _id < 100000:
                info = list(str(_id))
                ch = int(info[0]) - 1
                info[0] = str(ch)
                _id = int("".join(info))
            
            elif _id > 100000:
                info = list(str(_id))
                ch = int(info[1]) - 1
                info[1] = str(ch)
                _id = int("".join(info))

            self.client.Shop.buyItem(_id, withFraises, original_fullitem)

        @self.packet(args=['readShort', 'readBoolean'])
        async def Buy_Shop_Shaman_Custom(self, _id, withFraises):
            self.client.Shop.buyShamanCustomItem(_id, withFraises)

        @self.packet(args=['readShort', 'readBoolean'])
        async def Buy_Shop_Shaman_Item(self, _id, withFraises):
            self.client.Shop.buyShamanItem(_id, withFraises)

        @self.packet(args=['readUTF'])
        async def Cancel_Trade(self, playerName):
            self.client.cancelTrade(playerName)

        @self.packet(args=['readUTF'])
        async def Cancel_Transaction(self, token):
            self.client.transactionToken = ""

        @self.packet(args=[])
        async def Cancel_Verify_Email_Adress(self):
            self.client.lastEmailCode = ""

        @self.packet(args=['readShort'])
        async def Change_Mission(self, missionID):
            self.client.DailyQuests.changeMission(str(missionID))

        @self.packet(args=['readByte'])
        async def Change_Shaman_Badge(self, badge):
            if badge == 0 or badge in self.client.shamanBadges:
                self.client.equipedShamanBadge = badge
                self.client.sendBullePacket(Identifiers.bulle.BU_ChangeShamanBadge, self.client.playerID, badge)
                self.client.sendProfile(self.client.playerName)

        @self.packet(args=['readInt'])
        async def Change_Shaman_Color(self, color):
            self.client.shamanColor = "%06X" %(0xFFFFFF & color)
            self.client.sendBullePacket(Identifiers.bulle.BU_ChangeShamanColor, self.client.playerID, self.client.shamanColor)

        @self.packet(args=['readByte', 'readBoolean'])
        async def Change_Shaman_Type(self, _id, withoutSkills):
            if self.client.shamanNormalSaves < 1500:
                return
        
            self.client.shamanType = _id
            self.client.sendShamanType(_id, (self.client.shamanNormalSaves >= 5000 and self.client.shamanHardSaves >= 2000), withoutSkills)
            self.client.sendBullePacket(Identifiers.bulle.BU_ChangeShamanType, self.client.playerID, _id, int(withoutSkills))

        @self.packet(args=['readUTF', 'readUTF', 'readUTF'])
        async def Computer_Info(self, osLang, osinfo, flashver):
            if not osinfo.startswith("Windows") and not osinfo.startswith("Linux"):
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Tried log in with unknown OS type ({osinfo}).\n")
                self.client.transport.close()
                return
            
            if flashver != "WIN 32,0,0,445":
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Connect to the server with older flash player version ({flashver}).\n")
        
            self.client.computerLanguage = osLang
            self.client.playerInfo.append(osinfo)
            self.client.playerInfo.append(flashver)

        @self.packet(args=['readShort', 'readUTF', 'readUTF', 'readUTF'])
        async def Correct_Version(self, version, language, con_key, stand):
            if not self.client.isVerifiedClientVersion:
                if (version != self.server.swfInfo["version"]) or (con_key != self.server.swfInfo["connection_key"]):
                    self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] Version/CKEY check failure. ({version}/{con_key})\n")
                    self.client.transport.close()
                    return
            self.client.sendCorrectVersion(language, stand)
            self.client.playerInfo.append(stand)
            self.client.playerInfo.append(self.packet.readUTF())
            self.client.playerInfo.append(self.packet.readInt())
            self.packet.readUTF()
            self.client.playerInfo.append(self.packet.readUTF())
            self.client.playerInfo.append(self.packet.readUTF())
            self.client.playerInfo.append(self.packet.readInt())
            self.client.playerInfo.append(self.packet.readInt())

        @self.packet(args=['readUTF', 'readUTF', 'readUTF', 'readUTF', 'readShort', 'readUTF'], decrypt=True)
        async def Create_Account(self, playerName, password, email, captcha, unknown, flash_url):
            if flash_url != self.server.serverInfo["swf_url"]:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] FLASH URL check failure. {flash_url}\n")
                self.client.transport.close()
                return

            if not re.match("^(?=^(?:(?!.*_$).)*$)(?=^(?:(?!_{2,}).)*$)[A-Za-z][A-Za-z0-9_]{2,11}$", playerName) or len(playerName) > 11:
                # Player name is invalid
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(4).writeUTF("").writeUTF("").toByteArray())
            elif self.client.currentCaptcha != captcha:
                # captcha is not the same
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(7).writeUTF("").writeUTF("").toByteArray())
            elif self.server.checkAlreadyExistingAccount(playerName):
                # account already exist
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(3).writeUTF("").writeUTF("").toByteArray())
            elif len(self.server.getTotalAccountsByEmailAddress(email)) > 7:
                # too many accounts in given email address
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(10).writeUTF("").writeUTF("").toByteArray())
            elif not self.client.checkAccountCreationTime():
                # creating too much accounts on same time
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(5).writeUTF("").writeUTF("").toByteArray())
            else:
                try:
                    await self.client.createAccount(playerName, email, password)
                except:
                    # Unknown error (internal error)
                    self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(6).writeUTF("").writeUTF("").toByteArray())

        @self.packet(args=[])
        async def Create_Account_Captcha(self):
            if time.time() - self.client.CAPTime > 2:
                self.client.currentCaptcha, px, ly, lines = self.server.buildCaptchaCode()
                packet = ByteArray().writeByte(0).writeShort(px).writeShort(ly).writeShort(px * ly)
                
                for line in lines:
                    packet.writeBytes(b"\x00" * 4)
                    for value in line.split(","):
                        packet.writeUnsignedByte(int(value)).writeBytes(b"\x00" * 3)
                    packet.writeBytes(b"\x00" * 4)
                
                padding_length = ((px * ly) - ((packet.getLength() - 6) // 4)) * 4
                packet.writeBytes(b"\x00" * padding_length)
                
                captcha = packet.toByteArray()
                captcha = zlib.compress(captcha)
                self.client.sendPacket(Identifiers.send.Account_Registration_Captcha, ByteArray().writeInt(len(captcha)).writeBytes(captcha).toByteArray())
                self.client.CAPTime = time.time()

        @self.packet(args=['readUTF', 'readUTF'])
        async def Create_New_Cafe_Topic(self, message, title):
            self.client.Cafe.createNewCafeTopic(message, title)

        @self.packet(args=['readInt', 'readUTF'])
        async def Create_New_Cafe_Post(self, topicID, message):
            self.client.Cafe.createNewCafePost(topicID, message)

        @self.packet(args=['readUTF'])
        async def Create_Survey(self, description):
            if not self.client.privLevel >= 9:
                return
                
            description = '[' + description + ']'
            options = []
            while self.packet.bytesAvailable():
                options.append(self.packet.readUTF())
                
            if len(options) == 0:
                return
                
            p = ByteArray().writeInt(self.client.playerCode).writeUTF("").writeBoolean(False).writeUTF(description)
            for option in options:
                p.writeUTF(option)
          
            for player in self.server.players.copy().values():
                player.sendPacket(Identifiers.send.New_Survey, p.toByteArray())

        @self.packet(args=['readByte', 'readByte', 'readUnsignedByte', 'readUnsignedByte', 'readUTF'])
        async def Game_Log(self, errorC, errorCC, oldC, oldCC, error):
            if errorC == 1 and errorCC == 1:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}]->[OLD] [{time.strftime('%H:%M:%S')}] GameLog Error - C: {C} CC: {CC} error: {error}\n")
                if self.client.isServerErrorLogging:
                    self.server.sendStaffMessage(f"[CLIENT][{self.client.ipAddress}]->[OLD] [{time.strftime('%H:%M:%S')}] GameLog Error - C: {C} CC: {CC} error: {error}", "Owner")
            elif errorC == 60 and errorCC == 1:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}]->[TRIBULLE] [{time.strftime('%H:%M:%S')}] GameLog Error - Code: {oldC} error: {error}\n")
                if self.client.isServerErrorLogging:
                    self.server.sendStaffMessage(f"[CLIENT][{self.client.ipAddress}]->[TRIBULLE] [{time.strftime('%H:%M:%S')}] GameLog Error - Code: {oldC} error: {error}", "Owner")
            else:
                testfunc = ''
                ccc = [errorC, errorCC]
                for i in dir(Identifiers.send):
                    if '__' in i: continue
                    exec(f"self.valuer = Identifiers.send.{i}")
                    if self.valuer == ccc:
                        testfunc = i
                if testfunc == '':
                    testfunc = 'Unknown'
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] [{time.strftime('%H:%M:%S')}] GameLog Error - Func: {testfunc} C: {errorC} CC: {errorCC} error: {error}\n")
                if self.client.isServerErrorLogging:
                    self.server.sendStaffMessage(f"[CLIENT][{self.client.ipAddress}] [{time.strftime('%H:%M:%S')}] GameLog Error - Func: {testfunc} C: {errorC} CC: {errorCC} error: {error}", "Owner")

        @self.packet(args=['readInt', 'readUTF'])
        async def Delete_All_Cafe_Message(self, topicID, playerName):
            self.client.Cafe.deleteAllCafePosts(topicID, playerName)

        @self.packet(args=['readInt'])
        async def Delete_Cafe_Post(self, postID):
            self.client.Cafe.deleteCafePost(postID)

        @self.packet(args=['readUTF', 'readUTF', 'readUTF', 'readByte'])
        async def Enter_Room(self, community, roomName, passwordEntered, auto):
            if auto:
                self.client.sendEnterRoom("")
                return
            
            canSkip = False
                
            if community == "":
                community = self.client.playerLangue
                
            if roomName == "":
                community = self.client.playerLangue
                roomName = "1"
                                
            if passwordEntered != "":
                canSkip = (passwordEntered == self.server.rooms[roomName][15])
                if not canSkip:
                    self.client.sendPacket(Identifiers.send.Room_Password, ByteArray().writeUTF(roomName).toByteArray())
                    canSkip = False
                    return
                else:                
                    canSkip = True
                    
            if self.client.playerName in self.server.modoReports:
               self.client.sendPacket(Identifiers.send.Modopwet_Room_Password_Protected, ByteArray().writeUTF(self.client.playerName).writeUTF(roomName).writeBoolean(passwordEntered == "").toByteArray())
            
            originalRoomName = roomName
            roomName = f"{community}-{roomName}"
                        
            isCustom = self.packet.bytesAvailable()
            if isCustom:
                roomPassword = self.packet.readUTF()
                withoutShamanSkills = self.packet.readBoolean()
                withoutPhysicalConsumables = self.packet.readBoolean()
                withoutAdventureMaps = self.packet.readBoolean()
                withMiceCollisions = self.packet.readBoolean()
                withFallDamage = self.packet.readBoolean()
                roundDurationPercentage = self.packet.readUnsignedByte()
                miceWeightPercentage = self.packet.readInt()
                maximum_players = self.packet.readShort()
                map_rotation = []
                while self.packet.bytesAvailable():
                    map_rotation.append(self.packet.readUnsignedByte())
                                
                if roomName != self.client.lastRoomName:
                    if not roomName in self.server.rooms:
                        self.server.roomPlayers[roomName] = [self.client.playerName]
                        self.server.rooms[roomName] = [
                            self.client.bulleID,
                            1,
                            maximum_players,
                            self.server.getRoomGameMode(roomName),
                            roomName,
                            False,
                            True,
                            withoutShamanSkills,
                            withoutPhysicalConsumables,
                            withoutAdventureMaps,
                            withMiceCollisions,
                            withFallDamage,
                            roundDurationPercentage,
                            miceWeightPercentage,
                            map_rotation,
                            roomPassword
                        ]
                    else:                            
                        self.server.roomPlayers[roomName] = [self.client.playerName]
                        self.server.rooms[roomName][1] += 1
                                                
                    if self.client.lastRoomName != "":
                        self.server.roomPlayers[self.client.lastRoomName].remove(self.client.playerName)
                        self.server.rooms[self.client.lastRoomName][1] -= 1
                        roomPlayers = self.server.rooms[self.client.lastRoomName][1]
                        if roomPlayers <= 0:
                            del self.server.rooms[self.client.lastRoomName]
                            del self.server.roomPlayers[self.client.lastRoomName]
            print(canSkip)
            self.client.sendEnterRoom(f"{originalRoomName}", community, False, not isCustom, canSkip)

        @self.packet(args=[])
        async def Enter_Tribe_House(self):
            if not self.client.tribeName == "":
                self.client.sendEnterRoom(f"*\x03{self.client.tribeName}")

        @self.packet(args=['readShort', 'readBoolean'])
        async def Equip_Consumable(self, _id, equip):
            if equip:
                if _id in self.client.equipedConsumables:
                    self.client.equipedConsumables.remove(_id)
                self.client.equipedConsumables.append(_id)
            else:
                self.client.equipedConsumables.remove(_id)

        @self.packet(args=['readUTF'], decrypt=True)
        async def Execute_Command(self, command):
            if time.time() - self.client.CMDTime > 0.8:
                await self.client.ParseCommands.parseCommand(command)
                self.client.CMDTime = time.time()

        @self.packet(args=[])
        async def Init_Ping_System(self):
            self.client.PInfo[0] += 1
            self.client.sendPacket(Identifiers.send.Ping, ByteArray().writeByte(self.client.PInfo[0]).writeBoolean(self.client.PInfo[0] % 2 != 0).toByteArray())
            self.client.PInfo[1] = time.time()
            if self.client.PInfo[0] > 250:
                self.client.PInfo[0] = 0

        @self.packet(args=[])
        async def Language_List(self):
            data = ByteArray().writeShort(len(self.server.gameLanguages)).writeUTF(self.client.playerLangue)
            
            for info in self.server.gameLanguages[self.client.playerLangue]:
                data.writeUTF(info)

            for lang in self.server.gameLanguages:
                if lang != self.client.playerLangue:
                    data.writeUTF(lang)
                    for info in self.server.gameLanguages[lang]:
                        data.writeUTF(info)
            self.client.sendPacket(Identifiers.send.Language_List, data.toByteArray())

        @self.packet(args=['readUTF', 'readByte'])
        async def Letter(self, playerName, type_letter):
            consumables = {0:29, 1:30, 2:2241, 3:2330, 4:2351, 5:2522, 6:2576, 7:2581, 8:2585, 9:2591, 10:2609, 11:2612}
            if type_letter in consumables:
                count = self.client.playerConsumables[consumables[type_letter]] - 1
                if count <= 0:
                    del self.client.playerConsumables[consumables[type_letter]]
                else:
                    self.client.playerConsumables[consumables[type_letter]] = count
                    
                self.client.sendBullePacket(Identifiers.bulle.BU_UseInventoryConsumable, self.client.playerID, self.client.playerCode, consumables[type_letter])
                self.client.sendUpdateInventoryConsumable(consumables[type_letter], count)

                player = self.server.players.get(playerName)
                if (player != None):
                    p = ByteArray()
                    p.writeUTF(self.client.playerName)
                    p.writeUTF(self.client.playerLook)
                    p.writeUnsignedByte(type_letter)
                    p.writeBytes(self.packet.readUTFBytes(self.packet.getLength()))
                    player.sendPacket(Identifiers.send.Letter, p.toByteArray())
                    self.client.sendLangueMessage("", "$MessageEnvoye")
                else:
                    self.client.sendLangueMessage("", "$Joueur_Existe_Pas")

        @self.packet(args=['readUTF', 'readUTF', 'readUTF','readUTF', 'readInt'], decrypt=True)
        async def Login_Account(self, playerName, password, flash_url, startRoom, authKey):
            if flash_url != self.server.serverInfo["swf_url"]:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] FLASH URL check failure. {flash_url}\n")
                self.client.transport.close()
                return
                
            for i in self.server.swfInfo["login_keys"]:
                authKey ^= i
            
            if authKey != self.server.swfInfo["authkey"]:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] Authorization key check failure. {authKey}/{self.server.swfInfo['authkey']}\n")
                self.client.transport.close()
                return
                
            if self.client.loginWrongAttemps > 12:
                self.server.sendStaffMessage(f"The IP Address <font color='{IPTools.ColorIP(self.client.ipAddress)}'>{IPTools.EncodeIP(self.client.ipAddress)}</font> tried log in too many times.\n", "PrivMod|Mod|Admin")
                self.client.loginWrongAttemps = 0
                self.client.transport.close()
                return
                
            if playerName == "" and password != "":
                # only password field
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(2).writeUTF(playerName).writeUTF("").toByteArray())
                self.client.loginWrongAttemps += 1
            elif self.server.checkConnectedPlayer(playerName) and password != "":
                # player is already connected on the server
                self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(1).writeUTF(playerName).writeUTF("").toByteArray())
                self.client.loginWrongAttemps += 1
            else:
                try:
                    await self.client.loginPlayer(playerName, password, startRoom)
                except Exception as e:
                    # server error
                    self.client.sendPacket(Identifiers.send.Login_Result, ByteArray().writeByte(6).writeUTF(playerName).writeUTF("").toByteArray())
                    self.client.Logger.logException(e, "Serveur.log", traceback.format_exc())

        @self.packet(args=[])
        async def Login_Time(self):
            if self.client.awakeTimer != None: 
                self.client.awakeTimer.cancel()
                
            self.client.awakeTimer = self.server.loop.call_later(90, self.client.transport.close) # 1 minute + 30 seconds

        @self.packet(args=['readUTF', 'readBoolean'])
        async def Modopwet_BanHack(self, playerName, silent):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.banHack(playerName, silent)

        @self.packet(args=['readUTF', 'readBoolean', 'readBoolean', 'readBoolean'])
        async def Modopwet_Change_Langue(self, langue, modopwetOnlyPlayerReports, sortBy, reOpen):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.sendUpdateModopwet(langue.upper(), modopwetOnlyPlayerReports, sortBy, reOpen)

        @self.packet(args=['readUTF'])
        async def Modopwet_Chat_Log(self, playerName):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.openChatLog(playerName)

        @self.packet(args=['readUTF', 'readByte'])
        async def Modopwet_Delete_Report(self, playerName, closeType):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.sendReportResult(playerName, closeType)

        @self.packet(args=['readBoolean', 'readByte'])
        async def Modopwet_Notifications(self, isEnabled, languages):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.isModoPwetNotifications = isEnabled
                if isEnabled:
                    x = 0
                    while x < languages:
                        lang = self.packet.readUTF()
                        self.client.modoCommunitiesNotification.append(lang)
                        x += 1
                else:
                    x = 0
                    while x < languages:
                        if not lang in self.client.modoCommunitiesNotification:
                            continue
                            
                        self.client.modoCommunitiesNotification.remove(lang)
                        x += 1

        @self.packet(args=['readUTF', 'readByte'])
        async def Modopwet_Watch(self, playerName, isFollowing):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.sendWatchPlayer(playerName, isFollowing)

        @self.packet(args=['readByte'])
        async def NPC_Functions(self, _typ):
            if _typ == 4:
                self.client.sendNpcShop(self.packet.readUTF())
            else:
                self.client.sendNPCBuyItem(self.packet.readByte())

        @self.packet(args=['readShort'])
        async def Old_Protocol(self, length):
            data = self.packet.readUTFBytes(length)
            if isinstance(data, (bytes, bytearray)):
                data = data.decode()
                
            values = data.split('\x01')
            C = ord(values[0][0])
            CC = ord(values[0][1])
            values = values[1:]
            if (C, CC) == Identifiers.old.recv.Load_Map:
                mapID = values[0]
                if not mapID.isdigit():
                    self.client.sendPacket(Identifiers.old.send.Load_Map_Result, [])
                else:
                    self.client.sendBullePacket(Identifiers.bulle.BU_LoadMapEditor_Map, mapID, self.client.playerID)
                        
            elif (C, CC) == Identifiers.old.recv.Leave_Map_Editor:
                self.client.sendPacket(Identifiers.old.send.Map_Editor, ["0"])
                self.client.sendEnterRoom("")
                                
            elif (C, CC) == Identifiers.old.recv.Drawing_Clear:
                if self.client.privLevel != 10:
                    return
                self.client.sendBullePacket(Identifiers.bulle.BU_DrawingClear, self.client.roomName)
                
            elif (C, CC) == Identifiers.old.recv.Drawing_Point:
                if self.client.privLevel != 10:
                    return
                info = base64.b64encode(','.join(values).encode()).decode()
                self.client.sendBullePacket(Identifiers.bulle.BU_DrawingPoint, self.client.roomName, info, self.client.playerID)
                
            elif (C, CC) == Identifiers.old.recv.Drawing_Init:
                if self.client.privLevel != 10:
                    return
                info = base64.b64encode(','.join(values).encode())
                self.client.sendBullePacket(Identifiers.bulle.BU_DrawingStart, self.client.roomName, info, self.client.playerID)
            else:
                self.client.Logger.warn(f"[SERVER][{self.client.ipAddress}][OLD] The packet {C}:{CC} is not registered in the bulle.\n")

        @self.packet(args=[])
        async def Open_A801_Outfits(self):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
                
            p = ByteArray()
            p.writeInt(len(self.server.shopOutfitsCheck))
            for info in self.server.shopOutfitsCheck:
                p.writeInt(int(info))
                p.writeUTF(self.server.shopOutfitsCheck[info][5]) # name
                p.writeByte(int(self.server.shopOutfitsCheck[info][1])) # BACKGROUND
                p.writeUTF(str(datetime.datetime.fromtimestamp(int(self.server.shopOutfitsCheck[info][3]), tz=None)).split(' ')[0]) # date
                p.writeUTF(self.server.shopOutfitsCheck[info][0]) # LOOK
                p.writeByte(2 if not int(self.server.shopOutfitsCheck[info][4]) else 3) # is perm
            self.client.sendPacket(Identifiers.send.Open_A801_Outfits_Window, p.toByteArray())

        @self.packet(args=[])
        async def Open_A801_Promotions(self):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
        
            packet = ByteArray()
            packet.writeInt128(len(self.server.shopPromotions))
            self.data = {}
            for promo in self.server.shopPromotions:
                packet.writeInt128(promo["Id"])
                if promo["Category"] == -1:
                    packet.writeUTF(f'0,{promo["Item"]}')
                else:
                    packet.writeUTF(f'{promo["Category"]},{promo["Item"]}')
                packet.writeUTF(str(datetime.datetime.fromtimestamp(int(self.server.serverTime),tz=None)))
                packet.writeUTF(str(datetime.datetime.fromtimestamp(int(promo["Time"]), tz=None)))
                packet.writeInt128(promo["Discount"])
                packet.writeInt128(1 if promo["Perm"] else 2)
            self.client.sendPacket(Identifiers.send.Open_A801_Promotions_Window, packet.toByteArray())

        @self.packet(args=['readBoolean'])
        async def Open_Cafe(self, isCafeOpen):
            self.client.isCafeOpened = isCafeOpen

        @self.packet(args=['readInt'])
        async def Open_Cafe_Topic(self, topicID):
            self.client.Cafe.openCafeTopic(topicID)

        @self.packet(args=[])
        async def Open_Cafe_Warnings(self):
            self.client.Cafe.sendCafeWarnings()

        @self.packet(args=['readUTF'])
        async def Open_Community_Partner(self, partname):
            for partner in self.server.communityPartners:
                if partner["Name"] == partname:
                    self.client.sendPacket(Identifiers.send.Open_Link, ByteArray().writeUTF(partner["Link"]).toByteArray())
                    break

        @self.packet(args=[])
        async def Open_Dressing(self):
            packet = ByteArray()
            packet.writeInt(len(self.server.shopList))
            for item in self.server.shopList:
                packet.writeShort(item["category"]).writeShort(item["id"]).writeByte(item["customs"]).writeBoolean(item["new"]).writeBoolean("purchasable" in item).writeInt(item["cheese"]).writeInt(item["fraise"]).writeBoolean(False)

            promotions = [promotion for promotion in self.server.shopPromotions if not promotion["isShamanItem"]]
            packet.writeInt(len(promotions))
            for promotion in promotions:
                packet.writeInt(int(str(promotion["Category"]) + '0' + str(promotion["Item"])))
                packet.writeInt(promotion["Time"] - self.server.serverTime)
            
            self.client.sendPacket(Identifiers.send.Open_Dressing, packet.toByteArray())

        @self.packet(args=[])
        async def Open_Inventory(self):
            self.client.sendPlayerInventory()

        @self.packet(args=[])
        async def Open_Missions(self):
            self.client.DailyQuests.sendMissions()

        @self.packet(args=['readBoolean'])
        async def Open_Modopwet(self, isOpen):
            if self.client.privLevel >= 8 or self.client.isPrivMod:
                self.client.ModoPwet.openModoPwet(isOpen)
                self.client.isModoPwetOpened = isOpen

        @self.packet(args=['readShort'], decrypt=True)
        async def Parse_Tribulle(self, code):
            self.client.Tribulle.parseTribulleCode(code, self.packet)
            
        @self.packet(args=['readShort'], decrypt=True)
        async def Parse_Tribulle_Old(self, code):
            self.client.Tribulle.parseTribulleCodeOld(code, self.packet)

        @self.packet(args=['readByte'])
        async def Player_Buy_Skill(self, skill):
            self.client.Skills.sendPurchaseSkill(skill)

        @self.packet(args=['readUTF'])
        async def Player_IPS_Info(self, info):
            self.client.sendPacket(Identifiers.send.Player_IPS_Info, ByteArray().writeUTF(info).toByteArray())

        @self.packet(args=['readShort'])
        async def Player_FPS_Info(self, info):
            self.client.sendPacket(Identifiers.send.Player_FPS_Info, ByteArray().writeUTF(info).toByteArray())

        @self.packet(args=['readByte'])
        async def Player_Ping(self, VC):
            prev = self.client.PInfo[2]
            if self.client.PInfo[1] == 0:
                self.client.PInfo[2] = 60             
            else:
                self.client.PInfo[2] = int((time.time() - self.client.PInfo[1]) * 1000) + VC
                if prev*2 < self.client.PInfo[2]:
                    self.client.PInfo[2] = prev + 1

        @self.packet(args=[])
        async def Player_Redistribute_Skills(self):
            self.client.Skills.sendRedistributeSkills()

        @self.packet(args=['readUTF', 'readByte', 'readUTF'])
        async def Player_Report(self, playerName, type, comments):
            self.client.ModoPwet.makeReport(playerName, type, comments, self.client.playerName)

        @self.packet(args=[])
        async def Player_Shop_List(self):
            self.client.Shop.sendShopList(True)

        @self.packet(args=['readInt', 'readUTF', 'readUTF', 'readInt', 'readUTF', 'readShort', 'readUTF', 'readShort', 'readInt', 'readInt', 'readInt', 'readByte', 'readShort', 'readShort', 'readUTF'], decrypt=True)
        async def PreLogin_Verification(self, code, check1, check2, code2, check3, check4, check5, check6, check7, check8, check9, check10, check11, check12, check13):
            if code != self.client.verifycoder or code != code2:
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Verification stage 1 failed. The code ({code} or {code2} is different than {self.client.verifycoder}. (verifier code)\n")
                self.server.sendStaffMessage(f"The IP Address <font color='{IPTools.ColorIP(self.client.ipAddress)}'>{IPTools.EncodeIP(self.client.ipAddress)}</font> is a BOT.\n", "PrivMod|Mod|Admin")
                self.client.transport.close()
                return
                
            additional_checks = []
            additional_checks.append(check1 != "-b7")
            additional_checks.append(check2 != "--")
            additional_checks.append(check3 != "-``--,,")
            additional_checks.append(check4 != 64835)
            additional_checks.append(check5 != "--///v/")
            additional_checks.append(check7 != 820)
            additional_checks.append(check10 != 23)
            additional_checks.append(check13 != "+++")
            
            for status in additional_checks:
                if status != False:
                    self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Verification stage 2 failed.\n")
                    self.server.sendStaffMessage(f"The IP Address <font color='{IPTools.ColorIP(self.client.ipAddress)}'>{IPTools.EncodeIP(self.client.ipAddress)}</font> is a BOT.\n", "PrivMod|Mod|Admin")
                    self.client.transport.close()
                    return

        @self.packet(args=['readBoolean'])
        async def Purchase_Fraises(self, isSteam):
            if not self.server.isDebug:
                self.client.sendPacket(Identifiers.send.Purchase_Error)
                return
        
            r1 = self.server.shopPurchaseInfo
            p = ByteArray().writeByte(len(r1))
            for i in r1:
                p.writeInt(i["ID"]).writeShort(i["Amount"]).writeInt(i["Price"]).writeUTF(i["Currency"])
            self.client.sendPacket(Identifiers.send.Main_Purchase_Menu, p.toByteArray())

        @self.packet(args=['readInt'])
        async def Purchase_Fraises_Begin(self, option): # UNFINISHED
            if self.client.isEmailAddressVerified:
                self.client.transactionToken = hashlib.md5(str(uuid.uuid4()).encode()).hexdigest()
                self.client.transactionOption = int(option)
                self.client.sendPacket(Identifiers.send.Purchase_Fraises_Paypal, ByteArray().writeUTF(f"https://www.paypal.com/webscr?cmd=_express-checkout&useraction=commit&token={self.client.transactionToken}").writeUTF(self.client.transactionToken).toByteArray())
                self.client.sendServerMessage("Tip: If you close the window you can restore it with this command <J>/openpurchaselink</J>", True)
            else:
                self.client.sendPacket(Identifiers.send.Purchase_Fraises_Transaction_Error, ByteArray().writeUTF("").toByteArray())
                self.client.sendServerMessage("<R>Your email address need to be validated before purchase fraises.</R>", True)

        @self.packet(args=['readUTF', 'readUTF']) # UNFINISHED
        async def Purchase_Fraises_Transaction_Confirm(self, transactionTokenA, transactionTokenB):
            if transactionTokenA != transactionTokenB:
                self.client.transport.close()
                
            if transactionTokenA != self.client.transactionToken:
                self.client.transport.close()
                
            self.client.sendPacket(Identifiers.send.Purchase_Fraises_Transaction_Done, ByteArray().writeShort(self.server.shopPurchaseInfo[self.client.transactionOption]["Amount"]).toByteArray())
            self.client.shopFraises += self.server.shopPurchaseInfo[self.client.transactionOption]["Amount"]
            self.client.sendAnimZelda(2, 2)
            self.client.transactionToken = ""
            self.client.transactionOption = -1

        @self.packet(args=[])
        async def Ranking(self):
            if self.client.isGuest:
                return
                
            data = ByteArray()
            data.writeInt128(self.server.currentRankingSeason) # Current season
            data.writeInt128(Time.getDaysDiff(self.server.currentRankingSeasonTime)) # Remaining days
            total_players = self.server.cursor['users'].count_documents({})
            if total_players > 10:
                total_players = 10
                
            for info in ['CheeseCount', 'FirstCount', 'ShamanCheeses', 'UKNOWN_1', 'BootcampCount', 'UKNOWN_2', 'UKNOWN_3']:
                data.writeInt128(total_players) # Total players in the ranking
                if info.startswith('UKNOWN'):
                    res = self.server.cursor['users'].find().limit(10)
                    x = 1
                    rank = []
                    for user in res:
                        ppstats = list(map(int, filter(None, user['PlayerStats'].split(","))))
                        rank.append([user['PlayerID'], user['Username'], ppstats[3] if info[-1:] == '1' else ppstats[7] if info[-1:] == '2' else ppstats[9]])
                    rank = sorted(rank, key=lambda x: x[2],  reverse=True)
                    
                    for user in rank:
                        data.writeInt128(user[0]).writeUTF(user[1]).writeInt128(user[2]).writeInt128(x)
                        x += 1
                else:
                    info_cheese = self.server.cursor['users'].find().sort(info, -1).limit(10)
                    x = 1
                    for user in info_cheese:
                        data.writeInt128(user['PlayerID']).writeUTF(user['Username']).writeInt128(user[info]).writeInt128(x)
                        x += 1
                        
            for i in range(0, 8):
                data.writeInt128(1).writeInt128(1)
            self.client.sendPacket(Identifiers.send.Ranking, data.toByteArray())

        @self.packet(args=[])
        async def Reload_Cafe(self):
            if not self.client.isReloadCafe:
                self.client.Cafe.loadCafeMode()
                self.client.isReloadCafe = True
                self.server.loop.call_later(2, setattr, self.client, "isReloadCafe", False)

        @self.packet(args=['readInt'])
        async def Remove_Outfit(self, _id):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
                
            for i in self.server.shopInfo["fullLooks"]:
                if int(i["id"]) == _id:
                    if i["perm"] == True:
                        # Somebody is trying to do the impossible.
                        self.server.sendStaffMessage(f"The player {self.client.playerName} tried to remove a permanent outfit.", "Admin")
                    else:
                        self.server.shopInfo["fullLooks"].remove(i)
                    break
            self.server.shopOutfitsCheck = {}
            for item in self.server.shopOutfits:
                self.server.shopOutfitsCheck[str(item["id"])] = [item["look"], item["bg"], item["discount"], item["start"], item["perm"], item["name"], item["addedBy"]]
            await self.parsePacket(self.client.lastPacketID + 1, 149, 12, ByteArray())

        @self.packet(args=['readInt'])
        async def Remove_Sale(self, _id):
            if self.client.privLevel not in [4, 10] and not self.client.isFashionSquad:
                return
                
            for i in self.server.shopPromotions:
                if int(i["Id"]) == _id:
                    if i["Perm"] == True:
                        # Somebody is trying to do the impossible.
                        self.server.sendStaffMessage(f"The player {self.client.playerName} tried to remove a permanent promotion/sale.", "Admin")
                    else:
                        self.server.shopPromotions.remove(i)
                    break
            self.server.LoadShopPromotions(False)
            await self.parsePacket(self.client.lastPacketID + 1, 149, 16, ByteArray())

        @self.packet(args=['readInt', 'readInt'])
        async def Report_Cafe_Post(self, topicID, postID):
            self.client.Cafe.reportCafePost(topicID, postID)

        @self.packet(args=[])
        async def Request_Info(self):
            # user agent
            self.client.sendPacket(Identifiers.send.Request_Info, ByteArray().writeUTF("http://localhost/tfm/info.php").toByteArray())

        @self.packet(args=['readByte'])
        async def Rooms_List(self, mode):
            if mode == 0 or mode == None:
                mode = 1
            self.client.sendRoomList(mode)

        @self.packet(args=['readByte', 'readUTF'])
        async def Send_Staff_Chat_Message(self, _id, message):
            if self.client.privLevel < 4:
                return
                
            await self.client.sendStaffChannelMessage(_id, message)

        @self.packet(args=['readUTF'])
        async def Set_Language(self, lang):
            self.client.playerLangue = lang.lower()
            if "-" in self.client.playerLangue:
                self.client.playerLangue = self.client.playerLangue.split("-")[1]
            
            self.client.sendPacket(Identifiers.send.Set_Language, ByteArray().writeUTF(self.client.playerLangue.upper()).writeUTF(self.server.gameLanguages[self.client.playerLangue][1]).writeShort(0).writeBoolean(False).writeBoolean(True).writeUTF('').toByteArray())

        @self.packet(args=['readInt'])
        async def Shop_Equip_Shaman_Item(self, _id):
            self.client.Shop.equipShamanItem(_id)

        @self.packet(args=[])
        async def Shop_Info(self):
            self.client.Shop.sendShopInfo()

        @self.packet(args=['readInt', 'readByte'])
        async def Shop_Custom_Item(self, fullItem, length):
            customs = []
            i = 0
            while i < length:
                customs.append(self.packet.readInt())
                i += 1
            self.client.Shop.customItem(fullItem, customs)

        @self.packet(args=['readShort', 'readByte'])
        async def Shop_Custom_Shaman_Item(self, fullItem, length):
            customs = []
            i = 0
            while i < length:
                customs.append(self.packet.readInt())
                i += 1
            self.client.Shop.customShamanItem(fullItem, customs)

        @self.packet(args=['readByte'])
        async def Shop_Equip_Clothe(self, _id):
            self.client.Shop.equipClothe(_id)

        @self.packet(args=['readInt'])
        async def Shop_Equip_Item(self, _id):
            self.client.Shop.equipItem(_id)

        @self.packet(args=['readInt', 'readBoolean', 'readUTF', 'readBoolean'])
        async def Shop_Gift_Result(self, giftID, isGiftOpen, message, isMessageClosed):
            self.client.Shop.giftResult(giftID, isGiftOpen, message, isMessageClosed)

        @self.packet(args=['readUTF', 'readBoolean', 'readInt', 'readUTF'])
        async def Shop_Send_Gift(self, playerName, isShamanItem, fullItem, message):
            self.client.Shop.sendShopGift(playerName, isShamanItem, fullItem, message)

        @self.packet(args=['readInt', 'readBoolean'])
        async def Shop_Set_Favorite_Item(self, fullItem, status):
            if status:
                self.client.shopFavoriteItems += str(fullItem) if self.client.shopFavoriteItems == "" else "," + str(fullItem)
            else:
                tmp_string_list = self.client.shopFavoriteItems.split(',')
                fullItem = str(fullItem)
                if fullItem in tmp_string_list:
                    tmp_string_list.remove(fullItem)
                self.client.shopFavoriteItems = ','.join(tmp_string_list)

        @self.packet(args=['readByte', 'readBoolean'])
        async def Shop_Purchase_Emote(self, emote_id, withFraises):
            amount = -1
            for emote in self.server.shopEmoteList:
                if emote["id"] == emote_id:
                    amount = int(emote["fraise"]) if withFraises else int(emote["cheese"])
                    break
            if amount != -1:
                if withFraises:
                    self.client.shopFraises -= amount
                else:
                    self.client.shopCheeses -= amount
                self.client.shopEmotes += str(emote_id) if self.client.shopEmotes == "" else "," + str(emote_id)
                self.client.Shop.sendShopList(True)
                self.client.buyItemResult(emote_id)
                self.client.sendPlayerEmotes()
            else:
                self.server.sendStaffMessage(f"The player {self.client.playerName} tried purchase an invalid emote in the shop.", "Admin")

        @self.packet(args=['readShort'])
        async def Shop_View_Full_Look(self, look_id):
            self.client.Shop.viewFullLook(look_id)

        @self.packet(args=['readByte'])
        async def Shop_Save_Clothe(self, _id):
            self.client.Shop.saveClothe(_id)

        @self.packet(args=['readUTF'])
        async def Slash_Command(self, command):
            if command == '' and (self.client.privLevel >= 8 or self.client.isPrivMod):
                msg = "Commands dealing with naughy mice:<br><br>"
                for command in self.server.gameCommands:
                    if self.client.checkStaffPermission(command["Privileges"]):
                        msg += f"<J>/{command['Name']}</J> " if command["Channel"] == "ALL" else f"<J>.{command['Name']}</J> "
                        if "Arguments" in command:
                            for argument in command["Arguments"]:
                                msg += f"<V>["
                                msg += argument
                                msg += "]</V> "
                        msg += f"<BL>: {command['Description']}"
                        if 'Aliases' in command:
                            msg += f" (Aliases: /{', /'.join(command['Aliases'])})</BL><br>"
                        else:
                            msg += "</BL><br>"
                self.client.sendLogMessage(msg)

        @self.packet(args=['readByte'])
        async def Sonar_Information(self, code):
            if not self.client.playerName in self.server.playerMovement:
                return
        
            if code == 1:
                key = self.packet.readByte()
                time = self.packet.readInt()
                chars = {38:"↑", 37:"←", 39:"→", 40:"↓", 87:"↑", 68:"→", 65:"←", 83:"↓"}
                self.server.playerMovement[self.client.playerName].append(f"<BL>{chars[key]}</BL><G> + <V>{time}</V> ms</G>")
            elif code == 2:
                isJumping = self.packet.readBoolean()
                msg = f"jump: {bool(isJumping)}\n"
                
                if isJumping == 0:
                    length = self.packet.readByte()
                    x = 0
                    while x < length:
                        info1 = self.packet.readInt()
                        info2 = self.packet.readBoolean()
                        msg += f"   <BL>{info1}</BL><G> + <V>{info2}</V></G>\n"
                        x += 1
                else:
                    info1 = self.packet.readInt()
                    msg += f"   <V>{info1}</V>"
                self.server.playerMovement[self.client.playerName].append(msg)
            elif code == 3:
                info1 = self.packet.readByte()
                info2 = self.packet.readInt()
                self.server.playerMovement[self.client.playerName].append(f"<BL>{info1}</BL><G> + <V>{info2}</V> ms</G>")

        @self.packet(args=['readInt', 'readByte'])
        async def Survey_Answer(self, playerCode, optionID):
            for player in self.server.players.copy().values():
                if playerCode == player.playerCode:
                    player.sendPacket(Identifiers.send.Survey_Answer, ByteArray().writeByte(optionID).toByteArray())

        @self.packet(args=['readUTF'])
        async def Survey_Result(self, description):
            if not self.client.privLevel >= 9:
                return
                
            description = '[' + description + ']'
            options = []
            while self.packet.bytesAvailable():
                options.append(self.packet.readUTF())
            p = ByteArray()
            p.writeInt(0).writeUTF("").writeBoolean(False).writeUTF(description)
            for result in options:
                p.writeUTF(result)
            
            for player in self.server.players.copy().values():
                if player.playerName != self.client.playerName:
                    player.sendPacket(Identifiers.send.New_Survey, p.toByteArray())

        @self.packet(args=['readShort', 'readBoolean'])
        async def Trade_Add_Consusmable(self, id, isAdd):
            self.client.tradeAddConsumable(id, isAdd)

        @self.packet(args=['readUTF'])
        async def Trade_Invite(self, playerName):
            self.client.tradeInvite(playerName)

        @self.packet(args=['readBoolean'])
        async def Trade_Result(self, isAccept):
            self.client.tradeResult(isAccept)

        @self.packet(args=['readUTF'])
        async def Tribe_Invite(self, playerName):
            player = self.server.players.get(playerName)
            if player != None and player.tribeName in self.client.invitedTribeHouses:
                if self.client.roomName != "*%s%s" %(chr(3), player.tribeName):
                    self.client.sendEnterRoom(f"*\x03{player.tribeName}")

        @self.packet(args=['readShort'])
        async def Use_Consumable(self, _id):
            self.client.useConsumable(_id)

        @self.packet(args=['readUTF'])
        async def Validate_Email_Address_Code(self, code):
            if self.client.isEmailAddressVerified:
                return
        
            if self.client.lastEmailCode == code:
                self.client.shopCheeses += 40
                self.client.giveShopItem(209, False)
        
                self.client.sendPacket(Identifiers.send.Email_Address_Verified, '\x01')
                self.client.sendPacket(Identifiers.send.Email_Address_Code_Validated, '\x01')
                self.client.isEmailAddressVerified = True
            else:
                self.client.sendPacket(Identifiers.send.Send_Email_Code, ByteArray().writeByte(0).writeUTF("").toByteArray())
                self.client.sendServerMessage("The code you provided is an invalid. Please try again the process", True)

        @self.packet(args=['readInt', 'readBoolean'])
        async def Verify_Cafe_Post(self, topicID, status):
            self.client.Cafe.verifyCafePost(topicID, status)

        @self.packet(args=['readUTF'])
        async def Verify_Email_Address(self, emailAddress):
            if self.client.isEmailAddressVerified:
                return
                
            self.client.lastEmailCode = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(6))
            self.server.sendEmailMessage(self.client.lastEmailCode, emailAddress, self.client.playerName)
            self.client.sendPacket(Identifiers.send.Send_Email_Code, ByteArray().writeByte(1).writeUTF(emailAddress).toByteArray())

        @self.packet(args=['readUTF'])
        async def View_Cafe_Posts(self, playerName):
            self.client.Cafe.viewCafePosts(playerName)

        @self.packet(args=['readInt', 'readInt', 'readBoolean'])
        async def Vote_Cafe_Post(self, topicID, postID, mode):
            self.client.Cafe.voteCafePost(topicID, postID, mode)
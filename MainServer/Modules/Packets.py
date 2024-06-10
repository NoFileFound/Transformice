import base64
import datetime
import math
import random
import re
import time
from Modules.Identifiers import Identifiers
from Modules.ByteArray import ByteArray
from Utils.IPTools import IPTools

class Packets:
    def __init__(self, player, server):
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
                
        @self.packet(args=['readByte', 'readBoolean'])
        async def Buy_Shop_Clothe(self, _id, withFraises):
            self.client.Shop.buyClothe(_id, withFraises)
    
        @self.packet(args=['readInt', 'readBoolean'])
        async def Buy_Shop_Item(self, _id, withFraises):
            self.client.Shop.buyItem(_id, withFraises)
    
        @self.packet(args=['readShort', 'readBoolean'])
        async def Buy_Shop_Shaman_Custom(self, _id, withFraises):
            self.client.Shop.buyShamanCustomItem(_id, withFraises)
    
        @self.packet(args=['readShort', 'readBoolean'])
        async def Buy_Shop_Shaman_Item(self, _id, withFraises):
            self.client.Shop.buyShamanItem(_id, withFraises)
    
        @self.packet(args=['readShort', 'readUTF', 'readUTF', 'readUTF'])
        async def Correct_Version(self, version, language, con_key, stand):
            if not self.client.isVerifiedClientVersion:
                if (version != self.server.swfInfo["version"]) or (con_key != self.server.swfInfo["connection_key"]):
                    self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}] Version/CKEY check failure. ({version}/{con_key})\n")
                    self.client.transport.close()
                    return
            self.client.sendCorrectVersion(language, stand)
            
        @self.packet(args=['readUTF', 'readUTF', 'readUTF'])
        async def Computer_Info(self, osLang, osinfo, flashver):
            if not osinfo.startswith("Windows") and not osinfo.startswith("Linux"):
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Tried log in with unknown OS type ({osinfo}).\n")
                self.client.transport.close()
                return
            
            if flashver != "WIN 32,0,0,445":
                self.client.Logger.warn(f"[CLIENT][{self.client.ipAddress}] Connect to the server with older flash player version ({flashver}).\n")
        
            self.client.computerLanguage = osLang
            self.client.computerInformation = osinfo
            self.client.flashVersion = flashver
            
        @self.packet(args=[])
        async def Create_Account_Captcha(self):
            if time.time() - self.client.CAPTime > 2:
                self.client.currentCaptcha = random.choice(list(self.server.captchaList))
                self.client.sendPacket(Identifiers.send.Account_Registration_Captcha, ByteArray().writeBytes(base64.b64decode(self.server.captchaList[self.client.currentCaptcha][0])).toByteArray())
                self.client.CAPTime = time.time()
                
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
            elif self.server.getTotalAccountsByEmailAddress(email) > 7:
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
                        
        @self.packet(args=['readUTF', 'readUTF'])
        async def Create_New_Cafe_Topic(self, message, title):
            self.client.Cafe.createNewCafeTopic(message, title)
            
        @self.packet(args=['readInt', 'readUTF'])
        async def Create_New_Cafe_Post(self, topicID, message):
            self.client.Cafe.createNewCafePost(topicID, message)
                        
        @self.packet(args=['readByte', 'readByte', 'readUnsignedByte', 'readUnsignedByte', 'readUTF'])
        async def Game_Log(self, errorC, errorCC, oldC, oldCC, error):
            if errorC == 1 and errorCC == 1:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}]->[OLD] [{time.strftime('%H:%M:%S')}] GameLog Error - C: {C} CC: {CC} error: {error}\n")
            elif errorC == 60 and errorCC == 1:
                self.client.Logger.error(f"[CLIENT][{self.client.ipAddress}]->[TRIBULLE] [{time.strftime('%H:%M:%S')}] GameLog Error - Code: {oldC} error: {error}\n")
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
                
        @self.packet(args=['readInt', 'readUTF'])
        async def Delete_All_Cafe_Message(self, topicID, playerName):
            self.client.Cafe.deleteAllCafePosts(topicID, playerName)
                
        @self.packet(args=['readInt'])
        async def Delete_Cafe_Post(self, postID):
            self.client.Cafe.deleteCafePost(postID)
                
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
                    self.client.Logger.log(e, "Serveur.log")
                
        @self.packet(args=[])
        async def Login_Time(self):
            if self.client.awakeTimer != None: 
                self.client.awakeTimer.cancel()
                
            self.client.awakeTimer = self.server.loop.call_later(90, self.client.transport.close) # 1 minute + 30 seconds
            
        @self.packet(args=['readUTF'])
        async def Set_Language(self, lang):
            self.client.gameLanguage = lang
            if "-" in self.client.gameLanguage:
                self.client.gameLanguage = self.client.gameLanguage.split("-")[1]
            
            self.client.sendPacket(Identifiers.send.Set_Language, ByteArray().writeUTF(self.client.gameLanguage).writeUTF(self.server.gameLanguages[self.client.gameLanguage][1]).writeShort(0).writeBoolean(False).writeBoolean(True).writeUTF('').toByteArray())

        @self.packet(args=[])
        async def Language_List(self):
            data = ByteArray().writeShort(len(self.server.gameLanguages)).writeUTF(self.client.gameLanguage)
            
            for info in self.server.gameLanguages[self.client.gameLanguage]:
                data.writeUTF(info)

            for lang in self.server.gameLanguages:
                if lang != self.client.gameLanguage:
                    data.writeUTF(lang)
                    for info in self.server.gameLanguages[lang]:
                        data.writeUTF(info)
            self.client.sendPacket(Identifiers.send.Language_List, data.toByteArray())
            
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

        @self.packet(args=['readUTF'])
        async def Player_IPS_Info(self, info):
            self.client.sendPacket(Identifiers.send.Player_IPS_Info, ByteArray().writeUTF(info).toByteArray())
            
        @self.packet(args=['readShort'])
        async def Player_FPS_Info(self, info):
            self.client.sendPacket(Identifiers.send.Player_FPS_Info, ByteArray().writeUTF(info).toByteArray())
            
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
             
        @self.packet(args=['readByte'])
        async def Shop_Save_Clothe(self, _id):
            self.client.Shop.saveClothe(_id)
             
        @self.packet(args=['readInt', 'readBoolean'])
        async def Verify_Cafe_Post(self, topicID, status):
            self.client.Cafe.verifyCafePost(topicID, status)
             
        @self.packet(args=['readUTF'])
        async def View_Cafe_Posts(self, playerName):
            self.client.Cafe.viewCafePosts(playerName)
             
        @self.packet(args=['readInt', 'readInt', 'readBoolean'])
        async def Vote_Cafe_Post(self, topicID, postID, mode):
            self.client.Cafe.voteCafePost(topicID, postID, mode)

        
            
        @self.packet(args=['readUTF'])
        async def Send_Code(self, lang):
            #print(lang, packet_id)
            #if packet_id == 2 and self.client.isEmailAddressVerified:
            self.client.sendPacket(Identifiers.send.Email_Address_Code_Validated, ByteArray().writeBoolean(True).toByteArray())
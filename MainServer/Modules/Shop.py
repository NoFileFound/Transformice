#coding: utf-8
import base64
import binascii
import time

# Modules
from Modules.Identifiers import Identifiers
from Modules.ByteArray import ByteArray

class Shop:
    def __init__(self, player):
        self.client = player
        self.server = player.server

    def buyClothe(self, clotheID, withFraises):
        if withFraises:
            self.client.shopFraises -= 5 if clotheID == 0 else 50 if clotheID == 1 else 100
        else:
            self.client.shopCheeses -= 40 if clotheID == 0 else 1000 if clotheID == 1 else 2000 if clotheID == 2 else 4000

        info = "%02d/1;0,0,0,0,0,0,0,0,0/78583a/%s" %(clotheID, "fade55" if self.client.shamanNormalSaves >= 1000 else "95d9d6")
        self.client.shopClothes += info if self.client.shopClothes == "" else "|" + info

        self.sendShopList(False)

    def buyItem(self, fullItem, withFraises, original_fullitem): # UNFINISHED
        item_info = self.getShopItemInfo(fullItem)
        price = self.getShopItemPrice(item_info[0], item_info[1], withFraises)
        
        if withFraises:
            if self.client.shopFraises >= price:
                self.client.shopFraises -= price
                canBuy = True
        else:
            if self.client.shopCheeses >= price:
                self.client.shopCheeses -= price
                canBuy = True
        
        if canBuy:
            self.client.shopItems += str(original_fullitem) if self.client.shopItems == "" else "," + str(original_fullitem)
            self.sendItemBuy(original_fullitem)
            self.sendShopList(False)
            self.client.buyItemResult(original_fullitem)
        else:
            self.server.sendStaffMessage(f"The player {self.client.playerName} tried buy an item using a hack.", "PrivMod|Mod|Admin")

    def buyShamanCustomItem(self, fullItem, withFraises):
        items = self.client.shopShamanItems.split(",")
        canBuy = False
        canBuy2 = False
        for shopItem in items:
            item = shopItem.split("_")[0] if "_" in shopItem else shopItem
            if fullItem == int(item):
                items[items.index(shopItem)] = shopItem + "_"
                canBuy = True
                break

        if canBuy:
            if withFraises:
                if self.client.shopFraises >= 150:
                    self.client.shopFraises -= 150
                    canBuy2 = True
            else:
                if self.client.shopCheeses >= 4000:
                    self.client.shopCheeses -= 4000
                    canBuy2 = True
                    
            if canBuy2:
                self.client.shopShamanItems = ",".join(items)
            else:
                self.server.sendStaffMessage(f"The player {self.client.playerName} tried buy a custom shaman item using a hack.", "PrivMod|Mod|Admin")
        else:
            self.server.sendStaffMessage(f"The player {self.client.playerName} tried to buy a custom on not owned shaman item", "PrivMod|Mod|Admin")
        self.sendShopList(False)

    def buyShamanItem(self, fullItem, withFraises):
        price = self.getShopShamanItemPrice(fullItem, withFraises)
        self.client.shopShamanItems += str(fullItem) if self.client.shopShamanItems == "" else "," + str(fullItem)

        if withFraises:
            if self.client.shopFraises >= price:
                self.client.shopFraises -= price
                canBuy = True
        else:
            if self.client.shopCheeses >= price:
                self.client.shopCheeses -= price
                canBuy = True
        
        if canBuy:
            #self.sendItemBuy(fullItem)
            self.sendShopList(False)
            self.client.buyItemResult(fullItem, True)
        else:
            self.server.sendStaffMessage(f"The player {self.client.playerName} tried buy a shaman item using a hack.", "PrivMod|Mod|Admin")

    def checkShopGifts(self):
        if self.client.isGuest:
            return
    
        info = self.server.cursor['shopgifts'].find_one({'Username':self.client.playerName})
        if info:
            if not info["Gifts"] == "":
                for gift in info["Gifts"].split("/"):
                    values = binascii.unhexlify(gift.encode()).decode().split("|", 4)
                    self.server.lastShopGiftID += 1
                    self.client.sendPacket(Identifiers.send.Shop_Gift, ByteArray().writeInt(self.server.lastShopGiftID).writeUTF(self.client.playerName).writeUTF(values[1]).writeBoolean(bool(values[1])).writeInt(int(values[3])).writeUTF(values[4] if len(values) > 4 or values[4] != '' else "").writeBoolean(False).toByteArray())
                    self.server.shopGifts[self.server.lastShopGiftID] = [values[0], bool(values[2]), int(values[3])]
                self.server.cursor['shopgifts'].update_one({'Username':self.client.playerName}, {'$set':{'Gifts':''}})
            if not info["Messages"] == "":
                for message in info["Messages"].split("/"):
                    if not message == "":
                        values = binascii.unhexlify(message.encode()).decode().split("|", 4)
                        self.client.sendPacket(Identifiers.send.Shop_Gift, ByteArray().writeInt(0).writeUTF(values[0]).writeUTF(values[1]).writeBoolean(bool(values[2])).writeInt(int(values[3])).writeUTF(values[4]).writeBoolean(True).toByteArray())
                self.server.cursor['shopgifts'].update_one({'Username':self.client.playerName}, {'$set':{'Messages':''}})

    def customItem(self, fullItem, customs): # UNFINISHED
        _item = ""
        items = self.client.shopItems.split(",")
        for shopItem in items:
            sItem = shopItem.split("_")[0] if "_" in shopItem else shopItem
            if fullItem == int(sItem):
                _item = fullItem
                break
        if _item != "":
            newCustoms = map(lambda color: "%06X" %(0xffffff & color), customs)

            items[items.index(shopItem)] = sItem + "_" + "+".join(newCustoms)
            self.client.shopItems = ",".join(items)
            info = self.getShopItemInfo(_item)

            itemCat = info[0]
            item = info[1]
            equip = str(item) + self.getItemCustomization(_item, False)
            lookList = self.client.playerLook.split(";")
            lookItems = lookList[1].split(",")

            if "_" in lookItems[itemCat]:
                if lookItems[itemCat].split("_")[0] == str(item):
                    lookItems[itemCat] = equip
                            
            elif lookItems[itemCat] == str(item):
                lookItems[itemCat] = equip
            self.client.playerLook = lookList[0] + ";" + ",".join(lookItems)
            self.sendShopList(False)
            self.sendLookChange()

    def customShamanItem(self, fullItem, customs):
        items = self.client.shopShamanItems.split(",")
        _item = ""
        for shopItem in items:
            sItem = shopItem.split("_")[0] if "_" in shopItem else shopItem
            if fullItem == int(sItem):
                _item = fullItem
                break
        if _item != "":
            newCustoms = map(lambda color: "%06X" %(0xFFFFFF & color), customs)

            items[items.index(shopItem)] = sItem + "_" + "+".join(newCustoms)
            self.client.shopShamanItems = ",".join(items)

            item = str(_item) + self.getItemCustomization(_item, True)
            itemStr = str(_item)
            itemCat = int(itemStr[len(itemStr)-2:])
            index = itemCat if itemCat <= 4 else itemCat - 1 if itemCat <= 7 else 7 if itemCat == 10 else 8 if itemCat == 17 else 9
            index -= 1
            lookItems = self.client.shamanLook.split(",")

            if "_" in lookItems[index]:
                if lookItems[index].split("_")[0] == itemStr:
                    lookItems[index] = item
                            
            elif lookItems[index] == itemStr:
                lookItems[index] = item

            self.client.shamanLook = ",".join(lookItems)
            self.sendShopList(False)
            self.sendShamanLook()

    def equipClothe(self, clotheID):
        clothes = self.client.shopClothes.split("|")
        for clothe in clothes:
            values = clothe.split("/")
            if values[0] == "%02d" %(clotheID):
                self.client.playerLook = values[1]
                self.client.mouseColor = values[2]
                self.client.shamanColor = values[3]
                break
        self.sendLookChange()
        self.sendShopList(False)

    def equipItem(self, fullItem): # UNFINISHED
        info = self.getShopItemInfo(fullItem)
        itemCat = info[0]
        item = info[1]
        lookList = self.client.playerLook.split(";")
        lookItems = lookList[1].split(",")
        lookCheckList = lookItems[:]
        i = 0
        while i < len(lookCheckList):
            lookCheckList[i] = lookCheckList[i].split("_")[0] if "_" in lookCheckList[i] else lookCheckList[i]
            i += 1

        if itemCat <= 10:
            lookItems[itemCat] = "0" if lookCheckList[itemCat] == str(item) else str(item) + self.getItemCustomization(fullItem, False)
        elif itemCat == 21:
            lookList[0] = "1"
            color = "bd9067" if item == 0 else "593618" if item == 1 else "8c887f" if item == 2 else "dfd8ce" if item == 3 else "4e443a" if item == 4 else "e3c07e" if item == 5 else "272220" if item == 6 else "78583a"
            self.client.mouseColor = "78583a" if self.client.mouseColor == color else color
        else:
            lookList[0] = "1" if lookList[0] == str(item) else str(item)
            self.client.mouseColor = "78583a"

        self.client.playerLook = lookList[0] + ";" + ",".join(map(str, lookItems))
        self.sendLookChange()

    def equipShamanItem(self, fullItem):
        item = str(fullItem) + self.getItemCustomization(fullItem, True)
        itemStr = str(fullItem)
        itemCat = int(itemStr[:len(itemStr)-2])
        index = itemCat if itemCat <= 4 else itemCat - 1 if itemCat <= 7 else 7 if itemCat == 10 else 8 if itemCat == 17 else 9
        index -= 1
        lookItems = self.client.shamanLook.split(",")

        if "_" in lookItems[index]:
            if lookItems[index].split("_")[0] == itemStr:
                lookItems[index] = "0"
            else:
                lookItems[index] = item

        elif lookItems[index] == itemStr:
            lookItems[index] = "0"
        else:
            lookItems[index] = item

        self.client.shamanLook = ",".join(lookItems)
        self.sendShamanLook()

    def giftResult(self, giftID, isGiftOpen, message, isMessageClosed):
        giftID = int(giftID)
        if isGiftOpen:
        # Accept the gift
            values = self.server.shopGifts[giftID]
            player = self.server.players.get(values[0])
            isShamanItem = bool(values[1])
            fullItem = int(values[2])
            
            if player != None:
                player.sendLangueMessage("", "$DonItemRecu", self.client.playerName)
                del self.client.shopGifts[fullItem]
                

            if isShamanItem:
                self.client.shopShamanItems += str(fullItem) if self.client.shopShamanItems == "" else ",%s" %(fullItem)
                self.client.buyItemResult(fullItem, True)
            else:
                self.client.shopItems += str(fullItem) if self.client.shopItems == "" else ",%s" %(fullItem)
                self.client.buyItemResult(fullItem)
            self.sendShopList(False)


        elif message != "":
        # Resent the message to gifter
            values = self.server.shopGifts[giftID]
            player = self.server.players.get(values[0])
            if player != None:
                player.sendPacket(Identifiers.send.Shop_Gift, ByteArray().writeInt(giftID).writeUTF(self.client.playerName).writeUTF(self.client.playerLook).writeBoolean(bool(values[1])).writeInt(int(values[2])).writeUTF(message).writeBoolean(True).toByteArray())
            else:
                messages = ""
                rs = self.server.cursor['shopgifts'].find_one({'Username':values[0]})
                if rs:
                    messages = rs['Messages']
                    messages += ("" if messages == "" else "/") + binascii.hexlify("|".join(map(str, [self.client.playerName, self.client.playerLook, values[1], values[2], message])).encode()).decode()
                    self.server.cursor['shopgifts'].update_one({'Username':values[0]}, {'$set':{'Messages':messages}})
                
        elif isMessageClosed:
            del self.server.shopGifts[giftID]
            return

    def saveClothe(self, clotheID):
        clothes = self.client.shopClothes.split("|")
        for clothe in clothes:
            values = clothe.split("/")
            if values[0] == "%02d" %(clotheID):
                values[1] = self.client.playerLook
                values[2] = self.client.mouseColor
                values[3] = self.client.shamanColor
                clothes[clothes.index(clothe)] = "/".join(values)
                break

        self.client.shopClothes = "|".join(clothes)
        self.sendShopList(False)

    def checkInShop(self, checkItem) -> bool:
        if not self.client.shopItems == "":
            for shopItem in self.client.shopItems.split(","):
                if checkItem == int(shopItem.split("_")[0] if "_" in shopItem else shopItem):
                    return True
        else:
            return False

    def checkInPlayerGifts(self, playerName, fullItem) -> bool:
        cursor = self.server.cursor['shopgifts'].find_one({'Username':playerName})
        if cursor != None:
            gifts = cursor["Gifts"].split("/")
            for gift in gifts:
                values = binascii.unhexlify(gift.encode()).decode().split("|", 4)
                if int(values[3]) == fullItem:
                    return True
            return False
                
        else:
            return False

    def checkInPlayerShop(self, type, playerName, checkItem) -> bool:
        for rs in self.server.cursor['users'].find({'Username':playerName}):
            items = list(rs[type].split(','))
            if not len(items) == 1:
                for shopItem in items:
                    if shopItem != type and checkItem == int(shopItem.split("_")[0] if "_" in shopItem else shopItem):
                        return True
            else:
                return False

    def checkInShamanShop(self, checkItem) -> bool:
        if not self.client.shopShamanItems == "":
            for shamanItems in self.client.shopShamanItems.split(","):
                if checkItem == int(shamanItems.split("_")[0] if "_" in shamanItems else shamanItems):
                    return True
        else:
            return False

    def getItemCustomization(self, checkItem, isShamanShop) -> str:
        items = self.client.shopShamanItems if isShamanShop else self.client.shopItems
        if not items == "":
            for shopItem in items.split(","):
                itemSplited = shopItem.split("_")
                custom = itemSplited[1] if len(itemSplited) >= 2 else ""
                if int(itemSplited[0]) == checkItem:
                    return "" if custom == "" else ("_" + custom)
        else:
            return ""

    def getItemPriceInfo(self, item_category, item_id): #########
        #print(item_category, item_id)
        concat = str(item_category) + "|" + str(item_id)
        if concat in self.server.shopListCheck:
            return self.server.shopListCheck[concat]

    def getShopOutfits(self) -> dict:
        fulllooks = {}
        for _id in self.server.shopOutfitsCheck:
            if self.server.shopOutfitsCheck[_id][4] == True:
                fulllooks[_id] = self.server.shopOutfitsCheck[_id]
                
            elif self.server.shopOutfitsCheck[_id][3] >= self.server.serverTime:
                fulllooks[_id] = self.server.shopOutfitsCheck[_id]
                
        return fulllooks

    def getShopCategory(self, fullItem) -> int: # UNFINISHED
        return ((0 if fullItem // 10000 == 1 else fullItem // 10000) if fullItem > 9999 else fullItem // 100)

    def getShopItem(self, category, item_id) -> int:
        return (category * (10000 if item_id > 99 else 100) + item_id + (10000 if item_id > 99 else 0))

    def getShopItemID(self, fullItem, category) -> int:
        return (fullItem % 1000 if fullItem > 9999 else fullItem % 100 if fullItem > 999 else fullItem % (100 * category) if fullItem > 99 else fullItem)

    def getShopItemInfo(self, fullItem) -> list:
        item_cat = self.getShopCategory(fullItem)
        item_id = self.getShopItemID(fullItem, item_cat)
        return [item_cat, item_id]

    def getShopItemPrice(self, item_category, item_id, withFraises) -> int:
        price = self.getItemPriceInfo(item_category, item_id)[1 if withFraises else 0]
        for promotion in self.server.shopPromotions:
            if promotion["Category"] == item_category and promotion["Item"] == item_id:
                return price - int(promotion["Discount"] // 100.0 * price)
        return price

    def getShopItemPriceNoPromotion(self, item_category, item_id, withFraises) -> int:
        return self.getItemPriceInfo(item_category, item_id)[1 if withFraises else 0]

    def getShopShamanItemPrice(self, item_id, withFraises) -> int:
        price = self.server.shamanShopListCheck[str(item_id)][1 if withFraises else 0]
        for promotion in self.server.shopPromotions:
            if promotion["Category"] == -1 and promotion["Item"] == item_id:
                return price - int(promotion["Discount"] // 100.0 * price)
        return price

    def sendItemBuy(self, fullItem):
        self.client.sendPacket(Identifiers.send.Item_Buy, ByteArray().writeInt(fullItem).writeByte(4).toByteArray())

    def sendLookChange(self):
        look = self.client.playerLook.split(";")
        p = ByteArray().writeUnsignedShort(int(look[0]))

        for item in look[1].split(","):
            if "_" in item:
                custom = item.split("_")[1] if len(item.split("_")) >= 2 else ""
                realCustom = [] if custom == "" else custom.split("+")
                p.writeInt(int(item.split("_")[0]))
                p.writeByte(len(realCustom))

                x = 0
                while x < len(realCustom):
                    p.writeInt(int(realCustom[x], 16))
                    x += 1
            else:
                p.writeInt(int(item))
                p.writeByte(0)

        p.writeInt(int(self.client.mouseColor, 16))
        self.client.sendPacket(Identifiers.send.Mouse_Look, p.toByteArray())
        self.client.sendBullePacket(Identifiers.bulle.BU_ChangePlayerLook, self.client.playerID, base64.b64encode(self.client.playerLook.encode()).decode('utf-8'), self.client.mouseColor)

    def sendShamanItems(self):
        shamanItems = [] if self.client.shopShamanItems == "" else self.client.shopShamanItems.split(",")

        packet = ByteArray().writeShort(len(shamanItems))
        for item in shamanItems:
            if "_" in item:
                custom = item.split("_")[1] if len(item.split("_")) >= 2 else ""
                realCustom = [] if custom == "" else custom.split("+")
                packet.writeShort(int(item.split("_")[0])).writeByte(item in self.client.shamanLook.split(",")).writeByte(len(realCustom) + 1)
                x = 0
                while x < len(realCustom):
                    packet.writeInt(int(realCustom[x], 16))
                    x += 1
            else:
                packet.writeShort(int(item)).writeByte(item in self.client.shamanLook.split(",")).writeByte(0)
        self.client.sendPacket(Identifiers.send.Shaman_Items, packet.toByteArray())

    def sendShamanLook(self):
        items = ByteArray()

        count = 0        
        for item in self.client.shamanLook.split(","):
            realItem = int(item.split("_")[0]) if "_" in item else int(item)
            if realItem != 0:
                items.writeShort(realItem)
                count += 1
        self.client.sendPacket(Identifiers.send.Shaman_Look, ByteArray().writeShort(count).writeBytes(items.toByteArray()).toByteArray())
        self.client.sendBullePacket(Identifiers.bulle.BU_ChangeShamanLook, self.client.playerID, base64.b64encode(self.client.shopShamanItems.encode()).decode('utf-8'))

    def sendShopGift(self, playerName, isShamanItem, fullItem, message):
        if (not self.server.checkAlreadyExistingAccount(playerName) or playerName == self.client.playerName):
            self.sendShopGiftPacket(1, playerName)
        else:
            player = self.server.players.get(playerName)
            if player != None:
                if (player.Shop.checkInShamanShop(fullItem) if isShamanItem else player.Shop.checkInShop(fullItem)) or fullItem in self.client.shopGifts:
                    self.sendShopGiftPacket(2, playerName)
                else:
                    player.sendPacket(Identifiers.send.Shop_Gift, ByteArray().writeInt(self.server.lastShopGiftID).writeUTF(self.client.playerName).writeUTF(self.client.playerLook).writeBoolean(isShamanItem).writeInt(fullItem).writeUTF(message).writeBoolean(False).toByteArray())
                    self.sendShopGiftPacket(0, playerName)
                    self.server.shopGifts[self.server.lastShopGiftID] = [self.client.playerName, isShamanItem, fullItem]
                    self.server.lastShopGiftID += 1
                    player.shopGifts[fullItem] = [self.client.playerName, isShamanItem, fullItem, message]

                    if isShamanItem:
                        self.client.shopFraises -= self.getShopShamanItemPrice(fullItem, 1)
                    else:
                        info = self.getShopItemInfo(fullItem)
                        self.client.shopFraises -= self.getShopItemPrice(info[0], info[1], 1)
                    self.sendShopList(True)
            else:
                if (self.checkInPlayerShop("ShopShamanItems" if isShamanItem else "ShopItems", playerName, fullItem)) or self.checkInPlayerGifts(playerName, fullItem):
                    self.sendShopGiftPacket(2, playerName)
                else:
                    rs = self.server.cursor['shopgifts'].find_one({'Username':playerName})
                    if rs == None:
                        self.server.cursor['shopgifts'].insert_one({
                            "Username": playerName,
                            "Gifts": binascii.hexlify("|".join(map(str, [self.client.playerName, self.client.playerLook, isShamanItem, fullItem, message])).encode()).decode(),
                            "Messages": ""
                        })
                    else:
                        gifts = rs['Gifts']
                        gifts += "/" + binascii.hexlify("|".join(map(str, [self.client.playerName, self.client.playerLook, isShamanItem, fullItem, message])).encode()).decode()
                        self.server.cursor['shopgifts'].update_one({'Username':playerName}, {'$set':{'Gifts':gifts}})
                        gifts = ""
                    self.sendShopGiftPacket(0, playerName)

    def sendShopGiftPacket(self, packet_id, playerName):
        self.client.sendPacket(Identifiers.send.Gift_result, ByteArray().writeByte(packet_id).writeUTF(playerName).writeByte(0).writeInt(0).toByteArray())

    def sendShopList(self, sendItems=True):
        # Dictionary
        fulllooks = {}
        
        # Server List
        shopItems = self.server.shopList if sendItems else []
        shamanShopItems = self.server.shamanShopList if sendItems else []
        shopEmoteItems = self.server.shopEmoteList
        
        # Client List
        clientShopItems = [] if self.client.shopItems == "" else self.client.shopItems.split(",")
        clientShopFavoriteItems = [] if self.client.shopFavoriteItems == "" else self.client.shopItems.split(",")
        clientShopClothes = [] if self.client.shopClothes == "" else self.client.shopClothes.split("|")
        clientShamanItems = [] if self.client.shopShamanItems == "" else self.client.shopShamanItems.split(",")
        clientEmoteList = [] if self.client.shopEmotes == "" else self.client.shopEmotes.split(',')
    
        p = ByteArray().writeInt(self.client.shopCheeses).writeInt(self.client.shopFraises).writeUTF(self.client.playerLook)
        p.writeInt128(len(clientShopItems))
        for item in clientShopItems:
            if "_" in item:
                itemSplited = item.split("_")
                realItem = itemSplited[0]
                custom = itemSplited[1] if len(itemSplited) >= 2 else ""
                realCustom = [] if custom == "" else custom.split("+")
                p.writeInt128(int(realItem)).writeBoolean(item in clientShopFavoriteItems).writeInt128(len(realCustom))
                x = 0
                while x < len(realCustom):
                    p.writeInt128(int(realCustom[x], 16))
                    x += 1
            else:
                p.writeInt128(int(item)).writeBoolean(item in clientShopFavoriteItems).writeInt128(0)

        p.writeInt(len(shopItems))
        for item in shopItems:
            p.writeUnsignedShort(item["category"]).writeUnsignedShort(item["id"]).writeByte(item["customs"]).writeBoolean(item["new"]).writeByte(2).writeInt(item["cheese"]).writeInt(item["fraise"]).writeBoolean(False)
        
        if sendItems:
            fulllooks = self.getShopOutfits()
            
        p.writeByte(len(fulllooks))
        for info in fulllooks:
            p.writeUnsignedShort(int(info))
            p.writeUTF(fulllooks[info][0])
            p.writeByte(fulllooks[info][1])
            
        p.writeShort(len(clientShopClothes))
        for info in clientShopClothes:
            clotheSplited = info.split("/")
            p.writeUTF(clotheSplited[1] + ";" + clotheSplited[2] + ";" + clotheSplited[3])
        
        p.writeShort(len(clientShamanItems))
        for item in clientShamanItems:
            if "_" in item:
                itemSplited = item.split("_")
                realItem = itemSplited[0]
                custom = itemSplited[1] if len(itemSplited) >= 2 else ""
                realCustom = [] if custom == "" else custom.split("+")
                p.writeShort(int(realItem))
                p.writeByte(item in self.client.shamanLook.split(",")).writeByte(len(realCustom)+1)
                x = 0
                while x < len(realCustom):
                    p.writeInt(int(realCustom[x], 16))
                    x += 1
            else:
                p.writeShort(int(item)).writeByte(item in self.client.shamanLook.split(",")).writeByte(0)
        
        p.writeShort(len(shamanShopItems))
        for item in shamanShopItems:
            p.writeInt(item["id"]).writeByte(item["customs"]).writeBoolean(item["new"]).writeByte(item["flag"]).writeInt(item["cheese"]).writeShort(item["fraise"])
        
        p.writeInt128(len(shopEmoteItems))
        for item in shopEmoteItems:
            p.writeInt128(item["id"])
            p.writeInt128(item["cheese"])
            p.writeInt128(item["fraise"])
            p.writeByte(item["purchasable"])
            
        p.writeInt128(len(clientEmoteList))
        for item in clientEmoteList:
            p.writeInt128(int(item))
            
        self.client.sendPacket(Identifiers.send.Player_Shop_List, p.toByteArray())

    def sendShopCache(self):
        p = ByteArray().writeInt128(len(self.server.shopCachedFurs))
        for info in self.server.shopCachedFurs:
            p.writeInt128(info)
        
        self.client.sendPacket(Identifiers.send.Load_Fur_Cache, p.toByteArray())

    def sendShopInfo(self):            
        self.client.sendPacket(Identifiers.send.Shop_Info, ByteArray().writeInt(self.client.shopCheeses).writeInt(self.client.shopFraises).toByteArray())

    def sendShopShamanCache(self):
        p = ByteArray().writeInt128(len(self.server.shopCachedShamanItems))
        for info in self.server.shopCachedShamanItems:
            p.writeInt128(info)
            
        self.client.sendPacket(Identifiers.send.Load_Shaman_Object_Cache, p.toByteArray())


    def viewFullLook(self, visuID): # UNFINISHED
        print(f"[-] THIS FUNCTION IS NOT A FINISHED DUE MISSING INFORMATION HOW THIS IN GAME WORKS. FUNC: ViewFullLook ARGS: {visuID}")

  
    def sendPromotionPopup(self): # UNFINISHED
        if len(self.server.shopPromotions) > 0:
            promotion = self.server.shopPromotions[0]
            self.client.sendPacket(Identifiers.send.Promotion_Popup, ByteArray().writeShort(promotion["Category"]).writeShort(promotion["Item"]).writeByte(promotion["Discount"]).writeShort(2230).toByteArray())
            

    def sendPromotions(self): # UNFINISHED
        for promotion in self.server.shopPromotions:
            self.client.sendPacket(Identifiers.send.Promotion, ByteArray().writeBoolean(not promotion["Collector"]).writeBoolean(not promotion["isShamanItem"]).writeInt((self.getShopItem(promotion["Category"], promotion["Item"])) if promotion["isShamanItem"] == False else promotion["Item"]).writeBoolean(promotion["Time"] - self.server.serverTime > 0).writeInt(promotion["Time"] - self.server.serverTime).writeByte(promotion["Discount"]).toByteArray())
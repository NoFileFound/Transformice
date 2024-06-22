import sys
sys.dont_write_bytecode = True
#sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(sys.argv[0]))))

import asyncio
import random
import time
import string
import pymongo
import re
import sys
import Client, Bulle
from Utils import Config, Logger
from Utils.Time import Time

class Server(asyncio.Transport):
    def __init__(self):
        # Config
        self.bullesInfo = []
        self.captchaList = Config.Json().load_file("./Include/Server/captchas.json")
        self.communityPartners = Config.Json().load_file("./Include/Server/partners.json")
        self.forbiddenWords = Config.Json().load_file("./Include/Server/forbidden_words.json")
        self.gameCodes = Config.Json().load_file("./Include/Server/codes.json")
        self.gameCommands = Config.Json().load_file("./Include/Client/commands.json")
        self.gameLanguages = Config.Json().load_file("./Include/Server/languages.json")
        self.gameInfo = Config.Json().load_file("./Include/Server/game.json")
        self.inventoryConsumables = Config.Json().load_file("./Include/Server/inventory.json")
        self.profileStats = Config.Json().load_file("./Include/Server/profilestats.json")
        self.shopInfo = Config.Json().load_file("./Include/Server/shop.json")
        self.shopPromotions = Config.Json().load_file("./Include/Server/promotions.json")
        self.serverInfo = Config.Json().load_file("./Include/Server/server.json")
        self.swfInfo = Config.Json().load_file("./Include/Server/swf.json")
    
        # Integer
        self.bullecount = 0
        self.currentRankingSeason = 4
        self.currentRankingSeasonTime = 1939054893
        self.lastCafePostID = 0
        self.lastCafeTopicID = 0
        self.lastPlayerCode = 0
        self.lastPlayerID = 0
        self.lastSanctionID = 0
        self.lastShopGiftID = 0
        self.lastTribeID = 0
        
        # Boolean
        self.isDebug = False
        
        # List
        self.IPPermaBanCache = []
        self.IPTempBanCache = []
        self.reportedCafePosts = []
        self.shopList = []
        self.shamanShopList = []
        self.shopEmoteList = []
        self.shopOutfits = []
        
        # Dict
        self.bulles = {}
        self.chats = {}
        self.modoReports = {}
        self.playerMovement = {}
        self.players = {}
        self.shopGifts = {}
        self.shopListCheck = {}
        self.shamanShopListCheck = {}
        self.shopPromotionsCheck = {}
        self.shopOutfitsCheck = {}
        self.whisperMessages = {}
        
        # Loops
        self.loop = asyncio.new_event_loop()
        
        # Other
        self.cursor = None
        self.serverTime = int(time.time())
        self.Logger = Logger.Logger()

    def appendBulle(self, bulle):
        self.bullecount += 1
        self.bulles[self.bullecount] = bulle

    def checkAlreadyExistingAccount(self, playerName) -> bool:
        return self.cursor['users'].find_one({'Username':playerName}) != None

    def checkAlreadyExistingGuest(self, playerName) -> str:
        playerName = playerName.replace('#', '')
        playerName = re.sub('[^0-9a-zA-Z]+', '', playerName)
        if len(playerName) == 0 or self.checkConnectedPlayer("*" + playerName):
            playerName = "*Souris_%s" %("".join([random.choice(string.ascii_lowercase) for x in range(4)]))
        else:
            playerName = "*" + playerName
        return playerName

    def checkConnectedPlayer(self, playerName) -> bool:
        return playerName in self.players

    def checkMessage(self, message) -> int:
        i = 0
        while i < len(self.forbiddenWords):
            if re.search("[^a-zA-Z]*".join(self.forbiddenWords[i]), message.lower()):
                return True
            i += 1
        return False

    def genPlayerTag(self, playerName) -> str:
        tag = "".join([str(random.choice(range(9))) for x in range(4)])
        playerName += "#" + tag
        return playerName

    def getInventoryCategory(self, obj, _id) -> int:
        if int(_id) in [800,801,2253,2254,2257,2260,2261,2343,2472,2497,2504,2505,2506,2507,2508,2509]: return 10
        if int(_id) in [2473,2474,2491,2485,2487,2475,2476,2477,2478,2479,2480,2481,2482,2483,2484,2486,2488,2489,2490,2492,2493]: return 20
        if "fur" in obj or "pet" in obj: return 50
        if ("pencil" in obj) or (int(_id) in [4,2447,21]) or ("letter" in obj): return 40
        if ("launchlable" in obj) or (int(_id) in [2,3,16,23,0]): return 30
        return 100

    def getLastShopOutfitID(self) -> int:
        if len(self.shopInfo["fullLooks"]) == 0: 
            return 1000
        highestid = self.shopInfo["fullLooks"][-1]["id"]
        return highestid + 1

    def getLastShopPromotionID(self) -> int:
        if len(self.shopPromotions) == 0: 
            return 1000
        highestid = self.shopPromotions[-1]["Id"]
        return highestid + 1

    def getPlayerBadgesUnique(self, playerBadges) -> list:
        badges = list(map(int, playerBadges))
        listBadges = []
        for badge in badges:
            if not badge in listBadges:
                listBadges.append(badge)
        return listBadges

    def getPlayerID(self, playerName) -> int:
        if playerName in self.players:
            return self.players[playerName].playerID
        else:
            rs = self.cursor['users'].find_one({'Username':playerName})
            if rs:
                return rs['PlayerID']
            else:
                return 0

    def getPlayerName(self, playerID) -> str:
        rs = self.cursor['users'].find_one({'PlayerID' : int(playerID)})
        return rs['Username'] if rs else ""

    def getTempIPBanInfo(self, ipAddress) -> list:
        info = self.cursor["iptempban"].find_one({"IP":ipAddress, "State":"Active"})
        return [info["Duration"], info["Reason"]] if info else []

    def getTempPunishmentInfo(self, playerName, typ) -> list:
        typ = 'Banned' if typ == 1 else 'Muted'
        info = self.cursor['sanctions'].find_one({'Username':playerName, 'Type':typ, 'State':'Active'})
        return [info['Reason'], info['Duration'], info['Moderator']] if info else []

    def getTotalAccountsByEmailAddress(self, email) -> int:
        return list(self.cursor['users'].find({'Email' : email}))
                
                
    # Database
    
    def LoadBulles(self):
        self.bullesInfo = self.serverInfo["bulles"]
        totalbulles = len(self.bullesInfo)
        if totalbulles == 0:
            self.Logger.warn(f"There are no any bulles to load. The rooms won't work.\n")
        else:
            self.Logger.info(f"Loaded {totalbulles} bulle.\n")

    def LoadDatabase(self):
        try:
            self.cursor = pymongo.MongoClient(self.serverInfo["db_url"])[self.serverInfo["db_name"]]
            self.Logger.info(f"The database {self.serverInfo['db_name']} was connected.\n")
        except:
            self.Logger.error(f"Unable to connect to the database {self.serverInfo['db_name']}\n")
            sys.exit(0)

    def LoadDBInfo(self):
        self.lastCafePostID = self.cursor['cafeposts'].count_documents({})
        self.lastCafeTopicID = self.cursor['cafetopics'].count_documents({})
        self.lastPlayerID = self.cursor['users'].count_documents({})
        self.lastSanctionID = self.cursor['sanctions'].count_documents({})
        self.lastShopGiftID = self.cursor['shopgifts'].count_documents({})
        self.lastTribeID = self.cursor['tribes'].count_documents({})

    def LoadDebug(self):
        self.isDebug = self.serverInfo["debug"]
        self.Logger.info(f"Debug : {'on' if self.isDebug else 'off'}\n")

    def LoadPunishments(self):
        self.IPPermaBanCache = [doc['IP'] for doc in list(self.cursor["ippermaban"].find())]
        self.IPTempBanCache = [doc['IP'] for doc in list(self.cursor["iptempban"].find())]

    def LoadShopItems(self, sendMessage=True):
        self.shopList = self.shopInfo["shopItems"]
        self.shamanShopList = self.shopInfo["shamanItems"]
        self.shopEmoteList = self.shopInfo["shopEmotes"]
        self.shopOutfits = self.shopInfo["fullLooks"]

        for item in self.shopList:
            self.shopListCheck[f'{item["category"]}|{item["id"]}'] = [item["cheese"], item["fraise"], item["customs"]]

        for item in self.shamanShopList:
            self.shamanShopListCheck[str(item["id"])] = [item["cheese"], item["fraise"]]
        
        for item in self.shopOutfits:
            self.shopOutfitsCheck[str(item["id"])] = [item["look"], item["bg"], item["discount"], item["start"], item["perm"], item["name"], item["addedBy"]]
            
        if sendMessage:
            self.Logger.info(f"Loaded {len(self.shopList)} total shop items.\n")
            self.Logger.info(f"Loaded {len(self.shamanShopList)} total shop shaman items.\n")
            self.Logger.info(f"Loaded {len(self.shopEmoteList)} total shop emotes.\n")
            self.Logger.info(f"Loaded {len(self.shopOutfits)} total shop outfits.\n")

    def LoadShopPromotions(self, sendMessage=True):
        promotions = []
    
        for promotion in self.shopPromotions:
            #print(self.serverTime, promotion["Time"])
            #print(self.serverTime - promotion["Time"])
            if (self.serverTime < promotion["Time"]):
                promotions.append(promotion)
                self.shopPromotionsCheck[f"{promotion['Category']}|{promotion['Item']}"] = promotion["Time"]
    
        self.shopPromotions = promotions
        if sendMessage:
            self.Logger.info(f"Loaded {len(self.shopPromotions)} total shop promotions.\n")
        
    # Packets
    def sendDatabaseUpdate(self):
        for player in self.players.copy().values():
            player.updateDatabase()

    def sendReloadModules(self):
        for player in self.players.copy().values():
            player.reloadModules()

    def sendServerMessageAll(self, message, sender, isTab=False, staff_positions=[]):
        for player in self.players.copy().values():
            if (player.privLevel == sender.privLevel or player.privLevel in staff_positions) and player != sender:
                player.sendServerMessage(message, isTab)

    def sendStaffChannelMessage(self, _id, langue, identifiers, packet):
        for client in self.players.copy().values():
            if(_id == 2):
                if((client.privLevel >= 8 or client.isPrivMod) and client.playerLangue == langue):
                    client.sendPacket(identifiers, packet)
        
            elif(_id == 3):
                if((client.privLevel >= 8 or client.isPrivMod) and client.playerLangue == langue):
                    client.sendPacket(identifiers, packet)
                    
            elif(_id == 4):
                if((client.privLevel >= 8 or client.isPrivMod) or client.isPrivMod):
                    client.sendPacket(identifiers, packet)
                    
            elif(_id == 5):
                if((client.privLevel >= 8 or client.isPrivMod) or client.isPrivMod):
                    client.sendPacket(identifiers, packet)
                    
            elif(_id == 7):
                if(client.privLevel in [7, 10] or client.isMapCrew == True):
                    client.sendPacket(identifiers, packet)
                    
            elif(_id == 8):
                if(client.privLevel in [5, 10] or client.isLuaCrew == True):
                    client.sendPacket(identifiers, packet)
                    
            elif(_id == 9):
                if(client.privLevel in [6, 10] or client.isFunCorpPlayer == True):
                    client.sendPacket(identifiers, packet)
                                        
            elif(_id == 10):
                if(client.privLevel in [4, 10] or client.isFashionSquad == True):
                    client.sendPacket(identifiers, packet)

    def sendStaffMessage(self, message, staff_positions, isTab=False, isModoNotification=False):
        if isinstance(staff_positions, str):
            staff_positions = staff_positions.split("|")
            
        for player in self.players.copy().values():
            if player.checkStaffPermission(staff_positions):
                if isModoNotification:
                    if player.isModoPwetNotifications:
                        player.sendServerMessage(message, isTab)
                else:
                    player.sendServerMessage(message, isTab)


    # Other Functions
    def banPlayer(self, playerName, hours, reason, moderator, isSilent=False, disconnectIP=True) -> bool:
        if self.checkAlreadyExistingAccount(playerName):
            player = self.players.get(playerName)
            if player != None:        
                if moderator == "Serveur":
                    self.sendStaffMessage(f"The player {playerName} was banned for {hours} hour(s). Reason: Vote Populaire.", "PrivMod|Mod|Admin")
                    player.banVotes = []
                self.cursor["sanctions"].insert_one({"ID":self.lastSanctionID, "Username":playerName, "Type":"Banned", "State":"Active", "Duration":int(Time.getTime() + (hours * 60 * 60)) if hours != -1 else -1, "Reason":reason, "Moderator":moderator, "CancelledAuthor":"", "CancelledReason":""})
                if hours != -1:
                    player.sendPlayerBan(hours, reason, isSilent)
                    self.sendStaffMessage(f"{moderator} banned the player {playerName} for {hours}h ({reason}).", "PrivMod|Mod|Admin")
                else:
                    player.transport.close()
                    self.sendStaffMessage(f"{moderator} permanently banned the player {playerName} ({reason}).", "PrivMod|Mod|Admin")
                
                if disconnectIP:
                    for p in self.players.copy().values():
                        if p.ipAddress == player.ipAddress and p.playerName != playerName:
                            p.transport.close()
                self.lastSanctionID += 1
                return True
            
            else:
                self.cursor["sanctions"].insert_one({"ID":self.lastSanctionID, "Username":playerName, "Type":"Banned", "State":"Active", "Duration":int(Time.getTime() + (hours * 60 * 60)) if hours != -1 else -1, "Reason":reason, "Moderator":moderator, "CancelledAuthor":"", "CancelledReason":""})
                self.sendStaffMessage(f"{moderator} offline banned the player {playerName} for {hours}h ({reason}).", "PrivMod|Mod|Admin")
                self.lastSanctionID += 1
                return True
        return False

    def banIPAddress(self, ipAddress, hours, reason, moderator) -> bool:
        if ipAddress in self.IPTempBanCache:
            return False
            
        self.IPTempBanCache.append(ipAddress)
        self.cursor["iptempban"].insert_one({"IP":ipAddress, "State":"Active", "Duration":int(Time.getTime() + (hours * 60 * 60)), "Reason":reason, "Moderator":moderator, "CancelledAuthor":"", "CancelledReason":""})
        for player in self.players.copy().values():
            if player.ipAddress == ipAddress:
                self.banPlayer(player.playerName, hours, reason, moderator, True, True)
        return True

    def buildCaptchaCode(self) -> list:
        CC = "".join([random.choice(list(self.captchaList.keys())) for x in range(4)])
        words, px, py, lines = list(CC), 0, 1, []
        for count in range(1, 17):
            wc, values = 1, []
            for word in words:
                ws = self.captchaList[word]
                if count > len(ws):
                    count = len(ws)
                ws = ws[str(count)]
                values += ws.split(",")[(1 if wc > 1 else 0):]
                wc += 1
            lines += [",".join(map(str, values))]
            if px < len(values):
                px = len(values)
            py += 1
        return [CC, (px + 2), 17, lines]
 
    def removeTempIPBan(self, ipAddress) -> bool:
        if not ipAddress in self.IPTempBanCache:
            return False
            
        self.IPTempBanCache.remove(ipAddress)
        self.cursor["iptempban"].delete_one({"IP":ipAddress})
        return True
                
    def voteBanPopulaire(self, playerName, playerVoted, ip):
        player = self.players.get(playerName)
        if player != None and player.privLevel >= 1 and not ip in player.banVotes:
            player.banVotes.append(ip)
            if len(player.banVotes) == 20:
                self.banPlayer(playerName, 1, "Vote Populaire", "Server", False)
            self.sendStaffMessage(f"The player {playerVoted} voted to ban {playerName} ({len(player.banVotes)} / 20 votes)", "PrivMod|Mod|Admin", False)

    def removeTempUserBan(self, playerName):
        pass
        
    def removeModMute(self, playerName):
        pass

    def LoadModoPwet(self):
        pass
        
    def SaveModoPwet(self):
        pass

    def closeServer(self):
        self.sendDatabaseUpdate()
        self.SaveModoPwet()
        pass

    def sendServerRestart(self, seconds):
        pass

    def Main(self):
        self.LoadDatabase()
        self.LoadDBInfo()
        self.LoadBulles()
        self.LoadPunishments()
        self.LoadShopItems()
        self.LoadShopPromotions()
        self.LoadModoPwet()
        self.LoadDebug()
        
        
        try:
            for port in self.swfInfo["ports"]:
                self.loop.run_until_complete(self.loop.create_server(lambda: Client.Client(self, self.cursor), self.serverInfo["ip_address"], port))
                
            self.loop.run_until_complete(self.loop.create_server(lambda: Bulle.Bulle(self, self.cursor), self.serverInfo["ip_address"], self.swfInfo["bulle_port"])) # do bulle recv pls

            
            self.Logger.info(f"Server connected on {self.serverInfo['ip_address']}:{self.swfInfo['ports']}\n")
        except OSError:
            self.Logger.error("The server is already running.\n")
            return
            
        self.loop.run_forever()
        
if __name__ == "__main__":
    _Server = Server()
    _Server.Main()
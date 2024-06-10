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
from Utils import Config, Logger
import Client, Bulle

class Server(asyncio.Transport):
    def __init__(self):
        # Config
        self.bullesInfo = []
        self.captchaList = Config.Json().load_file("./Include/Server/captchas.json")
        self.communityPartners = Config.Json().load_file("./Include/Server/partners.json")
        self.forbiddenWords = Config.Json().load_file("./Include/Server/forbidden_words.json")
        self.gameLanguages = Config.Json().load_file("./Include/Server/languages.json")
        self.gameInfo = Config.Json().load_file("./Include/Server/game.json")
        self.shopInfo = Config.Json().load_file("./Include/Server/shop.json")
        self.shopPromotions = Config.Json().load_file("./Include/Server/promotions.json")
        self.serverInfo = Config.Json().load_file("./Include/Server/server.json")
        self.swfInfo = Config.Json().load_file("./Include/Server/swf.json")
    
        # Integer
        self.lastPlayerCode = 0
        self.bullecount = 0
        self.lastPlayerID = 0
        self.lastShopGiftID = 0
        self.lastCafePostID = 0
        self.lastCafeTopicID = 0
        
        # Boolean
        self.isDebug = False
        
        # List
        self.shopList = []
        self.shamanShopList = []
        self.shopEmoteList = []
        self.shopOutfits = []
        
        # Dict
        self.players = {}
        self.shopGifts = {}
        self.shopListCheck = {}
        self.shamanShopListCheck = {}
        self.shopOutfitsCheck = {}
        self.bulles = {}
        
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
        
    def checkAlreadyExistingGuest(self, playerName):
        playerName = re.sub('[^0-9a-zA-Z]+', '', playerName)
        if len(playerName) == 0 or self.checkConnectedPlayer("*" + playerName):
            playerName = "*Souris_%s" %("".join([random.choice(string.ascii_lowercase) for x in range(4)]))
        else:
            playerName = "*" + playerName
        return playerName
        
    def checkConnectedPlayer(self, playerName) -> bool:
        return playerName in self.players
        
    def checkMessage(self, message):
        i = 0
        while i < len(self.forbiddenWords):
            if re.search("[^a-zA-Z]*".join(self.forbiddenWords[i]), message.lower()):
                return True
            i += 1
        return False
        
    def genPlayerTag(self, playerName):
        tag = "".join([str(random.choice(range(9))) for x in range(4)])
        playerName += "#" + tag
        return playerName
        
    def getLastShopOutfitID(self):
        if len(self.shopInfo["fullLooks"]) == 0: 
            return 1000
        highestid = self.shopInfo["fullLooks"][-1]["id"]
        return highestid + 1

    def getLastShopPromotionID(self):
        if len(self.shopPromotions) == 0: 
            return 1000
        highestid = self.shopPromotions[-1]["Id"]
        return highestid + 1

    def getPlayerID(self, playerName):
        if playerName in self.players:
            return self.players[playerName].playerID
        else:
            rs = self.cursor['users'].find_one({'Username':playerName})
            if rs:
                return rs['PlayerID']
            else:
                return 0
                
    def getTotalAccountsByEmailAddress(self, email) -> int:
        return 0
        
    def sendStaffMessage(self, message, staff_roles, isTab=False, isNotUsingServerChannel=False) -> None: # "PrivMod|Mod|Admin"
        #perms = self.getStaffRoles()
        pass
    
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
        self.lastShopGiftID = self.cursor['shopgifts'].count_documents({})
        self.lastCafePostID = self.cursor['cafeposts'].count_documents({})
        self.lastCafeTopicID = self.cursor['cafetopics'].count_documents({})
            
    def LoadShopItems(self):
        self.shopList = self.shopInfo["shopItems"]
        self.shamanShopList = self.shopInfo["shamanItems"]
        self.shopEmoteList = self.shopInfo["shopEmotes"]
        self.shopOutfits = self.shopInfo["fullLooks"]
        self.Logger.info(f"Loaded {len(self.shopList)} total shop items.\n")
        self.Logger.info(f"Loaded {len(self.shamanShopList)} total shop shaman items.\n")
        self.Logger.info(f"Loaded {len(self.shopEmoteList)} total shop emotes.\n")
        self.Logger.info(f"Loaded {len(self.shopOutfits)} total shop outfits.\n")

        for item in self.shopList:
            self.shopListCheck[f'{item["category"]}|{item["id"]}'] = [item["cheese"], item["fraise"]]

        for item in self.shamanShopList:
            self.shamanShopListCheck[str(item["id"])] = [item["cheese"], item["fraise"]]
        
        for item in self.shopOutfits:
            self.shopOutfitsCheck[str(item["id"])] = [item["look"], item["bg"], item["discount"], item["start"], item["perm"], item["name"], item["addedBy"]]

    def LoadShopPromotions(self, sendMessage=True):
        promotions = []
    
        for promotion in self.shopPromotions:
            if promotion["Time"] >= self.serverTime:
                promotions.append(promotion)
    
        self.shopPromotions = promotions
        if sendMessage:
            self.Logger.info(f"Loaded {len(self.shopPromotions)} total shop promotions.\n")

    def Main(self):
        self.LoadDatabase()
        self.LoadDBInfo()
        self.LoadBulles()
        self.LoadShopItems()
        self.LoadShopPromotions()
        
        
        try:
            for port in self.swfInfo["ports"]:
                self.loop.run_until_complete(self.loop.create_server(lambda: Client.Client(self, self.cursor), self.serverInfo["ip_address"], port))
                
            self.loop.run_until_complete(self.loop.create_server(lambda: Bulle.Bulle(self, self.cursor), self.serverInfo["ip_address"], self.swfInfo["bulle_port"])) # do bulle recv pls
                
            self.isDebug = self.serverInfo["debug"]
            
            
            self.Logger.info(f"Server connected on {self.serverInfo['ip_address']}:{self.swfInfo['ports']}\n")
        except OSError:
            self.Logger.error("The server is already running.\n")
            return
            
        self.loop.run_forever()
        
if __name__ == "__main__":
    _Server = Server()
    _Server.Main()
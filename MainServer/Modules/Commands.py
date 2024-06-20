#coding: utf-8
import datetime
import base64
import hashlib
import re
import pycountry_convert

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.IPTools import IPTools
from Utils.Time import Time
from Utils.Other import Other


class Commands:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        self.currentArgsCount = 0
        self.argsNotSplited = ""
        self.commandName = ""
        self.commands = {}
        self.__init_2()
                
    def command(self, func=None, args=0, level=[], nosouris=False, tribe=False, debug=False, alias=[], reqrs=[]):
        if not func:
            reqrs = []
            if args > 0: reqrs.append(['args',args])
            if len(level) > 0: reqrs.append(['level',level])
            if nosouris: reqrs.append(['nosouris', nosouris])
            if tribe > 0: reqrs.append(['tribe', tribe])
            if debug > 0: reqrs.append(['debug', debug])
            return lambda x: self.command(x, args, level, nosouris, tribe, debug, alias, reqrs)
        else:
            for i in alias + [func.__name__]: 
                self.commands[i] = [reqrs, func]
        
    def requireArgs(self, arguments):
        if self.currentArgsCount < arguments:
            self.client.sendServerMessage("You need more arguments to use this command.", True)
            return False
        return self.currentArgsCount == arguments
        
    def requireLevel(self, level=[]):
        return self.client.checkStaffPermission(level) != False
        
    def requireTribePerm(self, permId):
        return False
        
    async def parseCommand(self, command):
        values = command.split(" ")
        command = values[0].lower()
        args = values[1:]
        self.argsNotSplited = " ".join(args)
        self.currentArgsCount = len(args)
        self.commandName = command
        if command in self.commands:
            self.server.cursor['commandlog'].insert_one({'Username':self.client.playerName, 'IP':IPTools.EncodeIP(self.client.ipAddress), 'Time':Time.getTime(), 'Command':command, 'Service':self.server.serverInfo['name']})
            for i in self.commands[command][0]:
                if i[0] == "args":
                    if not self.requireArgs(i[1]): return
                elif i[0] == "level":
                    if not self.requireLevel(i[1]): return
                elif i[0] == 'nosouris':
                    if self.client.isGuest: return
                elif i[0] == 'tribe':
                    if not self.requireTribePerm(i[1]): return
                elif i[0] == 'debug':
                    if not self.server.isDebug: return
            await self.commands[command][1](self, *args)
        elif self.client.privLevel >= 8 or self.client.isPrivMod:
          self.client.sendServerMessage(f"Invalid command <J>{command}</J>", True)      
            
    def __init_2(self):
# Guest / Souris Commands   
        @self.command(alias=['bootcamp', 'racing', 'survivor', 'vanilla'])
        async def normal(self):
            if self.commandName == "normal":
                self.client.sendEnterRoom("")
            else:
                self.client.sendEnterRoom(f"\x03{self.commandName}")
         
        @self.command(alias=['profil','perfil','profiel'])
        async def profile(self, playerName=''):
            if len(playerName) == 0:
                playerName = self.client.playerName
            else:
                playerName = Other.parsePlayerName(playerName)
            self.client.sendProfile(playerName)
            
        @self.command(alias=["temps"])
        async def time(self):
            self.client.playerTime += abs(Time.getSecondsDiff(self.client.loginTime))
            self.client.loginTime = Time.getTime()
            temps = map(int, [self.client.playerTime // 86400, self.client.playerTime // 3600 % 24, self.client.playerTime // 60 % 60, self.client.playerTime % 60])
            self.client.sendLangueMessage("", "$TempsDeJeu", *temps)
            
        @self.command
        async def tutorial(self):
            self.client.sendEnterRoom("\x03[Tutorial] %s" %(self.client.playerName))
                        
 
# Player Commands
        @self.command(nosouris=True, args=1)
        async def codecadeau(self, code):
            for i in self.server.gameCodes:
                if code.upper() == i['code']:
                    r1 = i['id']
                    r2 = i["amount"]
                    self.client.sendCodePrize("cheese" if r1 == 0 else "fraise" if r1 == 1 else "consumable", r2)
                    self.server.gameCodes.remove(i)
                    break
                    
        @self.command(nosouris=True, alias=['editor'])
        async def editeur(self):
            self.client.sendEnterRoom("\x03[Editeur] %s" %(self.client.playerName))
            self.client.sendPacket(Identifiers.old.send.Map_Editor, [])
            
        @self.command(nosouris=True)
        async def mapcrew(self):
            staffMessage = "$MapcrewPasEnLigne"
            staffMembers = {}
            for player in self.server.players.copy().values():
                if player.privLevel == 7 or player.isMapCrew:
                    if player.playerLangue in staffMembers:
                        names = staffMembers[player.playerLangue]
                        names.append(player.playerName)
                        staffMembers[player.playerLangue] = names
                    else:
                        names = []
                        names.append(player.playerName)
                        staffMembers[player.playerLangue] = names
            if len(staffMembers) > 0:
                staffMessage = "$MapcrewEnLigne"
                for member in staffMembers.items():
                    staffMessage += f"<br>[{member[0]}] <BV>{('<BV>, </BV>').join(member[1])}</BV>"
            self.client.sendLangueMessage("", staffMessage)
            
        @self.command(nosouris=True)
        async def mod(self):
            staffMessage = "$ModoPasEnLigne"
            staffMembers = {}
            for player in self.server.players.copy().values():
                if player.privLevel in [8, 9] or player.isPrivMod:
                    if player.playerLangue in staffMembers:
                        names = staffMembers[player.playerLangue]
                        names.append(player.playerName)
                        staffMembers[player.playerLangue] = names
                    else:
                        names = []
                        names.append(player.playerName)
                        staffMembers[player.playerLangue] = names
            if len(staffMembers) > 0:
                staffMessage = "$ModoEnLigne"
                for member in staffMembers.items():
                    staffMessage += f"<br>[{member[0]}] <BV>{('<BV>, </BV>').join(member[1])}</BV>"
            self.client.sendLangueMessage("", staffMessage)
            
        @self.command(nosouris=True, debug=True)
        async def staffroles(self):
            self.client.sendServerMessage(str(self.client.getStaffPermissions()), True)
                        
        @self.command(nosouris=True)
        async def totem(self):
            if self.client.shamanNormalSaves >= 1500 or self.server.isDebug:
                self.client.sendEnterRoom(f"\x03[Totem] {self.client.playerName}")
            
# Fashion squad commands
        @self.command(level=['FS', 'Mod', 'Admin', 'Owner'])
        async def lsfs(self):
            FS = ""
            for player in self.server.players.copy().values():
                if player.isFashionSquad or player.privLevel == 4:
                    FS += f"<font color='#ffb6c1'>• [{player.playerLangue}] {player.playerName} : {player.roomName} </font><br>"
            if FS != "":
                self.client.sendMessage(FS.rstrip("\n"))
            else:
                self.client.sendServerMessage("Don't have any online Fashion Squads at moment.", True)

        @self.command(level=['FS', 'Mod', 'Admin', 'Owner'], args=1)
        async def iteminfo(self, fullItem):
            info = self.client.Shop.getShopItemInfo(int(fullItem))
            cheesePrice, fraisePrice = self.client.Shop.getShopItemPriceNoPromotion(info[0], info[1], False), self.client.Shop.getShopItemPriceNoPromotion(info[0], info[1], True)
            self.client.sendServerMessage(f"Category: {info[0]}", True)
            self.client.sendServerMessage(f"Item ID: {info[1]}", True)
            self.client.sendServerMessage(f"Item Price: <J>{cheesePrice}</J> / <R>{fraisePrice}</R>", True)
            self.client.sendServerMessage(f"Item Promotion: {(self.client.Shop.getShopItemPrice(info[0], info[1], True) / fraisePrice) * 100}", True)

# Lua commands
        @self.command(level=['LU', 'Mod', 'Admin', 'Owner'])
        async def lslua(self):
            FS = ""
            for player in self.server.players.copy().values():
                if player.isFashionSquad or player.privLevel == 5:
                    FS += f"<font color='#79bbac'>• [{player.playerLangue}] {player.playerName} : {player.roomName} </font><br>"
            if FS != "":
                self.client.sendMessage(FS.rstrip("\n"))
            else:
                self.client.sendServerMessage("Don't have any online Lua Crews at moment.", True)

            
# Modo commands
        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def banhack(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            self.client.ModoPwet.banHack(playerName, False)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'])
        async def chatfilter(self, option, *args):
            if option == "":
                self.client.sendServerMessage("You need more arguments to use this command.", True)
                
            elif option == "list":
                msg = "Filtered strings:\n"
                for message in self.server.forbiddenWords:
                    msg += message + "\n"
                self.client.sendLogMessage(msg)
                
            elif option == "del":
                name = self.argsNotSplited.split(" ", 1)[1].replace("http://www.", "").replace("https://www.", "").replace("http://", "").replace("https://", "").replace("www.", "")
                if not name in self.server.forbiddenWords:
                    self.client.sendServerMessage(f"The string <N>[{name}]</N> is not in the filter.", True)
                else:
                    self.server.forbiddenWords.remove(name)
                    self.client.sendServerMessage(f"The string <N>[{name}]</N> has been removed from the filter.", True)
                    
            elif option == "add":
                name = self.argsNotSplited.split(" ", 1)[1].replace("http://www.", "").replace("https://www.", "").replace("http://", "").replace("https://", "").replace("www.", "")
                if name in self.server.forbiddenWords:
                    self.client.sendServerMessage(f"The string <N>[{name}]</N> is already filtered (matches [{name}]).", True)
                else:
                    self.server.forbiddenWords.append(name)
                    self.client.sendServerMessage(f"The string <N>[{name}]</N> has been added to the filter.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def chatlog(self, playerName):
            self.client.ModoPwet.openChatLog(Other.parsePlayerName(playerName))

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1, alias=['chercher'])
        async def find(self, text):
            result = ""
            for player in self.server.players.copy().values():
                if player.playerName.startswith(text):
                    result += "<BV>%s</BV> -> %s\n" %(player.playerName, player.roomName)
            result = result.rstrip("\n")
            if result != "":
                self.client.sendServerMessage(result, True)
            else:
                self.client.sendServerMessage("No results were found.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1, alias=['join'])
        async def follow(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None:
                if player.roomName != self.client.roomName:
                    community = "" if player.playerLangue == self.client.playerLangue else player.playerLangue
                    self.client.sendEnterRoom(player.roomName, community)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def ibanhack(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            self.client.ModoPwet.banHack(playerName, True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def ip(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None:
                country_code = pycountry_convert.country_name_to_country_alpha2(player.ipCountry, cn_name_format="default")
                continent = pycountry_convert.convert_continent_code_to_continent_name(pycountry_convert.country_alpha2_to_continent_code(country_code))
                msg = f"<BV>{playerName}</BV>'s IP address: {IPTools.EncodeIP(player.ipAddress)}"
                if self.client.privLevel == 10:
                    msg += f" ({player.ipAddress})"
                msg += "\n"
                msg += f"{country_code.upper()} - {player.ipCountry} ({continent}) - Community [{player.playerLangue.upper()}]"
                self.client.sendServerMessage(msg, True)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def ipnom(self, ipAddress):
            List = "Logs for the IP address ["+ipAddress+"]:"
            for rs in self.server.cursor['loginlog'].find({'IP':ipAddress}).distinct("Username"):
                if self.server.checkConnectedPlayer(rs):
                    List += "<br>" + rs + " <G>(online)</G>"
                else:
                    List += "<br>" + rs
            self.client.sendServerMessage(List, True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def l(self, xxx):
            if "." not in xxx:
                r = self.server.cursor['loginlog'].find({'Username':xxx})
                if r == None:
                    self.client.playerException.Invoke("notloggedin", xxx)
                else:
                    message = "<p align='center'>Connection logs for player: <BL>"+xxx+"</BL>\n</p>"
                    for rs in r[0:200]:
                        message += f"<p align='left'><V>[ {xxx} ]</V> <BL>{rs['Time']}</BL><G> ( <font color = '{IPTools.ColorIP(rs['IP'])}'>{rs['IP']}</font> - {rs['Country']} ) {rs['ConnectionID']} - {rs['Community']}</G><br>"
                    self.client.sendLogMessage(message)
            else:
                r = self.server.cursor['loginlog'].find({'IP':xxx})
                if r == None:
                    pass
                else:
                    message = "<p align='center'>Connection logs for IP Address: <V>"+xxx.upper()+"</V>\n</p>"
                    for rs in r[0:200]:
                        message += f"<p align='left'><V>[ {rs['Username']} ]</V> <BL>{rs['Time']}</BL><G> ( <font color = '{IPTools.ColorIP(xxx)}'>{xxx}</font> - {rs['Country']} ) {rs['ConnectionID']} - {rs['Community']}</BL><br>"
                    self.client.sendLogMessage(message)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def kick(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None:
                player.transport.close()
                self.server.sendServerMessageAll(f"The player {playerName} has been kicked by {self.client.playerName}.", self.client, False)
                self.client.sendServerMessage(f"The player {playerName} got kicked", True)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def nomip(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None:
                ipList = playerName+"'s last known IP addresses:"
                for rs in self.server.cursor['loginlog'].find({'Username':playerName}).distinct("IP"):
                    ipList += "<br>" + rs
                self.client.sendServerMessage(ipList, True)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1)
        async def relation(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None:
                displayed = []
                List = "The player <BV>"+str(player.playerName)+"</BV> has the following relations:"
                rss = self.server.cursor['loginlog'].find({"IP":IPTools.EncodeIP(player.ipAddress)})
                for rs in rss:
                    if rs['Username'] in displayed: continue
                    
                    if self.server.players.get(str(rs['Username'])) == None:
                        d = self.server.cursor['loginlog'].find({"Username":rs['Username']})
                        ips = []
                        ips2 = []
                        for i in d:
                            if i['Ip'] in ips2: continue
                            ips.append(f"<font color='{IPTools.ColorIP(IPTools.DecodeIP(i['IP']))}'>{i['IP']}</font>")
                            ips2.append(i['Ip'])
                        toshow = ", ".join(ips)
                        List += f"<br>- <BV>{rs['Username']}</BV> : {toshow}"
                    else:
                        ip31 = self.server.players.get(str(rs['Username']))
                        List += f"<br>- <BV>{rs['Username']}</BV> : <font color='{IPTools.ColorIP(ip31.ipAddress)}'>{IPTools.EncodeIP(ip31.ipAddress)}</font> (current IP)"
                    displayed.append(rs['Username'])
                self.client.sendServerMessage(List, True)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], alias=['movementinfo'])
        async def sonar(self, playerName, end=''):
            player = self.server.players.get(playerName)
            if player:
                self.client.sendPacket(Identifiers.send.MiniBox_New, ByteArray().writeShort(200).writeUTF("Sonar "+playerName).writeUTF('\n'.join(self.server.playerMovement[playerName]) if playerName in self.server.playerMovement else "\n").toByteArray())
                self.server.playerMovement[playerName] = []
                if end == 'end':
                    if not int(time.time() - self.client.lastSonarTime) > 2: 
                        self.currentArgsCount = 1
                    self.client.lastSonarTime = time.time()
                if self.currentArgsCount == 1:
                    player.sendPacket(Identifiers.send.Start_Sonar, ByteArray().writeInt(player.playerCode).writeBoolean(False).writeShort(69).toByteArray())
                else:
                    player.sendPacket(Identifiers.send.End_Sonar, ByteArray().writeInt(player.playerCode).toByteArray())

        @self.command(level=['PrivMod', 'Mod', 'Admin', 'Owner'], args=1, alias=['debanip'])
        async def unbanip(self, ipAddress):
            decip = IPTools.DecodeIP(ipAddress)
            if decip in self.server.IPTempBanCache:
                self.server.IPTempBanCache.remove(decip)
                self.server.cursor['iptempban'].delete_one({'IP':decip})
                self.server.sendServerMessageAll(f"The player {self.client.playerName} unbanned the ip address {ipAddress}.", self.client, False)
                self.client.sendServerMessage(f"The IP address {ipAddress} got unbanned.", True)
            else:
                self.client.sendServerMessage("The given IP is invalid or not banned.", True)

# Admin Commands
        @self.command(level=['Admin'], args=1)
        async def baniperm(self, ipAddress):
            decip = IPTools.DecodeIP(ipAddress)
            if decip not in self.server.IPPermaBanCache:
                self.server.IPPermaBanCache.append(decip)
                self.server.cursor['ippermaban'].insert_one({'IP':decip})
                for player in self.server.players.copy().values():
                    if player.ipAddress == decip:
                        player.transport.close()
                self.server.sendServerMessageAll(f"The player {self.client.playerName} blacklisted the given IP address {ipAddress}.", self.client, False)
                self.client.sendServerMessage(f"The IP address {ipAddress} got blacklisted.", True)
            else:
                self.client.sendServerMessage("The given IP is already blacklisted.", True)

        @self.command(level=['Admin', 'Owner'], args=2)
        async def changepassword(self, playerName, newPassword):
            playerName = Other.parsePlayerName(playerName)
            player = self.server.players.get(playerName)
            if player != None or self.server.checkAlreadyExistingAccount(playerName):
                salt = b'\xf7\x1a\xa6\xde\x8f\x17v\xa8\x03\x9d2\xb8\xa1V\xb2\xa9>\xddC\x9d\xc5\xdd\xceV\xd3\xb7\xa4\x05J\r\x08\xb0'
                hashtext = base64.b64encode(hashlib.sha256(hashlib.sha256(newPassword.encode()).hexdigest().encode() + salt).digest()).decode()
                self.server.cursor['users'].update_one({'Username':playerName},{'$set':{'Password': hashtext}})
                if player != None:
                    player.transport.close()
                self.client.sendServerMessage("Done.", True)
                self.server.sendServerMessageAll(f"The player {self.client.playerName} changed the password of the player {playerName}.", self.client, False)
            else:
                self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['Admin', 'Owner'], args=1, alias=['deluser', 'removeuser'])
        async def deleteuser(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            if playerName == self.client.playerName:
                self.client.sendServerMessage("You tried :)", True)
            else:
                player = self.server.players.get(playerName)
                if player != None or self.server.checkAlreadyExistingAccount(playerName):
                    self.server.cursor['users'].delete_one({'Username':playerName})
                    self.client.sendServerMessage(f"Done.", True)
                    self.server.sendServerMessageAll(f"The player {self.client.playerName} deleted the account of the player {playerName}.", self.client, False)
                    if player != None:
                        player.transport.close()
                else:
                    self.client.sendServerMessage("The supplied argument isn't a valid nickname.", True)

        @self.command(level=['Admin', 'Owner'], args=1, alias=['commandlog'])
        async def clog(self, playerName):
            playerName = Other.parsePlayerName(playerName)
            r = self.server.cursor['commandlog'].find({'Username':playerName})
            message = "<p align='center'>Command Log of (<V>"+playerName+"</V>)\n</p>"
            for rs in r:
                d = str(datetime.datetime.fromtimestamp(float(int(rs['Time']))))
                message += f"<p align='left'><V>[ {playerName} ]</V> <BL>{d}</BL> (<font color='{IPTools.ColorIP(rs['IP'])}'>{rs['IP']}</font>) -> {rs['Command']}</p>"
            self.client.sendLogMessage(message)

        @self.command(level=['Admin', 'Owner'])
        async def removeipbancache(self):
            self.server.IPPermaBanCache = []
            self.server.IPTempBanCache = []
            self.client.sendServerMessage("Done!", True)

        @self.command(level=['Admin', 'Owner'])
        async def smc(self, *args):
            for player in self.server.players.copy().values():
                player.sendPacket(Identifiers.send.Send_Staff_Chat_Message, ByteArray().writeByte(6).writeUTF(self.client.playerLangue + " " + self.client.playerName).writeUTF(self.argsNotSplited).writeBoolean(False).writeBoolean(True).writeByte(0).toByteArray())

        @self.command(level=['Admin', 'Owner'], args=1, alias=['debaniperm'])
        async def unbaniperm(self, ipAddress):
            decip = IPTools.DecodeIP(ipAddress)
            if decip in self.server.IPPermaBanCache:
                self.server.IPPermaBanCache.remove(decip)
                self.server.cursor['ippermaban'].delete_one({'IP':decip})
                self.server.sendServerMessageAll(f"The player {self.client.playerName} unbanned the ip address {ipAddress}.", self.client, False)
                self.client.sendServerMessage(f"The IP address {ipAddress} got unbanned.", True)
            else:
                self.client.sendServerMessage("The given IP is invalid or not banned.", True)

        @self.command(level=['Admin', 'Owner'])
        async def updatesql(self):
            self.server.sendDatabaseUpdate()
            self.client.sendServerMessage("The database got updated.", True)
            self.server.sendServerMessageAll(f"The database was updated by {self.client.playerName}.", self.client, False)

# Owner Commands
        @self.command(level=['Owner'], args=2)
        async def connectsocket(self, ipAddress, port):
            self.client.sendPacket(Identifiers.send.Connect_To_Server, ByteArray().writeUTF(f"{ipAddress}:{port}").toByteArray())

        @self.command(level=['Owner'], args=2)
        async def execpacket(self, code_packet, bytearr):
            code_packet = int(code_packet)
            C = code_packet >> 8
            CC = code_packet - (C << 8)
            self.client.sendPacket([C, CC], ByteArray().writeBytes(bytearr).toByteArray())

        @self.command(level=['Owner'])
        async def gameconfig(self):
            with open("./include/Server/game.json", 'r') as File:
                Log = File.read()
                File.close()
            self.client.sendLogMessage(Log.replace("<", "&amp;lt;").replace("\x0D\x0A", "\x0A"))

        @self.command(level=['Owner'])
        async def logerrors(self):
            self.client.isServerErrorLogging = not self.client.isServerErrorLogging
            self.client.sendServerMessage("You can log the server errors in game." if self.client.isServerErrorLogging else "You can't log the server errors in game.", True)

        @self.command(level=['Owner'], debug=True)
        async def logpacket(self):
            self.client.isPacketLogging = not self.client.isPacketLogging
            self.client.sendServerMessage("You can log the server packets in game." if self.client.isPacketLogging else "You can't log the server packets in game.", True)

        @self.command(level=['Owner'], args=1, debug=True)
        async def logpacketman(self, option, packet_id):
            if option == "add":
                if packet_id not in self.client.loggedPackets:
                    self.client.loggedPackets.append(packet_id)
                self.client.sendServerMessage("Done.", True)
            elif option == "del":
                if packet_id in self.client.loggedPackets:
                    self.client.loggedPackets.remove(packet_id)
                self.client.sendServerMessage("Done.", True)
            elif option == "list":
                self.client.sendServerMessage(f"Dislogged Packets: {', '.join(self.client.loggedPackets)}", True)
            else:
                self.client.sendServerMessage("Available options are [add, list, del].", True)

        @self.command(level=['Owner'])
        async def luaadmin(self):
            self.client.isLuaAdmin = not self.client.isLuaAdmin
            self.client.sendServerMessage("You can run lua programming as administrator." if self.client.isLuaAdmin else "You can't run lua programming as administrator.", True)

        @self.command(level=['Owner'], args=2)
        async def openconfig(self, typ, name):
            if typ not in ["Server", "Client"]:
                self.client.sendServerMessage("Invalid. Use Server or Client please.", True)
            else:
                with open(f"./include/{typ}/{name}.json", 'r') as File:
                    Log = File.read()
                    File.close()
                self.client.sendLogMessage(Log.replace("<", "&amp;lt;").replace("\x0D\x0A", "\x0A"))

        @self.command(level=['Owner'], args=2, debug=True)
        async def packetcode(self, C, CC):
            C = int(C)
            CC = int(CC)
            if C > 255 or CC > 255:
                self.client.sendServerMessage(f"Invalid packet [{C},{CC}]", True)
            else:
                self.client.sendServerMessage(f"Packet Code: {((C << 8) | (CC & 0xFF))}", True)

        @self.command(level=['Owner'], alias=['restart'])
        async def reboot(self):
            self.server.sendServerRestart()
            self.client.sendServerMessage("Done!", True)

        @self.command(level=['Owner'])
        async def reload(self, playerName=''):
            try:
                if len(playerName) == 0:
                    self.server.sendReloadModules()
                else:
                    player = self.server.players.get(playerName)
                    if player != None:
                        player.reloadModules()
                self.client.sendServerMessage("Done!", True)
            except Exception as e:
                self.client.sendServerMessage(f"Failed reload all modules. Error: {e}", True)

        @self.command(level=['Owner'])
        async def serverconfig(self):
            with open("./include/Server/server.json", 'r') as File:
                Log = File.read()
                File.close()
            self.client.sendLogMessage(Log.replace("<", "&amp;lt;").replace("\x0D\x0A", "\x0A"))

        @self.command(level=['Owner'])
        async def shopconfig(self):
            with open("./include/Server/shop.json", 'r') as File:
                Log = File.read()
                File.close()
            self.client.sendLogMessage(Log.replace("<", "&amp;lt;").replace("\x0D\x0A", "\x0A"))
            
        @self.command(level=['Owner'], alias=['closeserver', 'poweroff'])
        async def shutdown(self):
            self.server.closeServer()
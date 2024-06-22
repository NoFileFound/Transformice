#coding: utf-8
import aiohttp
import asyncio
import json
import requests

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.IPTools import IPTools
from Utils.Other import Other

class ChannelCommands:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        self.botName = "Delichoc"
        self.currentArgsCount = 0
        self.argsNotSplited = ""
        self.commandName = ""
        self.commands = {}
        self.__init_2()
        
        self.CHANNEL_MODERATEUR_INT = 4
        
    def command(self, func=None, args=0, channelid=0, alias=[], reqrs=[]):
        if not func:
            reqrs = []
            if args > 0: reqrs.append(['args',args])
            if channelid > 0: reqrs.append(['channelid', channelid])
            return lambda x: self.command(x, args, channelid, alias, reqrs)
        else:
            for i in alias + [func.__name__]: 
                self.commands[i] = [reqrs, func]
        
    def requireArgs(self, arguments):
        if self.currentArgsCount < arguments:
            self.client.sendServerMessage("You need more arguments to use this command.", True)
            return False
        return self.currentArgsCount == arguments

    def requireChannel(self, channelID):
        if channelID in [1, 6]:
            return False
    
        return self.channelID == channelID

    def sendChannelMessage(self, _id, message, isTab=False, isTranslation=False, *args):
        packet = ByteArray().writeByte(_id).writeUTF(self.botName).writeUTF(message).writeBoolean(isTab).writeBoolean(isTranslation).writeByte(len(args))
        for arg in args:
            packet.writeUTF(arg)
        self.server.sendStaffChannelMessage(_id, self.client.playerLangue, Identifiers.send.Send_Staff_Chat_Message, packet.toByteArray())

    async def parseCommand(self, command, channel):
        values = command.split(" ")
        command = values[0].lower()
        args = values[1:]
        self.argsNotSplited = " ".join(args)
        self.currentArgsCount = len(args)
        self.commandName = command
        self.channelID = channel
        if command in self.commands:
            for i in self.commands[command][0]:
                if i[0] == "args":
                    if not self.requireArgs(i[1]): return
                elif i[0] == "channelid":
                    if not self.requireChannel(i[1]): return
            await self.commands[command][1](self, *args)
    
    def __init_2(self):
        @self.command(channelid=4, args=1)
        async def addcommu(self, community):
            community = community.upper()
            if len(community) == 2:
                if community in self.client.modoCommunities:
                    self.client.modoCommunities.remove(community)
                else:
                    self.client.modoCommunities.append(community)
                    self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{self.client.playerName} you can now see the reports from community: {community}.", False, False)
    
        @self.command(channelid=4, args=1)
        async def avatar(self, playerName):
            player = self.server.players.get(Other.parsePlayerName(playerName))
            if player:
                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"Player {player.playerName}'s avatar is http://avatars.atelier801.com/{player.playerID}.png", False, False)

        @self.command(channelid=4, args=1, alias=['geoip'])
        async def geo(self, ipAddress):
            ipAddress = IPTools.DecodeIP(ipAddress)
            url = f"https://ipinfo.io/{ipAddress}/json"
            response = requests.get(url)
            if response.status_code == 200:
                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, str(response.json()), False, False)
            else:
                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{self.client.playerName}, GeoIP API unavailable.", False, False)
            
        @self.command(channelid=4)
        async def lspwet(self):
            self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{self.client.playerName} your communities are {', '.join(self.client.modoCommunities)}.", False, False)

        @self.command(channelid=4, args=1)
        async def modinfo(self, playerName):
            url = f"https://staff.atelier801.com/api/modinfo/{self.client.playerName}"
            headers = {
                "Authorization": "", # 💀💀💀
                "Content-Type": "application/json",
                "From": "Transformice"
            }
            async with aiohttp.ClientSession() as session:
                try:
                    async with session.get(url, headers=headers) as response:
                        if response.status == 200:
                            mod_info = await response.json()
                            text7 = f"<BV>Mod Info for <N>{name}<BL>\n• "
                            for community in mod_info['community']:
                                text7 += f"[{community.upper()}] "
                            text7 += get_name_string(mod_info['main'], mod_info['role'])

                            if mod_info.get('alts'):
                                text7 += "\n<BV>Alts: "
                                for mod_alt in mod_info['alts']:
                                    text7 += f"\n<BL> {get_name_string(mod_alt['nick'], mod_alt['role'])}"

                            self.client.sendServerMessage(text7, True)

                        else:
                            error_response = await response.text()
                            error_data = json.loads(error_response)
                            error_code = error_data.get('code')
                            if error_code in ["10014", "10018", "10019"]:
                                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, "Error: Mod info access denied", False, False)
                            elif error_code == "10020":
                                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, "Error: Search term is not valid", False, False)
                            elif error_code == "10017":
                                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, "Error: Staff member not found", False, False)
                            else:
                                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"Error: API unavailable ({error_data})", False, False)

                except aiohttp.ClientError as e:
                    self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f" Error: {str(e)}", False, False)

        @self.command(channelid=4, args=1, alias=['p'])
        async def profil(self, playerName):
            self.client.sendProfile(Other.parsePlayerName(playerName))
            
        @self.command(channelid=4, args=1)
        async def prom(self, playerName):
            player = self.server.players.get(Other.parsePlayerName(playerName))
            if player:
                player.isPrivMod = not player.isPrivMod
                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{playerName} has been promoted to private mod by {self.client.playerName}." if player.isPrivMod else f"{playerName} has been depromoted by {self.client.playerName}", False, False)

        @self.command(channelid=4, args=1)
        async def pwet(self, community):
            if len(community) == 2 or community == "ALL":
                self.client.modoPwetLangue = community.upper()
                self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{self.client.playerName} your community is {community}", False, False)

        @self.command(channelid=4, alias=['translate', 'gametrans'])
        async def trans(self, *args):
            self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, self.argsNotSplited, False, True)
                
        @self.command(channelid=4, args=1)
        async def watchcommu(self, community):
            community = community.upper()
            if len(community) == 2:
                if community in self.client.modoCommunitiesNotification:
                    self.client.modoCommunitiesNotification.remove(community)
                else:
                    self.client.modoCommunitiesNotification.append(community)
                    self.sendChannelMessage(self.CHANNEL_MODERATEUR_INT, f"{self.client.playerName} you can now watch every player in community {community}.", False, False)



        @self.command(channelid=4, args=1)
        async def stafflog(self, playerName):
            # [Delichoc] [All] [CENSORED]'s logs: Couldn't get shared link: Unknown Error?raw=1
            # -Delichoc- Please wait..
            # [Delichoc] [All] [CENSORED]'s logs: https://www.dropbox.com/s/XXXXXXXXXXXXXX.txt
            pass
            
        @self.command(channelid=4, args=1, alias=['flog*'])
        async def flog(self, playerName):
            pass
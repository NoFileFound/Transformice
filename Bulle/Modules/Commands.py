#coding: utf-8
import datetime

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

class Commands:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        self.currentArgsCount = 0
        self.argsNotSplited = ""
        self.commandName = ""
        self.commands = {}
        self.__init_2()
                
    def command(self, func=None, args=0, level=[], roomOwner=False, alias=[], reqrs=[]):
        if not func:
            reqrs = []
            if args > 0: reqrs.append(['args',args])
            if len(level) > 0: reqrs.append(['level', level])
            if roomOwner: reqrs.append(['roomOwner', roomOwner])
            return lambda x: self.command(x, args, level, roomOwner, alias, reqrs)
        else:
            for i in alias + [func.__name__]: 
                self.commands[i] = [reqrs, func]
        
    def requireArgs(self, arguments):
        if self.currentArgsCount < arguments:
            self.client.sendServerMessage("You need more arguments to use this command.", True)
            return False
        return self.currentArgsCount == arguments
        
    def requireLevel(self, level):
        return self.client.checkStaffPermission(level) != False
        
    def requireRoomOwner(self):
        return self.client.room.roomCreator == self.client.playerName
        
    async def parseCommand(self, command):
        values = command.split(" ")
        command = values[0].lower()
        args = values[1:]
        self.argsNotSplited = " ".join(args)
        self.currentArgsCount = len(args)
        self.commandName = command
        if command in self.commands:
            for i in self.commands[command][0]:
                if i[0] == "args":
                    if not self.requireArgs(i[1]): return
                elif i[0] == 'level':
                    if not self.requireLevel(i[1]): return
                elif i[0] == 'roomOwner':
                    if not self.requireRoomOwner(): return
            await self.commands[command][1](self, *args)
        else:
            self.client.sendServerMessage(f"[BULLE] Invalid command <J>{command}</J>", True)
            
    def __init_2(self):
# Guest / Souris Commands
        @self.command()
        async def mort(self):
            if not self.client.isDead:
                self.client.isDead = True
                if self.client.room.isAutoScore: 
                    self.client.playerScore += 1
                self.client.sendPlayerDied()
                await self.client.room.checkChangeMap()
                
        @self.command(roomOwner=True)
        async def mulodrome(self):
             if not self.client.room.isMulodrome:
                for player in self.client.room.players.copy().values():
                    player.sendPacket(Identifiers.send.Mulodrome_Start, int(player.playerName == self.client.playerName))
                    
        @self.command()
        async def resettotem(self):
            if self.client.room.isTotemEditor:
                self.client.tempTotem = [0 , ""]
                self.client.resetTotem = True
                self.client.isDead = True
                self.client.sendPlayerDied()
                await self.client.room.checkChangeMap()

        @self.command()
        async def sauvertotem(self):
            if self.client.room.isTotemEditor:
                self.client.totemInfo[0] = self.client.tempTotem[0]
                self.client.totemInfo[1] = self.client.tempTotem[1]
                self.client.sendPlayerDied()
                self.client.roomName = self.server.getRecommendedRoom(self.client.playerLangue)
                await self.client.enterRoom()

        @self.command()
        async def roominfo(self):
            self.client.sendServerMessage(f"BulleID: bulle{self.server.bulleInfo['id']} | Total players: {len(self.server.bulle_players)} |  Total rooms: {len(self.server.bulle_rooms)}.", True)
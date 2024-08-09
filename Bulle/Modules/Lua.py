#coding: utf-8
import asyncio
import json
import math
import os
import random
import re
import threading
import time
from lupa import LuaRuntime

# Modules
from Modules.ByteArray import ByteArray
from Modules.Identifiers import Identifiers

# Utils
from Utils.Time import Time
        
class Lua:
    def __init__(self, room, server):
        self.room = room
        self.server = server
    
        # Integer
        self.LastRoomObjectID = 2000
        self.lastloopid = 1
        self.maxData = 128000
    
        # Boolean
        self.running = True
        self.events = True
            
        # String
        self.name = ""
        self.script = ""
        self.customxml = ""
        
        # List
        self.HiddenCommands = []
        self.imagesadd = []
        self.jointsadd = []
        self.textsadd = []
        
        # Dict
        self.RoomObjects = {}
        self.loops = {}
        
        # NoneType
        self.owner = None
        self.runtime = None
        self._G = None
        
    def LoadPkgs(self):
        for file in os.listdir('./Include/lua/packages'):
            name = file.replace('.lua','')
            with open(f'./Include/lua/packages/{file}') as f:
                data = f.read()
                if name != "_G" and not name in self._G:
                    self._G[name] = self.runtime.table()
                self.runtime.execute(data)
        
    def SetupRuntimeGlobals(self):
        if self.runtime is None: return
        
        if self._G is None:
            self._G = self.runtime.globals()
            self.LoadPkgs()
            
        # debug.
        self._G['debug']['disableEventLog'] = lambda x: None
        self._G['debug']['disableTimerLog'] = lambda x: None
        self._G['debug']['getCurrentLuaThreadName'] = self.getCurrentLuaThreadName
        
        # -
        self._G['print'] = self.sendLuaMessage
        
        # tfm.get.misc.
        self._G['tfm']['get']['misc']['apiVersion'] = "0.28"
        
    def RefreshTFMGet(self):
        pass
        
    # Run
    def RunCode(self, code=""):
        self.script = code
        if self.runtime == None:
            self.runtime = LuaRuntime(unpack_returned_tuples=True)
            self.SetupRuntimeGlobals()
            
        if self.owner != None:
            for line in code.split('\r'):
                for while_stmt in re.findall('while[\s+(].*[\s+)]do', line):
                    intent = line.split('while')[0]
                    func = while_stmt.replace('while ','').replace(' do','')
                    id = random.randint(0, 10**10)
                    self.runtime.execute("""
                     debug.__while__%s = {
                         time = 0,
                         start = function(self)
                            self.time = os.time() + 0.4
                         end,
                         callback = function(self, ...)
                             if (os.time() - self.time > 0) then
                                 error("Lua destroyed : Runtime too long!")
                             end 
                            
                             return (...)
                         end
                     }""" % (id))
                    code = code.replace(while_stmt, f"debug.__while__{id}:start()\r{intent}while(debug.__while__{id}:callback({func})) do".replace('( ','(').replace(' )',')'))
        try:
            ts = time.time()
            self.runtime.execute(code)
            self.EventLoop()
        except Exception as error:
            self.script = "" 
            if self.owner != None:
                self.owner.sendLuaMessage(f"[<V>{self.owner.roomName}</V>] Init Error : {error}")
                
        te = time.time() - ts
        if self.owner != None:
            self.owner.sendLuaMessage(f"[<V>{self.owner.roomName}</V>] [{self.owner.playerName}] Lua script loaded in {int(te*1000)} ms. (4000 max)")
        
    # Invoke event
    def emit(self, eventName="", args=()):
        if self.runtime is None or not self.events: return
            
        self.RefreshTFMGet()
        if eventName == "NewGame":
            self.RoomObjects = {}

        if type(args) == tuple:
            args_strPack = ""

            for x in args:
                args_strPack += (str(x) if type(x) != str and type(x) != bool else '"%s"' % (x) if type(x) != bool else ("true" if x else "false")) + ","
        else:
            args_strPack = (str(args) if type(args) != str and type(args) != bool else '"%s"' % (args) if type(args) != bool else ("true" if args else "false")) + ","

        try:
            self.runtime.execute("if(event%s)then event%s(%s) end" % (str(eventName), str(eventName), args_strPack[:-1]))
        except Exception as error:
            if not self.owner is None:
                self.owner.sendLuaMessage("[<V>%s.lua</V>][<N>%s</N>] <BL>%s</BL>" % (self.owner.playerName, str(time.strftime("%H:%M:%S")), str(error)))
        
    def EventLoop(self):
        if not self.runtime is None:
            self.RefreshTFMGet()
            elapsed = (Time.getTime() - self.room.gameStartTime) * 1000
            remaining = ((self.room.roundTime + self.room.addTime) - (Time.getTime() - self.room.gameStartTime)) * 1000
            self.emit('Loop', (elapsed if elapsed >= 0 else 0, remaining if remaining >= 0 else 0))

            self.server.loop.call_later(0.5, self.EventLoop)
        
    # Helper Functions
    def print_lua_table(self, table):
        msg = "{\n  {\n"
        for key, value in table.items():
            if "__while__" in str(key):
                continue
            elif self._G.type(value) == "table":
                val = 'table: '+self.parse_val(value)
                msg += f"    {key}={val}\n"
            elif "method" in str(value) or "function" in str(value):
                val = 'function: '+self.parse_val(value)
                msg += f"    {key}={val}\n"
            else:
                msg += f"    {key}={str(value)}\n"

        return msg+ "  }\n}"
        
    # Functions
    def getCurrentLuaThreadName(self):
        return 'Lua '+str(self.runtime).split(' ')[-1:][0].split('>')[0]
    
    def sendLuaMessage(self, *args): 
        message = ""
        for x in args:
            temp = (self._G.tostring(x) if self._G.type(x) != "userdata" else "userdata") + ("  " if len(args) > 1 else "")
            if "table" in temp:
                message += self.print_lua_table(x)
            else:
                if '.0' in temp:
                    temp = str(int(float(temp)))
                message += temp
        if message and self.owner != None:
            self.owner.sendLuaMessage(message)
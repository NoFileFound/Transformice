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
                
    def FixUnicodeError(self, text=u""):
        if isinstance(text, bytes):
            text = text.decode()
        return text
        
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
        
        # system
        self._G['system'] = self.runtime.table()
        self._G['system']['exit'] = self.stopModule

        # tfm
        self._G['tfm']['exec']['addBonus'] = self.addBonus
        self._G['tfm']['exec']['addConjuration'] = self.addConjuration
        self._G['tfm']['exec']['addImage'] = self.addImage
        self._G['tfm']['exec']['addJoint'] = self.addJoint
        self._G['tfm']['exec']['addShamanObject'] = self.addShamanObject
        self._G['tfm']['exec']['attachBalloon'] = self.attachBalloon
        #self._G['tfm']['exec']['bindKeyboard'] = self.room.bindKeyBoard
        self._G['tfm']['exec']['changePlayerSize'] = self.changePlayerSize
        self._G['tfm']['exec']['chatMessage'] = self.chatMessage
        self._G['tfm']['exec']['disableAutoNewGame'] = self.disableAutoNewGame
        self._G['tfm']['exec']['disableAutoScore'] = self.disableAutoScore
        self._G['tfm']['exec']['disableAutoShaman'] = self.disableAutoShaman
        self._G['tfm']['exec']['disableAutoTimeLeft'] = self.disableAutoTimeLeft
        self._G['tfm']['exec']['disableDebugCommand'] = self.disableDebugCommand
        self._G['tfm']['exec']['disableMinimalistMode'] = self.disableMinimalistMode
        self._G['tfm']['exec']['disableMortCommand'] = self.disableMortCommand
        self._G['tfm']['exec']['disableWatchCommand'] = self.disableWatchCommand
        self._G['tfm']['exec']['disablePhysicalConsumables'] = self.disablePhysicalConsumables
        self._G['tfm']['exec']['displayParticle'] = self.displayParticle
        self._G['tfm']['exec']['explosion'] = self.explosion
        self._G['tfm']['exec']['freezePlayer'] = self.freezePlayer
        self._G['tfm']['exec']['getPlayerSync'] = self.getPlayerSync
        self._G['tfm']['exec']['giveCheese'] = self.giveCheese
        self._G['tfm']['exec']['giveConsumables'] = self.giveConsumables
        self._G['tfm']['exec']['giveMeep'] = self.giveMeep
        self._G['tfm']['exec']['giveTransformations'] = self.giveTransformations
        self._G['tfm']['exec']['killPlayer'] = self.killPlayer
        self._G['tfm']['exec']['linkMice'] = self.linkMice
        self._G['tfm']['exec']['lowerSyncDelay'] = self.lowerSyncDelay
        self._G['tfm']['exec']['moveCheese'] = self.moveCheese
        self._G['tfm']['exec']['moveObject'] = self.moveObject
        self._G['tfm']['exec']['movePhysicObject'] = self.moveObject
        self._G['tfm']['exec']['newGame'] = self.newGame
        self._G['tfm']['exec']['playEmote'] = self.playEmote
        self._G['tfm']['exec']['playerVictory'] = self.playerVictory
        self._G['tfm']['exec']['removeBonus'] = self.removeBonus
        self._G['tfm']['exec']['removeCheese'] = self.removeCheese
        self._G['tfm']['exec']['removeImage'] = self.removeImage
        self._G['tfm']['exec']['removeJoint'] = self.removeJoint
        self._G['tfm']['exec']['removeObject'] = self.room.removeObject
        self._G['tfm']['exec']['removePhysicObject'] = self.RemovePhysicObject
        self._G['tfm']['exec']['respawnPlayer'] = self.respawnPlayer
        self._G['tfm']['exec']['setAieMode'] = self.setAieMode
        self._G['tfm']['exec']['setAutoMapFlipMode'] = self.setAutoMapFlipMode
        self._G['tfm']['exec']['setGameTime'] = self.setGameTime
        self._G['tfm']['exec']['setPlayerGravityScale'] = self.setPlayerGravityScale
        self._G['tfm']['exec']['setPlayerNightMode'] = self.setPlayerNightMode
        self._G['tfm']['exec']['setNameColor'] = self.room.setNameColor
        self._G['tfm']['exec']['setPlayerScore'] = self.setPlayerScore
        self._G['tfm']['exec']['setPlayerSync'] = self.setPlayerSync
        self._G['tfm']['exec']['setShaman'] = self.setShaman
        #self._G['tfm']['exec']['setUIMapName'] = self.setMapName
        #self._G['tfm']['exec']['setUIShamanName'] = self.setShamanName
        self._G['tfm']['exec']['setWorldGravity'] = self.setWorldGravity
        
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
            
    def setWorldGravity(self, x=0, y=10):
        if y == 0:
            self.room.sendAll(Identifiers.old.send.Gravity, [x, 8])
        else:
            self.room.sendAll(Identifiers.old.send.Gravity, [x, y])
            
    def setShaman(self, target, makeAShaman=True):
        player = self.room.players.get(Utils.parsePlayerName(target))
        if player != None:
            player.isShaman = True
            self.room.sendAll(Identifiers.send.New_Shaman, ByteArray().writeInt(player.playerCode).writeByte(player.shamanType).writeShort(player.shamanLevel).writeShort(player.Skills.getShamanBadge()).toByteArray())

    def disableAutoNewGame(self, status=True):
        self.room.isFixedMap = status
        
    def disableAutoScore(self, status=True):
        self.room.noAutoScore = status

    def disableAutoShaman(self, status=True):
        self.room.noShaman = status

    def disableAutoTimeLeft(self, status=True):
        self.room.never20secTimer = status
    
    def disableDebugCommand(self, status=True):
        self.room.disableDebugCommand = status
        self.room.sendAll(Identifiers.send.Lua_Disable, ByteArray().writeBoolean(self.room.disableWatchCommand).writeBoolean(self.room.disableDebugCommand).writeBoolean(self.room.disableMinimalistMode).toByteArray())
    
    def disableMinimalistMode(self, status=True):
        self.room.disableMinimalistMode = status
        self.room.sendAll(Identifiers.send.Lua_Disable, ByteArray().writeBoolean(self.room.disableWatchCommand).writeBoolean(self.room.disableDebugCommand).writeBoolean(self.room.disableMinimalistMode).toByteArray())
    
    def disableMortCommand(self, status=True):
        self.room.disableMortCommand = status
        
    def disableWatchCommand(self, status=True):
        self.room.disableWatchCommand = status
        self.room.sendAll(Identifiers.send.Lua_Disable, ByteArray().writeBoolean(self.room.disableWatchCommand).writeBoolean(self.room.disableDebugCommand).writeBoolean(self.room.disableMinimalistMode).toByteArray())
    
    def disablePhysicalConsumables(self, status=True):
        self.room.roomDetails[2] = status
    
    def displayParticle(self, particleType, xPosition, yPosition, xSpeed=0, ySpeed=0, xAcceleration=0, yAcceleration=0, targetPlayer=""):
        packet = ByteArray()
        packet.writeByte(particleType)
        packet.writeShort(xPosition)
        packet.writeShort(yPosition)
        packet.writeShort(xSpeed)
        packet.writeShort(ySpeed)
        packet.writeShort(xAcceleration)
        packet.writeShort(yAcceleration)
        if targetPlayer == "":
            self.room.sendAll(Identifiers.send.Display_Particle, packet.toByteArray())
        else:
            player = self.server.players.get(Utils.parsePlayerName(targetPlayer))
            if player != None:
                player.sendPacket(Identifiers.send.Display_Particle, packet.toByteArray())

    def explosion(self, x, y, power, distance, miceOnly=False):
        for player in self.server.players.values():
            player.sendPacket([5, 17], [int(x), int(y), int(power), int(distance), bool(miceOnly)])

    def freezePlayer(self, playerName, freeze=True, displayIce=True):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player:
            player.sendPacket(Identifiers.send.Stop_Moving, ByteArray().writeBoolean(freeze).writeBoolean(displayIce).toByteArray())

    def getPlayerSync(self):
        self.chatMessage("Current Sync: "+self.room.currentSyncName, self.owner.playerName)
        return self.room.currentSyncName

    def giveCheese(self, target):
        player = self.room.players.get(Utils.parsePlayerName(target))
        if player != None and not player.isDead and not player.hasCheese:
            asyncio.ensure_future(player.sendGiveCheese(0))

    def giveConsumables(self, playerName, consumableId, amount=1):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player:
            player.giveConsumable(consumableId, amount)

    def giveMeep(self, target, status=True):
        player = self.room.players.get(Utils.parsePlayerName(target))
        if player != None and not player.isDead:
            player.sendPacket(Identifiers.send.Can_Meep, status)

    def giveTransformations(self, target, status=True):
        player = self.room.players.get(Utils.parsePlayerName(target))
        if player != None:
            player.sendPacket(Identifiers.send.Can_Transformation, int(status))
            player.hasLuaTransformations = status

    def killPlayer(self, target):
        player = self.room.players.get(Utils.parsePlayerName(target))
        if not player.isDead:
            player.isDead = True
            if player.room.noAutoScore:
                player.playerScore += 1
            player.sendPlayerDied()
            asyncio.ensure_future(player.room.checkChangeMap())

    def linkMice(self, Name, Target, status=True):
        player = self.server.players.get(Utils.parsePlayerName(Name))
        player1 = self.server.players.get(Utils.parsePlayerName(Target))
        if player != None and player1 != None:
            self.room.sendAll(Identifiers.send.Soulmate, ByteArray().writeBoolean(status).writeInt(player.playerCode).writeInt(player1.playerCode if status else -1).toByteArray())

    def lowerSyncDelay(self, playerName):
        player = self.server.players.get(Utils.parsePlayerName(playerName))
        if player != None:
            player.sendPacket(Identifiers.send.Lower_Sync_Delay, [player.playerName])

    def moveCheese(self, x, y):
        self.room.sendAll(Identifiers.old.send.Move_Cheese, [x, y])
            
    def moveObject(self, id, xy, vy, dat=False, x=0, y=0, r=False, i=0, b=False):
        self.RoomObjects[id]['velX'] = x
        self.RoomObjects[id]['velY'] = y
        self.RoomObjects[id]['posX'] = xy
        self.RoomObjects[id]['posY'] = vy
        self.RoomObjects[id]['angle'] = i
        self.RefreshTFMGet()
        packet = ByteArray()
        packet.writeInt(id)
        packet.writeShort(xy)
        packet.writeShort(vy)
        packet.writeBoolean(dat)
        packet.writeShort(x)
        packet.writeShort(y)
        packet.writeBoolean(r)
        packet.writeShort(i)
        packet.writeBoolean(b)
        self.room.sendAll(Identifiers.send.Move_Object, packet.toByteArray())

    def newGame(self, mapCode="", mirroredMap=False):
        self.room.forceNextMap = str(mapCode)
        self.room.mapInverted = mirroredMap
        self.room.canChangeMap = True
        asyncio.ensure_future(self.room.mapChange())

    def playEmote(self, playerName, emoteId, emoteArg=""):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player:
            asyncio.ensure_future(player.sendPlayerEmote(emoteId, emoteArg, False, True))

    def playerVictory(self, target):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player != None and not player.isDead:
            if not player.hasCheese:
                self.giveCheese(playerName)
            asyncio.ensure_future(player.playerWin(1, 0))

    def removeBonus(self, id=0, targetPlayer=""):
        p = ByteArray().writeInt(id)
        if targetPlayer == "":
            self.room.sendAll([5, 15], p.toByteArray())
        else:
            player = self.room.players.get(Utils.parsePlayerName(targetPlayer))
            if player != None:
                player.sendPacket([5, 15], p.toByteArray())
              
    def removeCheese(self, target):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player != None and not player.isDead and player.hasCheese:
            player.hasCheese = False
            player.sendRemoveCheese()
              
    def removeImage(self, imageId=0, targetPlayer="", visible=False, fadeIn=0, fadeOut=0):
        if not isinstance(targetPlayer, str): targetPlayer = ""
        if targetPlayer == "":
            self.room.sendAll(Identifiers.send.Remove_Image, ByteArray().writeInt(imageId).writeBoolean(visible).toByteArray())
        else:
            player = self.room.players.get(Utils.parsePlayerName(targetPlayer))
            if player != None:
                player.sendPacket(Identifiers.send.Remove_Image, ByteArray().writeInt(imageId).writeBoolean(visible).toByteArray())
        if imageId in self.imagesadd: self.imagesadd.remove(imageId)
        
    def removeJoint(self, id):
        if int(id) in self.jointsadd:
            self.jointsadd.remove(int(id))
        self.room.sendAll(Identifiers.send.Remove_Joint, [id])
        
    def RemovePhysicObject(self, id):
        self.room.sendAll(Identifiers.send.Remove_Physic_Object, [id])

    def respawnPlayer(self, playerName):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        print(playerName)
        if player != None:
            self.room.respawnSpecific(playerName)

    def setAieMode(self, enabled=True, sensibility=1, targetPlayer=""):
        if targetPlayer == "":
            self.room.sendAll(Identifiers.send.setAIEMode, ByteArray().writeBoolean(enabled).writeEncoded(sensibility * 1000).toByteArray())
        else:
            player = self.room.players.get(Utils.parsePlayerName(targetPlayer))
            if player != None:
                player.sendPacket(Identifiers.send.setAIEMode, ByteArray().writeBoolean(enabled).writeEncoded(sensibility * 1000).toByteArray())

    def setAutoMapFlipMode(self, status=False):
        self.room.autoMapFlipMode = status

    def setGameTime(self, time=0, add=False):
        if str(time).isdigit():
            if add:
                iTime = self.room.roundTime + (self.room.gameStartTime - Utils.getTime()) + self.room.addTime + int(time)
            else:
                iTime = int(time)
            iTime = 5 if iTime < 5 else (32767 if iTime > 32767 else iTime)
            for player in self.room.players.values():
                player.sendRoundTime(iTime)

            self.room.roundTime = iTime
            self.room.changeMapTimers(iTime)

    def setPlayerGravityScale(self, playerName, scale=1, windScale=1):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player != None:
            self.room.sendAll(Identifiers.send.PlayerScale, ByteArray().writeEncoded(player.playerCode).writeEncoded(scale * 1000).writeEncoded(windScale * 1000).toByteArray())

    def setPlayerNightMode(self, enable=True, playerName=""):
        if playerName == "":
            self.room.sendAll(Identifiers.send.NightMode, ByteArray().writeBoolean(enable).toByteArray())
        else:
            player = self.room.players.get(Utils.parsePlayerName(playerName))
            if player != None:
                player.sendPacket(Identifiers.send.NightMode, ByteArray().writeBoolean(enable).toByteArray())

    def setPlayerScore(self, playerName, score, amount=False):
        if amount is None:
            amount = False
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player:
            if amount:
                player.playerScore += score
            else:
                player.playerScore = score
            self.room.sendAll(Identifiers.send.Set_Player_Score, ByteArray().writeInt(player.playerCode).writeShort(player.playerScore).toByteArray())

    def setPlayerSync(self, playerName):
        player = self.room.players.get(Utils.parsePlayerName(playerName))
        if player != None:
            player.isSync = True
            self.room.currentSyncCode = player.playerCode
            self.room.currentSyncName = player.playerName
            if self.owner != None:
                self.chatMessage("New Sync: "+str(player.playerName), self.owner.playerName)
                    
    def addTextArea(self, id, text, targetPlayer="", x=50, y=50, width=0, height=0, backgroundColor=0x324650, borderColor=0, backgroundAlpha=1, fixedPos=False):
        self.textsadd.append(int(id))
        self.room.addTextArea(id, text, targetPlayer, x, y, width, height, backgroundColor, borderColor, backgroundAlpha, fixedPos)
    
    def removeTextArea(self, id, targetPlayer=""):
        if int(id) in self.textsadd:
            self.textsadd.remove(id)
        self.room.addTextArea(id, targetPlayer)
                
    def addBonus(self, type=1, x=0, y=0, id=0, angle=0, visible=True, targetPlayer=""):
        p = ByteArray()
        p.writeShort(x)
        p.writeShort(y)
        p.writeByte(type)
        p.writeShort(angle)
        p.writeInt(id)
        p.writeBoolean(visible)
        if targetPlayer == "" or not targetPlayer:
            self.room.sendAll(Identifiers.send.Skill_Object, p.toByteArray())
        else:
            player = self.room.players.get(targetPlayer)
            if player != None:
                player.sendPacket(Identifiers.send.Skill_Object, p.toByteArray())

    def addConjuration(self, x, y, duration=10000):
        self.room.sendAll(Identifiers.old.send.Add_Conjuration, [x, y, duration])
        self.server.loop.call_later(duration / 1000, self.room.sendAll, Identifiers.old.send.Conjuration_Destroy, [int(x), int(y)])

    def addImage(self, imageName = "", target = "", xPosition = 50, yPosition = 50, targetPlayer = "", scaleX = 1, scaleY = 1,angle = 0,alpha = 1,AnchorX=0,AnchorY=0):
        if imageName is None:
            imageName = ""
        if target is None:
            target = ""
        if xPosition is None:
            xPosition == 50
        if yPosition is None:
            yPosition = 50
        if targetPlayer is None:
            targetPlayer = ""
        if scaleX is None:
            scaleX = 1
        if scaleY is None:
            scaleY = 1
        if angle is None:
            angle = 0
        if alpha is None:
            alpha = 1
        if AnchorX is None:
            AnchorX = 0
        if AnchorY is None:
            AnchorY = 0
        packet = ByteArray()
        self.room.lastImageID += 1
        packet.writeInt(self.room.lastImageID)
        self.imagesadd.append(self.room.lastImageID)
        packet.writeUTF(imageName)
        packet.writeByte(1 if target.startswith("#") else 2 if target.startswith("$") else 3 if target.startswith("%") else 4 if target.startswith("?") else 5 if target.startswith("_") else 6 if target.startswith("!") else 7 if target.startswith("&") else 8 if target.startswith(":") else 9 if target.startswith("+") else 0)
        while not target[:1].isdigit(): target = target[1:]
        if '.0' in target: target = target.split('.')[0]
        packet.writeInt(int(target) if target.isdigit() else self.server.getPlayerCode(Utils.parsePlayerName(target)))
        packet.writeShort(xPosition)
        packet.writeShort(yPosition)
        packet.writeFloat(scaleX)
        packet.writeFloat(scaleY)
        packet.writeFloat(angle)
        packet.writeFloat(alpha)
        packet.writeFloat(AnchorX)
        packet.writeFloat(AnchorY)
        packet.writeByte(0)
        if targetPlayer == "":
            self.room.sendAll(Identifiers.send.Add_Image, packet.toByteArray())
        else:
            player = self.room.players.get(Utils.parsePlayerName(targetPlayer))
            if player != None:
                player.sendPacket(Identifiers.send.Add_Image, packet.toByteArray())
        return self.room.lastImageID
        
    def addJoint(self, id=0, ground1=0, ground2=0, jointDefinition={}):
        self.jointsadd.append(int(id))
        p = ByteArray()
        p.writeShort(id)
        p.writeShort(ground1)
        p.writeShort(ground2)
        jointDefinition=dict(jointDefinition)
        
        p.writeByte(0 if jointDefinition.get('type',0) > 3 else jointDefinition.get('type',0))
        for name in ['point1','point2','point3','point4']:
            p.writeBoolean(name in jointDefinition)
            try:
                p.writeShort(int(jointDefinition[name].replace(' ','').split(',')[0]))
                p.writeShort(int(jointDefinition[name].replace(' ','').split(',')[1]))
            except:
                p.writeInt(0)
        p.writeShort(1032 if jointDefinition.get('frequency',0)*100 > 1032 else jointDefinition.get('frequency',0)*100)
        p.writeShort(1032 if jointDefinition.get('damping',0)*100 > 1032 else jointDefinition.get('damping',0)*100)
        p.writeBoolean('line' in jointDefinition or 'color' in jointDefinition or 'alpha' in jointDefinition or 'foreground' in jointDefinition)
        p.writeShort(1032 if jointDefinition.get('line',0)*100 > 1032 else jointDefinition.get('line',0)*100)
        p.writeInt(int(jointDefinition.get('color',40349)))
        p.writeShort(jointDefinition.get('alpha',1) * 100)
        p.writeBoolean(jointDefinition.get('foreground',False))
        try:
            p.writeShort(int(jointDefinition['axis'].replace(' ','').split(',')[0]))
            p.writeShort(int(jointDefinition['axis'].replace(' ','').split(',')[1]))
        except:
            p.writeInt(0)
        p.writeBoolean('angle' in jointDefinition)
        p.writeShort(jointDefinition.get('angle',0))
        for name in ['limit1','limit2','forceMotor','speedMotor']:
            p.writeBoolean(name in jointDefinition)
            p.writeShort(jointDefinition.get(name,0) * 100)
        p.writeShort(jointDefinition.get('ratio',1) * 100)
        self.room.sendAll(Identifiers.send.Add_Joint, p.toByteArray())

    def addShamanObject(self, type=0, x=0, y=0, angle=0, vx=0, vy=0, ghost=False, options={}):
        self.LastRoomObjectID += 1
        _id = self.LastRoomObjectID
        self.RoomObjects[_id] = {'id': _id, 'type': type, 'angle': angle, 'ghost': ghost, 'velX': vx, 'velY': vy, 'posX': x, 'posY': y, 'rotationSpeed':(vx+vy)/2, 'stationary': (vx == 0 and vy == 0)}
        self.RefreshTFMGet()
        p = ByteArray()
        p.writeInt(_id)
        p.writeShort(type)
        p.writeShort(x)
        p.writeShort(y)
        p.writeShort(angle)
        p.writeByte(vx)
        p.writeByte(vy)
        p.writeBoolean(not ghost)
        p.writeByte(0)
        p.writeInt(options["fixedXSpeed"] if "fixedXSpeed" in options else 0)
        p.writeInt(options["fixedYSpeed"] if "fixedYSpeed" in options else 0)
        self.room.sendAll(Identifiers.send.Spawn_Object, p.toByteArray())
        return _id

    def attachBalloon(self, playerName, isAttached=True, colorType=1, ghost=False, speed=1):
        colorType = 4 if colorType > 4 else 1 if colorType < 1 else colorType
        player = self.server.players.get(Utils.parsePlayerName(playerName))
        if player != None:
            p = self.room.objectID + 1
            asyncio.ensure_future(player.sendPlaceObject(p,28,player.posX,player.posY-25,0,0,0,not ghost,True,colorType))
            if isAttached:
                self.room.sendAll(Identifiers.send.SetPositionToAttach, ByteArray().writeByte(-1).toByteArray())
                self.room.sendAll(Identifiers.send.AttachPlayer, ByteArray().writeInt(player.playerCode).writeInt(p).writeInt(speed*1000).toByteArray())
            else:
                self.room.sendAll(Identifiers.send.UnAttachPlayer, ByteArray().writeInt(player.playerCode).toByteArray())

    def changePlayerSize(self, name, size=1):
        size = float(size)
        size = 5.0 if size > 5.0 or size < 0.1 else size
        size = int(size * 100)
        player = self.server.players.get(Utils.parsePlayerName(name))
        if player != None:
            self.room.sendAll(Identifiers.send.Mouse_Size, ByteArray().writeInt(player.playerCode).writeShort(size).writeBoolean(False).toByteArray())

    def chatMessage(self, message, target=""):
        if target == "":
            for player in self.room.players.values():
                player.sendMessage(self.FixUnicodeError(message))
        else:
            player = self.room.players.get(target)

            if player != None:
                player.sendMessage(self.FixUnicodeError(message))
                    
    def stopModule(self, playerName="", action=0):
        self.room.isMinigame = False
        self.room.minigame = None
        self.runtime = None
        self.running = False
        self.room.luaRuntime = None
        
        if self.LastRoomObjectID > 2000:
            while self.LastRoomObjectID > 2000:
                self.room.removeObject(self.LastRoomObjectID)
                self.LastRoomObjectID -= 1

        for i in self.imagesadd:
            self.removeImage(i)
        
        for i in self.jointsadd:
            self.removeJoint(i)
        
        for i in self.textsadd:
            self.room.removeTextArea(i)

        for _id in self.loops:
            self.loops[_id].cancel()
            del self.loops[_id]
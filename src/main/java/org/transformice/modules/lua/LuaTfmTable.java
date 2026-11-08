package org.transformice.modules.lua;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.transformice.GameRoom;
import org.transformice.modules.lua.debug.*;
import org.transformice.modules.lua.system.*;
import org.transformice.modules.lua.tfm.exec.*;
import org.transformice.modules.lua.ui.*;

public final class LuaTfmTable extends TwoArgFunction {
    private final GameRoom room;

    /**
     * Creates a new LuaTfmTable.
     * @param room The given room.
     */
    public LuaTfmTable(GameRoom room) {
        this.room = room;
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("debug", new LuaTable());
        table.get("debug").set("disableEventLog", new LuaDisableEventLog(this.room));
        table.get("debug").set("disableTimerLog", new LuaDisableTimerLog(this.room));
        table.get("debug").set("getCurrentThreadName", new LuaGetCurrentLuaThreadName());
        table.set("tfm", new LuaTable());
        table.get("tfm").set("enum", new LuaTable());
        table.get("tfm").get("enum").set("emote", new LuaTable());
        table.get("tfm").get("enum").get("emote").set("dance", 0);
        table.get("tfm").get("enum").get("emote").set("laugh", 1);
        table.get("tfm").get("enum").get("emote").set("cry", 2);
        table.get("tfm").get("enum").get("emote").set("kiss", 3);
        table.get("tfm").get("enum").get("emote").set("angry", 4);
        table.get("tfm").get("enum").get("emote").set("clap", 5);
        table.get("tfm").get("enum").get("emote").set("sleep", 6);
        table.get("tfm").get("enum").get("emote").set("facepaw", 7);
        table.get("tfm").get("enum").get("emote").set("sit", 8);
        table.get("tfm").get("enum").get("emote").set("confetti", 9);
        table.get("tfm").get("enum").get("emote").set("flag", 10);
        table.get("tfm").get("enum").get("emote").set("marshmallow", 11);
        table.get("tfm").get("enum").get("emote").set("selfie", 12);
        table.get("tfm").get("enum").get("emote").set("highfive", 13);
        table.get("tfm").get("enum").get("emote").set("highfive_1", 14);
        table.get("tfm").get("enum").get("emote").set("highfive_2", 15);
        table.get("tfm").get("enum").get("emote").set("partyhorn", 16);
        table.get("tfm").get("enum").get("emote").set("hug", 17);
        table.get("tfm").get("enum").get("emote").set("hug_1", 18);
        table.get("tfm").get("enum").get("emote").set("hug_2", 19);
        table.get("tfm").get("enum").get("emote").set("jigglypuff", 20);
        table.get("tfm").get("enum").get("emote").set("kissing", 21);
        table.get("tfm").get("enum").get("emote").set("kissing_1", 22);
        table.get("tfm").get("enum").get("emote").set("kissing_2", 23);
        table.get("tfm").get("enum").get("emote").set("carnaval", 24);
        table.get("tfm").get("enum").get("emote").set("rockpaperscissors", 25);
        table.get("tfm").get("enum").get("emote").set("rockpaperscissors_1", 26);
        table.get("tfm").get("enum").get("emote").set("rockpaperscissors_2", 27);
        table.get("tfm").get("enum").set("shamanObject", new LuaTable());
        table.get("tfm").get("enum").get("shamanObject").set("arrow", 0);
        table.get("tfm").get("enum").get("shamanObject").set("littleBox", 1);
        table.get("tfm").get("enum").get("shamanObject").set("box", 2);
        table.get("tfm").get("enum").get("shamanObject").set("littleBoard", 3);
        table.get("tfm").get("enum").get("shamanObject").set("board", 4);
        table.get("tfm").get("enum").get("shamanObject").set("ball", 6);
        table.get("tfm").get("enum").get("shamanObject").set("trampoline", 7);
        table.get("tfm").get("enum").get("shamanObject").set("anvil", 10);
        table.get("tfm").get("enum").get("shamanObject").set("cannon", 17);
        table.get("tfm").get("enum").get("shamanObject").set("bomb", 23);
        table.get("tfm").get("enum").get("shamanObject").set("bluePortal", 26);
        table.get("tfm").get("enum").get("shamanObject").set("orangePortal", 27);
        table.get("tfm").get("enum").get("shamanObject").set("balloon", 28);
        table.get("tfm").get("enum").get("shamanObject").set("blueBalloon", 28);
        table.get("tfm").get("enum").get("shamanObject").set("redBalloon", 29);
        table.get("tfm").get("enum").get("shamanObject").set("greenBalloon", 30);
        table.get("tfm").get("enum").get("shamanObject").set("yellowBalloon", 31);
        table.get("tfm").get("enum").get("shamanObject").set("rune", 32);
        table.get("tfm").get("enum").get("shamanObject").set("chicken", 33);
        table.get("tfm").get("enum").get("shamanObject").set("snowBall", 34);
        table.get("tfm").get("enum").get("shamanObject").set("cupidonArrow", 35);
        table.get("tfm").get("enum").get("shamanObject").set("apple", 39);
        table.get("tfm").get("enum").get("shamanObject").set("sheep", 40);
        table.get("tfm").get("enum").get("shamanObject").set("littleBoardIce", 45);
        table.get("tfm").get("enum").get("shamanObject").set("littleBoardChocolate", 46);
        table.get("tfm").get("enum").get("shamanObject").set("iceCube", 54);
        table.get("tfm").get("enum").get("shamanObject").set("cloud", 57);
        table.get("tfm").get("enum").get("shamanObject").set("bubble", 59);
        table.get("tfm").get("enum").get("shamanObject").set("tinyBoard", 60);
        table.get("tfm").get("enum").get("shamanObject").set("companionCube", 61);
        table.get("tfm").get("enum").get("shamanObject").set("stableRune", 62);
        table.get("tfm").get("enum").get("shamanObject").set("balloonFish", 65);
        table.get("tfm").get("enum").get("shamanObject").set("longBoard", 67);
        table.get("tfm").get("enum").get("shamanObject").set("triangle", 68);
        table.get("tfm").get("enum").get("shamanObject").set("sBoard", 69);
        table.get("tfm").get("enum").get("shamanObject").set("paperPlane", 80);
        table.get("tfm").get("enum").get("shamanObject").set("rock", 85);
        table.get("tfm").get("enum").get("shamanObject").set("pumpkinBall", 89);
        table.get("tfm").get("enum").get("shamanObject").set("tombstone", 90);
        table.get("tfm").get("enum").get("shamanObject").set("paperBall", 95);
        table.get("tfm").get("enum").set("ground", new LuaTable());
        table.get("tfm").get("enum").get("ground").set("wood", 0);
        table.get("tfm").get("enum").get("ground").set("ice", 1);
        table.get("tfm").get("enum").get("ground").set("trampoline", 2);
        table.get("tfm").get("enum").get("ground").set("lava", 3);
        table.get("tfm").get("enum").get("ground").set("chocolate", 4);
        table.get("tfm").get("enum").get("ground").set("earth", 5);
        table.get("tfm").get("enum").get("ground").set("grass", 6);
        table.get("tfm").get("enum").get("ground").set("sand", 7);
        table.get("tfm").get("enum").get("ground").set("cloud", 8);
        table.get("tfm").get("enum").get("ground").set("water", 9);
        table.get("tfm").get("enum").get("ground").set("stone", 10);
        table.get("tfm").get("enum").get("ground").set("snow", 11);
        table.get("tfm").get("enum").get("ground").set("rectangle", 12);
        table.get("tfm").get("enum").get("ground").set("circle", 13);
        table.get("tfm").get("enum").get("ground").set("invisible", 14);
        table.get("tfm").get("enum").get("ground").set("web", 15);
        table.get("tfm").get("enum").get("ground").set("yellowGrass", 17);
        table.get("tfm").get("enum").get("ground").set("pinkGrass", 18);
        table.get("tfm").get("enum").get("ground").set("pinkGrass", 19);
        table.get("tfm").get("enum").set("particle", new LuaTable());
        table.get("tfm").get("enum").get("particle").set("whiteGlitter", 0);
        table.get("tfm").get("enum").get("particle").set("blueGlitter", 1);
        table.get("tfm").get("enum").get("particle").set("orangeGlitter", 2);
        table.get("tfm").get("enum").get("particle").set("cloud", 3);
        table.get("tfm").get("enum").get("particle").set("dullWhiteGlitter", 4);
        table.get("tfm").get("enum").get("particle").set("heart", 5);
        table.get("tfm").get("enum").get("particle").set("bubble", 6);
        table.get("tfm").get("enum").get("particle").set("tealGlitter", 9);
        table.get("tfm").get("enum").get("particle").set("spirit", 10);
        table.get("tfm").get("enum").get("particle").set("yellowGlitter", 11);
        table.get("tfm").get("enum").get("particle").set("ghostSpirit", 12);
        table.get("tfm").get("enum").get("particle").set("redGlitter", 13);
        table.get("tfm").get("enum").get("particle").set("waterBubble", 14);
        table.get("tfm").get("enum").get("particle").set("plus1", 15);
        table.get("tfm").get("enum").get("particle").set("plus10", 16);
        table.get("tfm").get("enum").get("particle").set("plus12", 17);
        table.get("tfm").get("enum").get("particle").set("plus14", 18);
        table.get("tfm").get("enum").get("particle").set("plus16", 19);
        table.get("tfm").get("enum").get("particle").set("meep", 20);
        table.get("tfm").get("enum").get("particle").set("redConfetti", 21);
        table.get("tfm").get("enum").get("particle").set("greenConfetti", 22);
        table.get("tfm").get("enum").get("particle").set("blueConfetti", 23);
        table.get("tfm").get("enum").get("particle").set("yellowConfetti", 24);
        table.get("tfm").get("enum").get("particle").set("diagonalRain", 25);
        table.get("tfm").get("enum").get("particle").set("curlyWind", 26);
        table.get("tfm").get("enum").get("particle").set("wind", 27);
        table.get("tfm").get("enum").get("particle").set("rain", 28);
        table.get("tfm").get("enum").get("particle").set("star", 29);
        table.get("tfm").get("enum").get("particle").set("littleRedHeart", 30);
        table.get("tfm").get("enum").get("particle").set("littlePinkHeart", 31);
        table.get("tfm").get("enum").get("particle").set("daisy", 32);
        table.get("tfm").get("enum").get("particle").set("bell", 33);
        table.get("tfm").get("enum").get("particle").set("projection", 35);
        table.get("tfm").get("enum").get("particle").set("egg", 34);
        table.get("tfm").get("enum").get("particle").set("mouseTeleportation", 36);
        table.get("tfm").get("enum").get("particle").set("shamanTeleportation", 37);
        table.get("tfm").get("enum").get("particle").set("lollipopConfetti", 38);
        table.get("tfm").get("enum").get("particle").set("yellowCandyConfetti", 39);
        table.get("tfm").get("enum").get("particle").set("pinkCandyConfetti", 40);
        table.get("tfm").set("exec", new LuaTable());
        table.get("tfm").get("exec").set("addBonus", new LuaAddBonus(this.room));
        ///  todo: tfm.exec.addConjuration
        ///  todo: tfm.exec.addImage
        ///  todo: tfm.exec.addJoint
        ///  todo: tfm.exec.addNpc
        ///  todo: tfm.exec.addPhysicObject
        ///  todo: tfm.exec.addShamanObject
        table.get("tfm").get("exec").set("attachBalloon", new LuaAttachBalloon(this.room));
        table.get("tfm").get("exec").set("bindKeyboard", new LuaBindKeyboard(this.room));
        table.get("tfm").get("exec").set("changePlayerSize", new LuaChangePlayerSize(this.room));
        table.get("tfm").get("exec").set("chatMessage", new LuaChatMessage(this.room));
        table.get("tfm").get("exec").set("disableAfkDeath", new LuaDisableAfkDeath(this.room));
        table.get("tfm").get("exec").set("disableAllShamanSkills", new LuaDisableAllShamanSkills(this.room));
        table.get("tfm").get("exec").set("disableAutoNewGame", new LuaDisableAutoNewGame(this.room));
        table.get("tfm").get("exec").set("disableAutoScore", new LuaDisableAutoScore(this.room));
        table.get("tfm").get("exec").set("disableAutoShaman", new LuaDisableAutoShaman(this.room));
        table.get("tfm").get("exec").set("disableAutoTimeLeft", new LuaDisableAutoTimeLeft(this.room));
        table.get("tfm").get("exec").set("disableDebugCommand", new LuaDisableDebugCommand(this.room));
        table.get("tfm").get("exec").set("disableMinimalistMode", new LuaDisableMinimalistMode(this.room));
        table.get("tfm").get("exec").set("disableMortCommand", new LuaDisableMortCommand(this.room));
        table.get("tfm").get("exec").set("disablePhysicalConsumables", new LuaDisablePhysicalConsumables(this.room));
        table.get("tfm").get("exec").set("disablePrespawnPreview", new LuaDisablePrespawnPreview(this.room));
        table.get("tfm").get("exec").set("disableWatchCommand", new LuaDisableWatchCommand(this.room));
        table.get("tfm").get("exec").set("displayParticle", new LuaDisplayParticle(this.room));
        table.get("tfm").get("exec").set("explosion", new LuaExplosion(this.room));
        table.get("tfm").get("exec").set("freezePlayer", new LuaFreezePlayer(this.room));
        table.get("tfm").get("exec").set("getPlayerSync", new LuaGetPlayerSync(this.room));
        table.get("tfm").get("exec").set("giveCheese", new LuaGiveCheese(this.room));
        ///  TODO: tfm.exec.giveConsumables
        table.get("tfm").get("exec").set("giveMeep", new LuaGiveMeep(this.room));
        table.get("tfm").get("exec").set("giveTransformations", new LuaGiveTransformations(this.room));
        table.get("tfm").get("exec").set("kickPlayer", new LuaKickPlayer(this.room));
        table.get("tfm").get("exec").set("killPlayer", new LuaKillPlayer(this.room));
        table.get("tfm").get("exec").set("linkMice", new LuaLinkMice(this.room));
        ///  TODO: tfm.exec.lowerSyncDelay ( playerName )
        ///  TODO: tfm.exec.moveObject ( objectId, xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset, angle, angleOffset )
        ///  TODO: tfm.exec.movePhysicObject ( objectId, xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset, angle, angleOffset )
        table.get("tfm").get("exec").set("movePlayer", new LuaMovePlayer(this.room));
        ///  TODO: tfm.exec.newGame ( mapCode, flipped )
        table.get("tfm").get("exec").set("playEmote", new LuaPlayEmote(this.room));
        table.get("tfm").get("exec").set("playMusic", new LuaPlayMusic(this.room));
        table.get("tfm").get("exec").set("playSound", new LuaPlaySound(this.room));
        table.get("tfm").get("exec").set("playerVictory", new LuaPlayerVictory(this.room));
        table.get("tfm").get("exec").set("removeBonus", new LuaRemoveBonus(this.room));
        table.get("tfm").get("exec").set("removeCheese", new LuaRemoveCheese(this.room));
        table.get("tfm").get("exec").set("removeImage", new LuaRemoveImage(this.room));
        table.get("tfm").get("exec").set("removeJoint", new LuaRemoveJoint(this.room));
        table.get("tfm").get("exec").set("removeObject", new LuaRemoveObject(this.room));
        table.get("tfm").get("exec").set("removePhysicObject", new LuaRemovePhysicObject(this.room));
        table.get("tfm").get("exec").set("respawnPlayer", new LuaRespawnPlayer(this.room));
        table.get("tfm").get("exec").set("setAieMode", new LuaSetAieMode(this.room));
        table.get("tfm").get("exec").set("setAutoMapFlipMode", new LuaSetAutoMapFlipMode(this.room));
        ///  TODO: tfm.exec.setGameTime ( time, init )
        table.get("tfm").get("exec").set("setNameColor", new LuaSetNameColor(this.room));
        ///  TODO: tfm.exec.setPlayerCollision ( playerName, collisionType, categoryBits, maskBits )
        table.get("tfm").get("exec").set("setPlayerGravityScale", new LuaSetPlayerGravityScale(this.room));
        ///  TODO: tfm.exec.setPlayerLook ( playerName, look )
        table.get("tfm").get("exec").set("setPlayerNightMode", new LuaSetPlayerNightMode(this.room));
        table.get("tfm").get("exec").set("setPlayerScore", new LuaSetPlayerScore(this.room));
        table.get("tfm").get("exec").set("setPlayerSync", new LuaSetPlayerSync(this.room));
        table.get("tfm").get("exec").set("setRoomMaxPlayers", new LuaSetRoomMaxPlayers(this.room));
        table.get("tfm").get("exec").set("setRoomPassword", new LuaSetRoomPassword(this.room));
        ///  TODO: tfm.exec.setShaman ( playerName, makeAShaman )
        ///  TODO: tfm.exec.setShamanMode ( playerName, mode )
        table.get("tfm").get("exec").set("setVampirePlayer", new LuaSetVampirePlayer(this.room));
        table.get("tfm").get("exec").set("setWorldGravity", new LuaSetWorldGravity(this.room));
        table.get("tfm").get("exec").set("snow", new LuaSnow(this.room));
        table.get("tfm").get("exec").set("stopMusic", new LuaStopMusic(this.room));
        table.set("system", new LuaTable());
        table.get("system").set("exit", new LuaSystemExit(this.room));
        table.get("system").set("bindKeyboard", new LuaBindKeyboard(this.room));
        table.get("system").set("bindMouse", new LuaBindMouse(this.room));
        table.get("system").set("disableChatCommandDisplay", new LuaDisableChatCommandDisplay(this.room));
        ///  TODO: system.giveAdventurePoint ( playerName, achievementCode, amount )
        ///  TODO: system.giveEventGift ( playerName, giftCode )
        ///  TODO: system.loadFile ( fileNumber )
        ///  TODO: system.loadPlayerData ( playerName )
        ///  TODO: system.newTimer ( callback, time, loop, arg1, arg2, arg3, arg4 )
        ///  TODO: system.removeTimer ( timerId )
        ///  TODO: system.saveFile ( data, fileNumber )
        ///  TODO: system.savePlayerData ( playerName, data )
        table.set("ui", new LuaTable());
        table.get("ui").set("addPopup", new LuaAddPopup(this.room));
        table.get("ui").set("addTextArea", new LuaAddTextArea(this.room));
        table.get("ui").set("removeTextArea", new LuaRemoveTextArea(this.room));
        table.get("ui").set("setBackgroundColor", new LuaSetBackgroundColor(this.room));
        table.get("ui").set("setMapName", new LuaSetMapName(this.room));
        table.get("ui").set("setShamanName", new LuaSetShamanName(this.room));
        table.get("ui").set("showColorPicker", new LuaShowColorPicker(this.room));
        table.get("ui").set("updateTextArea", new LuaUpdateTextArea(this.room));
        table.set("print", new LuaPrint(this.room));
        return table;
    }
}
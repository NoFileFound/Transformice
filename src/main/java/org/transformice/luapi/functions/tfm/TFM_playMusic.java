package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_PlayMusic;

public final class TFM_playMusic extends VarArgFunction {
    private final Room room;

    public TFM_playMusic(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playMusic() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.playMusic : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.playMusic : argument 2 can't be NIL."));
            } else {
                String soundFile = args.tojstring(1);
                String channel = args.tojstring(2);
                int volume = (args.isnil(3) ? 70 : args.toint(3));
                boolean loop = (!args.isnil(4) && args.toboolean(4));
                boolean fade = (args.isnil(5) || args.toboolean(5));
                String playerName = args.tojstring(6);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_PlayMusic(soundFile, channel, volume, loop, fade));
                } else {
                    this.room.sendAll(new C_PlayMusic(soundFile, channel, volume, loop, fade));
                }
            }
        }
        return NIL;
    }
}
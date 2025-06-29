package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_StopMusic;

public final class TFM_stopMusic extends VarArgFunction {
    private final Room room;

    public TFM_stopMusic(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.stopMusic() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.stopMusic : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.stopMusic : argument 2 can't be NIL."));
            } else {
                String channel = args.tojstring(1);
                String playerName = args.tojstring(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_StopMusic(channel, true));
                } else {
                    this.room.sendAll(new C_StopMusic(channel, true));
                }
            }
        }
        return NIL;
    }
}
package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Client;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_SetRoundTime;

public final class TFM_setGameTime extends VarArgFunction {
    private final Room room;

    public TFM_setGameTime(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setGameTime() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setGameTime : argument 1 can't be NIL."));
            } else {
                int time = args.toint(1);
                boolean init = args.isnil(2) || args.toboolean(2);
                if (init) {
                    for (Client player : this.room.getPlayers().values()) {
                        player.sendPacket(new C_SetRoundTime(time));
                    }

                    this.room.setLuaStartTimeMillis(System.currentTimeMillis());
                    this.room.setMapChangeTimer(time);
                }
            }
        }
        return NIL;
    }
}
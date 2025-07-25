package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setRoomMaxPlayers extends VarArgFunction {
    private final Room room;

    public TFM_setRoomMaxPlayers(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setRoomMaxPlayers() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setRoomMaxPlayers : argument 1 can't be NIL."));
            } else {
                this.room.setMaximumPlayers(args.isnil(1) ? 0 : args.toint(1));
            }
        }
        return NIL;
    }
}
package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_PlayerChangeSize;

public final class TFM_changePlayerSize extends VarArgFunction {
    private final Room room;

    public TFM_changePlayerSize(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.changePlayerSize() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.changePlayerSize : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int size = args.toint(2);
                if(size < 0.1 || size > 5) {
                    this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.changePlayerSize : The size must be between 0.1 and 5."));
                } else {
                    if(this.room.getPlayers().get(playerName) != null) {
                        this.room.sendAll(new C_PlayerChangeSize(this.room.getPlayers().get(playerName).getSessionId(), size, false));
                    }
                }
            }
        }
        return NIL;
    }
}
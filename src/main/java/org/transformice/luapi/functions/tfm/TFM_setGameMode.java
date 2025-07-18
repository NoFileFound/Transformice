package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setGameMode extends VarArgFunction {
    private final Room room;

    public TFM_setGameMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setGameMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setGameMode : argument 1 can't be NIL."));
            } else {
                String gameMode = args.tojstring(1);
                this.room.setGameMode(gameMode);
                this.room.changeMap();
            }
        }
        return NIL;
    }
}
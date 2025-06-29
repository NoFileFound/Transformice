package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_exit extends VarArgFunction {
    private final Room room;

    public SY_exit(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.exit() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (this.room.luaAdmin != null) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("Script terminated: Method system.exit called."));
            }

            this.room.stopLuaScript(true);
        }
        return NIL;
    }
}
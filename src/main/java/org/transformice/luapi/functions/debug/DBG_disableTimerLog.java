package org.transformice.luapi.functions.debug;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class DBG_disableTimerLog extends VarArgFunction {
    private final Room room;

    public DBG_disableTimerLog(Room room) {
        this.room = room;
    }

    /**
     * Invokes the debug.disableTimerLog() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (this.room.luaAdmin != null) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("The function debug.disableTimerLog is deprecated."));
            }
        }
        return NIL;
    }
}
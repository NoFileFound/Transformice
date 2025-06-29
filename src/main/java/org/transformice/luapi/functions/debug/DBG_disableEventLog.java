package org.transformice.luapi.functions.debug;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class DBG_disableEventLog extends VarArgFunction {
    private final Room room;

    public DBG_disableEventLog(Room room) {
        this.room = room;
    }

    /**
     * Invokes the debug.disableEventLog() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            this.room.disableEventLog = args.isnil(1) || args.toboolean(1);
        }
        return NIL;
    }
}
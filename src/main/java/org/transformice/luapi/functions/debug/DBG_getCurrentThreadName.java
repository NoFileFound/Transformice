package org.transformice.luapi.functions.debug;

// Imports
import org.luaj.vm2.LuaString;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class DBG_getCurrentThreadName extends VarArgFunction {
    private final Room room;

    public DBG_getCurrentThreadName(Room room) {
        this.room = room;
    }

    /**
     * Invokes the debug.getCurrentThreadName() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaString.valueOf(this.room.luaAdmin.getLuaThread().getPriority()); /// Module
    }
}
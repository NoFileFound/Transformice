package org.transformice.modules.lua.debug;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaDisableTimerLog extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new debug.disableTimerLog() function.
     * @param room The given room.
     */
    public LuaDisableTimerLog(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the debug.disableTimerLog() function.
     * @param args The arguments.
     * @return FALSE.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaValue.FALSE;
    }
}
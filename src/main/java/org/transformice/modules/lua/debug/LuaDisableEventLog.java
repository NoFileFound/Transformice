package org.transformice.modules.lua.debug;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaDisableEventLog extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new debug.disableEventLog() function.
     * @param room The given room.
     */
    public LuaDisableEventLog(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the debug.disableEventLog() function.
     * @param args The arguments.
     * @return True or False.
     */
    @Override
    public Varargs invoke(Varargs args) {
        room.getParseLua().isDisabledEventLog = args.arg1().toboolean();
        return LuaValue.valueOf(room.getParseLua().isDisabledEventLog);
    }
}
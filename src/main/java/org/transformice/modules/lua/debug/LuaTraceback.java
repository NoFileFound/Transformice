package org.transformice.modules.lua.debug;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class LuaTraceback extends VarArgFunction {
    private final LuaValue traceback;

    /**
     * Creates a new debug.traceback() function.
     * @param luaDebug The lua debug.
     */
    public LuaTraceback(LuaValue luaDebug) {
        this.traceback = luaDebug.get("traceback");
    }

    /**
     * Invokes the debug.traceback() function.
     * @param args The arguments.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return this.traceback.invoke(args);
    }
}
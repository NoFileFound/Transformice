package org.transformice.luapi.functions.os;

// Imports
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class OS_time extends VarArgFunction {
    /**
     * Invokes the os.time() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaInteger.valueOf(System.currentTimeMillis());
    }
}
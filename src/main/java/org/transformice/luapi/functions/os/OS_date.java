package org.transformice.luapi.functions.os;

// Imports
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public final class OS_date extends VarArgFunction {
    /**
     * Invokes the os.date() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaString.valueOf(new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US).format(new Date()));
    }
}
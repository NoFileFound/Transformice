package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class TFM_snow extends VarArgFunction {
    private final Room room;

    public TFM_snow(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.snow() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            int duration = args.isnil(1) ? 60 : args.toint(1);
            int snowballPower = args.isnil(2) ? 10 : args.toint(2);
            this.room.startSnow(duration, snowballPower, true);
        }
        return NIL;
    }
}
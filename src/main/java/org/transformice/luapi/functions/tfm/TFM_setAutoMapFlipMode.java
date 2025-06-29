package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class TFM_setAutoMapFlipMode extends VarArgFunction {
    private final Room room;

    public TFM_setAutoMapFlipMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setAutoMapFlipMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            this.room.autoMapFlipMode = args.isnil(1) || args.toboolean(1);
        }
        return NIL;
    }
}
package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class TFM_disableMortCommand extends VarArgFunction {
    private final Room room;

    public TFM_disableMortCommand(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableMortCommand() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            this.room.disableMortCommand = args.isnil(1) || args.toboolean(1);
        }
        return NIL;
    }
}
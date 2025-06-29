package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class TFM_disablePhysicalConsumables extends VarArgFunction {
    private final Room room;

    public TFM_disablePhysicalConsumables(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disablePhysicalConsumables() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            this.room.getRoomDetails().withoutPhysicalConsumables = args.isnil(1) || args.toboolean(1);
        }
        return NIL;
    }
}
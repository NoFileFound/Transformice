package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class SY_giveEventGift extends VarArgFunction {
    private final Room room;

    public SY_giveEventGift(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.giveEventGift() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        /// TODO: Implement system.giveEventGift()
        return NIL;
    }
}
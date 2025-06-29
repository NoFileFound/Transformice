package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class SY_giveAdventurePoint extends VarArgFunction {
    private final Room room;

    public SY_giveAdventurePoint(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.giveAdventurePoint() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        /// TODO: Implement system.giveAdventurePoint()
        return NIL;
    }
}
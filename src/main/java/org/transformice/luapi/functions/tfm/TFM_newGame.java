package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public class TFM_newGame extends VarArgFunction {
    private final Room room;

    public TFM_newGame(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.newGame() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        /// TODO: Finish it
        return NIL;
    }
}
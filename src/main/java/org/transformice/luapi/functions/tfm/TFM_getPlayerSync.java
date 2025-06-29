package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.LuaString;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

public final class TFM_getPlayerSync extends VarArgFunction {
    private final Room room;

    public TFM_getPlayerSync(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.getPlayerSync() function.
     * @param args The arguments.
     * @return String.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaString.valueOf(this.room.getCurrentSync().getPlayerName());
    }
}
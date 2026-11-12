package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.LuaString;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaGetPlayerSync extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.getPlayerSync() function.
     * @param room The given room.
     */
    public LuaGetPlayerSync(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.getPlayerSync() function.
     * @param args The arguments.
     * @return String.
     */
    @Override
    public Varargs invoke(Varargs args) {
        return LuaString.valueOf(this.room.getSync().getPlayerName());
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaDisableAutoScore extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.disableAutoScore() function.
     * @param room The given room.
     */
    public LuaDisableAutoScore(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableAutoScore() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.disableAutoScore = args.isnil(1) || args.toboolean(1);
        return NIL;
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaDisableAutoNewGame extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.disableAutoNewGame() function.
     * @param room The given room.
     */
    public LuaDisableAutoNewGame(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableAutoNewGame() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.disableAutoNewGame = args.isnil(1) || args.toboolean(1);
        return NIL;
    }
}
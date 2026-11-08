package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaSetRoomMaxPlayers extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setRoomMaxPlayers() function.
     * @param room The given room.
     */
    public LuaSetRoomMaxPlayers(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setRoomMaxPlayers() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.maximumPlayers = (args.isnil(1) ? 0 : args.toint(1));
        return NIL;
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaSetRoomPassword extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setRoomPassword() function.
     * @param room The given room.
     */
    public LuaSetRoomPassword(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setRoomPassword() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.roomPassword = args.isnil(1) ? "" : args.tojstring(1);
        return NIL;
    }
}
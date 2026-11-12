package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaSnow extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.snow() function.
     * @param room The given room.
     */
    public LuaSnow(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.snow() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        int duration = args.isnil(1) ? 60 : args.toint(1);
        int snowballPower = args.isnil(2) ? 10 : args.toint(2);
        this.room.sendStartSnow(duration, snowballPower, true);
        return NIL;
    }
}
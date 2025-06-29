package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.room.C_GravitationalSkill;

public final class TFM_setWorldGravity extends VarArgFunction {
    private final Room room;

    public TFM_setWorldGravity(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setWorldGravity() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            int gravity = args.isnil(1) ? 0 : args.toint(1);
            int wind = args.isnil(2) ? 0 : args.toint(2);
            this.room.sendAll(new C_GravitationalSkill(gravity, wind, 0));
        }
        return NIL;
    }
}
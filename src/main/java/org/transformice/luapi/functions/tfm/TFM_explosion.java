package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_Explosion;

public final class TFM_explosion extends VarArgFunction {
    private final Room room;

    public TFM_explosion(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.explosion() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.explosion : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.explosion : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.explosion : argument 3 can't be NIL."));
            } else if (args.isnil(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.explosion : argument 4 can't be NIL."));
            } else {
                int xPosition = args.toint(1);
                int yPosition = args.toint(2);
                int power = args.toint(3);
                int radius = args.toint(4);
                boolean miceOnly = args.toboolean(5);
                this.room.sendAll(new C_Explosion(xPosition, yPosition, power, radius, miceOnly));
            }
        }
        return NIL;
    }
}
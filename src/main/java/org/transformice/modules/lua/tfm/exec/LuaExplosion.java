package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendExplosion;

public final class LuaExplosion extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.explosion() function.
     * @param room The given room.
     */
    public LuaExplosion(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.explosion() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.explosion : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.explosion : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.explosion : argument 3 can't be NIL.");
        } else if (args.isnil(4)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.explosion : argument 4 can't be NIL.");
        } else {
            int xPosition = args.toint(1);
            int yPosition = args.toint(2);
            int power = args.toint(3);
            int radius = args.toint(4);
            boolean miceOnly = args.toboolean(5);
            this.room.broadcastPacket(new PacketSendExplosion(xPosition, yPosition, power, radius, miceOnly));
        }

        return NIL;
    }
}
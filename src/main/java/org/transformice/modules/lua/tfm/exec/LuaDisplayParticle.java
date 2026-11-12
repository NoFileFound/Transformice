package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaDisplayParticle;

public final class LuaDisplayParticle extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.displayParticle() function.
     * @param room The given room.
     */
    public LuaDisplayParticle(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.displayParticle() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.displayParticle : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.displayParticle : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.displayParticle : argument 3 can't be NIL.");
        } else {
            int particleType = args.toint(1);
            int xPosition = args.toint(2);
            int yPosition = args.toint(3);
            int xSpeed = args.toint(4);
            int ySpeed = args.toint(5);
            int xAcceleration = args.toint(6);
            int yAcceleration = args.toint(7);
            if (args.isnil(8)) {
                this.room.broadcastPacket(new PacketSendLuaDisplayParticle(particleType, xPosition, yPosition, xSpeed, ySpeed, xAcceleration, yAcceleration));
            } else {
                String targetPlayer = args.tojstring(8);
                if (this.room.getPlayers().get(targetPlayer) != null) {
                    this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaDisplayParticle(particleType, xPosition, yPosition, xSpeed, ySpeed, xAcceleration, yAcceleration));
                }
            }
        }
        return NIL;
    }
}
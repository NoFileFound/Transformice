package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_DisplayParticle;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_displayParticle extends VarArgFunction {
    private final Room room;

    public TFM_displayParticle(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.displayParticle() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.displayParticle : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.displayParticle : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.displayParticle : argument 3 can't be NIL."));
            } else {
                int particleType = args.toint(1);
                int xPosition = args.toint(2);
                int yPosition = args.toint(3);
                int xSpeed = args.toint(4);
                int ySpeed = args.toint(5);
                int xAcceleration = args.toint(6);
                int yAcceleration = args.toint(7);
                String targetPlayer = args.tojstring(8);
                if(args.isnil(8)) {
                    this.room.sendAll(new C_DisplayParticle(particleType, xPosition, yPosition, xSpeed, ySpeed, xAcceleration, yAcceleration));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_DisplayParticle(particleType, xPosition, yPosition, xSpeed, ySpeed, xAcceleration, yAcceleration));
                    }
                }
            }
        }
        return NIL;
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.packets.send.room.PacketSendMoveObject;

public final class LuaMovePhysicObject extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.movePhysicObject() function.
     * @param room The given room.
     */
    public LuaMovePhysicObject(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.movePhysicObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePhysicObject : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePhysicObject : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePhysicObject : argument 3 can't be NIL.");
        } else {
            int objectId = args.toint(1);
            int xPosition = args.toint(2);
            int yPosition = args.toint(3);
            boolean positionOffset = args.toboolean(4);
            int xSpeed = args.toint(5);
            int ySpeed = args.toint(6);
            boolean speedOffset = args.toboolean(7);
            int angle = args.toint(8);
            boolean angleOffset = args.toboolean(9);
            this.room.broadcastPacket(new PacketSendMoveObject(true, objectId, xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset, angle, angleOffset));
        }

        return NIL;
    }
}
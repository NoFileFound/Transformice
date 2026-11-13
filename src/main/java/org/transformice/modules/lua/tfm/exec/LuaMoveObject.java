package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendMoveObject;

public final class LuaMoveObject extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.moveObject() function.
     * @param room The given room.
     */
    public LuaMoveObject(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.moveObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.moveObject : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.moveObject : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.moveObject : argument 3 can't be NIL.");
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
            this.room.broadcastPacket(new PacketSendMoveObject(false, objectId, xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset, angle, angleOffset));
        }

        return NIL;
    }
}
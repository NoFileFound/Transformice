package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.sync.C_MoveObject;

public final class TFM_moveObject extends VarArgFunction {
    private final Room room;

    public TFM_moveObject(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.moveObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.moveObject : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.moveObject : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.moveObject : argument 3 can't be NIL."));
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
                this.room.sendAll(new C_MoveObject(false, objectId, xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset, angle, angleOffset));
            }
        }
        return NIL;
    }
}
package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_addShamanObject extends VarArgFunction {
    private final Room room;

    public TFM_addShamanObject(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addShamanObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addShamanObject : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addShamanObject : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addShamanObject : argument 3 can't be NIL."));
            } else {
                int objectType = args.toint(1);
                int xPosition = args.toint(2);
                int yPosition = args.toint(3);
                int angle = args.toint(4);
                int xSpeed = args.toint(5);
                int ySpeed = args.toint(6);
                boolean ghost = !args.toboolean(7);
                if(istable(8)) {
                    LuaTable objectInfo = args.checktable(8);
                    this.room.sendPlaceObject(this.room.getLastObjectID() + 1, objectType, xPosition, yPosition, angle, xSpeed, ySpeed, ghost, objectInfo.get("contactListener").toboolean(), new byte[]{}, null, true);
                } else {
                    this.room.sendPlaceObject(this.room.getLastObjectID() + 1, objectType, xPosition, yPosition, angle, xSpeed, ySpeed, ghost, false, new byte[]{}, null, true);
                }
                return LuaInteger.valueOf(this.room.getLastObjectID());
            }
        }
        return NIL;
    }
}
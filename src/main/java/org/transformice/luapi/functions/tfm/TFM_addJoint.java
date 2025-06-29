package org.transformice.luapi.functions.tfm;

// Imports
import org.bytearray.ByteArray;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_AddJoint;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_addJoint extends VarArgFunction {
    private final Room room;

    public TFM_addJoint(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addJoint() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addJoint : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addJoint : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addJoint : argument 3 can't be NIL."));
            } else if (args.isnil(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addJoint : argument 4 can't be NIL."));
            } else if (!args.istable(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addJoint : Argument 4 must be Table."));
            } else {
                int id = args.toint(1);
                int ground1 = args.toint(2);
                int ground2 = args.toint(3);
                LuaTable jointDef = args.checktable(4);
                this.room.sendAll(new C_AddJoint(new ByteArray().writeShort((short)id).writeShort((short)ground1).writeShort((short)ground2).writeByte(jointDef.get("type").toint()).writeBoolean(jointDef.get("point1").tojstring().matches("-?\\d+?,-?\\d+?")).writeShort((short)(jointDef.get("point1").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point1").tojstring().split(",")[0]) : 0)).writeShort((short) (jointDef.get("point1").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point1").tojstring().split(",")[1]) : 0)).writeBoolean(jointDef.get("point2").tojstring().matches("-?\\d+?,-?\\d+?")).writeShort((short) (jointDef.get("point2").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point2").tojstring().split(",")[0]) : 0)).writeShort((short) (jointDef.get("point2").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point2").tojstring().split(",")[1]) : 0)).writeBoolean(jointDef.get("point3").tojstring().matches("-?\\d+?,-?\\d+?")).writeShort((short) (jointDef.get("point3").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point3").tojstring().split(",")[0]) : 0)).writeShort((short) (jointDef.get("point3").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point3").tojstring().split(",")[1]) : 0)).writeBoolean(jointDef.get("point4").tojstring().matches("-?\\d+?,-?\\d+?")).writeShort((short) (jointDef.get("point4").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point4").tojstring().split(",")[0]) : 0)).writeShort((short) (jointDef.get("point4").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("point4").tojstring().split(",")[1]) : 0)).writeShort((short) (jointDef.get("frequency").tofloat() * 100)).writeShort((short) (jointDef.get("damping").tofloat() * 100)).writeBoolean(!jointDef.get("line").isnil()).writeShort((short) jointDef.get("line").toint()).writeInt(jointDef.get("color").toint()).writeShort((short) (jointDef.get("alpha").isnil() ? 100 : (int) (jointDef.get("alpha").tofloat() * 100))).writeBoolean(jointDef.get("foreground").toboolean()).writeShort((short) (jointDef.get("axis").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("axis").tojstring().split(",")[0]) : 0)).writeShort((short) (jointDef.get("axis").tojstring().matches("-?\\d+?,-?\\d+?") ? Integer.valueOf(jointDef.get("axis").tojstring().split(",")[1]) : 0)).writeBoolean(!jointDef.get("angle").isnil()).writeShort((short) jointDef.get("angle").toint()).writeBoolean(!jointDef.get("limit1").isnil()).writeShort((short) (jointDef.get("limit1").tofloat() * 100)).writeBoolean(!jointDef.get("limit2").isnil()).writeShort((short) (jointDef.get("limit2").tofloat() * 100)).writeBoolean(!jointDef.get("forceMotor").isnil()).writeShort((short) (jointDef.get("forceMotor").tofloat() * 100)).writeBoolean(!jointDef.get("speedMotor").isnil()).writeShort((short) (jointDef.get("speedMotor").tofloat() * 100)).writeShort((short) (jointDef.get("ratio").isnil() ? 100 : (int) (jointDef.get("ratio").tofloat() * 100)))));
            }
        }
        return NIL;
    }
}
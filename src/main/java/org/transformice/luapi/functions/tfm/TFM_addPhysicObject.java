package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_AddPhysicObject;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_addPhysicObject extends VarArgFunction {
    private final Room room;

    public TFM_addPhysicObject(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addPhysicObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addPhysicObject : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addPhysicObject : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addPhysicObject : argument 3 can't be NIL."));
            } else if (args.isnil(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addPhysicObject : argument 4 can't be NIL."));
            } else if (!args.istable(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addPhysicObject : Argument 4 must be Table."));
            } else {
                int id = args.toint(1);
                int xPosition = args.toint(2);
                int yPosition = args.toint(3);
                LuaTable bodyDef = args.checktable(4);
                this.room.sendAll(new C_AddPhysicObject(id, bodyDef.get("dynamic").toboolean(), bodyDef.get("type").toint(), xPosition, yPosition, bodyDef.get("width").toint(), bodyDef.get("height").toint(), bodyDef.get("foreground").toboolean(), (int)(bodyDef.get("friction").tofloat() * 100), (int)(bodyDef.get("restitution").tofloat() * 100), bodyDef.get("angle").toint(), bodyDef.get("color").toint(), bodyDef.get("miceCollision").isnil() || bodyDef.get("miceCollision").toboolean(), bodyDef.get("groundCollision").isnil() || bodyDef.get("groundCollision").toboolean(), bodyDef.get("fixedRotation").toboolean(), bodyDef.get("mass").toint() * 100, (int)(bodyDef.get("linearDamping").tofloat() * 100), (int)(bodyDef.get("angularDamping").tofloat() * 100), false, "", bodyDef.get("contactListener").isnil() || bodyDef.get("contactListener").toboolean()));
            }
        }
        return NIL;
    }
}
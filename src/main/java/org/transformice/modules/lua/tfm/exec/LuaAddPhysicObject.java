package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaAddPhysicObject extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.addPhysicObject() function.
     * @param room The given room.
     */
    public LuaAddPhysicObject(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addPhysicObject() function.
     * @param args The arguments.
     * @return NIL
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addPhysicObject : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addPhysicObject : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addPhysicObject : argument 3 can't be NIL.");
        } else if (args.isnil(4)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addPhysicObject : argument 4 can't be NIL.");
        } else if (!args.istable(4)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addPhysicObject : Argument 4 must be Table.");
        } else {
            int id = args.toint(1);
            int xPosition = args.toint(2);
            int yPosition = args.toint(3);
            LuaTable bodyDef = args.checktable(4);
            boolean dynamic = bodyDef.get("dynamic").toboolean();
            this.room.sendAddPhysicObject(id, dynamic, bodyDef.get("type").toint(), xPosition, yPosition, bodyDef.get("width").toint(), bodyDef.get("height").toint(), bodyDef.get("foreground").toboolean(), (int) (bodyDef.get("friction").tofloat() * 100), (int) (bodyDef.get("restitution").tofloat() * 100), bodyDef.get("angle").toint(), bodyDef.get("color").toint(), bodyDef.get("miceCollision").isnil() || bodyDef.get("miceCollision").toboolean(), bodyDef.get("groundCollision").isnil() || bodyDef.get("groundCollision").toboolean(), dynamic && bodyDef.get("fixedRotation").toboolean(), dynamic ? bodyDef.get("mass").toint() * 100 : 0, dynamic ? (int) (bodyDef.get("linearDamping").tofloat() * 100) : 0, dynamic ? (int) (bodyDef.get("angularDamping").tofloat() * 100) : 0, false, "", bodyDef.get("contactListener").isnil() || bodyDef.get("contactListener").toboolean());
        }

        return NIL;
    }
}
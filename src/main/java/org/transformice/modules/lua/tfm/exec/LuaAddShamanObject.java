package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;

public final class LuaAddShamanObject extends VarArgFunction {
    private final GameRoom room;
    private final GamePlayer luaAdmin;

    /**
     * Creates a new tfm.exec.addShamanObject() function.
     * @param room The given room.
     * @param luaAdmin The lua's executor.
     */
    public LuaAddShamanObject(GameRoom room, GamePlayer luaAdmin) {
        this.room = room;
        this.luaAdmin = luaAdmin;
    }

    /**
     * Invokes the tfm.exec.addShamanObject() function.
     * @param args The arguments.
     * @return NIL
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addShamanObject : argument 1 can't be NIL.");
            return NIL;
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addShamanObject : argument 2 can't be NIL.");
            return NIL;
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addShamanObject : argument 3 can't be NIL.");
            return NIL;
        } else {
            int objectType = args.toint(1);
            int xPosition = args.toint(2);
            int yPosition = args.toint(3);
            int angle = args.toint(4);
            int xSpeed = args.toint(5);
            int ySpeed = args.toint(6);
            boolean ghost = !args.toboolean(7);
            if (istable(8)) {
                LuaTable objectInfo = args.checktable(8);
                this.room.sendAddShamanObject(this.room.getNextObjectId(), objectType, xPosition, yPosition, angle, xSpeed, ySpeed, objectInfo.get("contactListener").toboolean(), ghost, this.luaAdmin, true);
            } else {
                this.room.sendAddShamanObject(this.room.getNextObjectId(), objectType, xPosition, yPosition, angle, xSpeed, ySpeed, false, ghost, this.luaAdmin, true);
            }

            return LuaValue.valueOf(this.room.getLastObjectId());
        }
    }
}
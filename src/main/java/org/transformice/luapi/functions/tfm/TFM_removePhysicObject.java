package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_RemovePhysicObject;

public final class TFM_removePhysicObject extends VarArgFunction {
    private final Room room;

    public TFM_removePhysicObject(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removePhysicObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.removePhysicObject : argument 1 can't be NIL."));
            } else {
                int objectId = args.toint(1);
                this.room.sendAll(new C_RemovePhysicObject(objectId));
            }
        }
        return NIL;
    }
}
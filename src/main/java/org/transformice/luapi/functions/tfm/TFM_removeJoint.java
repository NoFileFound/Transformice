package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_RemoveJoint;

public final class TFM_removeJoint extends VarArgFunction {
    private final Room room;

    public TFM_removeJoint(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeJoint() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.removeJoint : argument 1 can't be NIL."));
            } else {
                int jointId = args.toint(1);
                this.room.sendAll(new C_RemoveJoint(jointId));
            }
        }
        return NIL;
    }
}
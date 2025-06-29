package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setRoomPassword extends VarArgFunction {
    private final Room room;

    public TFM_setRoomPassword(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setRoomPassword() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setRoomPassword : argument 1 can't be NIL."));
            } else {
                if(!this.room.isTribeHouse()) {
                    this.room.setRoomPassword(args.tojstring(1));
                }
            }
        }
        return NIL;
    }
}
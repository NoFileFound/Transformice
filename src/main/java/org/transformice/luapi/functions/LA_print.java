package org.transformice.luapi.functions;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class LA_print extends VarArgFunction {
    private final Room room;

    public LA_print(Room client) {
        this.room = client;
    }

    /**
     * Invokes the print() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (this.room.luaAdmin != null) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage(args.tojstring(1)));
            }
        }

        return NIL;
    }
}
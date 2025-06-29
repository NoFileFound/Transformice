package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_BindKeyboard;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_bindKeyboard extends VarArgFunction {
    private final Room room;

    public SY_bindKeyboard(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.bindKeyboard() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.bindKeyboard : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.bindKeyboard : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.bindKeyboard : argument 3 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int keyCode = args.toint(2);
                boolean down = args.toboolean(3);
                boolean activate = args.isnil(4) || args.toboolean(4);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_BindKeyboard(keyCode, down, activate));
                }
            }
        }

        return NIL;
    }
}
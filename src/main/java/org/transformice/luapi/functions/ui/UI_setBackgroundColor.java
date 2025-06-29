package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_ChangeRoundBackground;

public final class UI_setBackgroundColor extends VarArgFunction {
    private final Room room;

    public UI_setBackgroundColor(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.setBackgroundColor() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setBackgroundColor : argument 1 can't be NIL."));
            } else {
                int color = this.room.luaApi.hexColorToInt(args.tojstring(1));
                if(color == -1) {
                    this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setBackgroundColor : Color format should be like #RRGGBB."));
                }

                this.room.sendAll(new C_ChangeRoundBackground(color));
            }
        }
        return NIL;
    }
}
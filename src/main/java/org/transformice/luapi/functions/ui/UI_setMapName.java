package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_SetMapName;

public final class UI_setMapName extends VarArgFunction {
    private final Room room;

    public UI_setMapName(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.setMapName() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setMapName : argument 1 can't be NIL."));
            } else {
                String text = args.tojstring(1);
                int result = this.room.luaApi.filterHtml(text);
                if (result != 0) {
                    if (result == 1) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setMapName : String 'http' is forbidden."));
                    } else if (result == 2) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setMapName : HTML tag 'a' can only be used for textarea callbacks."));
                    } else if (result == 3) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setMapName : HTML tag 'img' is forbidden."));
                    }

                } else {
                    this.room.sendAll(new C_SetMapName(text));
                }
            }
        }
        return NIL;
    }
}
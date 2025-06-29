package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_ShowColorPicker;

public final class UI_showColorPicker extends VarArgFunction {
    private final Room room;

    public UI_showColorPicker(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.showColorPicker() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.showColorPicker : argument 1 can't be NIL."));
            } else {
                int id = args.toint(1);
                String targetPlayer = args.tojstring(2);
                int defaultColor = args.toint(3);
                String title = args.isnil(4) ? "" : args.tojstring(4);
                int result = this.room.luaApi.filterHtml(title);
                if (result != 0) {
                    if (result == 1) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.showColorPicker : String 'http' is forbidden."));
                    } else if (result == 2) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.showColorPicker : HTML tag 'a' can only be used for textarea callbacks."));
                    } else if (result == 3) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.showColorPicker : HTML tag 'img' is forbidden."));
                    }

                } else {
                    if(targetPlayer.isEmpty()) {
                        this.room.sendAll(new C_ShowColorPicker(id, defaultColor, title));
                    } else {
                        if(this.room.getPlayers().get(targetPlayer) != null) {
                            this.room.getPlayers().get(targetPlayer).sendPacket(new C_ShowColorPicker(id, defaultColor, title));
                        }
                    }
                }
            }
        }
        return NIL;
    }
}
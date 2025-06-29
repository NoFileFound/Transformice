package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_AddPopup;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class UI_addPopup extends VarArgFunction {
    private final Room room;

    public UI_addPopup(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.addPopup() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : argument 3 can't be NIL."));
            } else {
                int id = args.toint(1);
                int type = args.toint(2);
                String text = args.tojstring(3);
                String targetPlayer = args.tojstring(4);
                int x = args.isnil(5) ? 50 : args.toint(5);
                int y = args.isnil(6) ? 50 : args.toint(6);
                int width = args.isnil(7) ? 200 : args.toint(7);
                boolean fixedPos = !args.isnil(8) && args.toboolean(8);
                int result = this.room.luaApi.filterHtml(text);
                if (result != 0) {
                    if (result == 1) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : String 'http' is forbidden."));
                    } else if (result == 2) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : HTML tag 'a' can only be used for textarea callbacks."));
                    } else if (result == 3) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addPopup : HTML tag 'img' is forbidden."));
                    }

                } else {
                    if(targetPlayer.equals("nil")) {
                        this.room.sendAll(new C_AddPopup(id, type, text, x, y, width, fixedPos));
                    } else {
                        if(this.room.getPlayers().get(targetPlayer) != null) {
                            this.room.getPlayers().get(targetPlayer).sendPacket(new C_AddPopup(id, type, text, x, y, width, fixedPos));
                        }
                    }
                }
            }
        }
        return NIL;
    }
}
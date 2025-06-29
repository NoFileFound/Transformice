package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_UpdateTextArea;

public final class UI_updateTextArea extends VarArgFunction {
    private final Room room;

    public UI_updateTextArea(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.updateTextArea() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.updateTextArea : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.updateTextArea : argument 2 can't be NIL."));
            } else {
                int id = args.toint(1);
                String text = args.tojstring(2);
                String targetPlayer = args.tojstring(3);
                int result = this.room.luaApi.filterHtml(text);
                if (result != 0) {
                    if (result == 1) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.updateTextArea : String 'http' is forbidden."));
                    } else if (result == 2) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.updateTextArea : HTML tag 'a' can only be used for textarea callbacks."));
                    } else if (result == 3) {
                        this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.updateTextArea : HTML tag 'img' is forbidden."));
                    }

                } else {
                    if (targetPlayer.isEmpty()) {
                        this.room.sendAll(new C_UpdateTextArea(id, text));
                    } else {
                        if(this.room.getPlayers().get(targetPlayer) != null) {
                            this.room.getPlayers().get(targetPlayer).sendPacket(new C_UpdateTextArea(id, text));
                        }
                    }
                }
            }
        }
        return NIL;
    }
}
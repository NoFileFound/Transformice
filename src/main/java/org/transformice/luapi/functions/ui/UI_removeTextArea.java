package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_RemoveTextArea;

public final class UI_removeTextArea extends VarArgFunction {
    private final Room room;

    public UI_removeTextArea(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.removeTextArea() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.removeTextArea : argument 1 can't be NIL."));
            } else {
                int id = args.toint(1);
                String targetPlayer = args.tojstring(2);
                if (targetPlayer.equals("nil")) {
                    this.room.sendAll(new C_RemoveTextArea(id));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_RemoveTextArea(id));
                    }
                }
            }
        }
        return NIL;
    }
}
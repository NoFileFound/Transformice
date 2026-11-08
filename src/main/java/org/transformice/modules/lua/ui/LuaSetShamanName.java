package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaSetShamanName;

public final class LuaSetShamanName extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.setShamanName() function.
     * @param room The given room.
     */
    public LuaSetShamanName(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.setShamanName() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.setShamanName : argument 1 can't be NIL.");
        } else {
            String text = args.tojstring(1);
            int result = Utils.filterHtml(text);
            if (result != 0) {
                if (result == 1) {
                    this.room.getParseLua().sendLuaMessage("ui.setShamanName : String 'http' is forbidden.");
                } else if (result == 2) {
                    this.room.getParseLua().sendLuaMessage("ui.setShamanName : HTML tag 'a' can only be used for textarea callbacks.");
                } else if (result == 3) {
                    this.room.getParseLua().sendLuaMessage("ui.setShamanName : HTML tag 'img' is forbidden.");
                }

            } else {
                this.room.broadcastPacket(new PacketSendLuaSetShamanName(text));
            }
        }

        return NIL;
    }
}
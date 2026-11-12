package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaAddPopup;

public final class LuaAddPopup extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.addPopup() function.
     * @param room The given room.
     */
    public LuaAddPopup(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.addPopup() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.addPopup : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("ui.addPopup : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("ui.addPopup : argument 3 can't be NIL.");
        } else {
            int id = args.toint(1);
            int type = args.toint(2);
            String text = args.tojstring(3);
            String targetPlayer = args.tojstring(4);
            int x = args.isnil(5) ? 50 : args.toint(5);
            int y = args.isnil(6) ? 50 : args.toint(6);
            int width = args.isnil(7) ? 200 : args.toint(7);
            boolean fixedPos = !args.isnil(8) && args.toboolean(8);
            int result = Utils.filterHtml(text);
            if (result != 0) {
                if (result == 1) {
                    this.room.getParseLua().sendLuaMessage("ui.addPopup : String 'http' is forbidden.");
                } else if (result == 2) {
                    this.room.getParseLua().sendLuaMessage("ui.addPopup : HTML tag 'a' can only be used for textarea callbacks.");
                } else if (result == 3) {
                    this.room.getParseLua().sendLuaMessage("ui.addPopup : HTML tag 'img' is forbidden.");
                }

            } else {
                if (targetPlayer.equals("nil")) {
                    this.room.broadcastPacket(new PacketSendLuaAddPopup(id, type, text, x, y, width, fixedPos));
                } else {
                    if (this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaAddPopup(id, type, text, x, y, width, fixedPos));
                    }
                }
            }
        }

        return NIL;
    }
}
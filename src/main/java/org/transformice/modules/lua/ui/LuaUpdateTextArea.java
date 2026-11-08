package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaUpdateTextArea;

public final class LuaUpdateTextArea extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.updateTextArea() function.
     * @param room The given room.
     */
    public LuaUpdateTextArea(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.updateTextArea() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.updateTextArea : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("ui.updateTextArea : argument 2 can't be NIL.");
        } else {
            int id = args.toint(1);
            String text = args.tojstring(2);
            String targetPlayer = args.tojstring(3);
            int result = Utils.filterHtml(text);
            if (result != 0) {
                if (result == 1) {
                    this.room.getParseLua().sendLuaMessage("ui.updateTextArea : String 'http' is forbidden.");
                } else if (result == 2) {
                    this.room.getParseLua().sendLuaMessage("ui.updateTextArea : HTML tag 'a' can only be used for textarea callbacks.");
                } else if (result == 3) {
                    this.room.getParseLua().sendLuaMessage("ui.updateTextArea : HTML tag 'img' is forbidden.");
                }

            } else {
                if (targetPlayer.isEmpty()) {
                    this.room.broadcastPacket(new PacketSendLuaUpdateTextArea(id, text));
                } else {
                    if (this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaUpdateTextArea(id, text));
                    }
                }
            }
        }

        return NIL;
    }
}
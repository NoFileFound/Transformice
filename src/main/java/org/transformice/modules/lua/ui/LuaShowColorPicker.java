package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaShowColorPicker;

public final class LuaShowColorPicker extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.showColorPicker() function.
     * @param room The given room.
     */
    public LuaShowColorPicker(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.showColorPicker() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.showColorPicker : argument 1 can't be NIL.");
        } else {
            int id = args.toint(1);
            String targetPlayer = args.tojstring(2);
            int defaultColor = args.isnil(3) ? 0 : args.toint(3);
            String title = args.isnil(4) ? "" : args.tojstring(4);
            int result = Utils.filterHtml(title);
            if (result != 0) {
                if (result == 1) {
                    this.room.getParseLua().sendLuaMessage("ui.showColorPicker : String 'http' is forbidden.");
                } else if (result == 2) {
                    this.room.getParseLua().sendLuaMessage("ui.showColorPicker : HTML tag 'a' can only be used for textarea callbacks.");
                } else if (result == 3) {
                    this.room.getParseLua().sendLuaMessage("ui.showColorPicker : HTML tag 'img' is forbidden.");
                }

            } else {
                if (targetPlayer.isEmpty()) {
                    this.room.broadcastPacket(new PacketSendLuaShowColorPicker(id, defaultColor, title));
                } else {
                    if (this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaShowColorPicker(id, defaultColor, title));
                    }
                }
            }
        }

        return NIL;
    }
}
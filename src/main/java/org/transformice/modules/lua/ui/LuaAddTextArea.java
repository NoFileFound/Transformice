package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaAddTextArea;

public final class LuaAddTextArea extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.addTextArea() function.
     * @param room The given room.
     */
    public LuaAddTextArea(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.addTextArea() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.addTextArea : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("ui.addTextArea : argument 2 can't be NIL.");
        } else {
            int id = args.toint(1);
            String text = args.tojstring(2);
            String targetPlayer = args.tojstring(3);
            int x = args.isnil(4) ? 50 : args.toint(4);
            int y = args.isnil(5) ? 50 : args.toint(5);
            int width = args.toint(6);
            int height  = args.toint(7);
            int backgroundColor = args.isnil(8) ? 0x324650 : args.toint(8);
            int borderColor = args.toint(9);
            int backgroundAlpha  = args.isnil(10) ? 100 : (int) (args.tofloat(10) * 100);
            boolean fixedPos = !args.isnil(8) && args.toboolean(8);
            int result = Utils.filterHtml(text);
            if (result != 0) {
                if (result == 1) {
                    this.room.getParseLua().sendLuaMessage("ui.addTextArea : String 'http' is forbidden.");
                } else if (result == 2) {
                    this.room.getParseLua().sendLuaMessage("ui.addTextArea : HTML tag 'a' can only be used for textarea callbacks.");
                } else if (result == 3) {
                    this.room.getParseLua().sendLuaMessage("ui.addTextArea : HTML tag 'img' is forbidden.");
                }

            } else {
                if (targetPlayer.equals("nil")) {
                    this.room.broadcastPacket(new PacketSendLuaAddTextArea(id, text, x, y, width, height, backgroundColor, borderColor, backgroundAlpha, fixedPos));
                } else {
                    if (this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaAddTextArea(id, text, x, y, width, height, backgroundColor, borderColor, backgroundAlpha, fixedPos));
                    }
                }
            }
        }

        return LuaValue.NIL;
    }
}
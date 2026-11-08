package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaSetBackgroundColor;

public final class LuaSetBackgroundColor extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.setBackgroundColor() function.
     * @param room The given room.
     */
    public LuaSetBackgroundColor(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.setBackgroundColor() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.setBackgroundColor : argument 1 can't be NIL.");
        } else {
            int color = args.isnil(1) ? 0x6A7495 : Utils.hexColorToInt(args.tojstring(1));
            if (color == -1)
                this.room.getParseLua().sendLuaMessage("ui.setBackgroundColor : Color format should be like #RRGGBB.");

            this.room.broadcastPacket(new PacketSendLuaSetBackgroundColor(color));
        }

        return NIL;
    }
}
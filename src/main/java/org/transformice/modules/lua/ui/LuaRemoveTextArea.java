package org.transformice.modules.lua.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaRemoveTextArea;

public final class LuaRemoveTextArea extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new ui.removeTextArea() function.
     * @param room The given room.
     */
    public LuaRemoveTextArea(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the ui.removeTextArea() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("ui.removeTextArea : argument 1 can't be NIL.");
        } else {
            int id = args.toint(1);
            String targetPlayer = args.tojstring(2);
            if (targetPlayer.equals("nil")) {
                this.room.broadcastPacket(new PacketSendLuaRemoveTextArea(id));
            } else {
                if (this.room.getPlayers().get(targetPlayer) != null) {
                    this.room.getPlayers().get(targetPlayer).broadcastPacket(new PacketSendLuaRemoveTextArea(id));
                }
            }
        }

        return NIL;
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.chat.PacketSendHtmlMessage;

public final class LuaChatMessage extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.chatMessage() function.
     * @param room The given room.
     */
    public LuaChatMessage(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.chatMessage() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.chatMessage : argument 1 can't be NIL.");
        } else {
            String message = args.tojstring(1);
            String playerName = args.tojstring(2);
            if (args.isnil(2)) {
                this.room.broadcastPacket(new PacketSendHtmlMessage(message));
            } else {
                if (this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendHtmlMessage(message));
                }
            }
        }

        return NIL;
    }
}
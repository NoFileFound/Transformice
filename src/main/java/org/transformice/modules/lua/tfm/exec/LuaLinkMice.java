package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendCreateSoulmate;

public final class LuaLinkMice extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.linkMice() function.
     * @param room The given room.
     */
    public LuaLinkMice(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.linkMice() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.linkMice : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.linkMice : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            String playerName2 = args.tojstring(2);
            boolean enable = args.toboolean(3);
            if(this.room.getPlayers().get(playerName) != null && this.room.getPlayers().get(playerName2) != null) {
                this.room.broadcastPacket(new PacketSendCreateSoulmate(this.room.getPlayers().get(playerName).getSessionId(), this.room.getPlayers().get(playerName2).getSessionId(), enable));
            }
        }
        return NIL;
    }
}
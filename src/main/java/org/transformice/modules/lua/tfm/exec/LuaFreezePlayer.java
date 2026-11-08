package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendIcedSkill;

public final class LuaFreezePlayer extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.freezePlayer() function.
     * @param room The given room.
     */
    public LuaFreezePlayer(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.freezePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.freezePlayer : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean freeze = args.toboolean(2);
            boolean displayIce = args.toboolean(3);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.broadcastPacket(new PacketSendIcedSkill(this.room.getPlayers().get(playerName).getSessionId(), freeze, displayIce));
        }

        return NIL;
    }
}
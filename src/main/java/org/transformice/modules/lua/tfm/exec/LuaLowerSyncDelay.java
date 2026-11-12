package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendLowerSyncDelay;

public final class LuaLowerSyncDelay extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.lowerSyncDelay() function.
     * @param room The given room.
     */
    public LuaLowerSyncDelay(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.lowerSyncDelay() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.lowerSyncDelay : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLowerSyncDelay(400));
        }

        return NIL;
    }
}
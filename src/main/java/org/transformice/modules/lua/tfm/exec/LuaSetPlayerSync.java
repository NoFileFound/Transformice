package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.legacy.PacketSendPlayerSynchronizer;

public final class LuaSetPlayerSync extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setPlayerSync() function.
     * @param room The given room.
     */
    public LuaSetPlayerSync(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerSync() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setPlayerSync : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            if (this.room.getPlayers().get(playerName) != null) {
                this.room.currentSync = this.room.getPlayers().get(playerName);
                for (GamePlayer player : this.room.getPlayers().values()) {
                    player.broadcastLegacyPacket(new PacketSendPlayerSynchronizer(this.room.getPlayers().get(playerName).getSessionId(), true));
                }
            }
        }

        return NIL;
    }
}
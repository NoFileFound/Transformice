package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendEnableNightMode;

public final class LuaSetPlayerNightMode extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setPlayerNightMode() function.
     * @param room The given room.
     */
    public LuaSetPlayerNightMode(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerNightMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        boolean enable = args.isnil(1) || args.toboolean(1);
        if (args.isnil(2)) {
            this.room.broadcastPacket(new PacketSendEnableNightMode(enable));
        } else {
            String playerName = args.tojstring(2);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendEnableNightMode(enable));
        }

        return NIL;
    }
}
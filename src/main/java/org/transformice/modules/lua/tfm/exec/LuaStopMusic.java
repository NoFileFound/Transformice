package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaStopMusic;

public final class LuaStopMusic extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.stopMusic() function.
     * @param room The given room.
     */
    public LuaStopMusic(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.stopMusic() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.stopMusic : argument 1 can't be NIL.");
        } else {
            String channel = args.tojstring(1);
            String playerName = args.tojstring(2);
            if (this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLuaStopMusic(channel, true));
            } else {
                this.room.broadcastPacket(new PacketSendLuaStopMusic(channel, true));
            }
        }

        return NIL;
    }
}
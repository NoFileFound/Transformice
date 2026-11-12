package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaPlayMusic;

public final class LuaPlayMusic extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.playMusic() function.
     * @param room The given room.
     */
    public LuaPlayMusic(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playMusic() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.playMusic : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.playMusic : argument 2 can't be NIL.");
        } else {
            String soundFile = args.tojstring(1);
            String channel = args.tojstring(2);
            int volume = (args.isnil(3) ? 70 : args.toint(3));
            boolean loop = (!args.isnil(4) && args.toboolean(4));
            boolean fade = (args.isnil(5) || args.toboolean(5));
            String playerName = args.tojstring(6);
            if (this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLuaPlayMusic(soundFile, channel, volume, loop, fade));
            } else {
                this.room.broadcastPacket(new PacketSendLuaPlayMusic(soundFile, channel, volume, loop, fade));
            }
        }

        return NIL;
    }
}
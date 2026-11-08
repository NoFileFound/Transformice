package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendEnableMeep;

public final class LuaGiveMeep extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.giveMeep() function.
     * @param room The given room.
     */
    public LuaGiveMeep(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.giveMeep() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.giveMeep : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean canMeep = args.isnil(2) || args.toboolean(2);
            if (this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).canMeep = canMeep;
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendEnableMeep(canMeep));
            }
        }

        return NIL;
    }
}
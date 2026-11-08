package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendEnableTransformation;

public final class LuaGiveTransformations extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.giveTransformations() function.
     * @param room The given room.
     */
    public LuaGiveTransformations(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.giveTransformations() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.giveTransformations : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean canTransform = args.toboolean(2);
            if (this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).canTransform = canTransform;
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendEnableTransformation(canTransform));
            }
        }

        return NIL;
    }
}
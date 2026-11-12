package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerSetGravityScale;

public final class LuaSetPlayerGravityScale extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setPlayerGravityScale() function.
     * @param room The given room.
     */
    public LuaSetPlayerGravityScale(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerGravityScale() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setPlayerGravityScale : argument 1 can't be NIL.");
        } else {
            String targetPlayer = args.tojstring(1);
            int gravityScale = args.isnil(2) ? 1 : args.toint(2);
            int windScale = args.isnil(3) ? 1 : args.toint(3);
            if (this.room.getPlayers().get(targetPlayer) != null)
                this.room.broadcastPacket(new PacketSendPlayerSetGravityScale(this.room.getPlayers().get(targetPlayer).getSessionId(), gravityScale, windScale));
        }

        return NIL;
    }
}
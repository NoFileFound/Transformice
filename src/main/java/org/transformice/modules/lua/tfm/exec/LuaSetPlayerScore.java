package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerSetScore;

public final class LuaSetPlayerScore extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setPlayerScore() function.
     * @param room The given room.
     */
    public LuaSetPlayerScore(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerScore() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setPlayerScore : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setPlayerScore : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int score = args.toint(2);
            boolean add = args.isnil(3) || args.toboolean(3);
            if (this.room.getPlayers().get(playerName) != null) {
                if (add) {
                    this.room.getPlayers().get(playerName).playerScore += (short)score;
                } else {
                    this.room.getPlayers().get(playerName).playerScore = (short)score;
                }

                this.room.broadcastPacket(new PacketSendPlayerSetScore(this.room.getPlayers().get(playerName).getSessionId(), this.room.getPlayers().get(playerName).playerScore));
            }
        }

        return NIL;
    }
}
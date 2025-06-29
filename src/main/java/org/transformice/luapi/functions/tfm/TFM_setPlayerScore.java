package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_SetPlayerScore;

public final class TFM_setPlayerScore extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerScore(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerScore() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerScore : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerScore : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int score = args.toint(2);
                boolean add = args.toboolean(3);
                if (this.room.getPlayers().containsKey(playerName)) {
                    if (add) {
                        this.room.getPlayers().get(playerName).playerScore += score;
                    } else {
                        this.room.getPlayers().get(playerName).playerScore = score;
                    }

                    this.room.sendAll(new C_SetPlayerScore(this.room.getPlayers().get(playerName).getSessionId(), this.room.getPlayers().get(playerName).playerScore));
                }
            }
        }
        return NIL;
    }
}
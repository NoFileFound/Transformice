package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaKillPlayer extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.killPlayer() function.
     * @param room The given room.
     */
    public LuaKillPlayer(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.killPlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.killPlayer : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).sendDeath();
        }

        return NIL;
    }
}
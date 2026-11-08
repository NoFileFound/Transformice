package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaSetVampirePlayer extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setVampirePlayer() function.
     * @param room The given room.
     */
    public LuaSetVampirePlayer(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setVampirePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setVampirePlayer : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean isVampire = args.isnil(2) || args.toboolean(2);
            if (this.room.getPlayers().get(playerName) != null) {
                if (isVampire) {
                    this.room.sendAddVampire(this.room.getPlayers().get(playerName));
                } else {
                    this.room.sendRemoveVampire(this.room.getPlayers().get(playerName));
                }
            }
        }

        return NIL;
    }
}
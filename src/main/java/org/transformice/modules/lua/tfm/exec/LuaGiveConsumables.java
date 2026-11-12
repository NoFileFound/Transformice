package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaGiveConsumables extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.giveConsumables() function.
     * @param room The given room.
     */
    public LuaGiveConsumables(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.giveConsumables() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.giveConsumables : argument 1 can't be NIL.");
        } else if(args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.giveConsumables : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            String itemId = args.tojstring(2);
            String amount = (args.isnil(3) ? "1" : args.tojstring(3));
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).getParseInventory().addConsumable(itemId, Integer.parseInt(amount), true);
        }

        return NIL;
    }
}
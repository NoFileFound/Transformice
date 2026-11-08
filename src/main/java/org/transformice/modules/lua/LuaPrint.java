package org.transformice.modules.lua;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaPrint extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new print() function.
     * @param room The given room.
     */
    public LuaPrint(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the print() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i <= args.narg(); i++) {
            if (i > 1)
                message.append(" ");

            message.append(args.arg(i).tojstring());
        }

        this.room.getParseLua().sendLuaMessage(message.toString());
        return NIL;
    }
}
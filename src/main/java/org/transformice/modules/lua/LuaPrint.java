package org.transformice.modules.lua;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
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
            LuaValue value = args.arg(i);
            if (value.istable()) {
                printTable(message, value, "");
            } else {
                message.append(value.tojstring());
            }
        }

        this.room.getParseLua().sendLuaMessage(message.toString());
        return NIL;
    }

    /**
     * Prints a table.
     * @param message The message.
     * @param table The table to print.
     * @param prefix The prefix.
     */
    private void printTable(StringBuilder message, LuaValue table, String prefix) {
        LuaTable luaTable = table.checktable();
        for (LuaValue key : luaTable.keys()) {
            LuaValue value = luaTable.get(key);
            message.append("\n").append(prefix).append(key.tojstring()).append(" = ");
            if (value.istable()) {
                message.append("{");
                printTable(message, value, prefix + "  ");
                message.append("\n").append(prefix).append("}");
            } else {
                message.append(value.tojstring());
            }
        }
    }
}
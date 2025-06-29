package org.transformice.luapi.functions.os;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class LUA_TABLE_OS extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("os", new LuaTable());
        table.get("os").set("time", new OS_time());
        table.get("os").set("date", new OS_date());
        return table.get("os");
    }
}
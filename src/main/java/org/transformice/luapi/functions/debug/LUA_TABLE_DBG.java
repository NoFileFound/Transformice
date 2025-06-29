package org.transformice.luapi.functions.debug;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.transformice.Room;

public class LUA_TABLE_DBG extends TwoArgFunction {
    private final Room room;

    public LUA_TABLE_DBG(Room room) {
        this.room = room;
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("debug", new LuaTable());
        table.get("debug").set("disableEventLog", new DBG_disableEventLog(this.room));
        table.get("debug").set("disableTimerLog", new DBG_disableEventLog(this.room));
        table.get("debug").set("getCurrentThreadName", new DBG_getCurrentThreadName(this.room));
        return table.get("debug");
    }
}
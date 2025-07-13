package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.transformice.Room;

public class LUA_TABLE_SY extends TwoArgFunction {
    private final Room room;

    public LUA_TABLE_SY(Room room) {
        this.room = room;
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("system", new LuaTable());
        table.get("system").set("bindKeyboard", new SY_bindKeyboard(this.room));
        table.get("system").set("bindMouse", new SY_bindMouse(this.room));
        table.get("system").set("disableChatCommandDisplay", new SY_disableChatCommandDisplay(this.room));
        table.get("system").set("exit", new SY_exit(this.room));
        table.get("system").set("loadFile", new SY_loadFile(this.room));
        table.get("system").set("loadPlayerData", new SY_loadPlayerData(this.room));
        table.get("system").set("newTimer", new SY_newTimer(this.room));
        table.get("system").set("removeTimer", new SY_removeTimer(this.room));
        table.get("system").set("saveFile", new SY_saveFile(this.room));
        table.get("system").set("savePlayerData", new SY_savePlayerData(this.room));
        return table.get("system");
    }
}
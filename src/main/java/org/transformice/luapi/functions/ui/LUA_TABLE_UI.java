package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.transformice.Room;

public class LUA_TABLE_UI extends TwoArgFunction {
    private final Room room;

    public LUA_TABLE_UI(Room room) {
        this.room = room;
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("ui", new LuaTable());
        table.get("ui").set("addLogWindow", new UI_addLogWindow(this.room));
        table.get("ui").set("addPopup", new UI_addPopup(this.room));
        table.get("ui").set("addTextArea", new UI_addTextArea(this.room));
        table.get("ui").set("addQuestionPopup", new UI_addQuestionPopup(this.room));
        table.get("ui").set("removeTextArea", new UI_removeTextArea(this.room));
        table.get("ui").set("setMapName", new UI_setMapName(this.room));
        table.get("ui").set("setShamanName", new UI_setShamanName(this.room));
        table.get("ui").set("showColorPicker", new UI_showColorPicker(this.room));
        table.get("ui").set("updateTextArea", new UI_updateTextArea(this.room));
        table.get("ui").set("setBackgroundColor", new UI_setBackgroundColor(this.room));
        table.get("ui").set("setPixel", new UI_setPixel(this.room));
        return table.get("ui");
    }
}
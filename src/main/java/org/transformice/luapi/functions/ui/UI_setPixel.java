package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.transformice.C_VisualConsumableInfo;

public final class UI_setPixel extends VarArgFunction {
    private final Room room;

    public UI_setPixel(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.setPixel() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setPixel : argument 1 can't be NIL."));
            } if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setPixel : argument 2 can't be NIL."));
            } if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setPixel : argument 3 can't be NIL."));
            } if (args.isnil(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setPixel : argument 4 can't be NIL."));
            } if (args.isnil(5)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.setPixel : argument 5 can't be NIL."));
            }
            else {
                this.room.sendAll(new C_VisualConsumableInfo(2, -1, new Object[]{args.toint(1), args.toint(2), args.toint(3), args.toint(4), args.toint(5)}));
            }
        }
        return NIL;
    }
}
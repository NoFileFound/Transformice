package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_BindMouseDown;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_bindMouse extends VarArgFunction {
    private final Room room;

    public SY_bindMouse(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.bindMouse() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.bindMouse : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean active = args.isnil(2) || args.toboolean(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_BindMouseDown(active));
                }
            }
        }
        return NIL;
    }
}
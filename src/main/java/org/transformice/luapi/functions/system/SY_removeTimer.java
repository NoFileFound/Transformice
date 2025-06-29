package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_removeTimer extends VarArgFunction{
    private final Room room;

    public SY_removeTimer(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.removeTimer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.removeTimer : argument 1 can't be NIL."));
            } else {
                int timerId = args.toint(1);
                if (this.room.getLuaTimers().containsKey(timerId)) {
                    this.room.getLuaTimers().get(timerId).cancel();
                    this.room.getLuaTimers().remove(timerId);
                }
            }
        }
        return NIL;
    }
}
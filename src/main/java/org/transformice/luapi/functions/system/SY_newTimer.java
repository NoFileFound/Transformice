package org.transformice.luapi.functions.system;

// Imports
import java.util.concurrent.TimeUnit;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;
import org.transformice.libraries.Timer;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_newTimer extends VarArgFunction {
    private final Room room;

    public SY_newTimer(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.newTimer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.newTimer : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.newTimer : argument 2 can't be NIL."));
            } else if (!args.isfunction(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.newTimer : Argument 1 must be Function."));
            } else {
                LuaFunction callback = args.checkfunction(1);
                int time = args.toint(2);
                boolean loop = args.toboolean(3);
                if (time >= 1000) {
                    int timerID = this.room.getLuaTimers().size() + 1;
                    Runnable run = () -> {this.room.luaApi.callEvent("Timer #" + timerID, callback, args.optvalue(4, NIL), args.optvalue(5, NIL), args.optvalue(6, NIL), args.optvalue(7, NIL));};
                    Timer timer = new Timer();
                    if (loop) {
                        timer.scheduleAtFixedRate(run, time, time, TimeUnit.MILLISECONDS);
                        this.room.getLuaTimers().put(timerID, timer);
                    } else {
                        timer.schedule(run, time, TimeUnit.MILLISECONDS);
                        this.room.getLuaTimers().put(timerID, timer);
                    }

                    return LuaInteger.valueOf(timerID);
                }
            }
        }
        return NIL;
    }
}
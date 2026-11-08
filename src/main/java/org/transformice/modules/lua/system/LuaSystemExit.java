package org.transformice.modules.lua.system;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.transformice.GameRoom;

public final class LuaSystemExit extends ZeroArgFunction {
    private final GameRoom room;

    /**
     * Creates a new system.exit() function.
     * @param room The given room.
     */
    public LuaSystemExit(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the system.exit() function.
     * @return NIL.
     */
    @Override
    public LuaValue call() {
        room.getParseLua().sendLuaMessage("Script terminated: Method system.exit called.");
        room.getParseLua().sendRoomStopScript();
        return LuaValue.NIL;
    }
}
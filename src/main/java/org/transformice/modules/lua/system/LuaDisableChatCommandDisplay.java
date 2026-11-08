package org.transformice.modules.lua.system;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaDisableChatCommandDisplay extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new system.disableChatCommandDisplay() function.
     * @param room The given room.
     */
    public LuaDisableChatCommandDisplay(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the system.disableChatCommandDisplay() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("system.disableChatCommandDisplay : argument 1 can't be NIL.");
        } else {
            String commandName = args.tojstring(1);
            boolean isHidden = args.optboolean(2, true);
            if (isHidden) {
                if (!this.room.getParseLua().getDisableChatCommandListDisplay().contains(commandName)) {
                    this.room.getParseLua().getDisableChatCommandListDisplay().add(commandName);
                }
            } else {
                this.room.getParseLua().getDisableChatCommandListDisplay().remove(commandName);
            }
        }

        return LuaValue.NIL;
    }
}
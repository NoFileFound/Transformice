package org.transformice.luapi.functions.system;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_disableChatCommandDisplay extends VarArgFunction {
    private final Room room;

    public SY_disableChatCommandDisplay(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.disableChatCommandDisplay() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.disableChatCommandDisplay : argument 1 can't be NIL."));
            } else {
                String command = args.tojstring(1);
                boolean activate = args.isnil(1) || args.toboolean(1);
                if (activate) {
                    if (this.room.getDisabledChatCommandsDisplay().size() < 100 && !this.room.getDisabledChatCommandsDisplay().contains(command)) {
                        this.room.getDisabledChatCommandsDisplay().add(command);
                    }

                } else if (this.room.getDisabledChatCommandsDisplay().contains(command)) {
                    this.room.getDisabledChatCommandsDisplay().remove(command);
                }
            }
        }

        return NIL;
    }
}
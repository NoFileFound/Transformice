package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaDisableRoomProperties;

public final class LuaDisableMinimalistMode extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.disableMinimalistMode() function.
     * @param room The given room.
     */
    public LuaDisableMinimalistMode(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableMinimalistMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.getParseLua().isDisabledMinimalistMode = args.isnil(1) || args.toboolean(1);
        this.room.broadcastPacket(new PacketSendLuaDisableRoomProperties(this.room.getParseLua().isDisabledWatchCommand, this.room.getParseLua().isDisabledDebugCommand, this.room.getParseLua().isDisabledMinimalistMode));
        return NIL;
    }
}
package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaDisableRoomProperties;

public final class LuaDisableWatchCommand extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.disableWatchCommand() function.
     * @param room The given room.
     */
    public LuaDisableWatchCommand(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableWatchCommand() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        this.room.getParseLua().isDisabledWatchCommand = args.isnil(1) || args.toboolean(1);
        this.room.broadcastPacket(new PacketSendLuaDisableRoomProperties(this.room.getParseLua().isDisabledWatchCommand, this.room.getParseLua().isDisabledDebugCommand, this.room.getParseLua().isDisabledMinimalistMode));
        return NIL;
    }
}
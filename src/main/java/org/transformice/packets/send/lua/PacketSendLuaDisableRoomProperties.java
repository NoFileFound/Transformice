package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaDisableRoomProperties extends PSSendPacket {
    private final boolean disableWatchCommand;
    private final boolean disableDebugCommand;
    private final boolean disableMinimalistMode;

    /**
     * Creates a new packet of SEND_DISABLE_ROOM_PROPERTIES.
     * @param disableWatchCommand Disables the /watch command.
     * @param disableDebugCommand Disables the /debug command.
     * @param disableMinimalistMode Disables the minimalist mode.
     */
    public PacketSendLuaDisableRoomProperties(boolean disableWatchCommand, boolean disableDebugCommand, boolean disableMinimalistMode) {
        super(PacketOpcodes.SEND_LUA_DISABLE_ROOM_PROPERTIES);
        this.disableWatchCommand = disableWatchCommand;
        this.disableDebugCommand = disableDebugCommand;
        this.disableMinimalistMode = disableMinimalistMode;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.disableWatchCommand).writeBoolean(this.disableDebugCommand).writeBoolean(this.disableMinimalistMode);
    }
}
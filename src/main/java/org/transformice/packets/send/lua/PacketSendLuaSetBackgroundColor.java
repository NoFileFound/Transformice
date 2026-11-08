package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaSetBackgroundColor extends PSSendPacket {
    private final int backgroundColor;

    /**
     * Creates a new packet of SEND_LUA_SET_BACKGROUND_COLOR.
     * @param backgroundColor The background's color.
     */
    public PacketSendLuaSetBackgroundColor(int backgroundColor) {
        super(PacketOpcodes.SEND_LUA_SET_BACKGROUND_COLOR);
        this.backgroundColor = backgroundColor;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.backgroundColor);
    }
}
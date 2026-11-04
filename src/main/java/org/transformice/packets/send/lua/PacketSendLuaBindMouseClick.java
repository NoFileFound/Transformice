package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaBindMouseClick extends PSSendPacket {
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_LUA_BIND_MOUSE_CLICK.
     * @param isEnabled Is the bind mouse enabled.
     */
    public PacketSendLuaBindMouseClick(boolean isEnabled) {
        super(PacketOpcodes.SEND_LUA_BIND_MOUSE_CLICK);
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isEnabled);
    }
}
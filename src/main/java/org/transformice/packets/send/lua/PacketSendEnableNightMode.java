package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEnableNightMode extends PSSendPacket {
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_ENABLE_NIGHT_MODE.
     * @param isEnabled Is the night mode enabled.
     */
    public PacketSendEnableNightMode(boolean isEnabled) {
        super(PacketOpcodes.SEND_ENABLE_NIGHT_MODE);
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isEnabled);
    }
}
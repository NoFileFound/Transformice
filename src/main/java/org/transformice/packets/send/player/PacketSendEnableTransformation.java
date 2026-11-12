package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEnableTransformation extends PSSendPacket {
    private final boolean enable;

    /**
     * Creates a new packet of SEND_ENABLE_TRANSFORMATION.
     * @param enable Should enable the transformation powers.
     */
    public PacketSendEnableTransformation(boolean enable) {
        super(PacketOpcodes.SEND_ENABLE_TRANSFORMATION);
        this.enable = enable;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.enable);
    }
}
package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEnableMeep extends PSSendPacket {
    private final boolean enable;

    /**
     * Creates a new packet of SEND_ENABLE_MEEP.
     * @param enable Should enable the transformation powers.
     */
    public PacketSendEnableMeep(boolean enable) {
        super(PacketOpcodes.SEND_ENABLE_MEEP);
        this.enable = enable;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.enable);
    }
}
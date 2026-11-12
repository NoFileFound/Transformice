package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerSaveRemainingNotification extends PSSendPacket {
    /**
     * Creates a new packet of SendPlayerSaveRemainingNotification.
     */
    public PacketSendPlayerSaveRemainingNotification() {
        super(LegacyPacketOpcodes.SendPlayerSaveRemainingNotification);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}
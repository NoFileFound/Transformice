package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopTimestamp extends PSSendPacket {
    private final long timestamp;

    /**
     * Creates a new packet of SEND_SHOP_TIMESTAMP.
     * @param timestamp The given timestamp (in seconds).
     */
    public PacketSendShopTimestamp(long timestamp) {
        super(PacketOpcodes.SEND_SHOP_TIMESTAMP);
        this.timestamp = timestamp;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.timestamp);
    }
}
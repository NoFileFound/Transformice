package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTotemItemCount extends PSSendPacket {
    private final int count;

    /**
     * Creates a new packet of SEND_TOTEM_ITEM_COUNT.
     * @param count The count of used objects.
     */
    public PacketSendTotemItemCount(int count) {
        super(PacketOpcodes.SEND_TOTEM_ITEM_COUNT);
        this.count = count * 2;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short)this.count);
    }
}
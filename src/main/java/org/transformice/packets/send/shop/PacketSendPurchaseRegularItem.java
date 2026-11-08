package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPurchaseRegularItem extends PSSendPacket {
    private final int itemId;

    /**
     * Creates a new packet of SEND_PURCHASE_REGULAR_ITEM.
     * @param itemId The item's id.
     */
    public PacketSendPurchaseRegularItem(int itemId) {
        super(PacketOpcodes.SEND_PURCHASE_REGULAR_ITEM);
        this.itemId = itemId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.itemId).writeByte(4);
    }
}
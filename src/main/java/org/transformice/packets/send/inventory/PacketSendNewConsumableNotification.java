package org.transformice.packets.send.inventory;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendNewConsumableNotification extends PSSendPacket {
    private final int itemId;
    private final int quantity;

    /**
     * Creates a new packet of SEND_NEW_CONSUMABLE_NOTIFICATION.
     * @param itemId The item's id.
     * @param quantity The item's quantity.
     */
    public PacketSendNewConsumableNotification(int itemId, int quantity) {
        super(PacketOpcodes.SEND_NEW_CONSUMABLE_NOTIFICATION);
        this.itemId = itemId;
        this.quantity = quantity;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(0).writeUnsignedShort(this.itemId).writeUnsignedShort(this.quantity);
    }
}
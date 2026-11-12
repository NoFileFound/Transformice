package org.transformice.packets.send.inventory;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendUpdateInventory extends PSSendPacket {
    private final int itemId;
    private final int quantity;

    /**
     * Creates a new packet of SEND_UPDATE_INVENTORY.
     * @param itemId The item's id.
     * @param quantity The item's quantity.
     */
    public PacketSendUpdateInventory(int itemId, int quantity) {
        super(PacketOpcodes.SEND_UPDATE_INVENTORY);
        this.itemId = itemId;
        this.quantity = quantity;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.itemId).writeUnsignedShort(this.quantity);
    }
}
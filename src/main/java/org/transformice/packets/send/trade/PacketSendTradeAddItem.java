package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeAddItem extends PSSendPacket {
    private final boolean forself;
    private final int itemId;
    private final boolean increase;
    private final int quantity;
    private final String image;

    /**
     * Creates a new packet of SEND_TRADE_ADD_ITEM.
     * @param forself Should send the packet to himself.
     * @param itemId The item's id.
     * @param quantity The quantity.
     * @param image The item's image.
     * @param increase The amount.
     */
    public PacketSendTradeAddItem(boolean forself, int itemId, boolean increase, int quantity, String image) {
        super(PacketOpcodes.SEND_TRADE_ADD_ITEM);
        this.forself = forself;
        this.itemId = itemId;
        this.increase = increase;
        this.quantity = quantity;
        this.image = image;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.forself);
        packet.writeShort((short) this.itemId);
        packet.writeBoolean(this.increase);
        packet.writeByte(this.quantity);
        packet.writeBoolean(!this.image.isEmpty());
        if (!this.image.isEmpty())
            packet.writeUTF(this.image, true);

        return packet;
    }
}
package org.transformice.packets.send.shop;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendVisualizeShopOutfit extends PSSendPacket {    private final int outfitId;
    private final String outfitLook;
    private final List<Object[]> outfitItems;

    /**
     * Creates a new packet of SEND_VISUALIZE_SHOP_OUTFIT.
     * @param outfitId The outfit's id.
     * @param outfitLook The outfit's look.
     * @param outfitItems The outfit's items.
     */
    public PacketSendVisualizeShopOutfit(int outfitId, String outfitLook, List<Object[]> outfitItems) {
        super(PacketOpcodes.SEND_VISUALIZE_SHOP_OUTFIT);
        this.outfitId = outfitId;
        this.outfitLook = outfitLook;
        this.outfitItems = outfitItems;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        int priceBefore = 0;
        int priceAfter = 0;

        packet.writeShort((short)this.outfitId);
        packet.writeUnsignedByte(20);
        packet.writeUTF(this.outfitLook, true);
        packet.writeByte(this.outfitItems.size() + 1);

        // The fur.
        packet.writeInt(-1);
        packet.writeUnsignedByte(1);
        packet.writeUnsignedShort(0);
        packet.writeUnsignedShort(0);
        packet.writeUnsignedByte(3);
        packet.writeUnsignedShort(0);
        packet.writeUnsignedShort(0);

        for (Object[] outfitItem : this.outfitItems) {
            packet.writeInt((int)outfitItem[2]);
            packet.writeUnsignedByte((boolean)outfitItem[3] ? 0 : 1);
            packet.writeUnsignedShort((int)outfitItem[5]);
            packet.writeUnsignedShort((int)outfitItem[6]);
            packet.writeUnsignedByte((boolean)outfitItem[4] ? 3 : String.valueOf(outfitItem[1]).isEmpty() ? 0 : 1);
            packet.writeUnsignedShort(0);
            packet.writeUnsignedShort(0);
            if (!(boolean)outfitItem[3]) {
                priceBefore += (int)outfitItem[5];
                priceAfter += (int)outfitItem[6];
            }
        }

        packet.writeShort((short)priceBefore);
        packet.writeShort((short)priceAfter);
        return packet;
    }
}
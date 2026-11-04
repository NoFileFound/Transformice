package org.transformice.packets.send.shop;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopLoadItemSprites extends PSSendPacket {
    private final List<Integer> spritesList;

    /**
     * Sends a new packet of SEND_SHOP_LOAD_ITEM_SPRITES.
     * @param spritesList The sprites list.
     */
    public PacketSendShopLoadItemSprites(List<Integer> spritesList) {
        super(PacketOpcodes.SEND_SHOP_LOAD_ITEM_SPRITES);
        this.spritesList = spritesList;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.spritesList.size());
        for (Integer sprite : this.spritesList)
            packet.writeInt128(sprite);

        return packet;
    }
}
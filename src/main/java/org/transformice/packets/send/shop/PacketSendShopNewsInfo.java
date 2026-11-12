package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopNewsInfo extends PSSendPacket {
    private final String fileId;

    /**
     * Creates a new packet SEND_SHOP_NEWS_INFO.
     * @param fileId The shop's news file id.
     */
    public PacketSendShopNewsInfo(String fileId) {
        super(PacketOpcodes.SEND_SHOP_NEWS_INFO);
        this.fileId = fileId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(fileId, true);
    }
}
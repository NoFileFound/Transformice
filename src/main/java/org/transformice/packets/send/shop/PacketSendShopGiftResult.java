package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopGiftResult extends PSSendPacket {
    private final int type;
    private final String playerName;

    /**
     * Creates a new packet of SEND_SHOP_GIFT_RESULT.
     * @param type The result's type.
     * @param playerName The player's name.
     */
    public PacketSendShopGiftResult(int type, String playerName) {
        super(PacketOpcodes.SEND_SHOP_GIFT_RESULT);
        this.type = type;
        this.playerName = playerName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.type);
        packet.writeUTF(this.playerName, true);
        packet.writeByte(0);
        packet.writeInt(0);
        return packet;
    }
}
package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopGift extends PSSendPacket {
    private final int lastGiftId;
    private final String playerName;
    private final String playerLook;
    private final boolean isShamanItem;
    private final int itemId;
    private final String message;
    private final boolean sendMessage;

    /**
     * Creates a new packet of SEND_SHOP_GIFT.
     * @param lastGiftId The last gift id.
     * @param playerName The player's name.
     * @param playerLook The player's look.
     * @param isShamanItem Is the item shaman or regular.
     * @param itemId The item's id.
     * @param message The gift's message.
     * @param sendMessage is the message sent or received.
     */
    public PacketSendShopGift(int lastGiftId, String playerName, String playerLook, boolean isShamanItem, int itemId, String message, boolean sendMessage) {
        super(PacketOpcodes.SEND_SHOP_GIFT);
        this.lastGiftId = lastGiftId;
        this.playerName = playerName;
        this.playerLook = playerLook;
        this.isShamanItem = isShamanItem;
        this.itemId = itemId;
        this.message = message;
        this.sendMessage = sendMessage;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.lastGiftId);
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.playerLook, true);
        packet.writeBoolean(this.isShamanItem);
        packet.writeInt(this.itemId);
        packet.writeUTF(this.message, true);
        packet.writeBoolean(this.sendMessage);
        return packet;
    }
}
package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopGift implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopGift(int lastGiftId, String playerName, String playerLook, boolean is_shaman_item, int item_id, String message, boolean sendMessage) {
        this.byteArray.writeInt(lastGiftId);
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(playerLook);
        this.byteArray.writeBoolean(is_shaman_item);
        this.byteArray.writeInt(item_id);
        this.byteArray.writeString(message);
        this.byteArray.writeBoolean(sendMessage);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TradeAddConsumable implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TradeAddConsumable(boolean forself, short itemId, boolean increase, int quantity, boolean isImage, String image) {
        this.byteArray.writeBoolean(forself);
        this.byteArray.writeShort(itemId);
        this.byteArray.writeBoolean(increase);
        this.byteArray.writeByte(quantity);
        this.byteArray.writeBoolean(isImage);
        if(isImage) {
            this.byteArray.writeString(image);
        }
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
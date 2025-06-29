package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopSpecialOffer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopSpecialOffer(boolean isSale, boolean isRegularItem, int itemId, boolean enable, long endDate, int discount) {
        this.byteArray.writeBoolean(isSale);
        this.byteArray.writeBoolean(isRegularItem);
        this.byteArray.writeInt(itemId);
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeInt(endDate);
        this.byteArray.writeByte(discount);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
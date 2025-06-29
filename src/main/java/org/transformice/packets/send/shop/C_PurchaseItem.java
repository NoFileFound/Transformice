package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PurchaseItem implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PurchaseItem(long itemId) {
        this.byteArray.writeInt(itemId);
        this.byteArray.writeByte(4);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopCurrency implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopCurrency(int cheeses, int strawberries) {
        this.byteArray.writeInt(cheeses);
        this.byteArray.writeInt(strawberries);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
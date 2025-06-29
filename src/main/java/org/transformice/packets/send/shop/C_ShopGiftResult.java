package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopGiftResult implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopGiftResult(int type, String playerName) {
        this.byteArray.writeByte(type);
        this.byteArray.writeString(playerName);
        this.byteArray.writeByte(0);
        this.byteArray.writeInt(0);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
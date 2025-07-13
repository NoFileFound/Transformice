package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetShopNews implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetShopNews(String newsFileId) {
        this.byteArray.writeString(newsFileId);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 100;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
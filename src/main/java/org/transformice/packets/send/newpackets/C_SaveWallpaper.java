package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SaveWallpaper implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SaveWallpaper(String fileId) {
        this.byteArray.writeString(fileId);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 47;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
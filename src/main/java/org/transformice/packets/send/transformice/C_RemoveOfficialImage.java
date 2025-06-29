package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveOfficialImage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveOfficialImage(String imageId) {
        this.byteArray.writeString(imageId);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
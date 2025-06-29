package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveTextArea implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveTextArea(int imageId) {
        this.byteArray.writeInt(imageId);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
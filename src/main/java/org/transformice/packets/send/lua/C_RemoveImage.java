package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveImage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveImage(int imageId, boolean fadeOut) {
        this.byteArray.writeInt(imageId);
        this.byteArray.writeBoolean(fadeOut);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
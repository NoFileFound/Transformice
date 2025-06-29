package org.transformice.packets.send.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OpenLink implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenLink(String url) {
        this.byteArray.writeString(url);
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
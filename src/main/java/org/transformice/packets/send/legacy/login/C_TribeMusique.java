package org.transformice.packets.send.legacy.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TribeMusique implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TribeMusique(String url) {
        this.byteArray.writeString(url, false);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.legacy.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OldCasierWindow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OldCasierWindow(String content) {
        this.byteArray.writeString(content, false);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 23;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
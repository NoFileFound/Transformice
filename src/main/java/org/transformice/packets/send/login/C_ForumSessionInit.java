package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ForumSessionInit implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ForumSessionInit(String token) {
        this.byteArray.writeString(token);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 41;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
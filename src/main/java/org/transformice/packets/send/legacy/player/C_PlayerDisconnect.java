package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerDisconnect implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerDisconnect(int sessionId) {
        this.byteArray.writeString(String.valueOf(sessionId), false);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
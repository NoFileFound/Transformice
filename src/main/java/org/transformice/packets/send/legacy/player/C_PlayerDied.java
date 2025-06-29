package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerDied implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerDied(int sessionId, int score) {
        this.byteArray.writeString(String.valueOf(sessionId), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString("0", false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(score), false);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
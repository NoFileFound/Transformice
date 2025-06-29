package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetPlayerScore implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetPlayerScore(int sessionId, int score) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort((short)score);
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
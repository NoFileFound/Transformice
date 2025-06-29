package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerVictory implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerVictory(int sessionId, int type, int score, int place, int time) {
        this.byteArray.writeByte(type);
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort((short)score);
        this.byteArray.writeByte(place);
        this.byteArray.writeUnsignedShort(time);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
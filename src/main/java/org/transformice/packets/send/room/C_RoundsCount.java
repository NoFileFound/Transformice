package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RoundsCount implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoundsCount(int roundId, int playerSession) {
        this.byteArray.writeByte(roundId);
        this.byteArray.writeInt(playerSession);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
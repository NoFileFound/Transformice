package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MulodromeResult implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MulodromeResult(int mulRound, int blueCount, int redCount) {
        this.byteArray.writeByte(mulRound);
        this.byteArray.writeShort((short)blueCount);
        this.byteArray.writeShort((short)redCount);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MulodromeWinner implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MulodromeWinner(int blueCount, int redCount) {
        this.byteArray.writeByte(blueCount == redCount ? 2 : (blueCount < redCount ? 1 : 0));
        this.byteArray.writeShort((short)blueCount);
        this.byteArray.writeShort((short)redCount);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
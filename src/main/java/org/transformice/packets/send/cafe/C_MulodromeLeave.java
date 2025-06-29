package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MulodromeLeave implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MulodromeLeave(int team, int position) {
        this.byteArray.writeByte(team);
        this.byteArray.writeByte(position);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
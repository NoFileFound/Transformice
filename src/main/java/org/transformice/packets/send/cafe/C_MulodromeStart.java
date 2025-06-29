package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MulodromeStart implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MulodromeStart(boolean isSelf) {
        this.byteArray.writeBoolean(isSelf);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
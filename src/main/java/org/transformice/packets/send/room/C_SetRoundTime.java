package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetRoundTime implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetRoundTime(int seconds) {
        this.byteArray.writeShort((short)seconds);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
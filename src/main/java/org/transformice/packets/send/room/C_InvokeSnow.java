package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_InvokeSnow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_InvokeSnow(boolean enable, int power) {
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeShort((short)power);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 23;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
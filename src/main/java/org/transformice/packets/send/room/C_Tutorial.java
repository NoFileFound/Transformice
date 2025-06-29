package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Tutorial implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Tutorial(int value) {
        this.byteArray.writeByte(value);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 90;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
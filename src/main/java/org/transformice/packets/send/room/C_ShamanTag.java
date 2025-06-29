package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanTag implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanTag(int type, short x, short y) {
        this.byteArray.writeByte(type);
        this.byteArray.writeShort(x);
        this.byteArray.writeShort(y);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
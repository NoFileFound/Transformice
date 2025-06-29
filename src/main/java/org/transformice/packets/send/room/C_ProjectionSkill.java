package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ProjectionSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ProjectionSkill(short x, short y, short dir) {
        this.byteArray.writeShort(x);
        this.byteArray.writeShort(y);
        this.byteArray.writeShort(dir);
    }

    @Override
    public int getC() {
        return 5;
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
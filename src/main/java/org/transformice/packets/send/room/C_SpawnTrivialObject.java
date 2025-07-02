package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnTrivialObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnTrivialObject(int objectId, int x, int y) {
        this.byteArray.writeByte(objectId);
        this.byteArray.writeShort((short) x);
        this.byteArray.writeShort((short) y);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
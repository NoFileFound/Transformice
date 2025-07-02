package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnSpirit implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnSpirit(int x, int y) {
        this.byteArray.writeInt(x);
        this.byteArray.writeInt(y);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 66;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
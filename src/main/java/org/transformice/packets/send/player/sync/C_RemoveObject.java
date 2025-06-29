package org.transformice.packets.send.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveObject(int objectId, boolean remove) {
        this.byteArray.writeInt(objectId);
        this.byteArray.writeBoolean(remove);
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
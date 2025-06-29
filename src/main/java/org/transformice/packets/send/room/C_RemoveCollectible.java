package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveCollectible implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveCollectible(int advId, int collectibleId) {
        this.byteArray.writeUnsignedByte(advId);
        this.byteArray.writeUnsignedShort(collectibleId);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 53;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
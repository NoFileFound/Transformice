package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddCollectible implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddCollectible(int advId, int individualId, int collectibleId, int posX, int posY) {
        this.byteArray.writeUnsignedByte(advId);
        this.byteArray.writeUnsignedShort(individualId);
        this.byteArray.writeUnsignedByte(collectibleId);
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 51;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddAdventureArea implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddAdventureArea(int advId, int areaId, int posX, int posY, int width, int height) {
        this.byteArray.writeUnsignedByte(advId);
        this.byteArray.writeUnsignedShort(areaId);
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
        this.byteArray.writeUnsignedShort(width);
        this.byteArray.writeUnsignedShort(height);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 54;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
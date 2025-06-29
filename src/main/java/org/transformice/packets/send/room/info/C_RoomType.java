package org.transformice.packets.send.room.info;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RoomType implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomType(int roomType) {
        this.byteArray.writeByte(roomType);
    }

    @Override
    public int getC() {
        return 7;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
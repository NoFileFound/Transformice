package org.transformice.packets.send.room.info;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RoomServer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomServer(int roomType) {
        this.byteArray.writeByte(roomType);
    }

    @Override
    public int getC() {
        return 7;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
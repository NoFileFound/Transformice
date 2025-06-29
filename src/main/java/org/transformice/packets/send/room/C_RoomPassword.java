package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RoomPassword implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomPassword(String roomName) {
        this.byteArray.writeString(roomName);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 39;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
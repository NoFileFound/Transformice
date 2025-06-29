package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetIndianaMouse implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetIndianaMouse(int sessionId, boolean enable) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(enable);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 46;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
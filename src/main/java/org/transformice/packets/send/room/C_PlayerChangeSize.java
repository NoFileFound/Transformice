package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerChangeSize implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerChangeSize(int sessionId, int size, boolean excludeShaman) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeUnsignedShort(size);
        this.byteArray.writeBoolean(excludeShaman);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
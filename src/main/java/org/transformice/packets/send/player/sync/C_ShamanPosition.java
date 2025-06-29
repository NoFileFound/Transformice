package org.transformice.packets.send.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanPosition implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanPosition(int sessionId, boolean direction) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(direction);
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
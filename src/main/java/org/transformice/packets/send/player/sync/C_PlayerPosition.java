package org.transformice.packets.send.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerPosition implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerPosition(int sessionId, boolean direction) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(direction);
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
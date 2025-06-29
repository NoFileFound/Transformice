package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanFly implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanFly(int sessionId, boolean isFlying) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(isFlying);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
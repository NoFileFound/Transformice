package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_VampireMode implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_VampireMode(int sessionId, boolean enable, boolean transmissible) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeBoolean(transmissible);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 66;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
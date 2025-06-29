package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RolloutSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RolloutSkill(int sessionId) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 5;
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
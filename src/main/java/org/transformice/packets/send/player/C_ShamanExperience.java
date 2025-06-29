package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanExperience implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanExperience(int level, int currentXp, int xpGoal) {
        this.byteArray.writeUnsignedShort(level);
        this.byteArray.writeInt(currentXp);
        this.byteArray.writeInt(xpGoal);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
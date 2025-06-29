package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanGainExperience implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanGainExperience(int xp) {
        this.byteArray.writeInt(xp);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
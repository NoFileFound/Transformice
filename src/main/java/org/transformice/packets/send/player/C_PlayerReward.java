package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerReward implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerReward(int type, int amount) {
        this.byteArray.writeByte(type);
        this.byteArray.writeInt(amount);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
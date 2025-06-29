package org.transformice.packets.send.level;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanEarnedExp implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanEarnedExp(int currentXp, int levelXp) {
        this.byteArray.writeShort((short)currentXp);
        this.byteArray.writeShort((short)levelXp);
    }

    @Override
    public int getC() {
        return 24;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
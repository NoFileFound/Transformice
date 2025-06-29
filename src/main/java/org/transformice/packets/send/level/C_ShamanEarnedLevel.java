package org.transformice.packets.send.level;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanEarnedLevel implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanEarnedLevel(String playerName, int level) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeUnsignedShort(level - 1);
    }

    @Override
    public int getC() {
        return 24;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetMonsterSpeed implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetMonsterSpeed(int monsterId, int speed) {
        this.byteArray.writeInt(monsterId);
        this.byteArray.writeInt(speed);
    }

    @Override
    public int getC() {
        return 26;
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
package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DespawnMonster implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DespawnMonster(int monsterId) {
        this.byteArray.writeInt(monsterId);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
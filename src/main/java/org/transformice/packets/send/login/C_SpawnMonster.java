package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnMonster implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnMonster(int monsterId, int x, int y, String monsterType) {
        this.byteArray.writeInt(monsterId);
        this.byteArray.writeInt(x);
        this.byteArray.writeInt(y);
        this.byteArray.writeString(monsterType);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
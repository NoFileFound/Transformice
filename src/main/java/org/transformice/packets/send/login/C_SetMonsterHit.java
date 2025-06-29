package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetMonsterHit implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetMonsterHit(int monsterId, boolean right) {
        this.byteArray.writeInt(monsterId);
        this.byteArray.writeBoolean(right);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
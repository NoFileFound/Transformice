package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_GravitationalSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_GravitationalSkill(int velX, int velY, int milliseconds) {
        this.byteArray.writeInt(milliseconds);
        this.byteArray.writeInt(velX * 1000);
        this.byteArray.writeInt(velY * 1000);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 28;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
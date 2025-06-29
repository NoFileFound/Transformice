package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Explosion implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Explosion(int posX, int posY, int power, int distance, boolean miceOnly) {
        this.byteArray.writeInt128(posX);
        this.byteArray.writeInt128(posY);
        this.byteArray.writeInt128(power);
        this.byteArray.writeInt128(distance);
        this.byteArray.writeBoolean(miceOnly);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DisplayParticle implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DisplayParticle(int id, int x, int y, int velX, int velY, int accX, int accY) {
        this.byteArray.writeByte(id);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(velX * 100L);
        this.byteArray.writeInt128(velY * 100L);
        this.byteArray.writeInt128(accX * 100L);
        this.byteArray.writeInt128(accY * 100L);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 27;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
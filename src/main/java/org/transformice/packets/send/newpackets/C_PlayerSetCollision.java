package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerSetCollision implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerSetCollision(int sessionId, int preset, int personalCat, int collidesCat) {
        this.byteArray.writeInt128(sessionId);
        this.byteArray.writeInt128(preset);
        this.byteArray.writeInt128(collidesCat);
        this.byteArray.writeInt128(personalCat);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 43;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
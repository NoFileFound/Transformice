package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetGravityScale implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetGravityScale(int sessionId, int x, int y) {
        this.byteArray.writeInt128(sessionId);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 32;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
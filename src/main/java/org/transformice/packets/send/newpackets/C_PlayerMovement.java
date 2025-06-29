package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerMovement implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerMovement(int sessionId, ByteArray data) {
        this.byteArray.writeInt128(sessionId);
        this.byteArray.writeBytes(data.toByteArray());
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 48;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
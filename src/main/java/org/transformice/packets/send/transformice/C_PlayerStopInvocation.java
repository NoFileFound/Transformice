package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerStopInvocation implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerStopInvocation(int sessionId) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
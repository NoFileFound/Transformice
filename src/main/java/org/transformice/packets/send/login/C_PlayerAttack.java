package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerAttack implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerAttack(int sessionId) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
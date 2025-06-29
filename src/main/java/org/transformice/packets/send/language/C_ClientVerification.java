package org.transformice.packets.send.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ClientVerification implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ClientVerification(int verificationKey) {
        this.byteArray.writeInt(verificationKey);
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
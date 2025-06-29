package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_VerifiedEmailAddress implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_VerifiedEmailAddress(String emailAddress, boolean verified) {
        this.byteArray.writeBoolean(verified);
        this.byteArray.writeString(emailAddress);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
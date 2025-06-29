package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_VerifyEmailAddressWinExclusivePrize implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_VerifyEmailAddressWinExclusivePrize(String emailAddress) {
        this.byteArray.writeString(emailAddress);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 64;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
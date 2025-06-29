package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SendEmailAddressCodeToChangePassword implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SendEmailAddressCodeToChangePassword(int errorCode) {
        this.byteArray.writeByte(errorCode);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
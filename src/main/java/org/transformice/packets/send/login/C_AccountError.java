package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AccountError implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AccountError(int errorCode) {
        this.byteArray.writeByte(errorCode);
        this.byteArray.writeString("");
        this.byteArray.writeString("");
    }

    public C_AccountError(String suggestedNames) {
        this.byteArray.writeByte(11);
        this.byteArray.writeString(suggestedNames);
        this.byteArray.writeString("");
    }

    public C_AccountError(int errorCode, String emailAddress2FA) {
        this.byteArray.writeByte(errorCode);
        this.byteArray.writeString("");
        this.byteArray.writeString(emailAddress2FA);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
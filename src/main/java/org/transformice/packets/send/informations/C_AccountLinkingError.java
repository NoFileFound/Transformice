package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AccountLinkingError implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AccountLinkingError(int errorType) {
        this.byteArray.writeByte(errorType);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
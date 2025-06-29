package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CodeValidatedEmailAddress implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CodeValidatedEmailAddress(boolean isSuccessful) {
        this.byteArray.writeBoolean(isSuccessful);
    }

    @Override
    public int getC() {
        return 28;
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
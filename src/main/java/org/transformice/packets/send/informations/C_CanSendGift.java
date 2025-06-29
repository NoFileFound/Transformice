package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CanSendGift implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CanSendGift(boolean canSend) {
        this.byteArray.writeBoolean(canSend);
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
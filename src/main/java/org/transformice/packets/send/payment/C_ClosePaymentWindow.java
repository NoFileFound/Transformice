package org.transformice.packets.send.payment;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ClosePaymentWindow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ClosePaymentWindow(int paymentTokenId) {
        this.byteArray.writeShort((short) paymentTokenId);
    }

    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
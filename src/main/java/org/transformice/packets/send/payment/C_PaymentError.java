package org.transformice.packets.send.payment;

// Imports
import org.transformice.packets.SendPacket;

public final class C_PaymentError implements SendPacket {
    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 60;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
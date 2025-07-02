package org.transformice.packets.send.payment;

// Imports
import org.transformice.packets.SendPacket;

public class C_ShowPurchasing implements SendPacket {
    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
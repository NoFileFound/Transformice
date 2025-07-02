package org.transformice.packets.send.shop;

// Imports
import org.transformice.packets.SendPacket;

public class C_NewHatAvailable implements SendPacket {
    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
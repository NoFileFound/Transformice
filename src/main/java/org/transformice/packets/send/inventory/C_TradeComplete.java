package org.transformice.packets.send.inventory;

// Imports
import org.transformice.packets.SendPacket;

public final class C_TradeComplete implements SendPacket {
    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
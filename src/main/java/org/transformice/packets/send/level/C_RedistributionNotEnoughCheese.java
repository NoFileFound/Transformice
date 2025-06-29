package org.transformice.packets.send.level;

// Imports
import org.transformice.packets.SendPacket;

public final class C_RedistributionNotEnoughCheese implements SendPacket {
    @Override
    public int getC() {
        return 24;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
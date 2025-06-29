package org.transformice.packets.send.cafe;

// Imports
import org.transformice.packets.SendPacket;

public final class C_MulodromeEnd implements SendPacket {
    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
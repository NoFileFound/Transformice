package org.transformice.packets.send.login;

// Imports
import org.transformice.packets.SendPacket;

public final class C_AutoWin implements SendPacket {
    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
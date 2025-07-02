package org.transformice.packets.send.legacy.login;

// Imports
import org.transformice.packets.SendPacket;

public final class C_OpenFacebookPage implements SendPacket {
    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
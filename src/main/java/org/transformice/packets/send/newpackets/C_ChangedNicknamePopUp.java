package org.transformice.packets.send.newpackets;

// Imports
import org.transformice.packets.SendPacket;

public final class C_ChangedNicknamePopUp implements SendPacket {
    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
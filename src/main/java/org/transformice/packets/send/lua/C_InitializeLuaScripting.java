package org.transformice.packets.send.lua;

// Imports
import org.transformice.packets.SendPacket;

public final class C_InitializeLuaScripting implements SendPacket {
    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
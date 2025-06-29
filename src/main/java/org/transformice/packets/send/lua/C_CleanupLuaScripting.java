package org.transformice.packets.send.lua;

// Imports
import org.transformice.packets.SendPacket;

public final class C_CleanupLuaScripting implements SendPacket {
    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
package org.transformice.packets.send.room;

// Imports
import org.transformice.packets.SendPacket;

public final class C_RemoveAllObjectsSkill implements SendPacket {
    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 32;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
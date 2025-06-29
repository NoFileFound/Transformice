package org.transformice.packets.send.legacy.editor;

// Imports
import org.transformice.packets.SendPacket;

public final class C_MapValidated implements SendPacket {
    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
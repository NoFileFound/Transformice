package org.transformice.packets.send.modopwet;

// Imports
import org.transformice.packets.SendPacket;

public class C_ModopwetEnableTool implements SendPacket {
    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return new byte[0];
    }
}
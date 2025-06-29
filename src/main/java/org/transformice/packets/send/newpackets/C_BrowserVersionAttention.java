package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BrowserVersionAttention implements SendPacket {
    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return new ByteArray().writeString("").toByteArray();
    }
}
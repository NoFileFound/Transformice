package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EnableNightMode implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EnableNightMode(boolean enable) {
        this.byteArray.writeBoolean(enable);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 33;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
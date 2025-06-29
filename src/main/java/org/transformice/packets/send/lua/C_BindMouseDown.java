package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BindMouseDown implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BindMouseDown(boolean active) {
        this.byteArray.writeBoolean(active);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
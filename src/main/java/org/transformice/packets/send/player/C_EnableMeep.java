package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EnableMeep implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EnableMeep(boolean enable) {
        this.byteArray.writeBoolean(enable);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 39;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
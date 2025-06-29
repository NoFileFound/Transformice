package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_FreezePlayer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_FreezePlayer(boolean freeze) {
        this.byteArray.writeBoolean(freeze);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 66;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
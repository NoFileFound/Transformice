package org.transformice.packets.send.transformation;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EnableTransformation implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EnableTransformation(boolean enable) {
        this.byteArray.writeBoolean(enable);
    }

    @Override
    public int getC() {
        return 27;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
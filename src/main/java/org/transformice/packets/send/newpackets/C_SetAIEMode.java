package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetAIEMode implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetAIEMode(boolean enable, int sensibility) {
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeInt128(sensibility * 1000);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
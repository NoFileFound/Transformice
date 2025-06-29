package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChangeRoundBackground implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeRoundBackground(int color) {
        this.byteArray.writeInt(color);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 25;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
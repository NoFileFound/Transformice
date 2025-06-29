package org.transformice.packets.send.ui;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_HideTopUi implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_HideTopUi(boolean hide) {
        this.byteArray.writeBoolean(hide);
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
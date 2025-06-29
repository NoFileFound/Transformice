package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TradeComplete implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TradeComplete() {}

    @Override
    public int getC() {
        return 31;
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
package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_GiveCurrency implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_GiveCurrency(int currency, int amount) {
        this.byteArray.writeByte(currency);
        this.byteArray.writeByte(amount);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
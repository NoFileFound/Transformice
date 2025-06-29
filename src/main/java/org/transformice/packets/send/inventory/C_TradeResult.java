package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TradeResult implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TradeResult(String playerName, int result) {
        this.byteArray.writeString(playerName).writeByte(result);
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
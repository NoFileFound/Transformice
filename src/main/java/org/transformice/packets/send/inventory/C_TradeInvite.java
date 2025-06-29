package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TradeInvite implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TradeInvite(int sessionId) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
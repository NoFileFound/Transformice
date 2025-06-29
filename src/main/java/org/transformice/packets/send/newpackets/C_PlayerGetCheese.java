package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerGetCheese implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerGetCheese(int sessionId, int cheeses) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(cheeses);
    }

    @Override
    public int getC() {
        return 144;
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
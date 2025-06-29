package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerDemoteShaman implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerDemoteShaman(int sessionId, int cheeses) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
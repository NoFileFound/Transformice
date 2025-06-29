package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BridgeBreak implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BridgeBreak(short bridgeCode) {
        this.byteArray.writeShort(bridgeCode);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 24;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
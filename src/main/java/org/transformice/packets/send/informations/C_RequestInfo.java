package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RequestInfo implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RequestInfo(String userAgentUrl) {
        this.byteArray.writeString(userAgentUrl);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PingPacket implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PingPacket(int payload, boolean isMainServer) {
        this.byteArray.writeByte(payload);
        this.byteArray.writeBoolean(isMainServer);
    }

    @Override
    public int getC() {
        return 28;
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
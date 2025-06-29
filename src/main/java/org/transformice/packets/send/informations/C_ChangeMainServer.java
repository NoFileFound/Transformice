package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChangeMainServer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeMainServer(String ipAddress) {
        this.byteArray.writeString(ipAddress);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 98;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
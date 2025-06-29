package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ServerRestart implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ServerRestart(int seconds) {
        this.byteArray.writeInt(seconds * 1000);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 88;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
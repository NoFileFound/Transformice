package org.transformice.packets.send.tribulle;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RejoindreCanalPublique implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RejoindreCanalPublique(String canal) {
        this.byteArray.writeString(canal);
    }

    @Override
    public int getC() {
        return 60;
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
package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_StopMusic implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_StopMusic(String channel, boolean disable) {
        this.byteArray.writeString(channel);
        this.byteArray.writeBoolean(disable);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 41;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
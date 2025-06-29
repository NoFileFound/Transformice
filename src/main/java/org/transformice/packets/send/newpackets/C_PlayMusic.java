package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayMusic implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayMusic(String fileName, String channel, int volume, boolean loop, boolean fade) {
        this.byteArray.writeString(fileName);
        this.byteArray.writeString(channel);
        this.byteArray.writeShort((short)volume);
        this.byteArray.writeBoolean(loop);
        this.byteArray.writeBoolean(fade);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlaySound implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlaySound(String fileName, int volume) {
        this.byteArray.writeString(fileName);
        this.byteArray.writeShort((short)volume);
        this.byteArray.writeBoolean(false);
    }

    public C_PlaySound(String fileName, int volume, int posX, int posY) {
        this.byteArray.writeString(fileName);
        this.byteArray.writeShort((short)volume);
        this.byteArray.writeBoolean(true);
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
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
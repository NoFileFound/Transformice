package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddTrap implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddTrap(int type, int posX, int posY, int width, int heigh, int percentage) {
        this.byteArray.writeShort((short)type);
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
        this.byteArray.writeShort((short)width);
        this.byteArray.writeShort((short)heigh);
        this.byteArray.writeShort((short)percentage);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 47;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
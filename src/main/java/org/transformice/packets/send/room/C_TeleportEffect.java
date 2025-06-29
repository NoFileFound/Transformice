package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TeleportEffect implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TeleportEffect(int type, int posX, int posY) {
        this.byteArray.writeByte(type);
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
    }

    @Override
    public int getC() {
        return 5;
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
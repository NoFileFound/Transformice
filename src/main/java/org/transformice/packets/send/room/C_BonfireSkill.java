package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BonfireSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BonfireSkill(int posX, int posY, int seconds) {
        this.byteArray.writeShort((short)posX);
        this.byteArray.writeShort((short)posY);
        this.byteArray.writeByte(seconds);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 45;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
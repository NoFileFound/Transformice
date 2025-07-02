package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_GrapnelSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_GrapnelSkill(int session, int x, int y) {
        this.byteArray.writeInt(session);
        this.byteArray.writeShort((short) (x * 30));
        this.byteArray.writeShort((short) (y * 30));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 37;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
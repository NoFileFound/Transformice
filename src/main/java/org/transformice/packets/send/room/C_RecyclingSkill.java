package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RecyclingSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RecyclingSkill(int jointId) {
        this.byteArray.writeShort((short)jointId);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 27;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
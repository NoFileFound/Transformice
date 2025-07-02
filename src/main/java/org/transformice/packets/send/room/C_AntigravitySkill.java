package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AntigravitySkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AntigravitySkill(int objectId, int arg) {
        this.byteArray.writeInt(objectId);
        this.byteArray.writeShort((short)(arg * 100));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
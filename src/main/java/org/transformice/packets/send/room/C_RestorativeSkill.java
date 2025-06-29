package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RestorativeSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RestorativeSkill(int objectId, int id) {
        this.byteArray.writeInt(objectId);
        this.byteArray.writeInt(id);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
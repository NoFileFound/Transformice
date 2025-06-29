package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DemolitionSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DemolitionSkill(int objectId) {
        this.byteArray.writeInt(objectId);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ConvertSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ConvertSkill(int objectId, boolean arg) {
        this.byteArray.writeInt(objectId);
        this.byteArray.writeBoolean(arg);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
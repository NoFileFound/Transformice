package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_HandymouseSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_HandymouseSkill(int handyMouseByte1, int objectId1, int handyMouseByte2, int objectId2) {
        this.byteArray.writeByte(handyMouseByte1);
        this.byteArray.writeInt(objectId1);
        this.byteArray.writeByte(handyMouseByte2);
        this.byteArray.writeInt(objectId2);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 35;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
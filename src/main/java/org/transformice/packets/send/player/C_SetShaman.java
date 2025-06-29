package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetShaman implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetShaman(int sessionId,  int shamanMode, int level, int shamanBadge,  int startedObjectId, boolean withoutSkills) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(shamanMode);
        this.byteArray.writeShort((short)level);
        this.byteArray.writeUnsignedShort(shamanBadge);
        this.byteArray.writeBoolean(withoutSkills);
        this.byteArray.writeInt(startedObjectId);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
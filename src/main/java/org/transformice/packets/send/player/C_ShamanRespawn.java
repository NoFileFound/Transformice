package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanRespawn implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanRespawn(int sessionId, int shamanType, int shamanBadge, int shamanLevel, boolean shamanNoSkills) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(shamanType);
        this.byteArray.writeByte(shamanBadge);
        this.byteArray.writeUnsignedShort(shamanLevel);
        this.byteArray.writeBoolean(shamanNoSkills);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
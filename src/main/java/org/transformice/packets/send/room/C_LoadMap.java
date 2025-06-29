package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Room;
import org.transformice.packets.SendPacket;

public final class C_LoadMap implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoadMap(int mapCode, int roomPlayers, int roundId, byte[] mapXml, String mapAuthor, int mapCategory, boolean isMirrored, boolean hasConj, boolean hasFallDamage, Room.RoomDetails roomDetails) {
        this.byteArray.writeInt(mapCode);
        this.byteArray.writeShort((short)roomPlayers);
        this.byteArray.writeByte(roundId);
        this.byteArray.writeInt(mapXml.length);
        this.byteArray.writeBytes(mapXml);
        this.byteArray.writeString(mapAuthor);
        this.byteArray.writeByte(mapCategory);
        this.byteArray.writeBoolean(isMirrored);
        this.byteArray.writeBoolean(hasConj);
        this.byteArray.writeBoolean(roomDetails != null && roomDetails.withMiceCollisions);
        this.byteArray.writeBoolean((roomDetails != null && roomDetails.withFallDamage) || hasFallDamage);
        this.byteArray.writeInt((roomDetails != null) ? roomDetails.miceWeight : 0);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
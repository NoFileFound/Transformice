package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LoadMap implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoadMap(String mapXML, int mapVotesYes, int mapVotesNo, int mapCategory) {
        this.byteArray.writeString(mapXML, false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(mapVotesYes), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(mapVotesNo), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(mapCategory), false);
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
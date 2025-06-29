package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MapVotePopup implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MapVotePopup(String mapName, int mapYesVotes, int mapNoVotes) {
        this.byteArray.writeString(mapName, false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(mapYesVotes), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(mapNoVotes), false);
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
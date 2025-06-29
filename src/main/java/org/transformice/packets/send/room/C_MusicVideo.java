package org.transformice.packets.send.room;

// Imports
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MusicVideo implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MusicVideo(Map<String, String> musicInfo, short musicTime) {
        this.byteArray.writeString(musicInfo.get("VideoID"));
        this.byteArray.writeString(musicInfo.get("Title"));
        this.byteArray.writeShort(musicTime);
        this.byteArray.writeString(musicInfo.get("By"));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 72;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
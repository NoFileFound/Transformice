package org.transformice.packets.send.room;

// Imports
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MusicPlaylist implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MusicPlaylist(List<Map<String, String>> musicVideos) {
        this.byteArray.writeShort((short)musicVideos.size());
        for(var musicVideo : musicVideos) {
            this.byteArray.writeString(musicVideo.get("Title")).writeString(musicVideo.get("By"));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 73;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
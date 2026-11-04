package org.transformice.packets.send.room;

// Imports
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMusicPlaylist extends PSSendPacket {
    private final List<Map<String, String>> musicVideos;

    /**
     * Creates a new packet of SEND_MUSIC_PLAYLIST.
     * @param musicVideos The music's playlist.
     */
    public PacketSendMusicPlaylist(List<Map<String, String>> musicVideos) {
        super(PacketOpcodes.SEND_MUSIC_PLAYLIST);
        this.musicVideos = musicVideos;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeShort((short) this.musicVideos.size());
        for (Map<String, String> musicVideo : this.musicVideos) {
            packet.writeUTF(musicVideo.get("Title"), true);
            packet.writeUTF(musicVideo.get("By"), true);
        }

        return packet;
    }
}
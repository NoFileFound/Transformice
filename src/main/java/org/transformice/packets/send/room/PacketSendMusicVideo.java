package org.transformice.packets.send.room;

// Imports
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMusicVideo extends PSSendPacket {
    private final Map<String, String> musicInfo;
    private final int musicTime;

    /**
     * Creates a new packet of SEND_MUSIC_VIDEO.
     * @param musicInfo The music information.
     * @param musicTime The music time.
     */
    public PacketSendMusicVideo(Map<String, String> musicInfo, int musicTime) {
        super(PacketOpcodes.SEND_MUSIC_VIDEO);
        this.musicInfo = musicInfo;
        this.musicTime = musicTime;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.musicInfo.get("VideoID"), true);
        packet.writeUTF(this.musicInfo.get("Title"), true);
        packet.writeShort((short) this.musicTime);
        packet.writeUTF(this.musicInfo.get("By"), true);
        return packet;
    }
}
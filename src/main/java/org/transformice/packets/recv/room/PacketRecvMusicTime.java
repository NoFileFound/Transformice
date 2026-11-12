package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.room.PacketSendMusicVideo;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MUSIC_TIME)
public final class PacketRecvMusicTime extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int time = psData.readInt();
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (room.getRoomType() != RoomType.MUSIC)
            return;

        if (!room.getMusicPlaylist().isEmpty()) {
            room.musicTime = time;
            String duration = room.getMusicPlaylist().getFirst().get("Duration");
            if (time >= Integer.parseInt(duration) - 5 && room.canChangeMusic) {
                room.canChangeMusic = false;
                room.getMusicPlaylist().removeFirst();
                if (!room.getMusicPlaylist().isEmpty()) {
                    room.musicTime = 0;
                    room.canChangeMusic = true;
                    room.getPlayers().values().forEach(player -> player.broadcastPacket(new PacketSendMusicVideo(room.getMusicPlaylist().getFirst(), room.musicTime)));
                } else {
                    room.isPlayingMusic = false;
                    room.musicTime = 0;
                }
            }
        }
    }
}
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
import org.transformice.packets.send.room.PacketSendMusicPlaylist;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MUSIC_PLAYLIST)
public final class PacketRecvMusicPlaylist extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (room.getRoomType() != RoomType.MUSIC)
            return;

        client.getPlayer().broadcastPacket(new PacketSendMusicPlaylist(room.getMusicPlaylist()));
    }
}
package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CHOOSE_NEXT_SHAMAN_STRM_ROOM)
public final class PacketRecvChooseNextShamanSTRMRoom extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        GameRoom playerRoom = client.getPlayer().getPlayerRoom();
        if (!playerRoom.getRoomNameSuffix().startsWith("*strm_" + player.getPlayerName()))
            return;

        playerRoom.forceNextShaman = player;
    }
}
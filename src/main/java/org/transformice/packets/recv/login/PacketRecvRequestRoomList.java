package org.transformice.packets.recv.login;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;
import org.transformice.packets.send.login.PacketSendRoomList;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_REQUEST_ROOM_LIST)
public final class PacketRecvRequestRoomList extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int gameModeId = psData.readByte();
        GamePlayer player = client.getPlayer();

        player.lastGameModeId = gameModeId;
        player.broadcastPacket(new PacketSendRoomList(gameModeId, gameServer.getRoomsByGameMode(gameModeId, player.getPlayerCommunity())));
    }
}
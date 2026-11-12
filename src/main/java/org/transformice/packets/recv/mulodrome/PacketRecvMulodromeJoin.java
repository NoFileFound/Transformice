package org.transformice.packets.recv.mulodrome;

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

// Packets
import org.transformice.packets.send.mulodrome.PacketSendMulodromeJoin;
import org.transformice.packets.send.mulodrome.PacketSendMulodromeLeave;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MULODROME_JOIN)
public final class PacketRecvMulodromeJoin extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int team = psData.readByte();
        int position = psData.readByte();
        GamePlayer player = client.getPlayer();
        GameRoom room = player.getPlayerRoom();
        int[] oldPosition = player.getMulodromePosition();
        if (oldPosition[0] != -1 || oldPosition[1] != -1) {
            room.broadcastPacket(new PacketSendMulodromeLeave(oldPosition[0], oldPosition[1]));
            if (oldPosition[0] == 1)
                room.getMulodromeRedTeam().remove(player.getPlayerName());
            else
                room.getMulodromeBlueTeam().remove(player.getPlayerName());
        }

        player.setMulodromePosition(team, position);
        room.broadcastPacket(new PacketSendMulodromeJoin(team, position, player.getSessionId(), player.getPlayerName(), player.getPlayerData().tribeName));
        if (team == 1)
            room.getMulodromeRedTeam().add(player.getPlayerName());
        else
            room.getMulodromeBlueTeam().add(player.getPlayerName());
    }
}
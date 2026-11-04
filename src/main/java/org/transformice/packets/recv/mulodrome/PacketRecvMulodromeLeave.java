package org.transformice.packets.recv.mulodrome;

// Imports
import java.util.Set;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.mulodrome.PacketSendMulodromeLeave;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MULODROME_LEAVE)
public final class PacketRecvMulodromeLeave extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GameRoom room = client.getPlayer().getPlayerRoom();
        int team = psData.readByte();
        int position = psData.readByte();
        room.broadcastPacket(new PacketSendMulodromeLeave(team, position));
        Set<String> players = team == 1 ? room.getMulodromeRedTeam() : room.getMulodromeBlueTeam();
        for (String playerName : players) {
            var player = room.getPlayers().get(playerName);
            if (player != null && player.getMulodromePosition()[1] == position) {
                players.remove(playerName);
                break;
            }
        }
    }
}
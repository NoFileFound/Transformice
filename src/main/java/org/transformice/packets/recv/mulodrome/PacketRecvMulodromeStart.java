package org.transformice.packets.recv.mulodrome;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MULODROME_START)
public final class PacketRecvMulodromeStart extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (!room.getMulodromeRedTeam().isEmpty() && !room.getMulodromeBlueTeam().isEmpty() && !room.getIsMulodrome())
            room.sendMulodromeStart();
    }
}
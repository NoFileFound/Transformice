package org.transformice.packets.recv.player;

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
@PSPacket(id = PacketOpcodes.RECV_PLAYER_SET_VAMPIRE)
public final class PacketRecvPlayerSetVampire extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int sessionId = psData.readInt();
        GamePlayer player = client.getPlayer();
        if (player.getSessionId() != sessionId)
            return;

        GameRoom room = player.getPlayerRoom();
        if (room.getVampireCount() == 0)
            return;

        room.sendAddVampire(player);
        room.getParseLua().handleEventPlayerVampire(player, player.getPlayerName());
    }
}
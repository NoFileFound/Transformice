package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_GET_COLLECTIBLE)
public final class PacketRecvPlayerGetCollectible extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int roundId = psData.readByte();
        if (roundId == client.getPlayer().getPlayerRoom().getLastRoundId())
            gameServer.getParseEvents().handleEventPlayerGetCollectible(client.getPlayer(), psData.readUnsignedByte(), psData.readUnsignedShort(), psData.readShort(), psData.readShort());
    }
}
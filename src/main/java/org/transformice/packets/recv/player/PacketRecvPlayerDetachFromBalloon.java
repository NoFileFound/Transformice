package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerDetachFromBalloon;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_DETACH_FROM_BALLOON)
public final class PacketRecvPlayerDetachFromBalloon extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        client.getPlayer().getPlayerRoom().broadcastPacket(new PacketSendPlayerDetachFromBalloon(client.getPlayer().getSessionId()));
    }
}
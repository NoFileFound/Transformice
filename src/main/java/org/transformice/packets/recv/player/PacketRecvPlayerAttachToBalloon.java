package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerAttachToBalloon;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_ATTACH_TO_BALLOON)
public final class PacketRecvPlayerAttachToBalloon extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();

        player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAttachToBalloon(player.getSessionId(), player.getPlayerRoom().getNextObjectId(), 1000));
    }
}
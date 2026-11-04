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
import org.transformice.packets.send.player.PacketSendShamanPosition;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SHAMAN_POSITION)
public final class PacketRecvShamanPosition extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (!player.isShaman)
            return;

        player.getPlayerRoom().broadcastPacket(player, new PacketSendShamanPosition(player.getSessionId(), psData.readBoolean()));
    }
}
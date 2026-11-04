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
import org.transformice.packets.send.player.PacketSendPlayerMeep;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_MEEP)
public final class PacketRecvPlayerMeep extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (!player.canMeep)
            return;

        int x = psData.readInt128();
        int y = psData.readInt128();
        player.getPlayerRoom().broadcastPacket(new PacketSendPlayerMeep(player.getSessionId(), x, y, player.isShaman ? 20 : 5));
        player.getPlayerRoom().getParseLua().handleEventPlayerMeep(player, x, y);
    }
}
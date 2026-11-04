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
import org.transformice.packets.send.player.PacketSendPlayerProfile;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_CHANGE_SHAMAN_BADGE)
public final class PacketRecvPlayerChangeShamanBadge extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int shamanBadge = psData.readByte();
        GamePlayer player = client.getPlayer();
        if (!player.getPlayerData().getShamanBadges().contains(shamanBadge) && shamanBadge != 0)
            return;

        player.getPlayerData().currentShamanBadge = shamanBadge;
        player.broadcastPacket(new PacketSendPlayerProfile(player.getPlayerData(), gameServer.getConfig().getGameConfig().getRoomStats()));
    }
}
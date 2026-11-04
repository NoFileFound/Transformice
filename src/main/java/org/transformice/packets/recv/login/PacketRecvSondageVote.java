package org.transformice.packets.recv.login;

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
import org.transformice.packets.send.login.PacketSendVoteSondage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SONDAGE_VOTE)
public final class PacketRecvSondageVote extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int playerId = psData.readInt();
        for (GamePlayer player : gameServer.getPlayers().values()) {
            if (!player.getIsGuest() && player.getGlobalId() == playerId)
                player.broadcastPacket(new PacketSendVoteSondage(psData.readUnsignedByte()));
        }
    }
}
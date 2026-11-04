package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_GET_CHEESE)
public final class PacketRecvPlayerGetCheese extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int roundId = psData.readInt();
        GamePlayer player = client.getPlayer();
        if (roundId == player.getPlayerRoom().getLastRoundId()) {
            player.sendGiveCheese(psData.readShort(), psData.readShort(), psData.readUnsignedByte(), psData.readUnsignedByte(), psData.readUnsignedShort());
        }
    }
}
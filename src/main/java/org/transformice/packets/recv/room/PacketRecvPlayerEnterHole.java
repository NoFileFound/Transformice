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
@PSPacket(id = PacketOpcodes.RECV_PLAYER_ENTER_HOLE)
public final class PacketRecvPlayerEnterHole extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int holeType = psData.readByte();
        int roundId = psData.readInt();
        int mapCode = psData.readInt();
        GamePlayer player = client.getPlayer();
        if (roundId == player.getPlayerRoom().getLastRoundId() && mapCode == player.getPlayerRoom().getCurrentMap().mapCode)
            player.sendEnterHole(holeType, psData.readShort(), psData.readShort(), psData.readShort());
    }
}
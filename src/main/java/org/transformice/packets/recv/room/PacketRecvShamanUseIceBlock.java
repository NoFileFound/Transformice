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
@PSPacket(id = PacketOpcodes.RECV_SHAMAN_USE_ICE_BLOCK)
public final class PacketRecvShamanUseIceBlock extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int playerSessionId = psData.readInt();
        int posX = psData.readShort();
        int posY = psData.readShort();
        GamePlayer player = client.getPlayer();
        if (player.isShaman && !player.isDead && (player.getPlayerRoom().firstShamanNumCompleted + player.getPlayerRoom().secondShamanNumCompleted > 0)) {
            if (player.iceCount > 0 && player.getSessionId() != playerSessionId) {
                gameServer.getPlayerBySessionId(playerSessionId).sendDeath();
                player.getPlayerRoom().sendAddShamanObject(-1, 54, posX, posY, 0, 0, 0, false, true, client.getPlayer(), true);
                player.iceCount--;
                player.getPlayerRoom().checkChangeMap();
            }
        }
    }
}
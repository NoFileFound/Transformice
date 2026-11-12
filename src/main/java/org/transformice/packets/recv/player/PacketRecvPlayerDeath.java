package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.AddBonusType;
import org.transformice.data.objects.RoomType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_DEATH)
public final class PacketRecvPlayerDeath extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int roundId = psData.readInt();
        GamePlayer player = client.getPlayer();
        GameRoom playerRoom = player.getPlayerRoom();
        if (roundId == playerRoom.getLastRoundId()) {
            player.sendDeath();
            if (player.isShaman) {
                if (player.bubbleCount > 0)
                    playerRoom.sendAddShamanObject(-1, 59, player.positionX, 450, 0, 0, 0, false, true, player, true);

                if (player.isDisintegration) {
                    playerRoom.sendAddBonus(AddBonusType.BONUS_DISINTEGRATION, player.positionX, 395, 0, true);
                }
            } else if (playerRoom.getRoomType() == RoomType.SURVIVOR)
                playerRoom.deathMice++;

            player.getPlayerRoom().checkChangeMap();
        }
    }
}
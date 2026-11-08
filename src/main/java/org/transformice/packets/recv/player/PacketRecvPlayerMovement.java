package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_MOVEMENT)
public final class PacketRecvPlayerMovement extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        psData.decryptPacket(gameServer.getConfig().getSWFConfig().getPacketKeys(), psHeader.readByte());
        int roundId = psData.readInt128();
        GamePlayer player = client.getPlayer();
        if (roundId == client.getPlayer().getPlayerRoom().getLastRoundId()) {
            boolean r1 = psData.readBoolean();
            boolean r2 = psData.readBoolean();
            if (r1) {
                player.isFacingRight = true;
                player.isFacingLeft = false;
            } else if (r2) {
                player.isFacingRight = false;
                player.isFacingLeft = true;
            }

            player.positionX = (int) (psData.readInt128() * (30.0 / 100));
            player.positionY = (int) (psData.readInt128() * (30.0 / 100));
            player.velocityX = (int) (psData.readInt128() * (1.0 / 10));
            player.velocityY = (int) (psData.readInt128() * (1.0 / 10));

            var frictionCharge = psData.readInt128() / 100;
            var frictionLossRate = psData.readInt128() / 10;
            player.isJumping = psData.readBoolean();

            player.getPlayerRoom().broadcastPacket(player, new PSSendPacket(PacketOpcodes.SEND_PLAYER_MOVEMENT) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    NetByteArray packet = new NetByteArray();
                    packet.writeInt128(player.getSessionId());
                    packet.writeBoolean(r1);
                    packet.writeBoolean(r2);
                    packet.writeInt128((int) ((player.positionX + player.velocityX * (player.lastPingValue / 2000.0)) * (100.0 / 30)));
                    packet.writeInt128((int) ((player.positionY + player.velocityY * (player.lastPingValue / 2000.0)) * (100.0 / 30)));
                    packet.writeInt128(player.velocityX * 10);
                    packet.writeInt128(player.velocityY * 10);
                    packet.writeInt128(frictionCharge * 100);
                    packet.writeInt128(frictionLossRate * 10);
                    packet.writeBoolean(player.isJumping);
                    packet.writeBytes(psData.toByteArray());
                    return packet;
                }
            });

            if (r1 || r2) {
                if (player.isAfk)
                    player.isAfk = false;
            }

            player.getPlayerRoom().getParseLua().sendLuaUpdatePlayerInfo(player);
        }
    }
}
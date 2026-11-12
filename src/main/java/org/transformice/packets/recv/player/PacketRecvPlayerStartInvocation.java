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
import org.transformice.packets.send.player.PacketSendPlayerStartInvocation;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_START_INVOCATION)
public final class PacketRecvPlayerStartInvocation extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (player.isShaman) {
            int objectCode = psData.readInt128();
            int posX = psData.readInt128();
            int posY = psData.readInt128();
            int angle = psData.readInt128();
            String rotation = psData.readUTF();
            boolean invocation = psData.readBoolean();

            if (!player.getPlayerRoom().getParseLua().disablePrespawnPreview)
                player.getPlayerRoom().broadcastPacket(player, new PacketSendPlayerStartInvocation(player.getSessionId(), objectCode, posX, posY, angle, rotation, invocation));

            player.getPlayerRoom().getParseLua().handleEventPlayerStartInvocation(player, objectCode, posX, posY, angle, rotation);
        }
    }
}
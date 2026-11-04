package org.transformice.packets.recv.cafe;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CAFE_CREATE_POST)
public final class PacketRecvCafeCreatePost extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        boolean isStaff = !player.getPlayerData().getStaffRoles().isEmpty();
        if (isStaff) {
            gameServer.getParseCafe().sendNewTopicPost(player, psData.readInt(), psData.readUTF());
            return;
        }

        PSTimer timer = gameServer.getParseCafe().getCreateCafePostTimer(player.getPlayerName());
        long remainingTime = timer.getRemainingTime();
        if (remainingTime <= 0) {
            gameServer.getParseCafe().sendNewTopicPost(player, psData.readInt(), psData.readUTF());
            timer.schedule();
        } else {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$AttendreNouveauMessage", new String[]{String.valueOf(remainingTime / 1000)}));
        }
    }
}
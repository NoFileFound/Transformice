package org.transformice.packets.recv.login;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.login.PacketSendCreateSondage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PUBLISH_SONDAGE_VOTES)
public final class PacketRecvPublishSondageVotes extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (!client.getPlayer().hasStaffPermission(StaffRoleType.ADMIN, false))
            return;

        String title = psData.readUTF();
        List<String> answers = new ArrayList<>();
        while (psData.getLength() > 0)
            answers.add(psData.readUTF());

        for (GamePlayer player : gameServer.getPlayers().values())
            player.broadcastPacket(new PacketSendCreateSondage(0, title, answers));
    }
}
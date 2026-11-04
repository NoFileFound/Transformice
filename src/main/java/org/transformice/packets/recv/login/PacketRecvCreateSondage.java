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
@PSPacket(id = PacketOpcodes.RECV_CREATE_SONDAGE)
public final class PacketRecvCreateSondage extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (!client.getPlayer().hasStaffPermission(StaffRoleType.ADMIN, false))
            return;

        String title = "[" + client.getPlayer().getPlayerName() + "] " + psData.readUTF();
        List<String> answers = new ArrayList<>();
        while (psData.getLength() > 0)
            answers.add(psData.readUTF());

        if (answers.size() > 1) {
            for (GamePlayer player : gameServer.getPlayers().values()) {
                if (!player.getIsGuest())
                    player.broadcastPacket(new PacketSendCreateSondage(client.getPlayer().getGlobalId(), title, answers));
            }
        }
    }
}
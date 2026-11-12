package org.transformice.packets.recv.login;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.login.PacketSendStaffServerPing;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_STAFF_SERVER_PING)
public final class PacketRecvStaffServerPing extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (client.getPlayer().getPlayerData().getStaffRoles().isEmpty() && !gameServer.getConfig().getIsDebug())
            return;

        client.getPlayer().broadcastPacket(new PacketSendStaffServerPing());
    }
}
package org.transformice.packets.recv.shop;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_STAFF_ADD_OUTFIT)
public final class PacketRecvStaffAddOutfit extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (player.hasStaffPermission(StaffRoleType.FASHIONSQUAD, false))
            gameServer.getParseShop().handleAddOutfit(client.getPlayer(), psData.readUTF(), psData.readShort(), psData.readUTF(), psData.readUTF());
    }
}
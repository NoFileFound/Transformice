package org.transformice.packets.recv.modopwet;

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
@PSPacket(id = PacketOpcodes.RECV_REFRESH_MODOPWET)
public final class PacketRecvRefreshModopwet extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (!player.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) && !player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) && !player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false))
            return;

        gameServer.getParseModopwet().sendOpenModopwet(player, true, psData.readUTF(), psData.readBoolean(), psData.readBoolean(), psData.readBoolean());
    }
}
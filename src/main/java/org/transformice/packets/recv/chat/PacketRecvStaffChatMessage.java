package org.transformice.packets.recv.chat;

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
@PSPacket(id = PacketOpcodes.RECV_STAFF_CHAT_MESSAGE)
public final class PacketRecvStaffChatMessage extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        byte channelId = psData.readByte();
        String channelMessage = psData.readUTF();
        GamePlayer player = client.getPlayer();

        boolean hasPerm = false;
        boolean isInternational = true;
        switch (channelId) {
            case 0:
            case 1:
                hasPerm = player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false);
                break;

            case 2:
            case 5:
                hasPerm = player.hasStaffPermission(StaffRoleType.ARBITRE, true) || player.hasStaffPermission(StaffRoleType.TRIAL_MODO, true) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, true) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, true);
                isInternational = channelId != 2;
                break;

            case 3:
            case 4:
                hasPerm = player.hasStaffPermission(StaffRoleType.TRIAL_MODO, true) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, true) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, true);
                isInternational = channelId != 3;
                break;

            case 6:
                hasPerm = player.hasStaffPermission(StaffRoleType.ADMIN, false);
                break;

            case 7:
                hasPerm = player.hasStaffPermission(StaffRoleType.LUADEV, true);
                break;

            case 8:
                hasPerm = player.hasStaffPermission(StaffRoleType.MAPCREW, true);
                break;

            case 9:
                hasPerm = player.hasStaffPermission(StaffRoleType.FUNCORP, true);
                break;

            case 11:
                hasPerm = player.hasStaffPermission(StaffRoleType.FUNCORP, false);
                break;

            case 10:
                hasPerm = player.hasStaffPermission(StaffRoleType.FASHIONSQUAD, true);
                break;
        }

        if (!hasPerm)
            return;

        gameServer.sendStaffChannelMessage(channelId, channelMessage, isInternational, player.getPlayerName(), player.getPlayerCommunity());
        if (channelId == 4)
            gameServer.getParseDelichoc().handleDelichocCommand(player, channelMessage);
    }
}
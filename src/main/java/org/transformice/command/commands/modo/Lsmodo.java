package org.transformice.command.commands.modo;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

@Command(name = "lsmodo", description = "Lists connected moderators and administrators and their channels.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Lsmodo implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        List<String[]> staffInfo = new ArrayList<>();
        for (GamePlayer client : server.getPlayers().values()) {
            if (client.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false) || client.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false)) {
                staffInfo.add(new String[]{client.getPlayerCommunity(), client.getPlayerName(), client.getRoomName()});
            }
        }

        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedByte(4);
        packet.writeUnsignedByte(staffInfo.size());
        for (String[] info : staffInfo) {
            packet.writeUTF(info[0], true);
            packet.writeUTF(info[1], true);
            packet.writeUTF(info[2], true);
        }

        packet.writeBoolean(true);
        player.broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_STAFF_LIST) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return packet;
            }
        });
    }
}
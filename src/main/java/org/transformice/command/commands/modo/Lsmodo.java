package org.transformice.command.commands.modo;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.level.C_OnlineStaffTeam;

@Command(
        name = "lsmodo",
        description = "Lists connected moderators and administrators and their channels.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Lsmodo implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        List<String> staffInfo = new ArrayList<>();
        for(Client client : server.getPlayers().values()) {
            if(!client.isGuest() && client.getAccount().getPrivLevel() >= 9) {
                staffInfo.add(client.playerCommunity + "_" + client.getPlayerName() + "_" + client.getRoomName());
            }
        }
        player.sendPacket(new C_OnlineStaffTeam(3, staffInfo));
    }
}
package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.chat.C_StaffChannelMessage;

@Command(
        name = "resign",
        description = "It serves to resign as an arbiter.",
        permission = {Command.CommandPermission.ARBITRE}
)
@SuppressWarnings("unused")
public final class Resign implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getAccount().getStaffRoles().remove("Arbitre");
        for(var client : server.getPlayers().values()) {
            if(client.hasStaffPermission("Arbitre", "ResignMsg") || client.hasStaffPermission("Modo", "ResignMsg")) {
                client.sendPacket(new C_StaffChannelMessage(2, "", String.format("%s has resigned.", player.getPlayerName())));
            }
        }
        player.closeConnection();
    }
}
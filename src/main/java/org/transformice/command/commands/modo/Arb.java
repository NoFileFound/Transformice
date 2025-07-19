package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.DBUtils;

// Packets
import org.transformice.packets.send.chat.C_StaffChannelMessage;

@Command(
        name = "arb",
        usage = "[playerName]",
        description = "Gives arbitre powers to your alt.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Arb implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        var account = DBUtils.findAccountByNickname(playerName);
        if(account == null || playerName.equals(player.getPlayerName())) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        account.getStaffRoles().add("Arbitre");
        for(var client : server.getPlayers().values()) {
            if(client.hasStaffPermission("Arbitre", "ResignMsg") || client.hasStaffPermission("Modo", "ResignMsg")) {
                client.sendPacket(new C_StaffChannelMessage(4, "Delichoc", String.format("New arb: %s", playerName)));
            }
        }

        if(server.checkIsConnected(playerName)) {
            server.getPlayers().get(playerName).closeConnection();
        }
    }
}
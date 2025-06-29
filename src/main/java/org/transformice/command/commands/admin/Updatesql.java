package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "updatesql",
        description = "Updates the database of all players.",
        permission = Command.CommandPermission.ADMINISTRATOR
)
@SuppressWarnings("unused")
public final class Updatesql implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        new Thread(() -> {
            for(Client client : server.getPlayers().values()) {
                client.saveDatabase();
            }
        }).start();
        CommandHandler.sendServerMessage(player, "Done");
    }
}
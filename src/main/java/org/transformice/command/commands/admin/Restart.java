package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "restart",
        description = "Restarts the server.",
        permission = Command.CommandPermission.ADMINISTRATOR
)
@SuppressWarnings("unused")
public final class Restart implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(server.rebootTimer == null) {
            server.sendServerRestart(0, 0);
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("ongoingrestart"));
        }
    }
}
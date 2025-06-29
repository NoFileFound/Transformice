package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "max",
        description = "Gives the maximum number of players connected since the last reboot.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Max implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        CommandHandler.sendServerMessage(player, String.format(Application.getTranslationManager().get("max_results"), server.getLastClientSessionId()));
    }
}
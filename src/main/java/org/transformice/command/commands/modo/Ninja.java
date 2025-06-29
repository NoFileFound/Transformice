package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "ninja",
        usage = "[playerName]",
        description = "Performs the same functionality as Watch in modopwet.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Ninja implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getParseModopwetInstance().sendWatchReport(args.getFirst(), false);
    }
}
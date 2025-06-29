package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "ibanhack",
        usage = "[playerName]",
        description = "Special banning order for cheaters, message is not sent in the room.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Ibanhack implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getParseModopwetInstance().sendBanHack(args.getFirst(), true);
    }
}
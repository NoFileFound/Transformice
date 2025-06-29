package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "ibandef",
        usage = "[playerName]",
        description = "Special banning order for cheaters permanently.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Ibandef implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getParseModopwetInstance().sendBanDef(args.getFirst(), String.join(" ", args.subList(1, args.size())), true);
    }
}
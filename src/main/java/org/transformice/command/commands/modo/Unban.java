package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "unban", usage = "[playerName] (reason)", description = "Removes a player's ban.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1, aliases = {"deban", "desban"})
@SuppressWarnings("unused")
public final class Unban implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        server.sendPlayerUnban(args.getFirst(), args.size() == 1 ? "Unspecified" : String.join(" ", args.subList(1, args.size())), player);
    }
}
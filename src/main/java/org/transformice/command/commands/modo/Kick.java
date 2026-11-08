package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "kick", usage = "[playerName] (reason)", description = "Disconnects the player from the game.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Kick implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        server.sendPlayerKick(args.getFirst(), args.size() == 1 ? "Unspecified" : String.join(" ", args.subList(1, args.size())), player);
    }
}
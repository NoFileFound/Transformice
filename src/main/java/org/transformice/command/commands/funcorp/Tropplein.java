package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "tropplein",
        usage = "[maxPlayers]",
        description = "Setting a limit for the number of players in a room.",
        permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.MAPCREW, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Tropplein implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(args.isEmpty()) {
            CommandHandler.sendServerMessage(player, "Tropplein : " + player.getRoom().getMaximumPlayers());
        } else {
            try {
                int maxPlayers = Integer.parseInt(args.getFirst());
                player.getRoom().setMaximumPlayers(maxPlayers);
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("tropplein_succ", maxPlayers));
            } catch (Exception ignored) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidnum"));
            }
        }
    }
}
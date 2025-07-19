package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;

import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "clearban",
        usage = "[playerName]",
        description = "Clears all vote bans from the given player.",
        permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Clearban implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        if(!server.checkIsConnected(playerName)) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        server.getPlayers().get(playerName).getVoteBans().clear();
        server.sendServerMessage(String.format("%s removed all ban votes of %s.", player.getPlayerName(), playerName), false, null);
    }
}
package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Stafflog;

@Command(
        name = "mumute",
        usage = "[playerName]",
        description = "Prevents the player from talking without them knowing, just for the duration of the connection.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Mumute implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client playerClient = server.getPlayers().get(playerName);
        if(playerClient != null) {
            playerClient.isMumuted = !playerClient.isMumuted;
            if(playerClient.isMumuted) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("mumuted_result", playerName));
                server.sendServerMessage(Application.getTranslationManager().get("mumuted_notify", player.getPlayerName(), playerName), false, player);
                new Stafflog(player.getPlayerName(), "Mumute", args);
            } else {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("unmumuted_result", playerName));
                server.sendServerMessage(Application.getTranslationManager().get("unmumuted_notify", player.getPlayerName(), playerName), false, player);
                new Stafflog(player.getPlayerName(), "Desmumute", args);
            }
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}
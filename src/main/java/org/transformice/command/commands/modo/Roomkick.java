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
        name = "roomkick",
        usage = "[playerName]",
        description = "Kicks a player from a room.",
        permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1,
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Roomkick implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client client = player.getRoom().getPlayers().get(playerName);
        if(client != null) {
            client.sendEnterRoom(server.getRecommendedRoom(client.playerCommunity), "");
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("roomkick_result", playerName, client.getRoomName()));
            server.sendServerMessage(Application.getTranslationManager().get("roomkick_notify", playerName, client.getRoomName(), player.getPlayerName()), false, player);
            new Stafflog(player.getPlayerName(), "Roomkick", args);
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}
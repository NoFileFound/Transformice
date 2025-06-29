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
        name = "prison",
        usage = "[playerName]",
        description = "Brings and traps the selected player in your room, however they cannot move unless they log out and come back.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Prison implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client playerClient = server.getPlayers().get(playerName);
        if(playerClient != null) {
            playerClient.isPrisoned = !playerClient.isPrisoned;
            if(playerClient.isPrisoned) {
                playerClient.sendEnterRoom(player.getRoomName(), "");
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("prisoned_result", playerName));
                server.sendServerMessage(Application.getTranslationManager().get("prisoned_notify", player.getPlayerName(), playerName), false, player);
                new Stafflog(player.getPlayerName(), "Prison", args);
            } else {
                playerClient.sendEnterRoom(server.getRecommendedRoom(playerClient.playerCommunity), "");
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("unprisoned_result", playerName));
                server.sendServerMessage(Application.getTranslationManager().get("unprisoned_notify", player.getPlayerName(), playerName), false, player);
            }
        } else {
            CommandHandler.sendServerMessage(player, String.format("The player %s isn't online.", playerName));
        }
    }
}
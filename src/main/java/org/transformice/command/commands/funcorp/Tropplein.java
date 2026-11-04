package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "tropplein", usage = "[maxPlayers]", description = "Setting a limit for the number of players in a room.", permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.MAPCREW, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Tropplein implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if(args.isEmpty()) {
            CommandHandler.sendServerMessage(player, "Tropplein : " + player.getPlayerRoom().maximumPlayers);
        } else {
            try {
                int maxPlayers = Integer.parseInt(args.getFirst());
                player.getPlayerRoom().maximumPlayers = maxPlayers;
                CommandHandler.sendServerMessage(player, String.format("Successfully set the maximum number of players to %d", maxPlayers));
            } catch (Exception ignored) {
                CommandHandler.sendServerMessage(player, "Invalid number");
            }
        }
    }
}
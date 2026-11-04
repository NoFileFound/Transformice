package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.PlayerEntity;

@Command(name = "playersql", usage = "[playerName]", description = "Updates the database of a specified player.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Playersql implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        PlayerEntity playerAccount = server.getDatabase().loadPlayerByName(args.getFirst());
        if (playerAccount == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        server.getDatabase().savePlayer(playerAccount);
        CommandHandler.sendServerMessage(player, String.format("Successfully saved the database of the player %s", playerAccount.getUsername()));
    }
}
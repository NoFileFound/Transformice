package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "updatesql", description = "Updates the database of all players.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Updatesql implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        server.saveDatabase();
        CommandHandler.sendServerMessage(player, "Successfully updated the database.");
    }
}
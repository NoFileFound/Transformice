package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "clearcommandcache", description = "Clears the command log cache.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Clearcommandcache implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        server.getDatabase().deleteAllCommandLogs();
    }
}
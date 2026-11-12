package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "restart", description = "Schedules a server restart after 120 seconds.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Restart implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        server.sendServerRestart(120);
        CommandHandler.sendServerMessage(player, "The server has been scheduled to restart in 120 seconds.");
    }
}
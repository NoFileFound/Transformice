package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "ping",
        description = "Displays your ping to the server."
)
@SuppressWarnings("unused")
public final class Ping implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        CommandHandler.sendServerMessage(player, "~" + player.lastPingResponse);
    }
}
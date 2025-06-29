package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "survivor",
        description = "Sends you to a survivor room."
)
@SuppressWarnings("unused")
public final class Survivor implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendEnterRoom(server.getRecommendedRoom("survivor"), "");
    }
}
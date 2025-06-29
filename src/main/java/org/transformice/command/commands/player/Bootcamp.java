package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "bootcamp",
        description = "Sends you to a bootcamp room."
)
@SuppressWarnings("unused")
public final class Bootcamp implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendEnterRoom(server.getRecommendedRoom("bootcamp"), "");
    }
}

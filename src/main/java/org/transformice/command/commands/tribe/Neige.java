package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "neige",
        description = "Starts to snow for 5 seconds.",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Neige implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getRoom().startSnow(1000000, 60, !player.getRoom().isSnowing());
    }
}
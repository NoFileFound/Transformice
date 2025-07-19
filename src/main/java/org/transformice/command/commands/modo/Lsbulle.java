package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "lsbulle",
        description = "Gives info about the satellite servers (which host the rooms).",
        permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Lsbulle implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        CommandHandler.sendServerMessage(player, String.format("[serveur] %d / %dm", server.getRoomsCount(), server.getPlayersCount()));
    }
}
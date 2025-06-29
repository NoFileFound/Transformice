package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "respawn",
        usage = "[playerName]",
        description = "Respawns a player.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1,
        aliases = {"re"}
)
@SuppressWarnings("unused")
public final class Respawn implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        if(player.getRoom().getPlayers().containsKey(playerName)) {
            player.getRoom().respawnPlayer(playerName);
        }
    }
}
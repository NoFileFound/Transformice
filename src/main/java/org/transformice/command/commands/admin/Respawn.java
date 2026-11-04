package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "respawn", usage = "[playerName]", description = "Respawns the player in his room.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1, aliases = {"re"})
@SuppressWarnings("unused")
public final class Respawn implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GamePlayer target = player.getPlayerRoom().getPlayers().get(args.getFirst());
        if (target == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        player.getPlayerRoom().sendRespawnPlayer(target);
        CommandHandler.sendServerMessage(player, String.format("The player %s has been respawned.", player.getPlayerName()));
    }
}
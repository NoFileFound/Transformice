package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "neige", description = "Starts to snow for 5 seconds.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Neige implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.getPlayerRoom().sendStartSnow(1000000, 60, !player.getPlayerRoom().getIsSnowing());
    }
}
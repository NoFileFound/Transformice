package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "tutorial", description = "Sends you to the tutorial.")
@SuppressWarnings("unused")
public final class Tutorial implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.sendEnterRoom("\u0003[Tutorial] " + player.getPlayerName(), "");
    }
}
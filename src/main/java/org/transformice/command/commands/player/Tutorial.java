package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "tutorial",
        description = "Sends you to the tutorial."
)
@SuppressWarnings("unused")
public final class Tutorial implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendEnterRoom((char)3 + "[Tutorial] " + player.getPlayerName(), "");
    }
}
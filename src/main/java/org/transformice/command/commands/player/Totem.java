package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "totem",
        description = "Sends you to your totem room."
)
@SuppressWarnings("unused")
public final class Totem implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendEnterRoom((char)3 + "[Totem] " + player.getPlayerName(), "");
    }
}
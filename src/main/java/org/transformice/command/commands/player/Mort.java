package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "mort",
        description = "Kill myself.",
        aliases = {"die", "kms", "kill", "suicide"}
)
@SuppressWarnings("unused")
public final class Mort implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(!player.isDead) {
            player.sendPlayerDeath();
            player.getRoom().checkChangeMap();
        }

        if (player.getRoom().luaMinigame != null) {
            player.getRoom().luaApi.callEvent("eventPlayerDied", player.getPlayerName(), -1);
        }
    }
}
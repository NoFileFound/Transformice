package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "ping", description = "Sends the player's ping latency.")
@SuppressWarnings("unused")
public final class Ping implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        CommandHandler.sendServerMessage(player, "~" + player.lastPingValue / 100);
    }
}
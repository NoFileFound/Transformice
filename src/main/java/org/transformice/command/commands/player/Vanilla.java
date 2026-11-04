package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "vanilla", description = "Sends you to a vanilla room.")
@SuppressWarnings("unused")
public final class Vanilla implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.sendEnterRoom(server.getRecommendedRoom("vanilla", player.getPlayerCommunity()), "");
    }
}
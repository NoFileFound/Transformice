package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "defilante", description = "Sends you to a defilante room.")
@SuppressWarnings("unused")
public final class Defilante implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.sendEnterRoom(server.getRecommendedRoom("defilante", player.getPlayerCommunity()), "");
    }
}
package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "mjj", description = "Sends you a last room mode.")
@SuppressWarnings("unused")
public final class Mjj implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.sendEnterRoom(server.getRecommendedRoom(switch (player.lastGameModeId) {
            case 0, 1 -> "";
            case 2 -> "bootcamp";
            case 3 -> "vanilla";
            case 8 -> "survivor";
            case 9 -> "racing";
            case 10 -> "defilante";
            case 11 -> "music";
            case 16 -> "village";
            default -> "";
        }, player.getPlayerCommunity()), "");
    }
}
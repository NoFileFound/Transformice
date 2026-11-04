package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(name = "time", description = "Shows the accumulated play time for your account.", aliases = {"temps"})
@SuppressWarnings("unused")
public final class Time implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        long playerTime = player.getPlayerData().playedTime + (Utils.getUnixTime() - player.getLoginTime());
        CommandHandler.sendTranslatedMessage(player, "", "$TempsDeJeu", new String[]{String.valueOf(playerTime / 86400), String.valueOf((playerTime / 3600) % 24), String.valueOf((playerTime / 60) % 60), String.valueOf(playerTime % 60)});
    }
}
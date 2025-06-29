package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "time",
        description = "Shows the accumulated play time for your account.",
        permission = Command.CommandPermission.PLAYER,
        aliases = {"temps"}
)
@SuppressWarnings("unused")
public final class Time implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        long playerTime = player.getAccount().getPlayedTime() + player.getLoginTime();
        CommandHandler.sendTranslatedMessage(player, "", "$TempsDeJeu", new String[]{String.valueOf(playerTime / 86400), String.valueOf((playerTime / 3600) % 24), String.valueOf((playerTime / 60) % 60), String.valueOf(playerTime % 60)});
    }
}
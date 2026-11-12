package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "skip", description = "Votes to skip the current music.")
@SuppressWarnings("unused")
public final class Skip implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.getPlayerRoom().checkMusicSkip();
    }
}
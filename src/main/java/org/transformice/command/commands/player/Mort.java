package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "mort", description = "Kill myself.", aliases = {"die", "kms", "kill", "suicide"})
@SuppressWarnings("unused")
public final class Mort implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        long gameStartMillis = player.getPlayerRoom().getGameStartTimeMillis();
        if (gameStartMillis > 0 && System.currentTimeMillis() - gameStartMillis < 15000)
            return;

        player.sendDeath();
        player.getPlayerRoom().checkChangeMap();
    }
}
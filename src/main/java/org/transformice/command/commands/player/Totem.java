package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "totem", description = "Sends you to your totem room.")
@SuppressWarnings("unused")
public final class Totem implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (player.getPlayerData().savesNormalWithSkill >= 1000)
            player.sendEnterRoom("\u0003[Totem] " + player.getPlayerName(), "");
    }
}
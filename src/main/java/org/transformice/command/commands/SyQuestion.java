package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "sy?", description = "Gets the current room's synchronizer.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class SyQuestion implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        CommandHandler.sendTranslatedMessage(player, "", "$SyncEnCours [" + player.getPlayerRoom().getSync().getPlayerName() + "]", new String[]{});
    }
}
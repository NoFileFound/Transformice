package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "sy?",
        description = "Gets the current room's synchronizer.",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class SyQuestion implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        CommandHandler.sendTranslatedMessage(player, "", "$SyncEnCours [" + player.getRoom().getCurrentSync().getPlayerName() + "]", new String[]{});
    }
}
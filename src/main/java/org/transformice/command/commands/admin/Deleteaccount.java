package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "deleteaccount", aliases = {"deleteuser"}, description = "Deletes the player from the database. if a player is a leader of a tribe the tribe gets dissolved.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Deleteaccount implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        ///  TODO: IMPLEMENT /deleteaccount
    }
}
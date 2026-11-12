package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "clearban", usage = "[playerName]", description = "Clears all vote bans from the given player.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Clearban implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GamePlayer client = server.getPlayers().get(args.getFirst());
        if (client == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        client.getVoteBans().clear();
        CommandHandler.sendServerMessage(player, String.format("Removed the vote bans of %s.", client.getPlayerName()));
        server.sendServerMessageOthers(player, String.format("%s removed all ban votes of %s.", player.getPlayerName(), client.getPlayerName()), StaffRoleType.ARBITRE);
    }
}
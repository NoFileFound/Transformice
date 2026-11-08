package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "mumute", usage = "[playerName]", description = "Prevents the player from talking without them knowing, just for the duration of the connection.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Mumute implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        if (playerName.equals(player.getPlayerName()))
            return;

        GamePlayer client = server.getPlayers().get(playerName);
        if (client == null) {
            CommandHandler.sendServerMessage(player, "The player is not connected.");
            return;
        }

        client.isMumuted = !client.isMumuted;
        CommandHandler.sendServerMessage(player, String.format("The player %s got %s.", playerName, (client.isMumuted ? "mumuted" : "unmumuted")));
        server.sendServerMessageOthers(player, String.format("%s %s %s.", player.getPlayerName(), (client.isMumuted ? "mumuted" : "unmumuted"), playerName), StaffRoleType.PUBLIC_MODO);
    }
}
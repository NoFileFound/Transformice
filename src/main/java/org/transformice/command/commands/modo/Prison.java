package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "prison", usage = "[playerName]", description = "Brings and traps the selected player in your room, however they cannot move unless they log out and come back.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Prison implements CommandHandler {
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

        client.isPrisoned = !client.isPrisoned;
        CommandHandler.sendServerMessage(player, String.format("The player %s got %s.", playerName, (client.isPrisoned ? "prisoned" : "unprisoned")));
        server.sendServerMessageOthers(player, String.format("%s %s %s.", player.getPlayerName(), (client.isPrisoned ? "prisoned" : "unprisoned"), playerName), StaffRoleType.PUBLIC_MODO);
    }
}
package org.transformice.command.commands.modo;

// Imports
import java.util.LinkedHashSet;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.ConnectionLogEntry;
import org.transformice.utils.Utils;

@Command(name = "ipnom", usage = "[IP]", description = "Gives the accounts a specific IP has connected to.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Ipnom implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String ipAddress = args.getFirst();
        if (!ipAddress.matches("^#[0-9A-Fa-f]{2}(\\.[0-9A-Fa-f]{2}){3}$")) {
            CommandHandler.sendServerMessage(player, "The supplied argument is not a valid IP address.");
            return;
        }

        List<ConnectionLogEntry> logs = server.getDatabase().loadConnectionLogsByIPAddress(Utils.decodeIP(ipAddress));
        StringBuilder accountList = new StringBuilder(String.format("Logs for the IP address [%s]:", ipAddress));
        for (String playerName : new LinkedHashSet<>(logs.stream().map(ConnectionLogEntry::getPlayerName).toList())) {
            accountList.append("<br>").append(playerName);
            if (server.getPlayers().containsKey(playerName))
                accountList.append(" <G>(online)</G>");
        }

        CommandHandler.sendServerMessage(player, accountList.toString());
    }
}
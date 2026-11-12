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

@Command(name = "nomip", usage = "[playerName]", description = "Gives the IPs connecting to an account since the last reboot.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Nomip implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        List<ConnectionLogEntry> logs = server.getDatabase().loadConnectionLogsByPlayer(playerName);
        if (logs.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("The player <BV>%s</BV> hasn't logged in since the last reboot.", playerName));
            return;
        }

        StringBuilder ipList = new StringBuilder(String.format("<BV>%s</BV>'s last known IP addresses:", playerName));
        for (String ip : new LinkedHashSet<>(logs.stream().map(ConnectionLogEntry::getIpAddress).toList()))
            ipList.append("<br>").append(Utils.encodeIP(ip));

        CommandHandler.sendServerMessage(player, ipList.toString());
    }
}
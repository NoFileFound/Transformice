package org.transformice.command.commands.modo;

// Imports
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.ConnectionLogEntry;
import org.transformice.utils.Utils;

@Command(name = "relation", usage = "[playerName]", description = "Gives list of related accounts since the last reboot.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Relation implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        List<ConnectionLogEntry> logs = server.getDatabase().loadConnectionLogsByPlayer(playerName);
        if (logs.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("The player <BV>%s</BV> hasn't logged in since the last reboot.", playerName));
            return;
        }

        Map<String, String> unique = new LinkedHashMap<>();
        for (ConnectionLogEntry log : logs) {
            if (log.getIpAddress() != null)
                unique.putIfAbsent(log.getPlayerName(), log.getIpAddress());
        }

        StringBuilder message = new StringBuilder(String.format("The player <BV>%s</BV> has the following relations:", playerName));
        for (Map.Entry<String, String> entry : unique.entrySet()) {
            String username = entry.getKey();
            String ip = Utils.encodeIP(entry.getValue());
            message.append(String.format("<br> - <BV>%s</BV> : <font color='%s'>%s</font> %s", username, Utils.colorIP(ip), ip, server.getPlayers().containsKey(username) ? "(current IP)" : ""));
        }

        CommandHandler.sendServerMessage(player, message.toString());
    }
}
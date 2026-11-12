package org.transformice.command.commands.modo;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.ConnectionLogEntry;
import org.transformice.utils.Utils;

@Command(name = "l", usage = "[playerName/IP]", description = "Lists the 200 previous connections of the requested nickname or IP address.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, aliases = {"loginlog"}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class L implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        boolean isIP = playerName.matches("^#[0-9A-Fa-f]{2}(\\.[0-9A-Fa-f]{2}){3}$");
        List<ConnectionLogEntry> logs = isIP ? server.getDatabase().loadConnectionLogsByIPAddress(Utils.decodeIP(playerName)) : server.getDatabase().loadConnectionLogsByPlayer(playerName);
        if (logs.isEmpty()) {
            CommandHandler.sendServerMessage(player, "The supplied argument is neither a valid nickname nor an IP address.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm").withZone(ZoneId.systemDefault());
        StringBuilder builder = new StringBuilder(String.format("<p align='center'>Connection logs for %s: <BL>%s</BL><br></p>", isIP ? "IP address" : "player", playerName));
        for (ConnectionLogEntry login : logs) {
            String hexIP = Utils.encodeIP(login.getIpAddress());
            builder.append(String.format("<p align='left'><V>[ %s ]</V> <BL>%s</BL><G> ( <font color='%s'>%s</font> - %s ) %s - %s</BL><br>", login.getPlayerName(), formatter.format(Instant.ofEpochSecond(login.getDate())), Utils.colorIP(hexIP), hexIP, login.getIpCountry(), login.getServiceName(), login.getGameCommunity()));
        }

        CommandHandler.sendLogMessage(player, 0, builder.toString());
    }
}
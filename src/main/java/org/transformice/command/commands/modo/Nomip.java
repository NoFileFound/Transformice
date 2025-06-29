package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Loginlog;
import org.transformice.utils.IPHex;

@Command(
        name = "nomip",
        usage = "[playerName]",
        description = "Gives the IPs connecting to an account since the last reboot.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Nomip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        List<Loginlog> logs = DBUtils.findConnectionLogs(playerName, false);
        if(!logs.isEmpty()) {
            StringBuilder ipList = new StringBuilder(String.format("<BV>%s</BV>'s last known IP addresses:", playerName));
            Set<String> distinctIPs = logs.stream().map(Loginlog::getIpAddress).collect(Collectors.toSet());
            for (String ip : distinctIPs) {
                ipList.append("<br>").append(IPHex.encodeIP(ip));
            }
            CommandHandler.sendServerMessage(player, ipList.toString());
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("nomip_noresult", playerName));
        }
    }
}
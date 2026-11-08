package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.utils.Utils;

@Command(name = "kickip", usage = "[IP]", description = "Disconnects the every player that has the specific ip address.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Kickip implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            String ipAddress = Utils.decodeIP(args.getFirst());
            int cnt = 0;
            for (GamePlayer client : server.getPlayers().values()) {
                if (client.getIpAddress().equals(ipAddress)) {
                    client.disconnect(true);
                    cnt++;
                }
            }

            if (cnt > 0) {
                CommandHandler.sendServerMessage(player, String.format("The IP %s got kicked.", args.getFirst()));
                server.sendServerMessageOthers(player, String.format("%s kicked everyone with the IP address: %s.", player.getPlayerName(), args.getFirst()), StaffRoleType.PUBLIC_MODO);
            } else {
                CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid IP address.");
            }
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid IP address.");
        }
    }
}
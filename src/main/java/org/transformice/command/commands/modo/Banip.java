package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.utils.Utils;

@Command(name = "banip", usage = "[IP]", description = "Bans the selected IP address.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 3)
@SuppressWarnings("unused")
public final class Banip implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            server.sendIPBan(Utils.decodeIP(args.getFirst()), Integer.parseInt(args.get(1)), String.join(" ", args.subList(2, args.size())));
            for (GamePlayer client : server.getPlayers().values()) {
                if (client.getIpAddress().equals(Utils.decodeIP(args.getFirst())))
                    client.disconnect(true);
            }

            CommandHandler.sendServerMessage(player, String.format("The IP %s got banned.", args.getFirst()));
            server.sendServerMessageOthers(player, String.format("%s banned the IP address: %s.", player.getPlayerName(), args.getFirst()), StaffRoleType.PUBLIC_MODO);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid IP address.");
        }
    }
}
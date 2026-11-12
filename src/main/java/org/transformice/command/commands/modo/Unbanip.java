package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.utils.Utils;

@Command(name = "unbanip", usage = "[IP]", description = "Unbans the selected IP address.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1, aliases = {"desbanip", "debanip"})
@SuppressWarnings("unused")
public final class Unbanip implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            server.sendIPUnban(Utils.decodeIP(args.getFirst()), player);
            CommandHandler.sendServerMessage(player, String.format("The IP %s got unbanned.", args.getFirst()));
            server.sendServerMessageOthers(player, String.format("%s unbanned the IP address: %s.", player.getPlayerName(), args.getFirst()), StaffRoleType.PUBLIC_MODO);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid IP address.");
        }
    }
}
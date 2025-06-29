package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Stafflog;
import org.transformice.utils.IPHex;

@Command(
        name = "kickip",
        usage = "[IP]",
        description = "Disconnects the every player that has the specific ip address.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Kickip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        try {
            server.disconnectIPAddress(IPHex.decodeIP(args.getFirst()), null);
            new Stafflog(player.getPlayerName(), "Kick", args);
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("kickedip", args.getFirst()));
            server.sendServerMessage(Application.getTranslationManager().get("kickedip_notify", player.getPlayerName(), args.getFirst()), false, player);
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidip"));
        }
    }
}
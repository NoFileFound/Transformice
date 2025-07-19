package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "geoip",
        usage = "[IP]",
        description = "Gives country information for an IP.",
        permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Geoip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String ipAddress = args.getFirst();
        if(ipAddress.matches("^#[0-9A-Fa-f]{2}(\\.[0-9A-Fa-f]{2}){3}$")) {
            /// TODO: [Unimplemented] Commands->geoip
            throw new RuntimeException("[Unimplemented] Commands->geoip");
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidip"));
        }
    }
}
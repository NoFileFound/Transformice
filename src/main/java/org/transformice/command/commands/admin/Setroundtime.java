package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "setroundtime",
        usage = "[seconds]",
        description = "Changes the delay of current round.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1,
        aliases = {"settime"}
)
@SuppressWarnings("unused")
public final class Setroundtime implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        try {
            short seconds = Short.parseShort(args.getFirst());
            player.getRoom().setMapChangeTimer(seconds);
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("changedroundtime", seconds));
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidroundtime"));
        }
    }
}
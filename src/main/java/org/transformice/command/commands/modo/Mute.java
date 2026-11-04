package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "mute", usage = "[playerName] [hours] [reason]", description = "Prevents the player from speaking for the set duration.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 3)
@SuppressWarnings("unused")
public final class Mute implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            server.sendPlayerMute(args.getFirst(), Integer.parseInt(args.get(1)), String.join(" ", args.subList(2, args.size())), player, false);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, "Invalid number.");
        }
    }
}
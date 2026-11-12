package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "iban", usage = "[playerName] [hours] [reason]", description = "Default ban command, message is not sent in the room.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 3)
@SuppressWarnings("unused")
public final class Iban implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            server.sendPlayerBan(args.getFirst(), Integer.parseInt(args.get(1)), String.join(" ", args.subList(2, args.size())), player, true);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, "Invalid number.");
        }
    }
}
package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "setroundtime", usage = "[seconds]", description = "Changes the delay of current round.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1, aliases = {"settime"})
@SuppressWarnings("unused")
public final class Setroundtime implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            short seconds = Short.parseShort(args.getFirst());
            player.getPlayerRoom().setRoundTime(seconds);
            CommandHandler.sendServerMessage(player, String.format("Successfully set the round time to %s", seconds));
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid number.");
        }
    }
}
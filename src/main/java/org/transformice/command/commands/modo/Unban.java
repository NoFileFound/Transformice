package org.transformice.command.commands.modo;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Sanction;
import org.transformice.database.collections.Stafflog;

@Command(
        name = "unban",
        usage = "[playerName] [reason]",
        description = "Removes a player's ban.",
        permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1,
        aliases = {"desban", "deban"}
)
@SuppressWarnings("unused")
public final class Unban implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        String reason = String.join(" ", args.subList(1, args.size()));
        Sanction mySanction = server.getLatestSanction(playerName, "banjeu");
        if (mySanction == null) {
            mySanction = server.getLatestSanction(playerName, "bandef");
        }

        if (mySanction != null && mySanction.getState().equals("Active")) {
            mySanction.setState("Cancelled");
            mySanction.setCancelAuthor(player.getPlayerName());
            mySanction.setCancelReason(reason);
            mySanction.setCancelDate(getUnixTime());
            mySanction.save();
            new Stafflog(player.getPlayerName(), "Unban", args);
            CommandHandler.sendServerMessage(player, String.format("The player %s got unbanned.", playerName));
            server.sendServerMessage(String.format("%s just unbanned the player %s (%s.).", player.getPlayerName(), playerName, reason), false, player);
            return;
        }

        CommandHandler.sendServerMessage(player, String.format("The player %s is not banned yet.", playerName));
    }
}
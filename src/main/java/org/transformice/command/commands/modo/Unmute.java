package org.transformice.command.commands.modo;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Sanction;
import org.transformice.database.collections.Stafflog;

@Command(
        name = "unmute",
        usage = "[playerName] [reason]",
        description = "Removes a player's mute.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1,
        aliases = {"demute", "desmute"}
)
@SuppressWarnings("unused")
public final class Unmute implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        String reason = String.join(" ", args.subList(1, args.size()));
        Sanction mySanction = server.getLatestSanction(playerName, "mutejeu");
        if (mySanction == null) {
            mySanction = server.getLatestSanction(playerName, "mutedef");
        }

        if (mySanction != null && mySanction.getState().equals("Active")) {
            mySanction.setState("Cancelled");
            mySanction.setCancelAuthor(player.getPlayerName());
            mySanction.setCancelReason(reason);
            mySanction.setCancelDate(getUnixTime());
            mySanction.save();
            new Stafflog(player.getPlayerName(), "Unmute", args);
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("playerunmuted", playerName));
            server.sendServerMessage(Application.getTranslationManager().get("unmuted_notify", player.getPlayerName(), playerName, reason), false, player);
            return;
        }

        CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("playernotmuted", playerName));
    }
}
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
import org.transformice.utils.IPHex;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "mute",
        usage = "[playerName] [hours] [reason]",
        description = "Prevents the player from speaking for the set duration.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 3
)
@SuppressWarnings("unused")
public final class Mute implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Sanction mySanction = server.getLatestSanction(playerName, "mutejeu");
        if (mySanction != null && mySanction.getState().equals("Active")) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("alreadymuted", playerName));
            return;
        } else {
            mySanction = server.getLatestSanction(playerName, "mutedef");
            if (mySanction != null && mySanction.getState().equals("Active")) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("alreadymuted", playerName));
                return;
            }
        }

        int muteHours = 0;
        try {
            muteHours = Integer.parseInt(args.get(1));
        } catch (NumberFormatException e) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidnum"));
        }

        Client playerClient = server.getPlayers().get(playerName);
        String reason = String.join(" ", args.subList(2, args.size()));
        if(server.getPlayerAccount(playerName) != null) {
            mySanction = new Sanction(playerName, (playerClient != null) ? IPHex.encodeIP(playerClient.getIpAddress()) : "offline", "mutejeu", player.getPlayerName(), reason, getUnixTime() + (muteHours * 3600L));
            mySanction.save();

            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("playermuted", playerName));
            if(playerClient != null) {
                new Stafflog(player.getPlayerName(), "Mute_Online", args);
                server.sendServerMessage(Application.getTranslationManager().get("mutenotify", player.getPlayerName(), playerName, muteHours, reason), false, player);
                player.getRoom().sendAll(new C_TranslationMessage("", "<ROSE>$MuteInfo2</ROSE>", new String[]{playerName, String.valueOf(muteHours), reason}));
            } else {
                new Stafflog(player.getPlayerName(), "Mute_Offline", args);
                server.sendServerMessage(Application.getTranslationManager().get("mutenotify_offline", player.getPlayerName(), playerName, muteHours, reason), false, player);
            }
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}
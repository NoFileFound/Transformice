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
import org.transformice.packets.send.legacy.login.C_BanMessage;

@Command(
        name = "iban",
        usage = "[playerName] [hours] [reason]",
        description = "Default ban command, message is not sent in the room.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 3
)
@SuppressWarnings("unused")
public final class Iban implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Sanction mySanction = server.getLatestSanction(playerName, "banjeu");
        if (mySanction != null && mySanction.getState().equals("Active")) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("alreadybanned", playerName));
            return;
        } else {
            mySanction = server.getLatestSanction(playerName, "bandef");
            if (mySanction != null && mySanction.getState().equals("Active")) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("alreadybanned", playerName));
                return;
            }
        }

        int banHours = 0;
        try {
            banHours = Integer.parseInt(args.get(1));
        } catch (NumberFormatException e) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidnum"));
        }

        Client playerClient = server.getPlayers().get(playerName);
        String reason = String.join(" ", args.subList(2, args.size()));
        if(server.getPlayerAccount(playerName) != null) {
            mySanction = new Sanction(playerName, (playerClient != null) ? IPHex.encodeIP(playerClient.getIpAddress()) : "offline", "banjeu", player.getPlayerName(), reason, getUnixTime() + (banHours * 3600L));
            mySanction.save();

            if(playerClient != null) {
                new Stafflog(player.getPlayerName(), "Ban_Online", args);
                server.sendServerMessage(Application.getTranslationManager().get("banplayernotify", player.getPlayerName(), playerName, banHours, reason), false, null);
                server.disconnectIPAddress(playerClient.getIpAddress(), player);
                playerClient.sendOldPacket(new C_BanMessage(banHours, reason));
            } else {
                new Stafflog(player.getPlayerName(), "Ban_Offline", args);
                server.sendServerMessage(Application.getTranslationManager().get("banplayernotify_offline", player.getPlayerName(), playerName, banHours, reason), false, null);
            }
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}
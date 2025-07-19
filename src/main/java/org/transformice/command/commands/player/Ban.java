package org.transformice.command.commands.player;

// Imports
import static org.transformice.utils.Utils.getUnixTime;

import java.util.ArrayList;
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
import org.transformice.packets.send.chat.C_StaffChannelMessage;
import org.transformice.packets.send.legacy.login.C_BanMessage;

@Command(
        name = "ban",
        usage = "[playerName] [hours] [reason]",
        description = "Bans the player name.",
        permission = Command.CommandPermission.PLAYER,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Ban implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        if(player.getRoomName().equals(String.format("*strm_%s", player.getPlayerName()))) {
            /// STRM support.
            if(player.getRoom().getPlayers().get(playerName) == null) return;
            player.getRoom().getPlayers().get(playerName).sendEnterRoom(server.getRecommendedRoom(""), "");
            return;
        }

        if(!player.hasStaffPermission("Arbitre", "Ban") && !player.hasStaffPermission("TrialModo", "Ban") && !player.hasStaffPermission("Modo", "Ban")) {
            /// VOTEBAN support.
            var client = player.getRoom().getPlayers().get(playerName);
            if(client == null) {
                return;
            }

            if(client.getVoteBans().contains(player.getIpAddress())) {
                return;
            }

            client.getVoteBans().add(player.getIpAddress());
            if(client.getVoteBans().size() > 10) {
                client.getVoteBans().clear();
                new Stafflog("Buffy", "Ban_Online", args);
                server.sendServerMessage(Application.getTranslationManager().get("banplayernotify", "Buffy", playerName, 1, "Vote populaire"), false, null);
                server.disconnectIPAddress(client.getIpAddress(), client);
                client.getRoom().sendAllOthers(client, new C_StaffChannelMessage(0, "", "$Message_Ban", new ArrayList<>(List.of(playerName, "1", "Vote populaire"))));
                client.sendOldPacket(new C_BanMessage(1, "Vote populaire"));
            }
        } else {
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
                    playerClient.getRoom().sendAllOthers(playerClient, new C_StaffChannelMessage(0, "", "$Message_Ban", new ArrayList<>(List.of(playerName, String.valueOf(banHours), reason))));
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
}
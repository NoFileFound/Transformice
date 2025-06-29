package org.transformice.command.commands.funcorp;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.room.C_PlayerChangeSize;

@Command(
        name = "changesize",
        usage = "[playerNames|*] [size|off]",
        description = "Temporarily changes the size.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Changesize implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("changesize_sizes", player.getRoom().getRoomFunCorpPlayersChangedSize().size()));
            return;
        }

        if (args.getFirst().equals("*")) {
            if (args.size() != 2) return;

            boolean isDisable = args.getLast().equals("off");
            int size;
            try {
                size = isDisable ? 100 : Integer.parseInt(args.getLast());
            } catch (NumberFormatException e) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("changesize_invalidsize"));
                return;
            }

            for (Client client : player.getRoom().getPlayers().values()) {
                player.getRoom().sendAll(new C_PlayerChangeSize(client.getSessionId(), size, false));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersChangedSize().contains(client.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersChangedSize().add(client.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersChangedSize().remove(client.getPlayerName());
                }
            }
            CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("changesize_all_norm") : Application.getTranslationManager().get("changesize_all", size));
        } else {
            boolean isDisable = args.getLast().equals("off");
            int size;
            try {
                size = isDisable ? 100 : Integer.parseInt(args.getLast());
            } catch (NumberFormatException e) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("changesize_invalidsize"));
                return;
            }

            List<String> playerNames = new ArrayList<>();
            for (String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                playerNames.add(playerName);
                player.getRoom().sendAll(new C_PlayerChangeSize(playerClient.getSessionId(), size, false));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersChangedSize().contains(playerClient.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersChangedSize().add(playerClient.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersChangedSize().remove(playerClient.getPlayerName());
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("changesize_players_norm") + formattedNames : Application.getTranslationManager().get("changesize_players", size, formattedNames));
            }
        }
    }
}
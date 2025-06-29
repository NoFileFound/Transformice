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
import org.transformice.libraries.Pair;

// Packets
import org.transformice.packets.send.room.C_CreateSoulmate;

@Command(
        name = "linkmice",
        usage = "[playerNames|*] (off)",
        description = "Temporarily links players.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Linkmice implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            StringBuilder message = new StringBuilder(Application.getTranslationManager().get("linkmice_links"));
            for(Pair<String, String> linkedPlayer : player.getRoom().getRoomFunCorpPlayersLinked()) {
                message.append("<br>").append(linkedPlayer.getFirst()).append(" - ").append(linkedPlayer.getSecond());
            }
            CommandHandler.sendServerMessage(player, message.toString());
            return;
        }

        if (args.getFirst().equals("*")) {
            boolean isDisable = args.getLast().equals("off");
            for (Client client : player.getRoom().getPlayers().values()) {
                Pair<String, String> couple = new Pair<>(player.getPlayerName(), client.getPlayerName());

                player.getRoom().sendAll(new C_CreateSoulmate(!isDisable, player.getSessionId(), client.getSessionId()));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersLinked().contains(couple))
                        player.getRoom().getRoomFunCorpPlayersLinked().add(couple);
                } else {
                    player.getRoom().getRoomFunCorpPlayersLinked().remove(couple);
                }
            }
            CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("linkmice_all_norm") : Application.getTranslationManager().get("linkmice_all"));
        } else {
            boolean isDisable = args.getLast().equals("off");
            List<String> playerNames = new ArrayList<>();
            Client firstPlayerClient = player.getRoom().getPlayers().get(args.getFirst());
            while(firstPlayerClient == null) {
                args.removeFirst();
                firstPlayerClient = player.getRoom().getPlayers().get(args.getFirst());
            }

            for (String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                Pair<String, String> couple = new Pair<>(firstPlayerClient.getPlayerName(), playerName);
                if(!couple.getFirst().equals(couple.getSecond())) {
                    playerNames.add(playerName);
                    player.getRoom().sendAll(new C_CreateSoulmate(!isDisable, playerClient.getSessionId(), firstPlayerClient.getSessionId()));
                    if (!isDisable) {
                        if(!player.getRoom().getRoomFunCorpPlayersLinked().contains(couple))
                            player.getRoom().getRoomFunCorpPlayersLinked().add(couple);
                    } else {
                        player.getRoom().getRoomFunCorpPlayersLinked().remove(couple);
                    }
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (isDisable ? Application.getTranslationManager().get("linkmice_players_norm") : Application.getTranslationManager().get("linkmice_players"))  + formattedNames);
            }
        }
    }
}
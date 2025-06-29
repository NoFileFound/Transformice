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

@Command(
        name = "colornick",
        usage = "[playerNames|*] (color|off)",
        description = "Temporarily changes the color of player nicknames.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Colornick implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            StringBuilder message = new StringBuilder(Application.getTranslationManager().get("colornick_colors", player.getRoom().getRoomFunCorpPlayersNickColor().size()));
            for(var colorInfo : player.getRoom().getRoomFunCorpPlayersNickColor().entrySet()) {
                String color = String.format("#%06X", colorInfo.getValue() & 0xFFFFFF);
                message.append("<br>").append(colorInfo.getKey()).append(" -> ").append(String.format("<font color='%s'>%s</font>", color, color));
            }
            CommandHandler.sendServerMessage(player, message.toString());
            return;
        }

        int color;
        try {
            color = Integer.parseInt(args.getLast());
        } catch(NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("colormouse_invalidcor"));
            return;
        }
        if(args.getFirst().equals("*")) {
            for (Client client : player.getRoom().getPlayers().values()) {
                client.setFunCorpNickcolor(color);
                if (color != -1) {
                    if(!player.getRoom().getRoomFunCorpPlayersNickColor().containsKey(client.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersNickColor().put(client.getPlayerName(), color);
                } else {
                    player.getRoom().getRoomFunCorpPlayersNickColor().remove(client.getPlayerName());
                }
            }
            CommandHandler.sendServerMessage(player, color == -1 ? Application.getTranslationManager().get("colornick_all_norm") : Application.getTranslationManager().get("colornick_all", String.format("#%06X", color & 0xFFFFFF), String.format("#%06X", color & 0xFFFFFF)));
        } else {
            args.removeLast();
            List<String> playerNames = new ArrayList<>();
            for(String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                playerNames.add(playerName);
                playerClient.setFunCorpNickcolor(color);
                if (color != -1) {
                    player.getRoom().getRoomFunCorpPlayersNickColor().put(playerClient.getPlayerName(), color);
                } else {
                    player.getRoom().getRoomFunCorpPlayersNickColor().remove(playerClient.getPlayerName());
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (color == -1) ? Application.getTranslationManager().get("colornick_players_norm", formattedNames) : Application.getTranslationManager().get("colornick_players", String.format("#%06X", color & 0xFFFFFF), String.format("#%06X", color & 0xFFFFFF), formattedNames));
            }
        }
    }
}
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
        name = "colormouse",
        usage = "[playerNames|*] (color|off)",
        description = "Temporarily gives a colorized fur.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Colormouse implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            StringBuilder message = new StringBuilder(Application.getTranslationManager().get("colormouse_colors", player.getRoom().getRoomFunCorpPlayersMouseColor().size()));
            for(var colorInfo : player.getRoom().getRoomFunCorpPlayersMouseColor().entrySet()) {
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
                client.setFunCorpMousecolor(color);
                if (color != -1) {
                    if(!player.getRoom().getRoomFunCorpPlayersMouseColor().containsKey(client.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersMouseColor().put(client.getPlayerName(), color);
                } else {
                    player.getRoom().getRoomFunCorpPlayersMouseColor().remove(client.getPlayerName());
                }
            }
            CommandHandler.sendServerMessage(player, color == -1 ? Application.getTranslationManager().get("colormouse_all_norm") : Application.getTranslationManager().get("colormouse_all", String.format("#%06X", color & 0xFFFFFF), String.format("#%06X", color & 0xFFFFFF)));
        } else {
            args.removeLast();
            List<String> playerNames = new ArrayList<>();
            for(String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                playerNames.add(playerName);
                playerClient.setFunCorpMousecolor(color);
                if (color != -1) {
                    player.getRoom().getRoomFunCorpPlayersMouseColor().put(playerClient.getPlayerName(), color);
                } else {
                    player.getRoom().getRoomFunCorpPlayersMouseColor().remove(playerClient.getPlayerName());
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (color == -1) ? Application.getTranslationManager().get("colormouse_players_norm", formattedNames) : Application.getTranslationManager().get("colormouse_players", String.format("#%06X", color & 0xFFFFFF), String.format("#%06X", color & 0xFFFFFF), formattedNames));
            }
        }
    }
}
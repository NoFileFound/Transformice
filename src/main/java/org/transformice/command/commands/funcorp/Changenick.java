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
        name = "changenick",
        usage = "[playerNames|*] [newNickname|off]",
        description = "Temporarily changes the player's nickname.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Changenick implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        List<String> playerNames = new ArrayList<>();
        String newNick;
        boolean isDisable;
        if(args.getFirst().equals("*")) {
            newNick = String.join(" ", args.subList(1, args.size()));
            isDisable = newNick.equals("off");
            for(Client client : player.getRoom().getPlayers().values()) {
                client.setFunCorpNickname(isDisable ? "" : newNick);
            }
            CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("changenick_all_norm") : Application.getTranslationManager().get("changenick_all", newNick));
        } else {
            for(String arg : args) {
                if(player.getRoom().getPlayers().get(arg) != null) {
                    playerNames.add(arg);
                }
            }

            newNick =  String.join(" ", args.subList(playerNames.size(), args.size()));
            isDisable = newNick.equals("off");
            for(String playerName : playerNames) {
                player.getRoom().getPlayers().get(playerName).setFunCorpNickname(isDisable ? "" : newNick);
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (isDisable ? Application.getTranslationManager().get("changenick_players_norm") : Application.getTranslationManager().get("changenick_players"))  + formattedNames);
            }
        }
    }
}
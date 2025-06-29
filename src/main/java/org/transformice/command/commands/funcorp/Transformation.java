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
import org.transformice.packets.send.transformation.C_EnableTransformation;

@Command(
        name = "transformation",
        usage = "[playerNames|*] (off)",
        description = "Temporarily gives the ability to transform.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Transformation implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("transformation_players", player.getRoom().getRoomFunCorpPlayersTransformationAbility().size()));
            return;
        }

        if (args.getFirst().equals("*")) {
            boolean isDisable = args.getLast().equals("off");
            for (Client client : player.getRoom().getPlayers().values()) {
                client.sendPacket(new C_EnableTransformation(!isDisable));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersTransformationAbility().contains(client.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersTransformationAbility().add(client.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersTransformationAbility().remove(client.getPlayerName());
                }
            }
            CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("transformation_all_norm") : Application.getTranslationManager().get("transformation_all"));
        } else {
            boolean isDisable = args.getLast().equals("off");
            List<String> playerNames = new ArrayList<>();
            for (String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                playerNames.add(playerName);
                playerClient.sendPacket(new C_EnableTransformation(!isDisable));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersTransformationAbility().contains(playerClient.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersTransformationAbility().add(playerClient.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersTransformationAbility().remove(playerClient.getPlayerName());
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (isDisable ? Application.getTranslationManager().get("transformation_players_norm") : Application.getTranslationManager().get("transformation_players_2"))  + formattedNames);
            }
        }
    }
}
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
import org.transformice.packets.send.player.C_EnableMeep;

@Command(
        name = "meep",
        usage = "[playerNames|*] (off)",
        description = "Give meep to players.",
        permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Meep implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("meep_players", player.getRoom().getRoomFunCorpPlayersMeepAbility().size()));
            return;
        }

        if (args.getFirst().equals("*")) {
            boolean isDisable = args.getLast().equals("off");
            for (Client client : player.getRoom().getPlayers().values()) {
                client.canMeep = !isDisable;
                client.sendPacket(new C_EnableMeep(!isDisable));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersMeepAbility().contains(client.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersMeepAbility().add(client.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersMeepAbility().remove(client.getPlayerName());
                }
            }
            CommandHandler.sendServerMessage(player, isDisable ? Application.getTranslationManager().get("meep_all_norm") : Application.getTranslationManager().get("meep_all"));
        } else {
            boolean isDisable = args.getLast().equals("off");
            List<String> playerNames = new ArrayList<>();
            for (String playerName : args) {
                Client playerClient = player.getRoom().getPlayers().get(playerName);
                if (playerClient == null) continue;

                playerNames.add(playerName);
                playerClient.canMeep = !isDisable;
                playerClient.sendPacket(new C_EnableMeep(!isDisable));
                if (!isDisable) {
                    if(!player.getRoom().getRoomFunCorpPlayersMeepAbility().contains(playerClient.getPlayerName()))
                        player.getRoom().getRoomFunCorpPlayersMeepAbility().add(playerClient.getPlayerName());
                } else {
                    player.getRoom().getRoomFunCorpPlayersMeepAbility().remove(playerClient.getPlayerName());
                }
            }

            if(!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (isDisable ? Application.getTranslationManager().get("meep_players_norm") : Application.getTranslationManager().get("meep_players_2"))  + formattedNames);
            }
        }
    }
}
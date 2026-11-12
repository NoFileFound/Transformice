package org.transformice.command.commands.funcorp;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "changenick", usage = "[playerNames|*] [newNickname|off]", description = "Temporarily changes the player's nickname.", permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Changenick implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        List<GamePlayer> playerNames = new ArrayList<>();
        int idx = 0;
        if (args.getFirst().equals("*")) {
            playerNames.addAll(player.getPlayerRoom().getPlayers().values());
            idx = 1;
        } else {
            while (idx < args.size()) {
                GamePlayer client = player.getPlayerRoom().getPlayers().get(args.get(idx));
                if (client == null)
                    break;

                playerNames.add(client);
                idx++;
            }
        }

        if (playerNames.isEmpty()) {
            CommandHandler.sendServerMessage(player, "No players found.");
            return;
        }

        String newNick = String.join(" ", args.subList(idx, args.size()));
        boolean isDisable = newNick.equals("off");
        for (GamePlayer client : playerNames)
            client.funcorpPlayerName = isDisable ? "" : newNick;

        if (args.getFirst().equals("*")) {
            CommandHandler.sendServerMessage(player, isDisable ? "All the players nickname was changed to normal." : String.format("All the players nickname was changed to %s", newNick));
        } else {
            String formattedNames = playerNames.stream().map(client -> "<BV>" + client.getPlayerName() + "</BV>").collect(Collectors.joining(", "));
            CommandHandler.sendServerMessage(player, (isDisable ? "Changed the nickname to players to normal: " : "Changed the nickname to players: ") + formattedNames);
        }
    }
}
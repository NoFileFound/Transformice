package org.transformice.command.commands.funcorp;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "colornick", usage = "[playerNames|*] (color|off)", description = "Temporarily changes the color of player nicknames.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Colornick implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom room = player.getPlayerRoom();
        Map<String, Integer> changedPlayers = room.getRoomFunCorpPlayersNickColor();
        if (args.isEmpty()) {
            StringBuilder message = new StringBuilder(String.format("Colored nicknames: %d", changedPlayers.size()));
            for (var colorInfo : changedPlayers.entrySet()) {
                String color = String.format("#%06X", colorInfo.getValue() & 0xFFFFFF);
                message.append("<br>").append(colorInfo.getKey()).append(" -> ").append(String.format("<font color='%s'>%s</font>", color, color));
            }

            CommandHandler.sendServerMessage(player, message.toString());
            return;
        }

        if (args.size() < 2)
            return;

        int color;
        try {
            color = Integer.parseInt(args.getLast());
        } catch (NumberFormatException ignored) {
            CommandHandler.sendServerMessage(player, "Invalid color.");
            return;
        }

        boolean isAll = args.getFirst().equals("*");
        String formattedColor = String.format("#%06X", color & 0xFFFFFF);
        List<GamePlayer> targets = new ArrayList<>();
        if (isAll) {
            targets.addAll(room.getPlayers().values());
        } else {
            for (int i = 0; i < args.size() - 1; i++) {
                GamePlayer target = room.getPlayers().get(args.get(i));
                if (target != null)
                    targets.add(target);
            }
        }

        List<String> playerNames = new ArrayList<>();
        for (GamePlayer target : targets) {
            String playerName = target.getPlayerName();
            target.funcorpNickColor = color;
            playerNames.add(playerName);
            if (color == -1) {
                changedPlayers.remove(playerName);
            } else {
                changedPlayers.put(playerName, color);
            }
        }

        if (isAll) {
            CommandHandler.sendServerMessage(player, color == -1 ? "All the nickname colors have been removed." : String.format("All the players now have the nickname color <font color='%s'>%s</font>.", formattedColor, formattedColor));
        } else if (!playerNames.isEmpty()) {
            String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
            CommandHandler.sendServerMessage(player, color == -1 ? String.format("Nickname colors removed from players: %s", formattedNames) : String.format("New nickname color (<font color='%s'>%s</font>) for players: %s", formattedColor, formattedColor, formattedNames));
        }
    }
}
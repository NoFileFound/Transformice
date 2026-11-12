package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "ls", usage = "[text]", description = "Lists of existing rooms that have the chosen keyword.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Ls implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String matching = args.isEmpty() ? null : args.getFirst();
        StringBuilder result = new StringBuilder();
        if (matching != null)
            result.append(String.format("<N>List of rooms matching [%s]:</N><br>", matching));

        int totalPlayers = 0;
        for (GameRoom room : server.getRooms().values()) {
            if (matching != null && !room.getRoomName().contains(matching))
                continue;

            int playerCount = room.getPlayers().size();
            result.append("<BL>").append(room.getRoomName()).append("</BL> <G>(").append(room.getRoomCommunity()).append(" / serveur) :</G> <V>").append(playerCount).append("</V><br>");
            totalPlayers += playerCount;
        }

        result.append("<J>Total players:</J> <R>").append(totalPlayers).append("</R>");
        CommandHandler.sendLogMessage(player, 0, result.toString());
    }
}
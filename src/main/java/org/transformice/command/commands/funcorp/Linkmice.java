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

// Packets
import org.transformice.packets.send.room.PacketSendCreateSoulmate;

@Command(name = "linkmice", usage = "[playerNames|*] (off)", description = "Temporarily links players.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Linkmice implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom room = player.getPlayerRoom();
        Map<String, String> linkedPlayers = room.getRoomFunCorpPlayersLinked();
        if (args.isEmpty()) {
            StringBuilder message = new StringBuilder("Linked players:");
            for (var link : linkedPlayers.entrySet())
                message.append("<br>").append(link.getKey()).append(" - ").append(link.getValue());

            CommandHandler.sendServerMessage(player, message.toString());
            return;
        }

        boolean isAll = args.getFirst().equals("*");
        boolean isDisable = args.getLast().equals("off");
        if (isAll) {
            for (GamePlayer client : room.getPlayers().values()) {
                String playerName = player.getPlayerName();
                String targetName = client.getPlayerName();
                room.broadcastPacket(new PacketSendCreateSoulmate(player.getSessionId(), client.getSessionId(), !isDisable));
                if (isDisable) {
                    linkedPlayers.remove(playerName, targetName);
                } else {
                    linkedPlayers.put(playerName, targetName);
                }
            }

            CommandHandler.sendServerMessage(player, isDisable ? "All the links have been removed." : "All the players are now linked.");
        } else {
            int j = 0;
            GamePlayer firstPlayer = null;
            while (j < args.size()) {
                firstPlayer = room.getPlayers().get(args.get(j));
                if (firstPlayer != null)
                    break;

                j++;
            }

            if (firstPlayer == null)
                return;

            List<String> playerNames = new ArrayList<>();
            String firstName = firstPlayer.getPlayerName();
            for (int i = j + 1; i < args.size() - (isDisable ? 1 : 0); i++) {
                GamePlayer target = room.getPlayers().get(args.get(i));
                if (target == null)
                    continue;

                String targetName = target.getPlayerName();
                if (firstName.equals(targetName))
                    continue;

                playerNames.add(targetName);
                room.broadcastPacket(new PacketSendCreateSoulmate(target.getSessionId(), firstPlayer.getSessionId(), !isDisable));
                if (isDisable) {
                    linkedPlayers.remove(firstName, targetName);
                } else {
                    linkedPlayers.put(firstName, targetName);
                }
            }

            if (!playerNames.isEmpty()) {
                String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
                CommandHandler.sendServerMessage(player, (isDisable ? "The links involving the following players have been removed: " : "The following players are now linked: ") + formattedNames);
            }
        }
    }
}
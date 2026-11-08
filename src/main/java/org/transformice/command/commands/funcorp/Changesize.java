package org.transformice.command.commands.funcorp;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.room.PacketSendPlayerChangeSize;

@Command(name = "changesize", usage = "[playerNames|*] [size|off]", description = "Temporarily changes the size.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Changesize implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom room = player.getPlayerRoom();
        List<String> changedPlayers = room.getRoomFunCorpPlayersChangedSize();
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("Players with sizes: %d", changedPlayers.size()));
            return;
        }

        if (args.size() < 2)
            return;

        boolean isAll = args.getFirst().equals("*");
        boolean isDisable = args.getLast().equals("off");
        int size;
        try {
            size = isDisable ? 100 : Integer.parseInt(args.getLast());
        } catch (NumberFormatException e) {
            CommandHandler.sendServerMessage(player, "Invalid size.");
            return;
        }

        if (!isDisable && (size < 10 || size > 500)) {
            CommandHandler.sendServerMessage(player, "Invalid size.");
            return;
        }

        List<String> playerNames = new ArrayList<>();
        if (isAll) {
            for (GamePlayer client : room.getPlayers().values()) {
                playerNames.add(client.getPlayerName());
            }
        } else {
            for (int i = 0; i < args.size() - 1; i++) {
                GamePlayer client = room.getPlayers().get(args.get(i));
                if (client != null) {
                    playerNames.add(client.getPlayerName());
                }
            }
        }

        for (String playerName : playerNames) {
            GamePlayer target = room.getPlayers().get(playerName);
            room.broadcastPacket(new PacketSendPlayerChangeSize(target.getSessionId(), size, false));
            if (isDisable) {
                changedPlayers.remove(playerName);
            } else if (!changedPlayers.contains(playerName)) {
                changedPlayers.add(playerName);
            }
        }

        if (isAll) {
            CommandHandler.sendServerMessage(player, isDisable ? "All the players now have their regular size." : String.format("All the players now have the size %d", size));
        } else if (!playerNames.isEmpty()) {
            String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
            CommandHandler.sendServerMessage(player, isDisable ? String.format("The following players now have their regular size: %s", formattedNames) : String.format("The following players now have the size %d: %s", size, formattedNames));
        }
    }
}
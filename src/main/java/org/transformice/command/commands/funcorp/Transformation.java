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
import org.transformice.packets.send.player.PacketSendEnableTransformation;

@Command(name = "transformation", usage = "[playerNames|*] (off)", description = "Temporarily gives the ability to transform.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Transformation implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom room = player.getPlayerRoom();
        List<String> changedPlayers = room.getRoomFunCorpPlayersTransformationAbility();
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("Players with transformations: %d", changedPlayers.size()));
            return;
        }

        boolean isAll = args.getFirst().equals("*");
        boolean isDisable = args.getLast().equals("off");
        List<GamePlayer> targets = new ArrayList<>();
        if (isAll) {
            targets.addAll(room.getPlayers().values());
        } else {
            for (int i = 0; i < args.size() - (isDisable ? 1 : 0); i++) {
                GamePlayer target = room.getPlayers().get(args.get(i));
                if (target != null)
                    targets.add(target);
            }
        }

        List<String> playerNames = new ArrayList<>();
        for (GamePlayer target : targets) {
            String playerName = target.getPlayerName();
            target.canTransform = !isDisable;
            target.broadcastPacket(new PacketSendEnableTransformation(!isDisable));
            playerNames.add(playerName);
            if (isDisable) {
                changedPlayers.remove(playerName);
            } else if (!changedPlayers.contains(playerName)) {
                changedPlayers.add(playerName);
            }
        }

        if (isAll) {
            CommandHandler.sendServerMessage(player, isDisable ? "All the transformations powers have been removed." : "Transformations powers given to all players in the room.");
        } else if (!playerNames.isEmpty()) {
            String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
            CommandHandler.sendServerMessage(player, (isDisable ? "Transformations powers removed from players: " : "Transformations powers given to players: ") + formattedNames);
        }
    }
}
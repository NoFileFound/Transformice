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
import org.transformice.packets.send.player.PacketSendEnableMeep;

@Command(name = "meep", usage = "[playerNames|*] (off)", description = "Give meep to players.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Meep implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom room = player.getPlayerRoom();
        List<String> changedPlayers = room.getRoomFunCorpPlayersMeepAbility();
        if (args.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("Players with meep: %d", changedPlayers.size()));
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
            target.canMeep = !isDisable;
            target.broadcastPacket(new PacketSendEnableMeep(!isDisable));
            playerNames.add(playerName);
            if (isDisable) {
                changedPlayers.remove(playerName);
            } else if (!changedPlayers.contains(playerName)) {
                changedPlayers.add(playerName);
            }
        }

        if (isAll) {
            CommandHandler.sendServerMessage(player, isDisable ? "All the meep powers have been removed." : "Meep powers given to all players in the room.");
        } else if (!playerNames.isEmpty()) {
            String formattedNames = playerNames.stream().map(name -> "<BV>" + name + "</BV>").collect(Collectors.joining(", "));
            CommandHandler.sendServerMessage(player, (isDisable ? "Meep powers removed from players: " : "Meep powers given to players: ") + formattedNames);
        }
    }
}
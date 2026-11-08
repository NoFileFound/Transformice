package org.transformice.command.commands.player;

// Imports
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "ban", usage = "[playerName] [hours] [reason]", description = "Bans the player name.", permission = Command.CommandPermission.PLAYER, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Ban implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        if (args.size() >= 3 && (player.hasStaffPermission(StaffRoleType.ARBITRE, false) || player.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || player.hasStaffPermission(StaffRoleType.ADMIN, false))) {
            try {
                server.sendPlayerBan(playerName, Integer.parseInt(args.get(1)), String.join(" ", args.subList(2, args.size())), player, false);
            } catch (NumberFormatException ignored) {
                CommandHandler.sendServerMessage(player, "Invalid number.");
            }

            return;
        }

        if (args.size() != 1)
            return;

        GameRoom room = player.getPlayerRoom();
        GamePlayer client = server.getPlayers().get(playerName);
        if (client == null || client == player || client.getPlayerRoom() != room)
            return;

        if (player.getPlayerName().equalsIgnoreCase(client.getPlayerName()))
            return;

        if (room.getRoomNameSuffix().startsWith("*strm_" + player.getPlayerName())) {
            client.sendEnterRoom(server.getRecommendedRoom("", client.getPlayerCommunity()), "");
            return;
        }

        String roomName = client.getRoomName();
        Set<String> votes = client.getVoteBans().computeIfAbsent(roomName, key -> new HashSet<>());
        if (!votes.add(player.getPlayerName()))
            return;

        if (votes.size() >= (int) Math.ceil(room.getDistinctPlayerCount() * 0.9) && room.getDistinctPlayerCount() >= 4) {
            votes.clear();
            server.getTempRestrictedRoomTimer(client.getPlayerName(), roomName).schedule();
        }
    }
}
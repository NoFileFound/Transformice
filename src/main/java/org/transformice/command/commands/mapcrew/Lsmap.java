package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.MapEntry;

@Command(name = "lsmap", usage = "(playerName)", description = "Lists all maps made by a player.", permission = {Command.CommandPermission.PLAYER}, aliases = {"lsmaps"})
@SuppressWarnings("unused")
public final class Lsmap implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = (!args.isEmpty()) ? args.getFirst() : player.getPlayerName();
        if (!args.isEmpty() && !player.hasStaffPermission(StaffRoleType.MAPCREW, false) && !player.hasStaffPermission(StaffRoleType.ARBITRE, false) && !player.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) && !player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) && !player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false))
            return;

        List<MapEntry> maps = server.getDatabase().loadMapsByAuthor(playerName);
        if (maps.isEmpty()) {
            CommandHandler.sendServerMessage(player, String.format("The player %s don't have any maps yet.", playerName));
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<font size= \"12\"><V>%s<N>'s maps: <BV>%s</font><br>", playerName, maps.size()));
        for (var map : maps) {
            int totalVotes = map.mapYesVotes + map.mapNoVotes;
            int rating = (int) ((1.0 * map.mapYesVotes / totalVotes) * 100);
            builder.append(String.format("<N>%s</N> - @%s - %s - %s%% - P%s<br>", map.getMapAuthor(), map.getMapCode(), totalVotes, rating, map.mapCategory));
        }

        CommandHandler.sendLogMessage(player, 0, builder.toString());
    }
}
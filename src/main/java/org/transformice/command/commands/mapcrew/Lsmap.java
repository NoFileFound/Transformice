package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.database.DBUtils;
import org.transformice.database.collections.MapEditor;

@Command(
        name = "lsmap",
        usage = "(playerName)",
        description = "Lists all maps made by a player.",
        permission = {Command.CommandPermission.PLAYER},
        aliases = {"lsmaps"}
)
@SuppressWarnings("unused")
public final class Lsmap implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = (!args.isEmpty()) ? args.getFirst() : player.getPlayerName();
        if(!args.isEmpty() && !player.hasStaffPermission("MapCrew", "lsmap") && !player.hasStaffPermission("Modo", "lsmap")) return;

        List<MapEditor> maps = DBUtils.findMapByCreator(playerName);
        if (maps.isEmpty()) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("mapsnotfound", playerName));
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<font size= \"12\"><V>%s<N>'s maps: <BV>%s</font><br>", playerName, maps.size()));
        for (var map : maps) {
            int totalVotes = map.getMapYesVotes() + map.getMapNoVotes();
            int rating = (int) ((1.0 * map.getMapYesVotes() / totalVotes) * 100);
            builder.append(String.format("<N>%s</N> - @%s - %s - %s%% - P%s<br>", map.getMapAuthor(), map.getMapCode(), totalVotes, rating, map.getMapCategory()));
        }
        CommandHandler.sendLogMessage(player, 0, builder.toString());
    }
}
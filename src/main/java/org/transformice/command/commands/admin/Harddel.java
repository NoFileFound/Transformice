package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.MapEntry;

@Command(name = "harddel", usage = "[@mapCode]", description = "Deletes a map from the database.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Harddel implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String mapCode = args.getFirst();
        if (!mapCode.startsWith("@")) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid map code.");
            return;
        }

        try {
            MapEntry mapEntry = server.getDatabase().loadMapByCode(Integer.parseInt(mapCode.substring(1)));
            if (mapEntry == null) {
                CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid map code.");
                return;
            }

            server.getDatabase().deleteMap(mapEntry);
            CommandHandler.sendServerMessage(player, String.format("Successfully deleted the map %s from the database.", mapCode));
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid map code.");
        }
    }
}
package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "dbggiveallconsumables", description = "Gives all consumables from the config.", permission = Command.CommandPermission.DEBUG_ONLY)
@SuppressWarnings("unused")
public final class Dbggiveallconsumables implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        for (var info : server.getInventoryConfig().keySet())
            player.getParseInventory().addConsumable(String.valueOf(info), 100, true);

        CommandHandler.sendServerMessage(player, "OK");
    }
}
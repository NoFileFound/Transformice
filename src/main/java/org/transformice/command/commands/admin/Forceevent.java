package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "forceevent", description = "Forces the event to happen in the next round. (Works only in eligible rooms).", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Forceevent implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        boolean res = server.getParseEvents().forceStartEventNextRound(player.getPlayerRoom());
        CommandHandler.sendServerMessage(player, (res ? "The event will start next round." : "This room is disabled showing the events."));
    }
}
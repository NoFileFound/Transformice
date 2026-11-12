package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "max", description = "Gives the maximum number of players connected since the last reboot.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Max implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        CommandHandler.sendServerMessage(player, String.format("Maximum number of players connected since the last reboot: <BV>%d</BV>.", server.getMaximumPlayerCount().size()));
    }
}
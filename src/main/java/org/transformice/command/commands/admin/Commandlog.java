package org.transformice.command.commands.admin;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.CommandLogEntry;

@Command(name = "commandlog", usage = "[playerName]", description = "Lists commands typed by the player.", permission = {Command.CommandPermission.ADMINISTRATOR}, aliases = {"commandlogs", "commandslog"})
@SuppressWarnings("unused")
public final class Commandlog implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.isEmpty() ? player.getPlayerName() : args.getFirst();
        List<CommandLogEntry> commandList = server.getDatabase().loadCommandLogByPlayer(playerName);
        if (commandList.isEmpty()) {
            CommandHandler.sendServerMessage(player, "The nickname has no recently entered commands.");
            return;
        }

        StringBuilder builder = new StringBuilder(String.format("<p align='center'><N>Command Logs for <V>%s</V></N></p><br>", playerName));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        for (CommandLogEntry command : commandList)
            builder.append(String.format("[%s] %s (<G>%s</G>) : <BL>%s</BL><br>", formatter.format(Instant.ofEpochSecond(command.getDate())), command.getPlayerName(), command.getIpAddress(), command.getCommand()));

        CommandHandler.sendLogMessage(player, 0, builder.toString());
    }
}
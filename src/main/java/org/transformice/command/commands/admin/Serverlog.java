package org.transformice.command.commands.admin;

// Imports
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "serverlog", usage = "(date)", description = "Views the server logging from specific date or current date if date is not specified.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Serverlog implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String date = args.isEmpty() ? "" : args.getFirst();
        String fileName = date.isEmpty() ? "app.log" : date + ".log";
        try {
            String log = Files.readString(Path.of("./logs/", fileName));
            CommandHandler.sendLogMessage(player, 1, log.replace("\r\n", "\n"));
        } catch (IOException ignored) {
            CommandHandler.sendServerMessage(player, String.format("Unable to find a log with the date %s", date));
        }
    }
}
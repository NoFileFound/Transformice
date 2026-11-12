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

@Command(name = "serverconfig", description = "Views the server configuration.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Serverconfig implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            String log = Files.readString(Path.of("./config/properties.json"));
            CommandHandler.sendLogMessage(player, 1, log.replace("\r\n", "\n"));
        } catch (IOException ignored) {
        }
    }
}
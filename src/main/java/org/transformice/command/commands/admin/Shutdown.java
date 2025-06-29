package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "shutdown",
        description = "Shutdowns the server.",
        permission = Command.CommandPermission.ADMINISTRATOR
)
@SuppressWarnings("unused")
public final class Shutdown implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        server.closeServer();
    }
}
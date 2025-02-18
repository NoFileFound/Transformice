package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "helloworld",
        description = "Hello world",
        permission = Command.CommandPermission.DEBUG_ONLY
)

public final class Helloworld implements CommandHandler {
    @Override
    public void execute(Client player, List<String> args) {
        CommandHandler.sendServerMessage(player, "Hello world");
    }
}
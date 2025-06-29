package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "module",
        usage = "(module name)",
        description = "Lists the number of players playing each official module.",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public class Module implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        /// TODO: Lua modules.
    }
}
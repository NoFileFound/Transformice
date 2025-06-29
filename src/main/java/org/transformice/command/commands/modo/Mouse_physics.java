package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "mouse_physics",
        usage = "[playerName]",
        description = "Gives information about mouse physics on given player.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Mouse_physics implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        /// TODO: [Unimplemented] Commands->mouse_physics
        throw new RuntimeException("[Unimplemented] Commands->mouse_physics");
    }
}
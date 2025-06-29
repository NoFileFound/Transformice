package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "roomevent",
        usage = "[on|off]",
        description = "Highlights the current room in the room list.",
        permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR},
        isFunCorpOnlyCommand = true
)
@SuppressWarnings("unused")
public final class Roomevent implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getRoom().isFunCorpHighlighedRoom = !player.getRoom().isFunCorpHighlighedRoom;
        CommandHandler.sendServerMessage(player, "Done.");
    }
}
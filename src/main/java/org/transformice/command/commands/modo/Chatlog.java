package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "chatlog",
        usage = "[playerName]",
        description = "Gets the chat and whispers messages on given player.",
        permission = { Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Chatlog implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getParseModopwetInstance().sendChatLog(args.getFirst());
    }
}
package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "avatar",
        usage = "[avatarId]",
        description = "Changes the avatar",
        permission = Command.CommandPermission.PLAYER,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Avatar implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getParseTribulleInstance().sendChangePlayerAvatar(Integer.parseInt(args.getFirst()));
    }
}
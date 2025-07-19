package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "commu",
        usage = "[community]",
        description = "Changes your community in-game.",
        permission = { Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.LUADEV}
)
@SuppressWarnings("unused")
public final class Commu implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String community = args.getFirst();
        if(community.length() != 2) {
            CommandHandler.sendServerMessage(player, "Invalid community name.");
            return;
        }

        player.playerCommunity = community.toUpperCase();
        player.sendEnterRoom(server.getRecommendedRoom(""), "");
    }
}
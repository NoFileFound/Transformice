package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "commu", usage = "[community]", description = "Changes your community in-game.", permission = { Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.LUADEV})
@SuppressWarnings("unused")
public final class Commu implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String community = args.getFirst();
        if(community.length() != 2) {
            CommandHandler.sendServerMessage(player, "Invalid community name.");
            return;
        }

        player.setPlayerCommunity(community.toUpperCase());
        player.sendEnterRoom(server.getRecommendedRoom("", player.getPlayerCommunity()), "");
    }
}
package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "moveall", usage = "[roomName]", description = "Moves every player from the server in a room. If a room doesn't exist it will be made automatically. If a player isn't in the same community as you it will be teleported to the room in his community.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Moveall implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String roomName = String.join(" ", args);
        for (GamePlayer client : server.getPlayers().values())
            client.sendEnterRoom(roomName, "");
    }
}
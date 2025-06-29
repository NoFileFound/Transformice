package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(
        name = "room*",
        usage = "[roomName]",
        description = "Joins the specified room on the specified community.",
        permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1,
        aliases = {"sala*", "salon*", "zimmer*", "kamer*"}
)
@SuppressWarnings("unused")
public final class RoomStar implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String roomName = args.getFirst();
        if(!roomName.contains("-")) {
            roomName = player.playerCommunity + '-' + roomName;
        }

        player.sendEnterRoom(Utils.formatRoomName(roomName), "");
    }
}
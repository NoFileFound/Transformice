package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(
        name = "creator",
        usage = "(roomName)",
        description = "Returns the first mouse to join the given room.",
        permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Creator implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String roomName = (!args.isEmpty()) ? args.getFirst() : player.getRoomName();
        if(!roomName.contains("-")) {
            roomName = player.playerCommunity + '-' + roomName;
        }

        roomName = Utils.formatRoomName(roomName);
        if(server.checkExistingRoom(roomName)) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("creator_found", roomName, server.getRooms().get(roomName).getRoomCreator()));
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("closeroom_roomnotexist", roomName));
        }
    }
}
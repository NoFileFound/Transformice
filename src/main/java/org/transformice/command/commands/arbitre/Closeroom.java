package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Stafflog;
import org.transformice.utils.Utils;

@Command(
        name = "closeroom",
        usage = "(roomName)",
        description = "Kicks everyone from the given room.",
        permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Closeroom implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String roomName = (!args.isEmpty()) ? String.join(" ", args.subList(1, args.size())) : player.getRoomName();
        if(!roomName.contains("-") && !roomName.startsWith("*")) {
            roomName = player.playerCommunity + '-' + roomName;
        }

        roomName = Utils.formatRoomName(roomName);
        if(server.checkExistingRoom(roomName)) {
            for(Client client : server.getRooms().get(roomName).getPlayers().values()) {
                client.sendEnterRoom(server.getRecommendedRoom(""), "");
            }
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("closeroom_roomclosed", roomName));
            server.sendServerMessage(Application.getTranslationManager().get("closeroom_roomclosednotify", player.getPlayerName(), roomName), false, player);
            new Stafflog(player.getPlayerName(), "Closeroom", args);
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("closeroom_roomnotexist", roomName));
        }
    }
}
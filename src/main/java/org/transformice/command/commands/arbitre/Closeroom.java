package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.utils.Utils;

@Command(name = "closeroom", usage = "(roomName)", description = "Kicks everyone from the given room.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Closeroom implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String roomName = (!args.isEmpty()) ? String.join(" ", args) : player.getRoomName();
        roomName = Utils.formatRoomName(roomName, player.getPlayerCommunity(), true);
        GameRoom room = server.getRooms().get(roomName);
        if (room == null) {
            CommandHandler.sendServerMessage(player, String.format("The room <BV>[%s]</BV> does not exist.", roomName));
            return;
        }

        for (GamePlayer client : room.getPlayers().values())
            client.sendEnterRoom(server.getRecommendedRoom("", client.getPlayerCommunity()), "");

        CommandHandler.sendServerMessage(player, String.format("The room <BV>[%s]</BV> got closed.", roomName));
        server.sendServerMessageOthers(player, String.format("%s closed the room [%s].", player.getPlayerName(), roomName), StaffRoleType.ARBITRE);
    }
}
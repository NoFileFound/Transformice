package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(name = "creator", usage = "(roomName)", description = "Returns the first mouse to join the given room.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Creator implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String roomName = (!args.isEmpty()) ? String.join(" ", args) : player.getRoomName();
        roomName = Utils.formatRoomName(roomName, player.getPlayerCommunity(), true);
        GameRoom room = server.getRooms().get(roomName);
        if (room == null)
            CommandHandler.sendServerMessage(player, String.format("The room <BV>[%s]</BV> does not exist.", roomName));
        else
            CommandHandler.sendServerMessage(player, String.format("Room [%s]'s creator: <BV>%s</BV>", roomName, room.getRoomAuthor()));
    }
}
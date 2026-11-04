package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(name = "lsroom", usage = "(roomName)", description = "Lists the players present in the room.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Lsroom implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String roomName = (!args.isEmpty()) ? String.join(" ", args) : player.getRoomName();
        roomName = Utils.formatRoomName(roomName, player.getPlayerCommunity(), true);
        GameRoom room = server.getRooms().get(roomName);
        if (room == null) {
            CommandHandler.sendServerMessage(player, String.format("The room <BV>[%s]</BV> does not exist.", roomName));
            return;
        }

        StringBuilder builder = new StringBuilder();
        StringBuilder hiddenPlayers = new StringBuilder();
        builder.append(String.format("Players in room [%s]: %d<br>", roomName, room.getPlayers().size()));
        for (GamePlayer client : room.getPlayers().values()) {
            String ip = Utils.encodeIP(client.getIpAddress());
            String line = String.format("<BL>%s / </BL><font color='%s'>%s</font> <G>(%s)</G>%s<br>", client.getPlayerName(), Utils.colorIP(ip), ip, Utils.getCountryNameByCode(client.getCountryCode()), client.isHidden ? " <BL>(invisible)</BL>" : "");
            if (client.isHidden)
                hiddenPlayers.append(line);
            else
                builder.append(line);
        }

        builder.append(hiddenPlayers);
        CommandHandler.sendServerMessage(player, builder.toString());
    }
}
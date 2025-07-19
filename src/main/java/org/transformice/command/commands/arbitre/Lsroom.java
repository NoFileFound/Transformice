package org.transformice.command.commands.arbitre;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.IPHex;
import org.transformice.utils.Utils;

@Command(
        name = "lsroom",
        usage = "(roomName)",
        description = "Lists the players present in the room.",
        permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Lsroom implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String roomName = (!args.isEmpty()) ? args.getFirst() : player.getRoomName();
        if(!roomName.contains("-")) {
            roomName = player.playerCommunity + '-' + roomName;
        }

        roomName = Utils.formatRoomName(roomName);
        if(server.checkExistingRoom(roomName)) {
            StringBuilder builder = new StringBuilder();
            builder.append(Application.getTranslationManager().get("lsroom_results", roomName, server.getRooms().get(roomName).getPlayersCount()));
            List<String> hiddenPlayers = new ArrayList<>();
            for(Client client : server.getRooms().get(roomName).getPlayers().values()) {
                if(client.isHidden) {
                    hiddenPlayers.add(client.getPlayerName());
                } else {
                    builder.append(String.format("<BL>%s / </BL><font color = '%s'>%s</font> <G>(%s)</G><br>", client.getPlayerName(), IPHex.colorIP(IPHex.encodeIP(client.getIpAddress())), IPHex.encodeIP(client.getIpAddress()), client.getCountryName()));
                }
            }
            for(String hiddenPlayer : hiddenPlayers) {
                Client client = server.getPlayers().get(hiddenPlayer);
                builder.append(String.format("<BL>%s / </BL><font color = '%s'>%s</font> <G>(%s)</G> <BL>(invisible)<BL>", client.getPlayerName(), IPHex.colorIP(IPHex.encodeIP(client.getIpAddress())), IPHex.encodeIP(client.getIpAddress()), client.getCountryName()));
            }
            CommandHandler.sendServerMessage(player, builder.toString());
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("closeroom_roomnotexist", roomName));
        }
    }
}
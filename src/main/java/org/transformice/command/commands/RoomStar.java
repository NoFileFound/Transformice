package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

@Command(name = "room*", usage = "[roomName]", description = "Joins the specified room on the specified community.", permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.LUADEV, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1, aliases = {"sala*", "salon*", "zimmer*", "kamer*"})
@SuppressWarnings("unused")
public final class RoomStar implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String roomName = String.join(" ", args);
        player.sendEnterRoom(Utils.formatRoomName(roomName, player.getPlayerCommunity(), true), "");
    }
}
package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.RoomType;

@Command(name = "info", description = "Info about the current map", permission = Command.CommandPermission.PLAYER)
@SuppressWarnings("unused")
public final class Info implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GameRoom playerRoom = player.getPlayerRoom();
        boolean check = playerRoom.getRoomType() == RoomType.EDITOR || playerRoom.getRoomType() == RoomType.TOTEM || playerRoom.getRoomType() == RoomType.TUTORIAL || playerRoom.getCurrentMap().mapPerma == -1;
        if (!check)
            CommandHandler.sendServerMessage(player, String.format("%s - @%d - %d - %d - P%d", playerRoom.getCurrentMap().mapName, playerRoom.getCurrentMap().mapCode, playerRoom.getCurrentMap().mapYesVotes + playerRoom.getCurrentMap().mapNoVotes, (playerRoom.getCurrentMap().mapYesVotes + playerRoom.getCurrentMap().mapNoVotes > 0) ? (playerRoom.getCurrentMap().mapYesVotes / playerRoom.getCurrentMap().mapNoVotes) * 100 : 100, playerRoom.getCurrentMap().mapPerma));
    }
}
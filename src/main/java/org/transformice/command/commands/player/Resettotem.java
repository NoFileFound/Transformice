package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.RoomType;

@Command(name = "resettotem", description = "Resets the current totem.")
@SuppressWarnings("unused")
public final class Resettotem implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (player.getPlayerRoom().getRoomType() == RoomType.TOTEM) {
            player.setIsResetTotem(true);
            player.getPlayerRoom().sendChangeMap();
        }
    }
}
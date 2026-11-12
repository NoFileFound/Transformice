package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.psframework.utils.PSBase64;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.RoomType;

@Command(name = "sauvertotem", description = "Saves the current totem.")
@SuppressWarnings("unused")
public final class Sauvertotem implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (player.getPlayerRoom().getRoomType() == RoomType.TOTEM) {
            player.tempTotem[1] = PSBase64.encode((String) player.tempTotem[1]);
            player.getPlayerData().totemInfo = player.tempTotem;
            player.tempTotem = new Object[]{0, ""};
            player.sendEnterRoom("", "");
        }
    }
}
package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.libraries.Pair;

@Command(
        name = "resettotem",
        description = "Resets the current totem.",
        permission = Command.CommandPermission.PLAYER
)
@SuppressWarnings("unused")
public final class Resettotem implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(player.getRoom().isTotem() && player.tempTotemInfo != null) {
            player.getAccount().setTotemInfo(new Object[]{player.tempTotemInfo.getFirst(), player.tempTotemInfo.getSecond()});
            player.getAccount().save();
            player.tempTotemInfo = new Pair<>(0, "");
            player.getRoom().checkChangeMap();
        }
    }
}
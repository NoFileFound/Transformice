package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "roomevent", usage = "[on|off]", description = "Highlights the current room in the room list.", permission = {Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR}, isFunCorpOnlyCommand = true)
@SuppressWarnings("unused")
public final class Roomevent implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.getPlayerRoom().isFunCorpHighlighedRoom = !player.getPlayerRoom().isFunCorpHighlighedRoom;
        CommandHandler.sendServerMessage(player, "Done.");
        /// TODO: [UNKNOWN] Investigate in the original game.
    }
}
package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "luadmin", description = "Enables the ability to run system lua functions.", permission = Command.CommandPermission.ADMINISTRATOR)
@SuppressWarnings("unused")
public final class Luadmin implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.isLuaAdmin = !player.isLuaAdmin;
        CommandHandler.sendServerMessage(player, player.isLuaAdmin ? "You can run functions programming as administrator." : "You can't run functions programming as administrator.");
    }
}
package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "luadmin",
        description = "Enables the ability to run java code in functions prompt.",
        permission = Command.CommandPermission.ADMINISTRATOR
)
@SuppressWarnings("unused")
public final class Luadmin implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.isLuaAdmin = !player.isLuaAdmin;
        CommandHandler.sendServerMessage(player, player.isLuaAdmin ? Application.getTranslationManager().get("luadevon") : Application.getTranslationManager().get("luadevoff"));
    }
}
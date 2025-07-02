package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.packets.send.chat.C_HtmlMessage;
import org.transformice.packets.send.chat.C_ServerMessage;

@Command(
        name = "module",
        usage = "(module name)",
        description = "Lists the number of players playing each official module.",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public class Module implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_ServerMessage(true, "Module list:"));
        for(String minigame : server.getMinigameList()) {
            player.sendPacket(new C_HtmlMessage(String.format("<VP>#%s</VP> : <G>0</G>", minigame)));
        }
    }
}
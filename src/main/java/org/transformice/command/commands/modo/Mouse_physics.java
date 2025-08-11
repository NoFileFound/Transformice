package org.transformice.command.commands.modo;

// Imports
import java.util.List;

import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.packets.send.chat.C_HtmlMessage;

@Command(
        name = "mouse_physics",
        usage = "[playerName]",
        description = "Gives information about mouse physics on given player.",
        permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Mouse_physics implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client client = server.getPlayers().get(playerName);

        if(client != null) {
            player.sendPacket(new C_HtmlMessage(String.format("<R>Jump power: %f</R>", client.lastJumpPower)));
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}
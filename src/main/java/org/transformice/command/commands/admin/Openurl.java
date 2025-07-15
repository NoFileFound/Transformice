package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.language.C_OpenLink;

@Command(
        name = "openurl",
        usage = "[url]",
        description = "Opens an url for everyone.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Openurl implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String url = args.getFirst();
        if(!url.matches("^(https?://)?([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})(:[0-9]{1,5})?(/\\S*)?$")) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidurl"));
            return;
        }

        for(Client client : player.getRoom().getPlayers().values()) {
            client.sendPacket(new C_OpenLink(url));
        }
    }
}
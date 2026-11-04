package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.language.PacketSendOpenLink;

@Command(name = "openlink", usage = "[url]", description = "Opens a browser link in every player in the server.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Openlink implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String url = args.getFirst();
        if (!url.matches("^(https?://)?([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})(:[0-9]{1,5})?(/\\S*)?$")) {
            CommandHandler.sendServerMessage(player, "The supplied link is an invalid.");
            return;
        }

        for (GamePlayer client : server.getPlayers().values())
            client.broadcastPacket(new PacketSendOpenLink(url));
    }
}
package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@Command(name = "ch", usage = "[playerName]", description = "Forces the specific player to be a shaman in next round.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.LUADEV, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Ch implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GamePlayer client = server.getPlayers().get(args.getFirst());
        if (client == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        if (client.getPlayerRoom().forceNextShaman == client) {
            client.getPlayerRoom().forceNextShaman = null;
            client.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$PasProchaineChamane %s", client.getPlayerName())));
        } else {
            client.getPlayerRoom().forceNextShaman = client;
            client.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$ProchaineChamane %s", client.getPlayerName())));
        }
    }
}
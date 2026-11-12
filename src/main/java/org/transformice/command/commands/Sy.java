package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.packets.send.legacy.PacketSendPlayerSynchronizer;

@Command(name = "sy", usage = "[playerName]", description = "Sets the current room's synchronizer.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Sy implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        var client = server.getPlayers().get(playerName);
        if (client == null || !client.getRoomName().equals(player.getRoomName())) {
            CommandHandler.sendServerMessage(player, String.format("The player %s is not online or is not in the same room as you.", playerName));
            return;
        }

        player.getPlayerRoom().currentSync = client;
        player.broadcastLegacyPacket(new PacketSendPlayerSynchronizer(client.getSessionId(), true));
        CommandHandler.sendTranslatedMessage(player, "", "$NouveauSync [" + player.getPlayerRoom().getSync().getPlayerName() + "]", new String[]{});
    }
}
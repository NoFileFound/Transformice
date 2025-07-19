package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.legacy.player.C_PlayerSync;

@Command(
        name = "sy",
        usage = "[playerName]",
        description = "Sets the current room's synchronizer.",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Sy implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        var info = server.getPlayers().get(playerName);
        if(info == null || !info.getRoom().equals(player.getRoom())) {
            CommandHandler.sendServerMessage(player, String.format("The player %s is not online or is not in the same room as you.", playerName));
            return;
        }
        player.getRoom().setCurrentSync(info);
        player.sendOldPacket(new C_PlayerSync(info.getSessionId(), true));
        CommandHandler.sendTranslatedMessage(player, "", "$NouveauSync [" + player.getRoom().getCurrentSync().getPlayerName() + "]", new String[]{});
    }
}
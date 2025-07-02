package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;

@Command(
        name = "info",
        description = "Info about the current map",
        permission = Command.CommandPermission.PLAYER
)
@SuppressWarnings("unused")
public final class Info implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_ServerMessage(true, String.format("%s - @%d - %d - %d - P%d", player.getRoom().getCurrentMap().mapName, player.getRoom().getCurrentMap().mapCode, player.getRoom().getCurrentMap().mapYesVotes + player.getRoom().getCurrentMap().mapNoVotes, (player.getRoom().getCurrentMap().mapYesVotes + player.getRoom().getCurrentMap().mapNoVotes > 0) ? (player.getRoom().getCurrentMap().mapYesVotes / player.getRoom().getCurrentMap().mapNoVotes) * 100 : 100, player.getRoom().getCurrentMap().mapPerma)));
    }
}
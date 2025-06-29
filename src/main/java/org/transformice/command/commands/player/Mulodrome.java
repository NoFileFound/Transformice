package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.cafe.C_MulodromeStart;

@Command(
        name = "mulodrome",
        description = "Starts a racing game with 2 teams."
)
@SuppressWarnings("unused")
public final class Mulodrome implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(player.getRoom().getRoomCreator().equals(player.getPlayerName()) && player.getRoom().isRacing()) {
            for(Client client : player.getRoom().getPlayers().values()) {
                client.sendPacket(new C_MulodromeStart(client.getPlayerName().equals(player.getPlayerName())));
            }
        }
    }
}
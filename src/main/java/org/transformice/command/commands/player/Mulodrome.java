package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.mulodrome.PacketSendMulodromeInit;

@Command(name = "mulodrome", description = "Starts a racing game with 2 teams.", permission = Command.CommandPermission.ROOM_OWNER)
@SuppressWarnings("unused")
public final class Mulodrome implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (!player.getPlayerRoom().getIsMulodrome()) {
            if (player.getPlayerRoom().getPlayers().size() > 1) {
                for (GamePlayer client : player.getPlayerRoom().getPlayers().values()) {
                    client.broadcastPacket(new PacketSendMulodromeInit(client.getPlayerName().equals(player.getPlayerName())));
                }
            }
        }
    }
}
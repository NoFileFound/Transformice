package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.legacy.PacketSendTribeMusique;

@Command(name = "musique", usage = "[mp3-url]", description = "Kicks the user who was invited to your tribe house.", permission = Command.CommandPermission.TRIBE, aliases = {"music"})
@SuppressWarnings("unused")
public final class Musique implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if(args.isEmpty()) {
            player.broadcastLegacyPacket(new PacketSendTribeMusique(""));
        } else {
            player.getPlayerRoom().broadcastLegacyPacket(new PacketSendTribeMusique(args.getFirst()));
        }
    }
}
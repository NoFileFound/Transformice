package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.PlayerEntity;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerProfile;

@Command(name = "profile", usage = "[playerName]", description = "Player stats information", aliases = {"perfil", "profiel", "profil"})
@SuppressWarnings("unused")
public final class Profile implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = (args.isEmpty()) ? player.getPlayerName() : args.getFirst();
        PlayerEntity playerAccount = server.getDatabase().loadPlayerByName(playerName);
        if(playerAccount != null)
            player.broadcastPacket(new PacketSendPlayerProfile(playerAccount, server.getConfig().getGameConfig().getRoomStats()));
    }
}
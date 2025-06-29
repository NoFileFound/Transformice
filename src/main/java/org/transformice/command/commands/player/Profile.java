package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Account;

// Packets
import org.transformice.packets.send.player.C_Profile;

@Command(
        name = "profile",
        usage = "[playerName]",
        description = "Player stats information",
        aliases = {"perfil", "perfil", "profiel", "profil"}
)
@SuppressWarnings("unused")
public final class Profile implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = (args.isEmpty()) ? player.getPlayerName() : args.getFirst();
        Account playerAccount = server.getPlayerAccount(playerName);
        if(playerAccount != null)
            player.sendPacket(new C_Profile(playerAccount, server.checkIsConnected(playerName)));
    }
}
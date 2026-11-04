package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.network.NetSession;

@Command(name = "verifysession", usage = "[playerName]", description = "If admin is on 2FA authorization you have to verify his session and he will manage to login. ", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Verifysession implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        for (NetSession session : server.getClientSessions().values()) {
            if (session.tmp2FAAdmin.equals(playerName)) {
                session.hasConfirmed2FA = true;
                CommandHandler.sendServerMessage(player, "OK");
                break;
            }
        }

        CommandHandler.sendServerMessage(player, "Not Found");
    }
}
package org.transformice.command.commands.modo;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.newpackets.C_MiniLogMessage;
import org.transformice.packets.send.newpackets.sonar.C_StartSonar;
import org.transformice.packets.send.newpackets.sonar.C_StopSonar;

@Command(
        name = "sonar",
        usage = "[playerName]",
        description = "Send results about their movement.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Sonar implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        if(!server.checkIsConnected(playerName)) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        Client client = server.getPlayers().get(playerName);
        if(args.size() == 2 && args.get(1).equals("fin")) {
            if(getUnixTime() - client.lastSonarTime < 2) {
                server.getSonarPlayerMovement().remove(playerName);
                player.sendPacket(new C_StopSonar(client.getSessionId()));
                return;
            }
            client.lastSonarTime = getUnixTime();
        }

        player.sendPacket(new C_MiniLogMessage(100, 2, "Sonar " + playerName, server.getSonarPlayerMovement().containsKey(playerName) ? String.join("\n", server.getSonarPlayerMovement().get(playerName)) : "\n"));
        server.getSonarPlayerMovement().remove(playerName);
        player.sendPacket(new C_StartSonar(client.getSessionId(), true));
    }
}
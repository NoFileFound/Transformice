package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.PacketSendStartSonar;
import org.transformice.packets.send.PacketSendStopSonar;
import org.transformice.packets.send.informations.PacketSendMiniLog;

@Command(name = "sonar", usage = "[playerName]", description = "Send results about their movement.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Sonar implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        if (server.getPlayers().get(playerName) == null) {
            CommandHandler.sendServerMessage(player, "The player is not online.");
            return;
        }

        GamePlayer client = server.getPlayers().get(playerName);
        if (args.size() == 2 && args.get(1).equals("fin")) {
            if (Utils.getUnixTime() - client.lastSonarTime < 2) {
                server.getSonarPlayerMovement().remove(playerName);
                player.broadcastPacket(new PacketSendStopSonar(client.getSessionId()));
                return;
            }

            client.lastSonarTime = Utils.getUnixTime();
        }

        player.broadcastPacket(new PacketSendMiniLog(100, 2, "Sonar " + playerName, server.getSonarPlayerMovement().containsKey(playerName) ? String.join("\n", server.getSonarPlayerMovement().get(playerName)) : "\n"));
        server.getSonarPlayerMovement().remove(playerName);
        player.broadcastPacket(new PacketSendStartSonar(client.getSessionId(), true, PSRandom.nextInt(0, 32575)));
    }
}
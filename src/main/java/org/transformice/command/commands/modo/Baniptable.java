package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "baniptable", usage = "[playerName]", description = "Bans the player's IP address for an hour.", permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Baniptable implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        GamePlayer client = server.getPlayers().get(args.getFirst());
        if (client == null) {
            CommandHandler.sendServerMessage(player, "The player is not connected.");
            return;
        }

        if (client.getPlayerName().equals(player.getPlayerName()))
            return;

        try {
            Process process = new ProcessBuilder("iptables", "-I", "INPUT", "-s", client.getIpAddress(), "-j", "DROP").redirectErrorStream(true).start();
            if (process.waitFor() != 0) {
                CommandHandler.sendServerMessage(player, "iptables rule failed.");
                return;
            }

            client.disconnect(true);
            PSTimer timer = new PSTimer(server.getScheduler());
            timer.schedule(() -> {
                try {
                    new ProcessBuilder("iptables", "-D", "INPUT", "-s", client.getIpAddress(), "-j", "DROP").redirectErrorStream(true).start().waitFor();
                } catch (Exception ignored) {

                }
            },1, TimeUnit.HOURS);
            CommandHandler.sendServerMessage(player, "iptables rule added.");
        } catch (Exception e) {
            CommandHandler.sendServerMessage(player, "Failed to execute iptables.");
        }
    }
}
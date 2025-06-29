package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_ChangeMainServer;

@Command(
        name = "dbgip",
        usage = "[ipAddress:port1-port2-port3-port4-port5]",
        description = "Changes the main server ip address.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Dbgip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_ChangeMainServer(args.getFirst()));
    }
}
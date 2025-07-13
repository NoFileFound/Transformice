package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.newpackets.C_DisableSynchronization;

@Command(
        name = "dissync",
        description = "Disables the synchronization on this room.",
        permission = Command.CommandPermission.ADMINISTRATOR
)
@SuppressWarnings("unused")
public final class Dissync implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getRoom().sendAll(new C_DisableSynchronization());
    }
}
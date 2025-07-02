package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_PlayTestSound;

@Command(
        name = "testsound",
        description = "",
        permission = {Command.CommandPermission.DEBUG_ONLY}
)
@SuppressWarnings("unused")
public final class Testsound implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_PlayTestSound("french", args.getFirst()));
    }
}
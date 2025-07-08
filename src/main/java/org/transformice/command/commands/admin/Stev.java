package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.packets.send.chat.C_ServerMessage;

@Command(
        name = "stev",
        description = "Invokes the event in the next round.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        aliases = {"startevent"}
)
@SuppressWarnings("unused")
public final class Stev implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.getRoom().setEventTime(true);
        player.sendPacket(new C_ServerMessage(true, "The event is going to appear in the next round."));
    }
}
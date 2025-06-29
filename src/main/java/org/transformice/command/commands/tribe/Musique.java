package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.legacy.login.C_TribeMusique;

@Command(
        name = "musique",
        usage = "[mp3-url]",
        description = "Kicks the user who was invited to your tribe house.",
        permission = Command.CommandPermission.TRIBE
)
@SuppressWarnings("unused")
public final class Musique implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(args.isEmpty()) {
            player.sendOldPacket(new C_TribeMusique(""));
        } else {
            player.getRoom().sendAllOld(new C_TribeMusique(args.getFirst()));
        }
    }
}
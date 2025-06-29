package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "pw",
        description = "Changes the password of current room.",
        permission = Command.CommandPermission.ROOM_OWNER
)
@SuppressWarnings("unused")
public final class Pw implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(player.getRoom().canAddPassword) {
            String password = (args.isEmpty()) ? "" : String.join(" ", args.subList(0, args.size()));
            player.getRoom().setRoomPassword(password);
            player.sendPacket(new C_TranslationMessage("", (password.isEmpty()) ? "$MDP_Desactive" : "$Mot_De_Passe : " + password));
        }
    }
}
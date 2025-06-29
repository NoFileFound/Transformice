package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.player.C_PlayerTitleList;
import org.transformice.packets.send.transformice.C_PlayerChangeTitle;

@Command(
        name = "title",
        description = "",
        usage = "(titleId)",
        permission = Command.CommandPermission.PLAYER,
        aliases = {"titel", "titre", "titulo"}
)
@SuppressWarnings("unused")
public final class Title implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(args.isEmpty()) {
            player.sendPacket(new C_PlayerTitleList(player.getAccount().getTitleList()));
        } else {
            short titleId = Short.parseShort(args.getFirst());
            for(double title : player.getAccount().getTitleList()) {
                if((short) (title) == titleId) {
                    player.getAccount().setCurrentTitle(title);
                    player.sendPacket(new C_PlayerChangeTitle(player.getAccount().getPlayerGender(), titleId));
                    break;
                }
            }

        }
    }
}
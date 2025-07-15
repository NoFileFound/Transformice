package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.database.DBUtils;
import org.transformice.database.collections.MapEditor;
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "np",
        usage = "(mapCode)",
        description = "Changes the current map",
        permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.MAPCREW, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Np implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if(args.isEmpty()) {
            player.getRoom().setMapChangeTimer(0);
            player.getRoom().changeMap();
            return;
        }

        String mapCode = args.getFirst();
        if(mapCode.startsWith("@")) {
            MapEditor mapInfo = DBUtils.findMapByCode(Integer.parseInt(mapCode.substring(1)));
            if(mapInfo == null) {
                player.sendPacket(new C_TranslationMessage("", "$CarteIntrouvable"));
                return;
            }
            player.sendPacket(new C_TranslationMessage("", String.format("$ProchaineCarte : %s - %s", mapInfo.getMapAuthor(), mapInfo.getMapCode())));
            player.getRoom().forceNextMap = mapCode;
            player.getRoom().setMapChangeTimer(0);
            player.getRoom().changeMap();
        } else {
            player.sendPacket(new C_TranslationMessage("", String.format("$ProchaineCarte : %s", mapCode)));
            player.getRoom().forceNextMap = mapCode;
            player.getRoom().setMapChangeTimer(0);
            player.getRoom().changeMap();
        }
    }
}
package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.MapEditor;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "del",
        usage = "(mapCode)",
        description = "Deletes the map. (Sets to p44)",
        permission = {Command.CommandPermission.MAPCREW}
)
@SuppressWarnings("unused")
public final class Del implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        int mapCode;
        try {
            mapCode = Integer.parseInt(args.getFirst().replace("@", ""));
        } catch (NumberFormatException e) {
            player.sendPacket(new C_TranslationMessage("", "$CarteIntrouvable"));
            return;
        }

        MapEditor map = DBUtils.findMapByCode(mapCode);
        if (map == null) {
            player.sendPacket(new C_TranslationMessage("", "$CarteIntrouvable"));
            return;
        }

        map.setMapCategory(44);
        map.save();

        CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("mapdeleteddone", args.getFirst()));
    }
}
package org.transformice.command.commands.mapcrew;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.MapEntry;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@Command(name = "del", usage = "(mapCode)", description = "Changes the map category to p44.", permission = {Command.CommandPermission.MAPCREW, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Del implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        int mapCode;
        try {
            mapCode = Integer.parseInt(args.getFirst().replace("@", ""));
        } catch (NumberFormatException e) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
            return;
        }

        MapEntry map = server.getDatabase().loadMapByCode(mapCode);
        if (map == null) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
            return;
        }

        int oldCategory = map.mapCategory;
        map.mapCategory = 44;
        server.getDatabase().saveMap(map);
        CommandHandler.sendServerMessage(player, String.format("Successfully deleted the map (%s).", mapCode));
        server.sendServerMessageOthers(player, String.format("[%s] @%d : %d -> %d", player.getPlayerName(), mapCode, oldCategory, map.mapCategory), StaffRoleType.MAPCREW);
    }
}
package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.MapEntry;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@Command(name = "np", usage = "(mapCode)", description = "Forces the specific map to appear now.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.LUADEV, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Np implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (args.isEmpty()) {
            player.getPlayerRoom().sendChangeMap();
            return;
        }

        try {
            String mapCode = args.getFirst();
            int code = Integer.parseInt(mapCode.startsWith("@") ? mapCode.substring(1) : mapCode);
            if (mapCode.startsWith("@")) {
                MapEntry mapInfo = server.getDatabase().loadMapByCode(code);
                if (mapInfo == null) {
                    player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
                    return;
                }

                player.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$ProchaineCarte : %s - %s", mapInfo.getMapAuthor(), mapInfo.getMapCode())));
            } else {
                player.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$ProchaineCarte : %s", mapCode)));
            }

            player.getPlayerRoom().forceNextMap = code;
            player.getPlayerRoom().sendChangeMap();
        } catch (Exception ignored) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
        }
    }
}
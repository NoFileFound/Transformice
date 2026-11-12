package org.transformice.command.commands;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.MapEntry;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@Command(name = "npp", usage = "[mapCode]", description = "Forces the specific map to appear in the next round.", permission = {Command.CommandPermission.TRIBE, Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.LUADEV, Command.CommandPermission.MAPCREW, Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Npp implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        try {
            String mapCode = args.getFirst();
            if (mapCode.startsWith("@")) {
                int code = Integer.parseInt(mapCode.substring(1));
                MapEntry mapInfo = server.getDatabase().loadMapByCode(code);
                if (mapInfo == null) {
                    player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
                    return;
                }

                player.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$ProchaineCarte : %s - %s", mapInfo.getMapAuthor(), mapInfo.getMapCode())));
                player.getPlayerRoom().forceNextMap = new GameRoom.MapDetails(mapInfo, false);
            } else {
                int code = Integer.parseInt(mapCode);
                player.broadcastPacket(new PacketSendTranslatedMessage("", String.format("$ProchaineCarte : %s", code)));
                player.getPlayerRoom().forceNextMap = new GameRoom.MapDetails(code, "", server.getVanillaMapXml(code));
            }

        } catch (NumberFormatException ignored) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$CarteIntrouvable"));
        }
    }
}
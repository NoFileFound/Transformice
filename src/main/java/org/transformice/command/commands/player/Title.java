package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.PlayerTitleInfo;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerUnlockedTitleList;

@Command(name = "title", description = "Shows/Changes the player's title/titles.", aliases = {"titel", "titre", "titulo"})
@SuppressWarnings("unused")
public final class Title implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (args.isEmpty()) {
            player.broadcastPacket(new PacketSendPlayerUnlockedTitleList(player.getPlayerData().getUnlockedTitles()));
        } else {
            short titleId = Short.parseShort(args.getFirst());
            for (PlayerTitleInfo titleInfo : player.getPlayerData().getUnlockedTitles()) {
                if (titleInfo.titleId == titleId) {
                    player.sendChangeTitle(titleInfo);
                    break;
                }
            }
        }
    }
}
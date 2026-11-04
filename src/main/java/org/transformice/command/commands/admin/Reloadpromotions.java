package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.shop.PacketSendShopPromotion;

@Command(name = "reloadpromotions", description = "Reloads the shop promotions.", permission = Command.CommandPermission.ADMINISTRATOR, aliases = {"reloadsales"})
@SuppressWarnings("unused")
public final class Reloadpromotions implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        for (GamePlayer client : server.getPlayers().values()) {
            if (!client.getIsGuest()) {
                for (GameConfig.ShopPromotion promotion : server.getConfig().getGameConfig().getShopPromotions()) {
                    if (promotion.getExpirationDate() > Utils.getUnixTime()) {
                        client.broadcastPacket(new PacketSendShopPromotion(promotion));
                    }
                }
            }
        }

        CommandHandler.sendServerMessage(player, "Successfully reloaded the shop promotions.");
    }
}
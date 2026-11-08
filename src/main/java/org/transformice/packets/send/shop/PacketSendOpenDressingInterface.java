package org.transformice.packets.send.shop;

// Imports
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.shop.ShopConfig;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.modules.ModuleShop;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

public final class PacketSendOpenDressingInterface extends PSSendPacket {
    private final Map<String, ShopConfig.ShopItemConfig> shopItems;
    private final List<GameConfig.ShopPromotion> shopPromotions;

    /**
     * Creates a new packet of SEND_OPEN_DRESSING_INTERFACE.
     * @param shopItems The shop's config.
     * @param shopPromotions The shop's promotions.
     */
    public PacketSendOpenDressingInterface(Map<String, ShopConfig.ShopItemConfig> shopItems, List<GameConfig.ShopPromotion> shopPromotions) {
        super(PacketOpcodes.SEND_OPEN_DRESSING_INTERFACE);
        this.shopItems = shopItems;
        this.shopPromotions = shopPromotions;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.shopItems.size());
        for (var entry : this.shopItems.entrySet()) {
            String[] parts = entry.getKey().split("_");
            packet.writeUnsignedShort(Integer.parseInt(parts[0]));
            packet.writeUnsignedShort(Integer.parseInt(parts[1]));
            packet.writeByte(entry.getValue().getColorNum());
            packet.writeBoolean(entry.getValue().getIsNew());
            packet.writeByte(entry.getValue().getType());
            packet.writeInt(entry.getValue().getCheesePrice());
            packet.writeInt(entry.getValue().getStrawberryPrice());
            packet.writeBoolean(entry.getValue().getRequireAnotherItem() != -1);
            if (entry.getValue().getRequireAnotherItem() != -1)
                packet.writeInt(entry.getValue().getRequireAnotherItem());
        }

        var filteredPromotions = this.shopPromotions.stream().filter(promotion -> !promotion.getIsShamanItem()).toList();
        packet.writeInt(filteredPromotions.size());
        for (var promotion : filteredPromotions) {
            String[] parts = promotion.getItemId().split("_");
            int category = Integer.parseInt(parts[0]);
            int itemId = Integer.parseInt(parts[1]);
            packet.writeInt(ModuleShop.getClientShopInfo(category, itemId));
            packet.writeInt((int) (promotion.getExpirationDate() - Utils.getUnixTime()));
        }

        return packet;
    }
}
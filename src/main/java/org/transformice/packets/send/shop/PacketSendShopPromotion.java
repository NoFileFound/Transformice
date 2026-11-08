package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.modules.ModuleShop;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopPromotion extends PSSendPacket {
    private final GameConfig.ShopPromotion promotion;
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_SHOP_PROMOTION.
     * @param promotion The promotion.
     * @param isEnabled Is the promotion enabled.
     */
    public PacketSendShopPromotion(GameConfig.ShopPromotion promotion, boolean isEnabled) {
        super(PacketOpcodes.SEND_SHOP_PROMOTION);
        this.promotion = promotion;
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.promotion.getPromotionType() == GameConfig.ShopPromotion.PromotionType.PROMOTION);
        packet.writeBoolean(!this.promotion.getIsShamanItem());
        if (this.promotion.getItemId().contains("_")) {
            String[] info = this.promotion.getItemId().split("_");
            packet.writeInt(ModuleShop.getClientShopInfo(Integer.parseInt(info[0]), Integer.parseInt(info[1])));
        } else {
            int itemId = Integer.parseInt(this.promotion.getItemId());
            packet.writeInt(itemId);
        }

        packet.writeBoolean(this.isEnabled);
        packet.writeInt(this.promotion.getExpirationDate());
        packet.writeByte(this.promotion.getPromotionType() == GameConfig.ShopPromotion.PromotionType.PROMOTION ? this.promotion.getDiscount() : 0);
        return packet;
    }
}
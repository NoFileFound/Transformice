package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopPromotion extends PSSendPacket {
    private final GameConfig.ShopPromotion promotion;

    /**
     * Creates a new packet of SEND_SHOP_PROMOTION.
     * @param promotion The promotion.
     */
    public PacketSendShopPromotion(GameConfig.ShopPromotion promotion) {
        super(PacketOpcodes.SEND_SHOP_PROMOTION);
        this.promotion = promotion;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.promotion.getPromotionType() == GameConfig.ShopPromotion.PromotionType.PROMOTION);
        packet.writeBoolean(!this.promotion.getIsShamanItem());
        packet.writeInt(this.promotion.getItemId());
        packet.writeBoolean(true);
        packet.writeInt(this.promotion.getExpirationDate());
        packet.writeByte(this.promotion.getPromotionType() == GameConfig.ShopPromotion.PromotionType.PROMOTION ? this.promotion.getDiscount() : 0);
        return packet;
    }
}
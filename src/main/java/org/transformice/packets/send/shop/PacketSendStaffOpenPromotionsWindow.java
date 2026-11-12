package org.transformice.packets.send.shop;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendStaffOpenPromotionsWindow extends PSSendPacket {
    private final List<GameConfig.ShopPromotion> promotions;
    private final boolean isAdministrator;

    /**
     * Creates a new packet of SEND_STAFF_OPEN_PROMOTIONS_WINDOW.
     * @param promotions The shop's promotions.
     * @param isAdministrator Is the player administrator.
     */
    public PacketSendStaffOpenPromotionsWindow(List<GameConfig.ShopPromotion> promotions, boolean isAdministrator) {
        super(PacketOpcodes.SEND_STAFF_OPEN_PROMOTIONS_WINDOW);
        this.promotions = promotions;
        this.isAdministrator = isAdministrator;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.promotions.size());
        for (GameConfig.ShopPromotion promotion : this.promotions) {
            packet.writeInt128(promotion.getPromotionId());
            packet.writeUTF(String.valueOf(promotion.getItemId()), true);
            packet.writeUTF(Instant.ofEpochSecond(promotion.getStartDate()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), true);
            packet.writeUTF(Instant.ofEpochSecond(promotion.getExpirationDate()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), true);
            packet.writeInt128(promotion.getDiscount());
            packet.writeInt128((this.isAdministrator ? 2 : (promotion.getIsPerm() ? 1 : 2)));
        }

        return packet;
    }
}
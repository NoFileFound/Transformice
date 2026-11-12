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

public final class PacketSendStaffOpenOutfitsWindow extends PSSendPacket {
    private final List<GameConfig.ShopOutfit> outfits;
    private final boolean isAdministrator;

    /**
     * Creates a new packet of SEND_STAFF_OPEN_OUTFITS_WINDOW.
     * @param outfits The shop outfits.
     * @param isAdministrator Is the player administrator.
     */
    public PacketSendStaffOpenOutfitsWindow(List<GameConfig.ShopOutfit> outfits, boolean isAdministrator) {
        super(PacketOpcodes.SEND_STAFF_OPEN_OUTFITS_WINDOW);
        this.outfits = outfits;
        this.isAdministrator = isAdministrator;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.outfits.size());
        for (GameConfig.ShopOutfit outfit : this.outfits) {
            packet.writeInt(outfit.getOutfitId());
            packet.writeUTF(outfit.getOutfitName(), true);
            packet.writeByte(outfit.getOutfitBackgroundId().ordinal());
            packet.writeUTF(Instant.ofEpochSecond(outfit.getExpirationDate()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), true);
            packet.writeUTF(outfit.getOutfitLook(), true);
            packet.writeByte((this.isAdministrator ? 2 : (outfit.getIsPerm() ? 1 : 2)));
        }

        return packet;
    }
}
package org.transformice.packets.send.newpackets;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.shop.ShopOutfitsConfig.ShopOutfit;

public final class C_OpenFashionSquadOutfitsWindow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenFashionSquadOutfitsWindow(Map<Integer, ShopOutfit> info) {
        this.byteArray.writeInt(info.size());
        for(var outfitInfo : info.entrySet()) {
            this.byteArray.writeInt(outfitInfo.getKey());
            this.byteArray.writeString(outfitInfo.getValue().outfit_name);
            this.byteArray.writeByte(outfitInfo.getValue().outfit_bg);
            this.byteArray.writeString(Instant.ofEpochSecond(outfitInfo.getValue().outfit_date).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            this.byteArray.writeString(outfitInfo.getValue().outfit_look);
            this.byteArray.writeByte(outfitInfo.getValue().is_perm ? 3 : 2);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
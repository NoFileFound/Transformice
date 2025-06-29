package org.transformice.packets.send.newpackets;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.shop.PromotionsConfig.Promotion;

public final class C_OpenFashionSquadSalesWindow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenFashionSquadSalesWindow(Map<Integer, Promotion> sales) {
        this.byteArray.writeInt128(sales.size());
        for(var info : sales.entrySet()) {
            this.byteArray.writeInt128(info.getKey());
            this.byteArray.writeString(String.valueOf(info.getValue().item_id));
            this.byteArray.writeString(Instant.ofEpochSecond(info.getValue().promotion_start_date).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            this.byteArray.writeString(Instant.ofEpochSecond(info.getValue().promotion_end_date).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            this.byteArray.writeInt128(info.getValue().promotion_percentage);
            this.byteArray.writeByte(info.getValue().is_perm ? 1 : 2);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
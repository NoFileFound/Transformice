package org.transformice.packets.send.transformice;

// Imports
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.shop.PromotionsConfig;
import org.transformice.properties.configs.shop.ShopItemConfig;
import org.transformice.utils.Utils;

public final class C_OpenDressingWindow implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenDressingWindow(Map<String, ShopItemConfig.ShopItem> items, Map<Integer, PromotionsConfig.Promotion> promotions) {
        this.byteArray.writeInt(items.size());
        for (var entry : items.entrySet()) {
            String[] parts = entry.getKey().split("_");
            this.byteArray.writeUnsignedShort(Integer.parseInt(parts[0]));
            this.byteArray.writeUnsignedShort(Integer.parseInt(parts[1]));
            this.byteArray.writeByte(entry.getValue().color_num);
            this.byteArray.writeBoolean(entry.getValue().is_new);
            this.byteArray.writeByte(entry.getValue().type);
            this.byteArray.writeInt(entry.getValue().cheese_price);
            this.byteArray.writeInt(entry.getValue().strawberry_price);
            this.byteArray.writeBoolean(entry.getValue().require_item != -1);
            if(entry.getValue().require_item != -1) {
                this.byteArray.writeInt(entry.getValue().require_item);
            }
        }

        var filteredPromotions = promotions.entrySet().stream().filter(entry -> entry.getValue().is_regular_item).toList();
        this.byteArray.writeInt(filteredPromotions.size());
        for (var entry : filteredPromotions) {
            this.byteArray.writeInt(Integer.parseInt(entry.getValue().item_id.replace("_", "")));
            this.byteArray.writeInt(entry.getValue().promotion_end_date - Utils.getUnixTime());
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
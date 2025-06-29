package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class ShopShamanItemConfig implements Property {
    private final Int2ObjectMap<ShopShamanItem> shopShamanItemInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.shopShamanItemInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, ShopShamanItem> tmp = JsonLoader.loadJson("server/shop/shop_shaman_items.json", new TypeToken<Map<Integer, ShopShamanItem>>() {}.getType());
        if(tmp != null) {
            this.shopShamanItemInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalshopshamanitems", this.shopShamanItemInstance.size()));
    }

    @Override
    public void saveFile() {

    }

    public static class ShopShamanItem {
        public int color_num;
        public int type;
        public int cheese_price;
        public int strawberry_price;
        public boolean is_new;
    }
}
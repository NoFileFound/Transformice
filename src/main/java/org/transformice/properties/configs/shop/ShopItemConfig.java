package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class ShopItemConfig implements Property {
    private final Object2ObjectOpenHashMap<String, ShopItem> shopItemInstance = new Object2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.shopItemInstance;
    }

    @Override
    public void loadFile() {
        Map<String, ShopItem> tmp = JsonLoader.loadJson("server/shop/shop_items.json", new TypeToken<Map<String, ShopItem>>() {}.getType());
        if(tmp != null) {
            this.shopItemInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalshopitems", this.shopItemInstance.size()));
    }

    @Override
    public void saveFile() {

    }

    public static class ShopItem {
        public int color_num;
        public boolean is_new;
        public int type;
        public int cheese_price;
        public int strawberry_price;
        public int require_item;
    }
}
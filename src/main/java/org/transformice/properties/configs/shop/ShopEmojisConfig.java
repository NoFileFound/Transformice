package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class ShopEmojisConfig implements Property {
    private final Int2ObjectMap<ShopEmoji> shopEmojisInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.shopEmojisInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, ShopEmoji> tmp = JsonLoader.loadJson("server/shop/shop_emojis.json", new TypeToken<Map<Integer, ShopEmoji>>() {}.getType());
        if(tmp != null) {
            this.shopEmojisInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalshopemojis", this.shopEmojisInstance.size()));
    }

    @Override
    public void saveFile() {

    }

    public static class ShopEmoji {
        public int cheese_price;
        public int strawberry_price;
        public boolean is_new;
    }
}
package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class ShopBannersConfig implements Property {
    private final Int2ObjectMap<ShopBanner> shopBannersInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.shopBannersInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, ShopBanner> tmp = JsonLoader.loadJson("server/shop/shop_banners.json", new TypeToken<Map<Integer, ShopBanner>>() {}.getType());
        if(tmp != null) {
            this.shopBannersInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalshopbanners", this.shopBannersInstance.size()));
    }

    @Override
    public void saveFile() {

    }

    public static class ShopBanner {
        public int cheese_price;
        public int strawberry_price;
        public boolean is_new;
    }
}
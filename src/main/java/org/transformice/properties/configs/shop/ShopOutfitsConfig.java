package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class ShopOutfitsConfig implements Property {
    private final Int2ObjectMap<ShopOutfit> shopOutfitsInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.shopOutfitsInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, ShopOutfit> tmp = JsonLoader.loadJson("client/outfits.json", new TypeToken<Map<Integer, ShopOutfit>>() {}.getType());
        if(tmp != null) {
            this.shopOutfitsInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalshopoutfits", this.shopOutfitsInstance.size()));
    }

    @Override
    public void saveFile() {
        JsonLoader.saveJson("client/outfits.json", this.shopOutfitsInstance);
    }

    public static class ShopOutfit {
        public String outfit_name;
        public String outfit_look;
        public int outfit_bg;
        public String outfit_author;
        public long outfit_date;
        public boolean is_perm;

        public ShopOutfit(String name, String look, int bg, String author, long date, boolean isPerm) {
            this.outfit_name = name;
            this.outfit_look = look;
            this.outfit_bg = bg;
            this.outfit_author = author;
            this.outfit_date = date;
            this.is_perm = isPerm;
        }
    }
}
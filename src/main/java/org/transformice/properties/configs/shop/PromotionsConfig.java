package org.transformice.properties.configs.shop;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class PromotionsConfig implements Property {
    private final Int2ObjectMap<Promotion> promotionsInstance = new Int2ObjectOpenHashMap<>();

    @Override
    public Object getInstance() {
        return this.promotionsInstance;
    }

    @Override
    public void loadFile() {
        Map<Integer, Promotion> tmp = JsonLoader.loadJson("client/promotions.json", new TypeToken<Map<Integer, Promotion>>() {}.getType());
        if(tmp != null) {
            this.promotionsInstance.putAll(tmp);
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalpromotions", this.promotionsInstance.size()));
    }

    @Override
    public void saveFile() {
        JsonLoader.saveJson("client/promotions.json", this.promotionsInstance);
    }

    public static class Promotion {
        public String item_id;
        public long promotion_start_date;
        public long promotion_end_date;
        public int promotion_percentage;
        public String promotion_author;
        public boolean is_perm;
        public boolean is_sale;
        public boolean is_regular_item;

        public Promotion(String item_id, long startDate, long endDate, int percentage, String author, boolean isPerm, boolean isSale, boolean isRegularItem) {
            this.item_id = item_id;
            this.promotion_start_date = startDate;
            this.promotion_end_date = endDate;
            this.promotion_percentage = percentage;
            this.promotion_author = author;
            this.is_perm = isPerm;
            this.is_sale = isSale;
            this.is_regular_item = isRegularItem;
        }
    }
}
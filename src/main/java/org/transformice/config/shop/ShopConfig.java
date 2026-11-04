package org.transformice.config.shop;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class ShopConfig {
    @JsonProperty("shop_banners")
    private Map<Integer, ShopBannerConfig> shop_banners = new HashMap<>();
    @JsonProperty("shop_emoji")
    private Map<Integer, ShopEmojiConfig> shop_emoji = new HashMap<>();
    @JsonProperty("shop_items")
    private Map<Integer, ShopItemConfig> shop_items = new HashMap<>();
    @JsonProperty("shop_shaman_items")
    private Map<Integer, ShopShamanItemConfig> shop_shaman_items = new HashMap<>();

    /**
     * Creates a new shop config.
     */
    public ShopConfig() {

    }

    /**
     * Gets the shop banners.
     * @return The shop banners.
     */
    public Map<Integer, ShopBannerConfig> getShopBanners() {
        return this.shop_banners;
    }

    /**
     * Gets the shop emoji.
     * @return The shop emoji.
     */
    public Map<Integer, ShopEmojiConfig> getShopEmoji() {
        return this.shop_emoji;
    }

    /**
     * Gets the shop regular items.
     * @return The shop regular items.
     */
    public Map<Integer, ShopItemConfig> getShopItems() {
        return this.shop_items;
    }

    /**
     * Gets the shop shaman items.
     * @return The shop shaman items.
     */
    public Map<Integer, ShopShamanItemConfig> getShopShamanItems() {
        return this.shop_shaman_items;
    }

    /**
     * Creates a new banner shop config.
     */
    public static class ShopBannerConfig {
        @JsonProperty("cheese_price")
        private int cheese_price;
        @JsonProperty("strawberry_price")
        private int strawberry_price;
        @JsonProperty("is_new")
        private boolean is_new;

        /**
         * Creates a new shop banner config.
         */
        public ShopBannerConfig() {

        }

        /**
         * Gets the shop item's cheese price.
         * @return The item's cheese price.
         */
        public int getCheesePrice() {
            return this.cheese_price;
        }

        /**
         * Gets the shop item's strawberry price.
         * @return The shop item's strawberry price.
         */
        public int getStrawberryPrice() {
            return this.strawberry_price;
        }

        /**
         * Checks if the shop item is new.
         * @return True if the shop item is new or else False.
         */
        public boolean getIsNew() {
            return this.is_new;
        }
    }

    /**
     * Creates a new emoji shop config.
     */
    public static class ShopEmojiConfig {
        @JsonProperty("cheese_price")
        private int cheese_price;
        @JsonProperty("strawberry_price")
        private int strawberry_price;
        @JsonProperty("is_new")
        private boolean is_new;

        /**
         * Creates a new shop banner config.
         */
        public ShopEmojiConfig() {

        }

        /**
         * Gets the shop item's cheese price.
         * @return The item's cheese price.
         */
        public int getCheesePrice() {
            return this.cheese_price;
        }

        /**
         * Gets the shop item's strawberry price.
         * @return The shop item's strawberry price.
         */
        public int getStrawberryPrice() {
            return this.strawberry_price;
        }

        /**
         * Checks if the shop item is new.
         * @return True if the shop item is new or else False.
         */
        public boolean getIsNew() {
            return this.is_new;
        }
    }

    /**
     * Creates a new shop item
     */
    public static class ShopItemConfig {

    }

    /**
     * Creates a new shaman item config.
     */
    public static class ShopShamanItemConfig {
        @JsonProperty("color_num")
        private int color_num;
        @JsonProperty("type")
        private int type;
        @JsonProperty("cheese_price")
        private int cheese_price;
        @JsonProperty("strawberry_price")
        private int strawberry_price;
        @JsonProperty("is_new")
        private boolean is_new;

        /**
         * Creates a new shop shaman item config.
         */
        public ShopShamanItemConfig() {

        }

        /**
         * Gets the color's num.
         * @return The color's num.
         */
        public int getColorNum() {
            return this.color_num;
        }

        /**
         * Gets the item's type.
         * @return The item's type.
         */
        public int getType() {
            return this.type;
        }

        /**
         * Gets the item's cheese price.
         * @return The item's cheese price.
         */
        public int getCheesePrice() {
            return this.cheese_price;
        }

        /**
         * Gets the item's strawberry price.
         * @return The item's strawberry price.
         */
        public int getStrawberryPrice() {
            return this.strawberry_price;
        }

        /**
         * Checks if the shaman item is new.
         * @return True if the shaman item is new or else False.
         */
        public boolean getIsNew() {
            return this.is_new;
        }
    }
}
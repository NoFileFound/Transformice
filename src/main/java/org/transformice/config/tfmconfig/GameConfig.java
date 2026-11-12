package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import org.transformice.utils.Utils;

import java.util.List;
import java.util.Map;

public class GameConfig {
    @JsonProperty("enable_email_address_login")
    private boolean enable_email_address_login = true;
    @JsonProperty("enable_server_anticheat")
    private boolean enable_server_anticheat = false;
    @JsonProperty("login_attempts")
    private int login_attempts = 25;
    @JsonProperty("max_accounts_per_email")
    private int max_accounts_per_email = 8;
    @JsonProperty("max_players")
    private int max_players = 1000;
    @JsonProperty("map_editor_export_cheese_price")
    private int map_editor_export_cheese_price = 40;
    @JsonProperty("shop_shaman_sprites")
    private List<Integer> shop_shaman_sprites;
    @JsonProperty("shop_item_sprites")
    private List<Integer> shop_item_sprites;
    @JsonProperty("partners")
    private List<Map<String, String>> partners;
    @JsonProperty("minimum_players_to_count_stats")
    private int minimum_players_to_count_stats = 11;
    @JsonProperty("yt_key")
    private String yt_key;
    @JsonProperty("chatfilter")
    private List<String> chatfilter;
    @JsonProperty("room_stats")
    private RoomStats room_stats;
    @JsonProperty("shop_promotions")
    private List<ShopPromotion> shop_promotions;
    @JsonProperty("shop_outfits")
    private List<ShopOutfit> shop_outfits;

    /**
     * Creates a new game config.
     */
    public GameConfig() {

    }

    /**
     * Checks if email addresses are allowed to log in.
     * @return True if email addresses can be used to log in or else False.
     */
    public boolean isEnableEmailAddressLogin() {
        return this.enable_email_address_login;
    }

    /**
     * Checks if the server-side anticheat is enabled.
     * @return True if the server-side anticheat is enabled or else False.
     */
    public boolean isEnableServerAnticheat() {
        return this.enable_server_anticheat;
    }

    /**
     * Gets the maximum login attempts.
     * @return The maximum login attempts.
     */
    public int getLoginAttempts() {
        return this.login_attempts;
    }

    /**
     * Gets the maximum accounts that one email address can have.
     * @return The maximum accounts that one email address can have.
     */
    public int getMaxAccountsPerEmail() {
        return this.max_accounts_per_email;
    }

    /**
     * Gets the maximum players to enter in the game.
     * @return The maximum players that can play on this server.
     */
    public int getMaxPlayers() {
        return this.max_players;
    }

    /**
     * Gets the map editor's export map cheese price.
     * @return The export map in map editor cheese price.
     */
    public int getMapEditorExportCheesePrice() {
        return this.map_editor_export_cheese_price;
    }

    /**
     * Gets the minimum players to start counting stats.
     * @return The minimum players required.
     */
    public int getMinimumPlayersToCountStats() {
        return this.minimum_players_to_count_stats;
    }

    /**
     * Gets the shop shaman's sprite ids.
     * @return The shop shaman's sprite ids.
     */
    public List<Integer> getShopShamanSprites() {
        return this.shop_shaman_sprites;
    }

    /**
     * Gets the shop fur's sprite ids.
     * @return The shop fur's sprite ids.
     */
    public List<Integer> getShopItemSprites() {
        return this.shop_item_sprites;
    }

    /**
     * Gets the server's partners.
     * @return The server's partners.
     */
    public List<Map<String, String>> getPartners() {
        return this.partners;
    }

    /**
     * Gets the YouTube's api key.
     * @return The YouTube's api key.
     */
    public String getYoutubeApiKey() {
        return this.yt_key;
    }

    /**
     * Gets the server's blacklisted words.
     * @return The server's blacklisted words.
     */
    public List<String> getChatFilter() {
        return this.chatfilter;
    }

    /**
     * Gets the room stats.
     * @return The room stats.
     */
    public RoomStats getRoomStats() {
        return this.room_stats;
    }

    /**
     * Gets the shop promotions.
     * @return The shop promotions.
     */
    public List<ShopPromotion> getShopPromotions() {
        return this.shop_promotions;
    }

    /**
     * Gets the shop outfits.
     * @return The shop outfits.
     */
    public List<ShopOutfit> getShopOutfits() {
        return this.shop_outfits;
    }

    /**
     * Creates a new room stats.
     */
    public static class RoomStats {
        @JsonProperty("racing_play_rounds")
        private int racing_play_rounds = 1500;
        @JsonProperty("racing_complete_rounds")
        private int racing_complete_rounds = 10000;
        @JsonProperty("racing_get_firstsecondthird")
        private int racing_get_firstsecondthird = 10000;
        @JsonProperty("racing_get_first")
        private int racing_get_first = 10000;
        @JsonProperty("survivor_play_rounds")
        private int survivor_play_rounds = 1500;
        @JsonProperty("survivor_be_shaman")
        private int survivor_be_shaman = 800;
        @JsonProperty("survivor_kill_mice")
        private int survivor_kill_mice = 20000;
        @JsonProperty("survivor_survive")
        private int survivor_survive = 10000;
        @JsonProperty("defilante_play_rounds")
        private int defilante_play_rounds = 1500;
        @JsonProperty("defilante_complete_rounds_firstsecondthird")
        private int defilante_complete_rounds_firstsecondthird = 10000;
        @JsonProperty("defilante_collect_points")
        private int defilante_collect_points = 100000;

        /**
         * Gets the racing play rounds.
         * @return The racing play rounds.
         */
        public int getRacingPlayRounds() {
            return this.racing_play_rounds;
        }

        /**
         * Gets the racing completed rounds.
         * @return The racing completed rounds.
         */
        public int getRacingCompleteRounds() {
            return this.racing_complete_rounds;
        }

        /**
         * Gets the racing completed rounds in 3, 2 or 1 place.
         * @return THe racing completed rounds in 3, 2 or 1 place.
         */
        public int getRacingGetFirstSecondThird() {
            return this.racing_get_firstsecondthird;
        }

        /**
         * Gets the racing completed rounds in 1 place.
         * @return The racing completed rounds in 1 place.
         */
        public int getRacingGetFirst() {
            return this.racing_get_first;
        }

        /**
         * Gets the survivor played rounds.
         * @return The survivor played rounds.
         */
        public int getSurvivorPlayRounds() {
            return this.survivor_play_rounds;
        }

        /**
         * Gets the survivor be shaman.
         * @return The survivor be shaman.
         */
        public int getSurvivorBeShaman() {
            return this.survivor_be_shaman;
        }

        /**
         * Gets the survivor killed mice.
         * @return The survivor killed mice.
         */
        public int getSurvivorKillMice() {
            return this.survivor_kill_mice;
        }

        /**
         * Gets the survivor survived rounds.
         * @return The survivor survived rounds.
         */
        public int getSurvivorSurvive() {
            return this.survivor_survive;
        }

        /**
         * Gets the defilante play rounds.
         * @return The defilante play rounds.
         */
        public int getDefilantePlayRounds() {
            return this.defilante_play_rounds;
        }

        /**
         * Gets the defilante complete rounds.
         * @return The defilante completed rounds.
         */
        public int getDefilanteCompleteRoundsFirstSecondThird() {
            return this.defilante_complete_rounds_firstsecondthird;
        }

        /**
         * Gets the defilante collect points.
         * @return The defilante collect points.
         */
        public int getDefilanteCollectPoints() {
            return this.defilante_collect_points;
        }
    }

    /**
     * Creates a new shop promotion.
     */
    public static class ShopPromotion {
        @JsonProperty("promotion_id")
        private int promotion_id;
        @JsonProperty("promotion_type")
        private PromotionType promotion_type;
        @JsonProperty("is_shaman_item")
        private boolean is_shaman_item;
        @JsonProperty("item_id")
        private String item_id;
        @JsonProperty("start_date")
        private long start_date;
        @JsonProperty("expiration_date")
        private long expiration_date;
        @JsonProperty("discount")
        private int discount;
        @JsonProperty("is_perm")
        private boolean is_perm;

        /**
         * Creates a new promotion.
         */
        public ShopPromotion() {

        }

        /**
         * Creates a new shop promotion.
         * @param promotionId The promotion's id.
         * @param itemId The item's id.
         * @param isShamanItem Is the item a shaman one.
         * @param expirationDate The promotion expiration date.
         * @param discount The promotion discount.
         */
        public ShopPromotion(int promotionId, String itemId, boolean isShamanItem, long startDate, long expirationDate, int discount) {
            this();
            this.promotion_id = promotionId;
            this.promotion_type = PromotionType.PROMOTION;
            this.is_shaman_item = isShamanItem;
            this.item_id = itemId;
            this.start_date = startDate;
            this.expiration_date = expirationDate;
            this.discount = discount;
        }

        /**
         * Gets the promotion's id.
         * @return The promotion's id.
         */
        public int getPromotionId() {
            return this.promotion_id;
        }

        /**
         * Gets the promotion type.
         * @return The promotion type.
         */
        public PromotionType getPromotionType() {
            return this.promotion_type;
        }

        /**
         * Checks if the promotion is for shaman or regular items.
         * @return True if the promotion is for shaman item or else False.
         */
        public boolean getIsShamanItem() {
            return this.is_shaman_item;
        }

        /**
         * Gets the item's id.
         * @return The item's id.
         */
        public String getItemId() {
            return this.item_id;
        }

        /**
         * Gets the promotion's start date.
         * @return The promotion's start date.
         */
        public long getStartDate() {
            return this.start_date;
        }

        /**
         * Gets the promotion's expiration date.
         * @return The promotion's expiration date.
         */
        public long getExpirationDate() {
            return this.expiration_date;
        }

        /**
         * Gets the promotion's discount.
         * @return The promotion's discount.
         */
        public int getDiscount() {
            return this.discount;
        }

        /**
         * Checks if the promotion can't be removed by Fashion squad.
         * @return True if the promotion can't be removed by Fashion squad.
         */
        public boolean getIsPerm() {
            return this.is_perm;
        }

        /**
         * The promotion type.
         */
        public enum PromotionType {
            PROMOTION,
            COLLECTOR
        }
    }

    /**
     * Creates a new shop outfit.
     */
    public static class ShopOutfit {
        @JsonProperty("outfit_id")
        private int outfit_id;
        @JsonProperty("outfit_name")
        private String outfit_name;
        @JsonProperty("outfit_bg")
        private OutfitBackground outfit_bg;
        @JsonProperty("outfit_look")
        private String outfit_look;
        @JsonProperty("expiration_date")
        private long expiration_date;
        @JsonProperty("is_perm")
        private boolean is_perm;

        /**
         * Creates a new shop outfit.
         */
        public ShopOutfit() {

        }

        /**
         * Creates a new outfit.
         * @param outfitId The outfit's id.
         * @param outfitName The outfit's name.
         * @param outfitBackground The outfit's background.
         * @param outfitLook The outfit's look.
         * @param expirationDate THe outfit's expiration date.
         */
        public ShopOutfit(int outfitId, String outfitName, int outfitBackground, String outfitLook, long expirationDate) {
            this();
            this.outfit_id = outfitId;
            this.outfit_name = outfitName;
            this.outfit_bg = OutfitBackground.parseFrom(outfitBackground);
            this.outfit_look = outfitLook;
            this.expiration_date = expirationDate;
            this.is_perm = false;
        }

        /**
         * Gets the outfit's id.
         * @return The outfit's id.
         */
        public int getOutfitId() {
            return this.outfit_id;
        }

        /**
         * Gets the outfit's name.
         * @return The outfit's name.
         */
        public String getOutfitName() {
            return this.outfit_name;
        }

        /**
         * Gets the outfit's look.
         * @return The outfit's look.
         */
        public String getOutfitLook() {
            return this.outfit_look;
        }

        /**
         * Gets the outfit's background.
         * @return The outfit's background.
         */
        public OutfitBackground getOutfitBackgroundId() {
            return this.outfit_bg;
        }

        /**
         * Gets the outfit's expiration date.
         * @return The outfit's expiration date.
         */
        public long getExpirationDate() {
            return this.expiration_date;
        }

        /**
         * Checks if the outfit can't be removed by Fashion squad.
         * @return True if the outfit can't be removed by Fashion squad.
         */
        public boolean getIsPerm() {
            return this.is_perm;
        }

        /**
         * The outfit's background id.
         */
        public enum OutfitBackground {
            GREENERY,
            BEACH,
            OCEAN,
            VALENTINES,
            HALLOWEEN,
            FISHING,
            CLOUDY,
            CHO,
            HUGGING,
            STARS,
            HALLOWEEN_2,
            BLUEBERRIES,
            SNOW,
            SNOW_2,
            TSUKI,
            CANDY,
            BLOOD,
            NONE;

            /**
             * Gets the background name.
             * @param value The background id.
             * @return The background name.
             */
            public static OutfitBackground parseFrom(int value) {
                OutfitBackground[] backgrounds = values();
                return value >= 0 && value < backgrounds.length ? backgrounds[value] : NONE;
            }
        }
    }
}
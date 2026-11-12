package org.transformice.config.inventory;

// Imports
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import java.util.ArrayList;
import java.util.List;

public class ConsumableConfig {
    @JsonProperty("sort")
    private int sort;
    @JsonProperty("priority")
    private int priority;
    @JsonProperty("limit")
    private int limit;
    @JsonProperty("countdown")
    private int countdown;
    @JsonProperty("fromEvent")
    private boolean fromEvent;
    @JsonProperty("canTrade")
    private boolean canTrade;
    @JsonProperty("canUse")
    private boolean canUse;
    @JsonProperty("canEquip")
    private boolean canEquip;
    @JsonProperty("canUseWhenDead")
    private boolean canUseWhenDead;
    @JsonProperty("category")
    private int category;
    @JsonProperty("image")
    private String image;
    @JsonProperty("prize")
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Prize> prize = new ArrayList<>(); // for chests

    /**
     * Creates a new consumable config.
     */
    public ConsumableConfig() {

    }

    /**
     * Creates a new consumable config.
     */
    public ConsumableConfig(String itemImage) {
        this.image = itemImage;
    }

    /**
     * Gets can equip.
     * @return Can equip.
     */
    public boolean getCanEquip() {
        return this.canEquip;
    }

    /**
     * Gets can trade.
     * @return Can trade.
     */
    public boolean getCanTrade() {
        return this.canTrade;
    }

    /**
     * Gets can use.
     * @return Can use.
     */
    public boolean getCanUse() {
        return this.canUse;
    }

    /**
     * Gets can use when dead.
     * @return Can use when dead.
     */
    public boolean getCanUseWhenDead() {
        return this.canUseWhenDead;
    }

    /**
     * Gets the category.
     * @return The category.
     */
    public int getCategory() {
        return this.category;
    }

    /**
     * Gets the countdown.
     * @return The countdown.
     */
    public int getCountdown() {
        return this.countdown;
    }

    /**
     * Gets from event.
     * @return The from event.
     */
    public boolean getFromEvent() {
        return this.fromEvent;
    }

    /**
     * Gets the image.
     * @return The image.
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Gets the limit.
     * @return The limit.
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Gets the priority.
     * @return The priority.
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Gets the sort.
     * @return The sort.
     */
    public int getSort() {
        return this.sort;
    }

    /**
     * Gets the prize.
     * @return The prize.
     */
    public List<Prize> getPrize() {
        return this.prize;
    }


    public static class Prize {
        @JsonProperty("type")
        private ChestPrizeType type;
        @JsonProperty("info")
        private int info;

        /**
         * Creates a new chest prize.
         * @param type The prize type.
         * @param info The prize.
         */
        public Prize(ChestPrizeType type, int info) {
            this.type = type;
            this.info = info;
        }

        /**
         * Returns the chest prize type.
         * @return The chest prize type.
         */
        public ChestPrizeType getType() {
            return this.type;
        }

        /**
         * Returns the prize info.
         * @return The prize info.
         */
        public int getInfo() {
            return this.info;
        }

        public enum ChestPrizeType {
            BADGE,
            TITLE
        }
    }
}
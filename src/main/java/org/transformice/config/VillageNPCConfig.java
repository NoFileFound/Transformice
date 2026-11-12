package org.transformice.config;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class VillageNPCConfig {
    @JsonProperty("title_id")
    private int title_id;
    @JsonProperty("feminine")
    private boolean feminine;
    @JsonProperty("look")
    private String look;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("emote")
    private int emote;
    @JsonProperty("facing_right")
    private boolean facing_right;
    @JsonProperty("facing_player")
    private boolean facing_player;
    @JsonProperty("npc_interface")
    private int npc_interface;
    @JsonProperty("message")
    private String message;
    @JsonProperty("items")
    private List<Item> items;

    /**
     * Creates a new village NPC.
     */
    public VillageNPCConfig() {

    }

    /**
     * Gets the title id.
     * @return The title id.
     */
    public int getTitleId() {
        return this.title_id;
    }

    /**
     * Gets is feminine.
     * @return The is feminine.
     */
    public boolean getIsFeminine() {
        return this.feminine;
    }

    /**
     * Gets the look.
     * @return The look.
     */
    public String getLook() {
        return this.look;
    }

    /**
     * Gets the X.
     * @return The X.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the Y.
     * @return The Y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the emote.
     * @return  The emote.
     */
    public int getEmote() {
        return this.emote;
    }

    /**
     * Gets the facing_right.
     * @return The facing_right.
     */
    public boolean getIsFacingRight() {
        return this.facing_right;
    }

    /**
     * Gets the facing_player.
     * @return The facing_player.
     */
    public boolean getIsFacingPlayer() {
        return this.facing_player;
    }

    /**
     * Gets the npc interface.
     * @return The npc interface.
     */
    public int getNpcInterface() {
        return this.npc_interface;
    }

    /**
     * Gets the message.
     * @return The message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the items.
     * @return The items.
     */
    public List<Item> getItems() {
        return this.items;
    }

    public static class Item {
        @JsonProperty("type")
        private int type;
        @JsonProperty("item_id")
        private int item_id;
        @JsonProperty("quantity")
        private int quantity;
        @JsonProperty("cost_type")
        private int cost_type;
        @JsonProperty("cost_id")
        private int cost_id;
        @JsonProperty("cost_quantity")
        private int cost_quantity;
        @JsonProperty("hover_text_template")
        private String hover_text_template;
        @JsonProperty("hover_text_args")
        private String hover_text_args;

        /**
         * Creates a new item.
         */
        public Item() {

        }

        /**
         * Gets the item's type.
         * @return The item's type.
         */
        public int getType() {
            return this.type;
        }

        /**
         * Gets the item's id.
         * @return The item's id.
         */
        public int getItemId() {
            return this.item_id;
        }

        /**
         * Gets the item's quantity.
         * @return The item's quantity.
         */
        public int getQuantity() {
            return this.quantity;
        }

        /**
         * Gets the item's cost type.
         * @return The item's cost type.
         */
        public int getCostType() {
            return this.cost_type;
        }

        /**
         * Gets the item's cost id.
         * @return The item's cost id.
         */
        public int getCostId() {
            return this.cost_id;
        }

        /**
         * Gets the item's cost quantity.
         * @return The item's cost quantity.
         */
        public int getCostQuantity() {
            return this.cost_quantity;
        }

        /**
         * Gets the hover_text_template.
         * @return The hover_text_template.
         */
        public String getHoverTextTemplate() {
            return this.hover_text_template;
        }

        /**
         * Gets the hover_text_args.
         * @return The hover_text_args.
         */
        public String getHoverTextArgs() {
            return this.hover_text_args;
        }
    }
}
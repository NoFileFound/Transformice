package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import org.transformice.data.objects.EventType;

public class EventConfig {
    @JsonProperty("flyer_img")
    private String flyer_img = "flyer_2024_06_20.jpg";
    @JsonProperty("banner_id")
    private Integer banner_id;
    @JsonProperty("minimum_minutes_to_start_event")
    private Integer minimum_minutes_to_start_event = 15;
    @JsonProperty("banner_bg_img")
    private String banner_bg_img = "";
    @JsonProperty("banner_fg_img")
    private String banner_fg_img = "";
    @JsonProperty("cheese_sprite_suffix")
    private String cheese_sprite_suffix = "";
    @JsonProperty("shop_news_file_id")
    private String shop_news_file_id = "";
    @JsonProperty("event_name")
    private EventType event_name;
    @JsonProperty("event_id")
    private Integer event_id;

    /**
     * Creates a new event config.
     */
    public EventConfig() {

    }

    /**
     * Gets the event's flyer image.
     * @return The event's flyer image.
     */
    public String getFlyerImg() {
        return this.flyer_img;
    }

    /**
     * Gets the event's banner id.
     * @return The event's banner id.
     */
    public Integer getBannerId() {
        return this.banner_id;
    }

    /**
     * Gets the event's banner background image.
     * @return The event's banner background image.
     */
    public String getBannerBackgroundImg() {
        return this.banner_bg_img;
    }

    /**
     * Gets the event's banner foreground image.
     * @return The event's banner foreground image.
     */
    public String getBannerForegroundImg() {
        return this.banner_fg_img;
    }

    /**
     * Gets the event's cheese sprite suffix.
     * @return The cheese sprite suffix.
     */
    public String getCheeseSpriteSuffix() {
        return this.cheese_sprite_suffix;
    }

    /**
     * Gets the event's shop news.
     * @return The event's shop news.
     */
    public String getShopNewsFileId() {
        return this.shop_news_file_id;
    }

    /**
     * Gets the event's name.
     * @return The event's name.
     */
    public EventType getEventName() {
        return this.event_name;
    }

    /**
     * Gets the event's id.
     * @return The event's id.
     */
    public Integer getEventId() {
        return this.event_id;
    }

    /**
     * Gets the minimum time until event starts.
     * @return The minimum time until event starts.
     */
    public int getMinimumMinutesToShowEvent() {
        return this.minimum_minutes_to_start_event;
    }
}
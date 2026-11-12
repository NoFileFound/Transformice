package org.transformice.config.titleconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;

public class TitleData {
    @JsonProperty("titleId")
    private int titleId;
    @JsonProperty("titleStars")
    private int titleStars;

    /**
     * Creates a new title data.
     */
    public TitleData() {

    }

    /**
     * Gets the title id.
     * @return The title id.
     */
    public int getTitleId() {
        return titleId;
    }

    /**
     * Gets the title stars.
     * @return The title stars.
     */
    public int getTitleStars() {
        return titleStars;
    }
}
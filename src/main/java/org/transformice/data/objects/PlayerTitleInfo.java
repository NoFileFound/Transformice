package org.transformice.data.objects;

// Imports
import dev.morphia.annotations.Entity;

@Entity("titleInfo")
public final class PlayerTitleInfo {
    public short titleId;
    public byte titleStars;

    /**
     * Creates a new title info.
     */
    public PlayerTitleInfo() {
        // Required by Morphia
        this.titleId = 0;
        this.titleStars = 0;
    }

    /**
     * Creates a new title info.
     * @param titleId The title's id.
     * @param titleStars The title's stars.
     */
    public PlayerTitleInfo(short titleId, byte titleStars) {
         this.titleId = titleId;
         this.titleStars = titleStars;
    }
}
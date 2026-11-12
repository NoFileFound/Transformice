package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("mapeditor")
public class MapEntry {
    @Id private int mapCode;
    public Integer mapCategory;
    private String mapAuthor;
    public String mapXML;
    public Integer mapYesVotes;
    public Integer mapNoVotes;

    /**
     * Creates a new map.
     */
    public MapEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new map entry.
     * @param mapCode The map's id.
     * @param mapAuthor The map's author.
     * @param mapCategory The map's category.
     * @param mapXml The map's XML code.
     */
    public MapEntry(int mapCode, String mapAuthor, int mapCategory, String mapXml) {
        this();
        this.mapCode = mapCode;
        this.mapAuthor = mapAuthor;
        this.mapXML = mapXml;
        this.mapCategory = mapCategory;
        this.mapYesVotes = 0;
        this.mapNoVotes = 0;
    }

    /**
     * Gets the map's author.
     * @return The map's author.
     */
    public String getMapAuthor() {
        return this.mapAuthor;
    }

    /**
     * Gets the map's code.
     * @return The map's code.
     */
    public int getMapCode() {
        return this.mapCode;
    }
}
package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import org.transformice.database.DBManager;

@Entity(value = "mapeditor", useDiscriminator = false)
@Getter
public final class MapEditor {
    @Id private final int id; // default
    private final int mapCode;
    @Setter private Integer mapCategory;
    private final String mapAuthor;
    @Setter private String mapXML;
    @Setter private Integer mapYesVotes;
    @Setter private Integer mapNoVotes;

    /**
     * Creates a new instance of Map.
     * @param mapXML The map xml.
     * @param mapAuthor The map author.
     * @param isTribeHouse Is exported as a tribe house.
     */
    public MapEditor(String mapXML, String mapAuthor, boolean isTribeHouse) {
        this.id = (int)DBManager.getCounterValue("lastMapCode");
        this.mapCode = this.id;
        this.mapAuthor = mapAuthor;
        this.mapXML = mapXML;
        this.mapCategory = (isTribeHouse ? 22 : 0);
        this.mapYesVotes = 0;
        this.mapNoVotes = 0;
    }

    /**
     * Deletes a map from database.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Saves the connection info in the database.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
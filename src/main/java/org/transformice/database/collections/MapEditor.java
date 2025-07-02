package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(value = "mapeditor", useDiscriminator = false)
@Getter
public class MapEditor {
    private Integer mapCode;
    private Integer mapCategory;
    private String mapAuthor;
    private String mapXML;
    private @Setter Integer mapYesVotes;
    private @Setter Integer mapNoVotes;
}
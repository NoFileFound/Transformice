package org.transformice.database.embeds;

// Imports
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embedded
public class Quest {
    @Id private final long id;
    private final int missionType;
    @Setter private int missionCollected;
    private final int missionTotal;
    private final int missionPrize;

    public Quest(int id, int missionType, int missionCollected, int missionTotal, int missionPrize) {
        this.id = id;
        this.missionType = missionType;
        this.missionCollected = missionCollected;
        this.missionTotal = missionTotal;
        this.missionPrize = missionPrize;
    }
}
package org.transformice.database.collections;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.transformice.database.DBManager;
import org.transformice.database.embeds.TribeHistoricEntry;
import org.transformice.database.embeds.TribeRank;

@Entity(value = "tribes", useDiscriminator = false)
@Getter
public final class Tribe {
    private final @Id long id;
    private final String tribeLeader;
    @Setter private String tribeName;
    @Setter private Integer tribeHouseMap;
    private final List<String> tribeMembers;
    private final long createdAt;
    private final List<TribeRank> tribeRanks;
    private final List<TribeHistoricEntry> tribeHistory;
    @Setter private String tribeMessage;

    /**
     * Creates a new tribe.
     * @param tribeLeader The tribe leader.
     * @param tribeName The tribe name.
     */
    public Tribe(final String tribeLeader, final String tribeName) {
        this.id = DBManager.getCounterValue("lastTribeId");
        this.tribeLeader = tribeLeader;
        this.tribeName = tribeName;
        this.tribeHouseMap = 0;
        this.tribeMembers = new ArrayList<>();
        this.createdAt = getUnixTime();
        this.tribeRanks = new ArrayList<>();
        this.tribeHistory = new ArrayList<>();
        this.tribeMessage = "";
        this.initTribe(tribeLeader);
    }

    /**
     * Creates the default ranks in the current tribe.
     * @param tribeLeader The tribe leader.
     */
    private void initTribe(String tribeLeader) {
        this.tribeMembers.add(tribeLeader);

        this.tribeRanks.add(new TribeRank("${trad#TG_0}", 0,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_1}", 1,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_2}", 2,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_3}", 3,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_4}", 4,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_5}", 5,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_6}", 6,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_7}", 7,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_8}", 8,0));
        this.tribeRanks.add(new TribeRank("${trad#TG_9}", 9, 10)); // leader

        this.tribeHistory.add(new TribeHistoricEntry(1, String.format("{\"auteur\":\"%s\",\"tribu\":\"%s\"}", tribeLeader, tribeName)));
    }

    /**
     * Saves the tribe in the database.
     */
    public void save() {
        DBManager.saveInstance(this);
    }

    /**
     * Deletes the tribe in the database.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }
}
package org.transformice.database.collections;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.transformice.Client;
import org.transformice.database.DBManager;
import org.transformice.database.embeds.ReportReporter;

@Entity(value = "modopwet", useDiscriminator = false)
@Getter
public final class Report {
    private final @Id long id;
    private final String playerName;
    @Setter private String playerCommunity;
    private final Integer playedHours;
    private final Long timestamp;
    @Setter private Boolean isDeleted = false;
    @Setter private String deletedBy = "";
    private final List<ReportReporter> reporters;

    /**
     * Creates a new report in the modopwet.
     * @param player The player who is reported.
     * @param reporter The player who reported.
     * @param reportType The report type.
     * @param reportReason The report comment (reason).
     * @param playerKarma The reporter's karma.
     */
    public Report(Client player, String reporter, int reportType, String reportReason, short playerKarma) {
        this.id = DBManager.getCounterValue("lastReportId");
        this.playerName = player.getPlayerName();
        this.playerCommunity = player.playerCommunity;
        this.playedHours = (int)(player.isGuest() ? (player.getLoginTime() / 3600) : player.getAccount().getPlayedTime() / 3600);
        this.timestamp = getUnixTime();
        this.reporters = new ArrayList<>(List.of(new ReportReporter(reporter, reportReason, reportType, playerKarma)));
    }

    /**
     * Checks if reporter with given player name exist.
     * @param playerName The given player name.
     * @return True if exist or else False.
     */
    public boolean checkReporter(String playerName) {
        for (ReportReporter reporter : this.reporters) {
            if(reporter.getPlayerName().equals(playerName)) return true;
        }
        return false;
    }

    /**
     * Deletes the report from the database.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Saves the report in the database.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
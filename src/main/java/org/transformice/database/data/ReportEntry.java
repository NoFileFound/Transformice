package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import java.util.ArrayList;
import java.util.List;
import org.transformice.data.objects.ReportType;
import org.transformice.utils.Utils;

@Entity("modopwet")
public class ReportEntry {
    @Id private long id;
    private String playerName;
    private ReportType reportType;
    private Long reportCreationDate;
    private List<ReporterEntry> reporters;
    public PlayerConnectionStatus playerConnectionStatus;
    @Transient public String playerCommunity;
    public Long playedTime;
    public Boolean isResolved;
    public String resolvedBy;

    /**
     * Creates a new report.
     */
    public ReportEntry() {
        // Required by Morphia
        this.reporters = new ArrayList<>();
    }

    /**
     * Creates a new report in the modopwet.
     * @param reportId The report's id.
     * @param playerName The reported's player name.
     * @param playerCommunity The reported's community.
     * @param reportType The report's type.
     * @param playedTime The reported's played time.
     * @param reporter The reporter's information.
     */
    public ReportEntry(final int reportId, final String playerName, final String playerCommunity, final long playedTime, final ReportType reportType, final ReporterEntry reporter) {
        this();
        this.id = reportId;
        this.playerName = playerName;
        this.playerCommunity = playerCommunity;
        this.playedTime = playedTime;
        this.reportType = reportType;
        this.reportCreationDate = Utils.getUnixTime();
        this.playerConnectionStatus = PlayerConnectionStatus.CONNECTED;
        this.isResolved = false;
        this.reporters = new ArrayList<>(List.of(reporter));
    }

    /**
     * Gets the report's id.
     * @return The report's id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Gets the reported player's name.
     * @return The reported player's name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the report's creation date.
     * @return The report's creation date.
     */
    public Long getReportCreationDate() {
        return this.reportCreationDate;
    }

    /**
     * Gets the report's type.
     * @return The report's type.
     */
    public ReportType getReportType() {
        return this.reportType;
    }

    /**
     * Gets the report's reporters.
     * @return The report's reporters.
     */
    public List<ReporterEntry> getReporters() {
        return this.reporters;
    }

    /**
     * The reported player's connection status.
     */
    public enum PlayerConnectionStatus {
        CONNECTED,
        DISCONNECTED
    }

    /**
     * Creates a new reporter.
     */
    @Embedded
    public static class ReporterEntry {
        public String playerName;
        public String reportComment;
        public ReportType reportType;
        private long reportAge;
        public int playerKarma;

        /**
         * Creates a new reporter.
         */
        public ReporterEntry() {
            // Required by Morphia.
        }

        /**
         * Creates a new reporter.
         * @param playerName The reporter's name.
         * @param reportComment The reporter's report comment.
         * @param reportType The reporter's report type.
         * @param playerKarma The reporter's reputation.
         */
        public ReporterEntry(String playerName, String reportComment, ReportType reportType, int playerKarma) {
            this();
            this.playerName = playerName;
            this.reportComment = reportComment;
            this.reportType = reportType;
            this.reportAge = Utils.getUnixTime();
            this.playerKarma = playerKarma;
        }

        /**
         * Gets the reporter's player name.
         * @return The reporter's player name.
         */
        public String getPlayerName() {
            return this.playerName;
        }

        /**
         * Gets the reporter's report age.
         * @return The reporter's report age.
         */
        public long getReportAge() {
            return this.reportAge;
        }

        /**
         * Gets the reporter's comment.
         * @return The reporter's comment.
         */
        public String getReportComment() {
            return this.reportComment;
        }

        /**
         * Gets the reporter's view of report's type.
         * @return The report's type.
         */
        public ReportType getReportType() {
            return this.reportType;
        }
    }
}
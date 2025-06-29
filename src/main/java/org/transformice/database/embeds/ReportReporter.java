package org.transformice.database.embeds;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Embedded;
import lombok.Getter;

@Getter
@Embedded
public class ReportReporter {
    private final String playerName;
    private final String reportComment;
    private final short playerKarma;
    private final int reportType;
    private final long reportAge;

    public ReportReporter(String playerName, String reportComment, int reportType, short playerKarma) {
        this.playerName = playerName;
        this.reportComment = reportComment;
        this.playerKarma = playerKarma;
        this.reportType = reportType;
        this.reportAge = getUnixTime();
    }
}
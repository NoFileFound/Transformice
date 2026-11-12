package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.transformice.utils.Utils;

@Entity(value = "sanctions")
public class SanctionEntry {
    @Id private long id;
    private String playerName;
    private String ipAddress;
    private SanctionType sanctionType;
    private String author;
    private String reason;
    private Long createdDate;
    private Long expirationDate;
    public Boolean isPermanent;
    public String cancelAuthor;
    public String cancelReason;
    public Long cancelDate;
    public SanctionState sanctionState;

    /**
     * Creates a new sanction.
     */
    public SanctionEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new sanction.
     * @param sanctionId The sanction's id.
     * @param playerName The guilty player.
     * @param ipAddress The player's ip address.
     * @param sanctionType The punishment type.
     * @param moderatorName The moderator.
     * @param reason The reason.
     * @param endDate When to expire. (-1 for permanent).
     */
    public SanctionEntry(final long sanctionId, final String playerName, final String ipAddress, final SanctionType sanctionType, final String moderatorName, final String reason, final Long endDate) {
        this();
        this.id = sanctionId;
        this.playerName = playerName;
        this.ipAddress = ipAddress;
        this.sanctionType = sanctionType;
        this.author = moderatorName;
        this.reason = reason;
        this.createdDate = Utils.getUnixTime();
        if (endDate == -1) {
            this.isPermanent = true;
            this.expirationDate = (long) -1;
        } else {
            this.isPermanent = false;
            this.expirationDate = endDate;
        }

        this.cancelAuthor = "";
        this.cancelReason = "";
        this.cancelDate = 0L;
        this.sanctionState = SanctionState.ACTIVE;
    }

    /**
     * Cancels the provided sanction.
     * @param cancelAuthor The sanction canceler's author.
     * @param cancelReason The sanction canceler's reason.
     */
    public void cancelSanction(String cancelAuthor, String cancelReason) {
        this.cancelAuthor = cancelAuthor;
        this.cancelReason = cancelReason;
        this.cancelDate = Utils.getUnixTime();
        this.sanctionState = SanctionState.CANCELED;
    }

    /**
     * Gets the sanction's author.
     * @return The sanction's author.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Gets the sanction's creation date.
     * @return The sanction's creation date.
     */
    public Long getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Gets the sanction's expiration date.
     * @return The sanction's expiration date.
     */
    public Long getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * Gets the sanction's id.
     * @return The sanction's id.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the sanction's ip address.
     * @return The sanction's ip address.
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * Gets the sanction's player.
     * @return The sanction's player.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the sanction's reason.
     * @return The sanction's reason.
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Gets the sanction's type.
     * @return The sanction's type.
     */
    public SanctionType getSanctionType() {
        return this.sanctionType;
    }

    /**
     * The sanction's type,
     */
    public enum SanctionType {
        SANCTION_BAN,
        SANCTION_MUTE
    }

    /**
     * The sanction's state.
     */
    public enum SanctionState {
        ACTIVE,
        TAXED,
        EXPIRED,
        CANCELED
    }
}
package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import org.transformice.database.DBManager;

@Entity(value = "casier", useDiscriminator = false)
@Getter
public final class Sanction {
    private final @Id long id;
    private final String playerName;
    private final String type;
    private final String mode = "Manual";
    private final String author;
    private final String reason;
    private final Boolean isPermanent;
    @Setter private String state;
    private final Long createdDate;
    private final Long expirationDate;
    private String cancelAuthor;
    private String cancelReason;
    private Long cancelData;

    /**
     * Creates a new sanction.
     * @param playerName The guilty player.
     * @param type The punishment type.
     * @param moderatorName The moderator.
     * @param reason The reason.
     * @param endDate When to expire. (-1 for permanent).
     */
    public Sanction(String playerName, String type, String moderatorName, String reason, Long endDate) {
        this.id = DBManager.getCounterValue("lastSanctionId");
        this.playerName = playerName;
        this.type = type;
        this.author = moderatorName;
        this.reason = reason;
        this.createdDate = (System.currentTimeMillis() / 1000);
        if(endDate == -1) {
            this.isPermanent = true;
            this.expirationDate = -1L;
        } else {
            this.isPermanent = false;
            this.expirationDate = endDate;
        }
        this.cancelAuthor = "";
        this.cancelReason = "";
        this.cancelData = 0L;
    }

    /**
     * Deletes a sanction.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Saves a sanction.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
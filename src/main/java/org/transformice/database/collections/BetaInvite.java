package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.transformice.database.DBManager;

@Entity(value = "betainvites", useDiscriminator = false)
@Getter
public final class BetaInvite {
    @Id private ObjectId id; // default
    private final String beta_key;
    private final String issuer;
    private final long startDate;
    private final long endDate;

    /**
     * Creates a new beta key.
     * @param beta_key The key.
     * @param issuer The author of the key.
     * @param duration Time until expire. (days)
     */
    public BetaInvite(String beta_key, String issuer, long duration) {
        this.beta_key = beta_key;
        this.issuer = issuer;
        this.startDate = (System.currentTimeMillis() / 1000);
        this.endDate = this.startDate + (duration * 86400);
        this.save();
    }

    /**
     * Deletes a beta key.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Saves a beta key.
     */
    private void save() {
        DBManager.saveInstance(this);
    }
}
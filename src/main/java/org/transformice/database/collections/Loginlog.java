package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.transformice.database.DBManager;

@Entity(value = "connexionlog", useDiscriminator = false)
@Getter
public final class Loginlog {
    private @Id ObjectId id; // default
    private final String playerName;
    private final Long date;
    private final String ipAddress;
    private final String ipCountry;
    private final String gameCommunity;
    private final String serviceName = "Transformice";

    /**
     * Creates new loginlog.
     * @param playerName The player's name.
     * @param date The date when he logged in the game.
     * @param ipAddress His IP address.
     * @param ipCountry His IP Country.
     */
    public Loginlog(String playerName, Long date, String ipAddress, String ipCountry, String gameLangue) {
        this.playerName = playerName;
        this.date = date;
        this.ipAddress = ipAddress;
        this.ipCountry = ipCountry;
        this.gameCommunity = gameLangue;
        this.save();
    }

    /**
     * Saves the connection info in the database.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
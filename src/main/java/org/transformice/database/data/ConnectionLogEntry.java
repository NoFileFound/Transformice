package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.transformice.utils.Utils;

@Entity(value = "connectionlogs")
public class ConnectionLogEntry {
    @Id private ObjectId id;
    private String playerName;
    private Long date;
    private String ipAddress;
    private String ipCountry;
    private String gameCommunity;
    private String serviceName;

    /**
     * Creates a new connection log entry.
     */
    public ConnectionLogEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new connection log entry.
     * @param playerName The player's name.
     * @param ipAddress The player's IP address.
     * @param ipCountry The player's IP country.
     * @param gameCommunity The player's game community.
     */
    public ConnectionLogEntry(String playerName, String ipAddress, String ipCountry, String gameCommunity) {
        this();
        this.playerName = playerName;
        this.date = Utils.getUnixTime();
        this.ipAddress = ipAddress;
        this.ipCountry = ipCountry;
        this.gameCommunity = gameCommunity;
        this.serviceName = "Transformice";
    }

    /**
     * Gets the date.
     * @return The date.
     */
    public Long getDate() {
        return this.date;
    }

    /**
     * Gets the game community.
     * @return The game community.
     */
    public String getGameCommunity() {
        return this.gameCommunity;
    }

    /**
     * Gets the IP address.
     * @return The IP address.
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * Gets the IP country.
     * @return The IP country.
     */
    public String getIpCountry() {
        return this.ipCountry;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the service name.
     * @return The service name.
     */
    public String getServiceName() {
        return this.serviceName;
    }
}
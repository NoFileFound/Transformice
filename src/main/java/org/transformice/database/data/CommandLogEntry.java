package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.transformice.utils.Utils;

@Entity(value = "commandlogs")
public class CommandLogEntry {
    @Id private ObjectId id;
    private String playerName;
    private Long date;
    private String ipAddress;
    private String gameCommunity;
    private String command;

    /**
     * Creates a new command log entry.
     */
    public CommandLogEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new command log entry.
     * @param playerName The player's name.
     * @param ipAddress The player's IP address.
     * @param gameCommunity The player's game community.
     */
    public CommandLogEntry(String playerName, String ipAddress, String gameCommunity, String command) {
        this();
        this.playerName = playerName;
        this.date = Utils.getUnixTime();
        this.ipAddress = ipAddress;
        this.gameCommunity = gameCommunity;
        this.command = command;
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
     * Gets the player's name.
     * @return The player's name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the player's entered command.
     * @return The player's entered command.
     */
    public String getCommand() {
        return this.command;
    }
}
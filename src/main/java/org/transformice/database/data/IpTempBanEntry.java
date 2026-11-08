package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import org.transformice.utils.Utils;

@Entity(value = "iptempban")
public class IpTempBanEntry {
    private String ipAddress;
    private int duration;
    private long startTime;
    private String reason;

    /**
     * Creates a new ip temp ban.
     */
    public IpTempBanEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new ip temp ban.
     * @param ipAddress The given ip address.
     * @param hours The given hours.
     */
    public IpTempBanEntry(String ipAddress, int hours, String reason) {
        this.ipAddress = ipAddress;
        this.duration = hours;
        this.reason = reason;
        this.startTime = Utils.getUnixTime();
    }

    /**
     * Gets the blacklisted ip.
     * @return The blacklisted ip.
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * Gets the ip ban hours.
     * @return The ip ban hours.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Gets the start time.
     * @return The start time.
     */
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Gets the reason.
     * @return The reason.
     */
    public String getReason() {
        return this.reason;
    }
}
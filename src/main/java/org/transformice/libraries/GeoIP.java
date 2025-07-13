package org.transformice.libraries;

// Imports
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import org.transformice.Application;

public final class GeoIP {
    private static DatabaseReader reader;

    /**
     * Initializes the geoip database.
     */
    public static void loadGeoDatabase() {
        try {
            File dbFile = new File("config/GeoIP.dat");
            if (!dbFile.exists()) {
                throw new IOException("GeoIP.dat not found at " + dbFile.getAbsolutePath());
            }

            reader = new DatabaseReader.Builder(dbFile).build();
            Application.getLogger().info("GeoIP database loaded");
        } catch (Exception ex) {
            Application.getLogger().error("[#] GeoIP database could not be loaded", ex);
            System.exit(1);
        }
    }

    /**
     * Gets the continent information of given ip address.
     * @param ipAddress The given ip address.
     * @return Continent object.
     */
    public static Continent getContinent(String ipAddress) {
        try {
            return reader.country(InetAddress.getByName(ipAddress)).getContinent();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Gets the country information of given ip address.
     * @param ipAddress The given ip address.
     * @return Country object.
     */
    public static Country getCountry(String ipAddress) {
        try {
            return reader.country(InetAddress.getByName(ipAddress)).getCountry();
        } catch (Exception ignored) {
            return null;
        }
    }
}
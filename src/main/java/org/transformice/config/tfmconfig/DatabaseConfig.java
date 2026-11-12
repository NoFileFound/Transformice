package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseConfig {
    @JsonProperty("host")
    private String host = "127.0.0.1";
    @JsonProperty("port")
    private int port = -1;
    @JsonProperty("name")
    private String name = "Transformice";
    @JsonProperty("username")
    private String username = "";
    @JsonProperty("password")
    private String password = "";

    /**
     * Creates a new instance of DatabaseConfig.
     */
    public DatabaseConfig() {

    }

    /**
     * Gets the database's host.
     * @return The database's host.
     */
    public String getHost() { return this.host; }

    /**
     * Gets the database's port.
     * @return The database's port.
     */
    public int getPort() { return this.port; }

    /**
     * Gets the database's name.
     * @return The database's name.
     */
    public String getName() { return this.name; }

    /**
     * Gets the database's root username.
     * @return The database's root username.
     */
    public String getUsername() { return this.username; }

    /**
     * Gets the database's root password.
     * @return The database's root password.
     */
    public String getPassword() { return this.password; }
}
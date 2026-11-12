package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminPanelConfig {
    @JsonProperty("port")
    private int port = 8882;
    @JsonProperty("username")
    private String username = "root";
    @JsonProperty("password")
    private String password = "changeme";

    /**
     * Creates a new config of the admin panel.
     */
    public AdminPanelConfig() {

    }

    /**
     * Gets the admin panel's port.
     * @return The admin panel's port.
     */
    public int getPort() { return port; }

    /**
     * Gets the admin panel's username.
     * @return The admin panel's username.
     */
    public String getUsername() { return username; }

    /**
     * Gets the admin panel's password.
     * @return The admin panel's password.
     */
    public String getPassword() { return password; }
}
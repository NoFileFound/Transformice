package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailConfig {
    @JsonProperty("host")
    private String host = "";
    @JsonProperty("port")
    private int port = 0;
    @JsonProperty("username")
    private String username = "";
    @JsonProperty("password")
    private String password = "";

    /**
     * Creates a new config of SMTP.
     */
    public EmailConfig() {

    }

    /**
     * Returns the SMTP's host.
     * @return The SMTP's host.
     */
    public String getHost() { return host; }

    /**
     * Returns the SMTP's port.
     * @return The SMTP's port.
     */
    public int getPort() { return port; }

    /**
     * Returns the SMTP's username.
     * @return The SMTP's username.
     */
    public String getUsername() { return username; }

    /**
     * Returns the SMTP's password.
     * @return The SMTP's password.
     */
    public String getPassword() { return password; }
}
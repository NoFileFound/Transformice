package org.transformice.config;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import org.transformice.config.tfmconfig.*;

public class TransformiceConfig {
    @JsonProperty("is_debug")
    private boolean is_debug;
    @JsonProperty("db_config")
    private DatabaseConfig db_config;
    @JsonProperty("adminpanel_config")
    private AdminPanelConfig adminpanel_config;
    @JsonProperty("email_config")
    private EmailConfig email_config;
    @JsonProperty("swf_config")
    private SwfConfig swf_config;
    @JsonProperty("event_config")
    private EventConfig event_config;
    @JsonProperty("game_config")
    private GameConfig game_config;

    /**
     * Creates a new transformice config.
     */
    public TransformiceConfig() {
        this.db_config = new DatabaseConfig();
        this.adminpanel_config = new AdminPanelConfig();
        this.email_config = new EmailConfig();
        this.swf_config = new SwfConfig();
    }

    /**
     * Checks if the server is in debug mode.
     * @return True if the server is in debug mode or else False.
     */
    public boolean getIsDebug() {
        return this.is_debug;
    }

    /**
     * Gets the database config.
     * @return The database config.
     */
    public DatabaseConfig getDBConfig() {
        return this.db_config;
    }

    /**
     * Gets the admin panel config.
     * @return The admin panel config.
     */
    public AdminPanelConfig getAdmPanelConfig() {
        return this.adminpanel_config;
    }

    /**
     * Gets the SMTP config.
     * @return The SMTP config.
     */
    public EmailConfig getSMTPConfig() {
        return this.email_config;
    }

    /**
     * Gets the SWF config.
     * @return The SWF config.
     */
    public SwfConfig getSWFConfig() {
        return this.swf_config;
    }

    /**
     * Gets the Event config.
     * @return The Event config.
     */
    public EventConfig getEventConfig() {
        return this.event_config;
    }

    /**
     * Gets the Game's config.
     * @return The game's config.
     */
    public GameConfig getGameConfig() {
        return this.game_config;
    }
}
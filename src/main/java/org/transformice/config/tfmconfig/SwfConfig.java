package org.transformice.config.tfmconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SwfConfig {
    @JsonProperty("version")
    private int version = 893;
    @JsonProperty("ports")
    private List<Integer> ports = List.of(11801, 12801, 13801, 14801);
    @JsonProperty("connection_key")
    private String connection_key = "rtTmLdcXKpu";
    @JsonProperty("packet_keys")
    private List<Integer> packet_keys = List.of(40, 47, 8, 48, 18, 59, 3, 57, 41, 19, 117, 115, 118, 87, 105, 73, 98, 100, 80, 66);
    @JsonProperty("login_keys")
    private List<Integer> login_keys = List.of(10101561, 676121, 1, 10656886, 10707218, 32, 10532735, 11307842, 11866524, 7043074, 256, 501638, 262144);
    @JsonProperty("authorization_key")
    private long authorization_key = 1099831313;
    @JsonProperty("swf_url")
    private String swf_url = "";

    /**
     * Creates a new config of the swf.
     */
    public SwfConfig() {

    }

    /**
     * Gets the swf's version.
     * @return The swf's version.
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * Gets the swf's ports.
     * @return The swf's ports.
     */
    public List<Integer> getPorts() {
        return this.ports;
    }

    /**
     * Gets the swf's connection key.
     * @return The swf's connection key.
     */
    public String getConnectionKey() {
        return this.connection_key;
    }

    /**
     * Gets the swf's packet keys.
     * @return The swf's packet keys.
     */
    public List<Integer> getPacketKeys() {
        return this.packet_keys;
    }

    /**
     * Gets the swf's login keys.
     * @return The swf's login keys.
     */
    public List<Integer> getLoginKeys() {
        return this.login_keys;
    }

    /**
     * Gets the swf's authorization key.
     * @return The swf's authorization key.
     */
    public long getAuthorizationKey() {
        return this.authorization_key;
    }

    /**
     * Gets the swf's url.
     * @return The swf's url.
     */
    public String getSwfUrl() {
        return this.swf_url;
    }
}
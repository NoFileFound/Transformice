package org.transformice.data.objects;

public final class PlayerConnectionInfo {
    public String connectionPlatform;
    public String browserInfo;
    public int loaderStageSize;
    public String CCFData;
    public String fontNameHash;
    public String serverString;
    public int referrerId;
    public int connectionTimeStamp;
    public String osLanguage;
    public String osName;
    public String flashVersion;

    /**
     * Creates a new connection info.
     * @param platform The session's platform name.
     * @param browserInfo The session's browser agent.
     * @param loaderStageSize The session's loader size.
     * @param ccf The session's CCF data.
     * @param fontNameHash The session's font name hash.
     * @param serverString The session's flash info.
     * @param referrerId The session's connection type.
     * @param timestamp The session's connection timestamp.
     */
    public PlayerConnectionInfo(String platform, String browserInfo, int loaderStageSize, String ccf, String fontNameHash, String serverString, int referrerId, int timestamp) {
        this.connectionPlatform = (platform.isEmpty() ? "(null)" : platform);
        this.browserInfo = (browserInfo.isEmpty() ? "(null)" : browserInfo);
        this.loaderStageSize = loaderStageSize;
        this.CCFData = ccf;
        this.fontNameHash = fontNameHash;
        this.serverString = serverString;
        this.referrerId = referrerId;
        this.connectionTimeStamp = timestamp;
        this.osLanguage = "null";
        this.osName = "null";
        this.flashVersion = "null";
    }

    /**
     * Updates the additional information.
     * @param osLanguage The OS language.
     * @param osName The OS name.
     * @param flashVersion The flash player version.
     */
    public void updateAdditionalInfo(String osLanguage, String osName, String flashVersion) {
        this.osLanguage = osLanguage;
        this.osName = osName;
        this.flashVersion = flashVersion;
    }
}
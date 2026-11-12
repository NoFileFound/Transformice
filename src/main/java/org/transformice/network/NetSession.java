package org.transformice.network;

// Imports
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.psframework.game.PSGameClient;
import org.psframework.network.PSSendPacket;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.PlayerConnectionInfo;
import org.transformice.timers.TimerKeepAlive;

public final class NetSession extends PSGameClient {
    public static final AttributeKey<NetSession> CLIENT_KEY = AttributeKey.valueOf("client");
    private final Channel channel;
    private GamePlayer player;
    private PlayerConnectionInfo connectionData;
    private int verificationCode;
    private int loginAttempts;
    private String registerCaptchaCode;
    private String currentCommunity;
    private boolean hasPassedHandShake;
    private boolean hasPassedBotVerification;
    private boolean hasBanned;
    public boolean hasConfirmed2FA;
    private final String countryCode;
    private final String countryLanguageCode;
    private final TimerKeepAlive keepAliveTimer;
    public String tmpEmailAddress;
    public String tmpEmailAddressVerCode;
    public String tmp2FAcode;
    public String tmpUsername;
    public String tmp2FAAdmin;
    public boolean isVerifiedIdentity;

    // Debug
    private final boolean isDebugPacketLog;
    private final boolean isDebugException;

    /**
     * Creates a new socket session.
     * @param server The game server.
     * @param channel The session's channel.
     */
    public NetSession(GameServer server, Channel channel) {
        super(server);
        this.channel = channel;
        this.verificationCode = -1;
        this.loginAttempts = 0;
        this.hasPassedHandShake = false;
        this.hasPassedBotVerification = false;
        this.hasBanned = false;
        this.hasConfirmed2FA = false;
        this.isVerifiedIdentity = false;
        this.currentCommunity = "";
        this.registerCaptchaCode = "";
        this.tmpEmailAddress = "";
        this.tmpEmailAddressVerCode = "";
        this.tmp2FAcode = "";
        this.tmpUsername = "";
        this.tmp2FAAdmin = "";
        this.isDebugPacketLog = server.getConfig().getIsDebug();
        this.isDebugException = server.getConfig().getIsDebug();

        // Timers
        this.keepAliveTimer = new TimerKeepAlive(this::disconnect, 30, TimeUnit.SECONDS);

        // GeoIP
        var countryInfo = server.getGeoIP().getLanguageAndCountry(this.getIpAddress());
        this.countryCode = countryInfo.countryCode();
        this.countryLanguageCode = countryInfo.languageCode();
    }

    /**
     * Disconnects the session.
     */
    @Override
    public void disconnect() {
        this.channel.close();
    }

    /**
     * Gets the session's ip address.
     * @return The session's ip address.
     */
    @Override
    public String getIpAddress() {
        var address = this.channel.remoteAddress();
        if (address instanceof InetSocketAddress inetAddress)
            return inetAddress.getAddress() != null ? inetAddress.getAddress().getHostAddress() : inetAddress.getHostString();

        return "127.0.0.1";
    }

    /**
     * Sends a packet to the session.
     * @param packet The packet to send.
     */
    @Override
    public void sendData(PSSendPacket packet) {
        if (this.isDebugPacketLog)
            this.server.getLogger().debug(String.format("[PacketHandler] [%s] Send packet: %s", this.getIpAddress(), packet.getOpcode()));

        PSByteArray payload = packet.build();
        NetByteArray buffer = new NetByteArray();
        int length = payload.getLength() + 2;
        while (length >= 128) {
            buffer.writeUnsignedByte((length & 0x7F) | 0x80);
            length >>>= 7;
        }

        buffer.writeUnsignedByte(length);
        buffer.writeUnsignedShort(packet.getOpcode());
        buffer.writeBytes(payload.toByteArray());
        this.channel.writeAndFlush(Unpooled.wrappedBuffer(buffer.toByteArray()));
    }

    /**
     * Sends a legacy packet.
     * @param packet The packet to send.
     */
    public void sendOldData(PSSendPacket packet) {
        if (this.isDebugPacketLog)
            this.server.getLogger().debug(String.format("[PacketHandler] [%s] Send legacy packet: %s", this.getIpAddress(), packet.getOpcode()));

        NetByteArray packetData = new NetByteArray();
        NetByteArray buffer = new NetByteArray();
        byte[] data = packet.build().toByteArray();
        int opcode = packet.getOpcode();
        packetData.writeUnsignedShort(data.length > 0 ? data.length + 3 : 2);
        packetData.writeUTF(String.valueOf((char) ((opcode >> 8) & 0xFF)) + (char) (opcode & 0xFF), false);
        packetData.writeByte(1);
        packetData.writeBytes(data);
        int length = packetData.getLength() + 2;
        while (length >= 128) {
            buffer.writeUnsignedByte((length & 0x7F) | 0x80);
            length >>>= 7;
        }

        buffer.writeUnsignedByte(length);
        buffer.writeUnsignedByte(1);
        buffer.writeUnsignedByte(1);
        buffer.writeBytes(packetData.toByteArray());
        this.channel.writeAndFlush(Unpooled.wrappedBuffer(buffer.toByteArray()));
    }

    /**
     * Checks if the session is banned from the game.
     * @return True if the player is banned from the game.
     */
    public boolean getCheckHasBanned() {
        return this.hasBanned;
    }

    /**
     * Checks if the player has confirmed the 2FA.
     * @return True if the player has confirmed the 2FA.
     */
    public boolean getCheckHasConfirmed2FA() {
        return this.hasConfirmed2FA;
    }

    /**
     * Checks if the session has passed the anti-bot verification.
     * @return True if the session has passed or else False.
     */
    public boolean getCheckHasPassedBotVerification() {
        return this.hasPassedBotVerification;
    }

    /**
     * Checks if the session has sent the handshake packet.
     * @return True if the session has sent or else False.
     */
    public boolean getCheckHasPassedHandShakePacket() {
        return this.hasPassedHandShake;
    }

    /**
     * Checks if the client can bypass server kick from sending client exceptions.
     * @return True if the client won't be kicked if raised exception or else False.
     */
    public boolean getCheckIsDebugException() {
        return this.isDebugException;
    }

    /**
     * Checks if the client can log messages about send/recv any packet.
     * @return True if the client can be logged or else False.
     */
    public boolean getIsDebugPacket() {
        return this.isDebugPacketLog;
    }

    /**
     * Gets the session's connection data fetched from the information packets.
     * @return The session's connection data.
     */
    public PlayerConnectionInfo getConnectionData() {
        return this.connectionData;
    }

    /**
     * Gets the session's country iso 2 code.
     * @return The session's country code.
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * Gets the session's country's language iso 2 code.
     * @return The session's country's language code.
     */
    public String getCountryLanguageCode() {
        return this.countryLanguageCode;
    }

    /**
     * Gets the current community that was picked from the communities list.
     * @return The current community.
     */
    public String getCurrentCommunity() {
        return this.currentCommunity;
    }

    /**
     * Gets the session's login attempts before log in in the game.
     * @return The session's login attempts.
     */
    public int getLoginAttempts() {
        return this.loginAttempts;
    }

    /**
     * Gets the keep alive timer.
     * @return The keep alive timer.
     */
    public TimerKeepAlive getKeepAliveTimer() {
        return this.keepAliveTimer;
    }

    /**
     * Gets the session's player object.
     * @return The session's player object. (If the session don't have assigned player object it will return {@code null}.)
     */
    public GamePlayer getPlayer() {
        return this.player;
    }

    /**
     * Gets the session's register captcha code.
     * @return The session's register captcha.
     */
    public String getRegisterCaptchaCode() {
        return this.registerCaptchaCode;
    }

    /**
     * Gets the session's verification code.
     * @return The session's verification code.
     */
    public int getVerificationCode() {
        return this.verificationCode;
    }

    /**
     * Sets the session's connection information fetched from the handshake.
     * @param data The connection data.
     */
    public void setConnectionData(PlayerConnectionInfo data) {
        this.connectionData = data;
    }

    /**
     * Sets the current community.
     * @param currentCommunity The community to set.
     */
    public void setCurrentCommunity(String currentCommunity) {
        this.currentCommunity = currentCommunity;
    }

    /**
     * Sets whether this session has been banned.
     * @param hasBanned True if the session is banned or else False.
     */
    public void setHasBanned(boolean hasBanned) {
        this.hasBanned = hasBanned;
    }

    /**
     * Sets if the player has confirmed the 2FA.
     * @param hasConfirmed2FA True if the player has confirmed the 2FA or else False.
     */
    public void setHasConfirmed2FA(boolean hasConfirmed2FA) {
        this.hasConfirmed2FA = hasConfirmed2FA;
    }

    /**
     * Sets whether this session has completed the handshake.
     * @param hasPassedHandShake True if the handshake was successfull or else False.
     */
    public void setHasPassedHandShake(boolean hasPassedHandShake) {
        this.hasPassedHandShake = hasPassedHandShake;
    }

    /**
     * Sets whether this session has completed the anti bot verification.
     * @param hasPassedBotVerification True if the anti bot verification was successfull or else False.
     */
    public void setHasPassedBotVerification(boolean hasPassedBotVerification) {
        this.hasPassedBotVerification = hasPassedBotVerification;
    }

    /**
     * Sets the session's login attempts count.
     * @param attempts The login attempt count to set.
     */
    public void setLoginAttempts(int attempts) {
        this.loginAttempts = attempts;
    }

    /**
     * Assigns a player to this session.
     * @param player the player to assign to this session.
     */
    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    /**
     * Sets the register captcha code to the session.
     * @param registerCaptchaCode The register capTcha code to set.
     */
    public void setRegisterCaptchaCode(String registerCaptchaCode) {
        this.registerCaptchaCode = registerCaptchaCode;
    }

    /**
     * Sets the verification code to the session.
     * @param verificationCode The verification code to set.
     */
    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
}
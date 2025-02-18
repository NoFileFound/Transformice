package org.transformice;

// Imports
import com.maxmind.geoip2.record.Country;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.Getter;
import org.bytearray.ByteArray;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.transformice.database.collections.Account;
import org.transformice.libraries.GeoIP;
import org.transformice.libraries.Pair;
import org.transformice.libraries.Timer;
import org.transformice.packets.SendPacket;

// Packets
import org.transformice.packets.send.legacy.C_BanMessage;
import org.transformice.packets.send.login.C_LoginSouris;
import org.transformice.packets.send.login.C_PlayerIdentity;
import org.transformice.packets.send.mapeditor.C_ExportMapCheeseAmount;
import org.transformice.packets.send.tribulle.C_SwitchNewTribulle;

public class Client {
    public int loginAttempts;
    public int verCode;
    public String loginLangue;
    public String osLanguage;
    public String osName;
    public String registerCaptcha;
    @Getter private Account account;
    @Getter private Server server;
    @Getter private int sessionId;
    @Getter private boolean isGuest;
    @Getter private String ipAddress;
    @Getter private String playerName;
    @Getter private String roomName;
    @Getter final private String countryLangue;
    @Getter final private String countryName;
    private final Channel channel;
    private boolean isClosed = false;

    // Timers
    public Timer keepAliveTimer;
    public Timer createAccountTimer;

    public Client(Server server, Channel channel) {
        Country country = GeoIP.getCountry(this.ipAddress);

        this.server = server;
        this.channel = channel;
        this.ipAddress = ((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress();
        this.countryLangue = (country != null) ? country.getIsoCode() : "jp";
        this.countryName = (country != null) ? country.getName() : "Japan";
        this.verCode = -1;
        this.loginAttempts = 0;

        // Timers
        this.keepAliveTimer = new Timer(Application.getPropertiesInfo().timers.keep_alive.enable, Application.getPropertiesInfo().timers.keep_alive.delay);
        this.createAccountTimer = new Timer(Application.getPropertiesInfo().timers.create_account.enable, Application.getPropertiesInfo().timers.create_account.delay);
    }

    /**
     * Sends the required packets on login.
     * @param account The account (null for guests).
     * @param playerName The player name.
     * @param roomName The room to enter.
     * @param isNewRegistered Is new account.
     */
    public void sendLogin(Account account, String playerName, String roomName, boolean isNewRegistered) {
        this.account = account;
        this.playerName = playerName;
        this.roomName = roomName;
        this.isGuest = (account == null);
        this.loginAttempts = 0;

        if (this.account != null) {
            Pair<Long, String> banInfo = this.server.getLatestSanctionStatus(playerName, "banjeu");
            if (banInfo.getFirst() == -1) {
                this.sendOldPacket(new C_BanMessage(banInfo.getSecond()));
                return;
            }

            if (banInfo.getFirst() > 0) {
                this.sendOldPacket(new C_BanMessage((banInfo.getFirst() / 3600) * 3600000, banInfo.getSecond()));
                return;
            }
        }

        this.sessionId = ++this.server.lastClientSessionId;
        this.server.recordLoginLog(playerName, ipAddress, "Japan", this.loginLangue);
        this.server.getPlayers().put(this.playerName, this);
        this.sendPacket(new C_PlayerIdentity(this));
        this.sendPacket(new C_ExportMapCheeseAmount());
        this.sendPacket(new C_SwitchNewTribulle());

        if (this.isGuest) {
            this.sendPacket(new C_LoginSouris(1, 10));
            this.sendPacket(new C_LoginSouris(2, 5));
            this.sendPacket(new C_LoginSouris(3, 15));
            this.sendPacket(new C_LoginSouris(4, 200));
        }

    }

    /**
     * Closes the connection of current instance.
     */
    public void closeConnection() {
        this.isClosed = true;

        this.account.save();
        for (Timer timer : new Timer[] {this.keepAliveTimer, this.createAccountTimer}) {
            if (timer != null) {
                timer.cancel();
            }
        }

        this.channel.close();
    }

    /**
     * Broadcast a packet in current player.
     * @param packet The given packet.
     */
    public void sendPacket(SendPacket packet) {
        if(this.isClosed) {
            throw new RuntimeException("Attempt to send packet on disconnected client.");
        }

        byte[] data = packet.getPacket();
        ByteArray _packet = new ByteArray();

        int length;
        for(length = data.length + 2; length >= 128; length >>= 7) {
            _packet.writeUnsignedByte(((length & 127) | 128));
        }
        _packet.writeUnsignedByte(length);
        _packet.writeUnsignedByte(packet.getC());
        _packet.writeUnsignedByte(packet.getCC());
        _packet.writeBytes(data);

        Application.getLogger().debug(String.format("[Packet] The Ip Address %s sent a packet [%d, %d]", this.ipAddress, packet.getC(), packet.getCC()));
        this.channel.write(ChannelBuffers.wrappedBuffer(_packet.toByteArray()));
    }

    /**
     * Broadcast a legacy (old) packet in current player.
     * @param packet The given packet.
     */
    public void sendOldPacket(SendPacket packet) {
        if(this.isClosed) {
            throw new RuntimeException("Attempt to send packet on disconnected client.");
        }

        ByteArray data = new ByteArray();
        ByteArray _packet = new ByteArray();
        data.writeString(String.join(Character.toString((char)1), String.valueOf((char)packet.getC()) + (char) packet.getCC(), new String(packet.getPacket())));

        int length;
        for(length = data.getLength() + 2; length >= 128; length >>= 7) {
            _packet.writeUnsignedByte(((length & 127) | 128));
        }
        _packet.writeUnsignedByte(length);
        _packet.writeUnsignedByte(1);
        _packet.writeUnsignedByte(1);
        _packet.writeBytes(data.toByteArray());

        Application.getLogger().info(String.format("[Packet] The Ip Address %s sent an old packet [%d, %d]", this.ipAddress, packet.getC(), packet.getCC()));
        this.channel.write(ChannelBuffers.wrappedBuffer(_packet.toByteArray()));
    }

    /**
     * Calculates all privileges of current player based on privilege level and privilege roles.
     * @return The all privileges that current player has.
     */
    public ArrayList<Integer> calculatePrivileges() {
        if(this.isGuest) {
            return new ArrayList<>();
        }

        ArrayList<Integer> privileges = new ArrayList<>();
        if(this.account.getPrivLevel() == 4) {
            privileges.add(7);
        }

        if(this.account.getPrivLevel() == 5) {
            privileges.add(13);
        }

        if(this.account.getPrivLevel() == 6) {
            privileges.add(12);
        }

        if(this.account.getPrivLevel() == 7) {
            privileges.add(15);
        }

        if(this.account.getPrivLevel() == 8) {
            privileges.add(11);
        }

        if(this.account.getPrivLevel() == 9 || this.account.getPrivLevel() == 10) {
            privileges.add(1);
            privileges.add(3);
            privileges.add(5);
        }

        if(this.account.getPrivLevel() == 11) {
            privileges.add(5);
            privileges.add(10);
        }

        for(String staffRole : this.account.getStaffRoles()) {
            if(staffRole.equals("Sentinelle")) {
                privileges.add(7);
            }

            if(staffRole.equals("FunCorp")) {
                privileges.add(13);
            }

            if(staffRole.equals("LuaDev")) {
                privileges.add(12);
            }

            if(staffRole.equals("FashionSquad")) {
                privileges.add(15);
            }

            if(staffRole.equals("MapCrew")) {
                privileges.add(11);
            }
        }

        return new ArrayList<>(new HashSet<>(privileges));
    }
}
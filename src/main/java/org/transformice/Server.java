package org.transformice;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.transformice.command.CommandLoader;
import org.transformice.connection.*;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.Loginlog;
import org.transformice.database.collections.Report;
import org.transformice.database.collections.Sanction;
import org.transformice.database.collections.Tribe;
import org.transformice.libraries.Pair;
import org.transformice.libraries.Timer;
import org.transformice.packets.PacketHandler;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.chat.C_StaffChannelMessage;
import org.transformice.packets.send.informations.C_ServerRestart;
import org.transformice.packets.send.level.C_OnlineStaffTeam;

public final class Server {
    public int lastGiftID;
    private final List<Channel> channels = new ArrayList<>();
    private final Map<Integer, Client> clientSessions = new HashMap<>();
    private boolean isClosed;
    @Getter public int lastClientSessionId;
    @Getter private Map<String, List<String>> sonarPlayerMovement;
    @Getter private final PacketHandler packetHandler;
    @Getter private final CommandLoader commandHandler;
    @Getter private final List<String> tempBlackList;
    @Getter private final ArrayList<String> minigameList;
    @Getter private final Int2ObjectMap<Object[]> shopGifts;
    @Getter private final Object2ObjectMap<String, Client> players;
    @Getter private final Object2ObjectMap<String, Room> rooms;
    @Getter private final Object2ObjectMap<String, Report> gameReports;
    @Getter private final Object2ObjectMap<String, List<String>> chats;
    @Getter private final Object2ObjectMap<String, Object2ObjectMap<String, Deque<String[]>>> whisperMessages;
    @Getter private final Object2ObjectMap<String, Object2ObjectMap<String, Deque<String[]>>> chatMessages;

    // Cache
    @Getter private Object2ObjectMap<String, Account> cachedAccounts;
    @Getter private Object2ObjectMap<String, Tribe> cachedTribes;
    @Getter private Object2ObjectMap<Pair<String, String>, Sanction> cachedSanctions;

    // Timers
    public Timer deleteModopwetReportTimer;
    public Timer rebootTimer;
    public final Map<String, Timer> createCafeTopicTimer;
    public final Map<String, Timer> createCafePostTimer;
    public final Map<String, Timer> createAccountTimer;
    public final Map<String, Timer> changeDailyQuestTimer;
    public final Map<String, Timer> canChangeDailyQuestTimer;

    // Vanilla
    public static Map<Integer, String> specialMapXmlList = new HashMap<>();
    public static Map<Integer, String> vanillaMapXmlList = new HashMap<>();
    public static final List<Integer> vanillaMapList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233));
    public static final List<Integer> vanillaNoShamMapList = new ArrayList<>(List.of(8, 10, 14, 22, 23, 28, 29, 33, 42, 55, 57, 58, 61, 70, 77, 78, 87, 88, 108, 122, 123, 124, 125, 126, 144, 148, 149, 150, 151, 172, 173, 174, 175, 178, 179, 180, 188, 189, 190, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 218, 219, 220, 221, 222, 224, 225, 231, 232, 233));

    // Titles
    public final Map<Integer, Double> firstTitleList = Map.ofEntries(
            Map.entry(1, 9.1), Map.entry(10, 10.1), Map.entry(100, 11.1), Map.entry(200, 12.1),
            Map.entry(300, 42.1), Map.entry(400, 43.1), Map.entry(500, 44.1), Map.entry(600, 45.1),
            Map.entry(700, 46.1), Map.entry(800, 47.1), Map.entry(900, 48.1), Map.entry(1000, 49.1),
            Map.entry(1100, 50.1), Map.entry(1200, 51.1), Map.entry(1400, 52.1), Map.entry(1600, 53.1),
            Map.entry(1800, 54.1), Map.entry(2000, 55.1), Map.entry(2200, 56.1), Map.entry(2400, 57.1),
            Map.entry(2600, 58.1), Map.entry(2800, 59.1), Map.entry(3000, 60.1), Map.entry(3200, 61.1),
            Map.entry(3400, 62.1), Map.entry(3600, 63.1), Map.entry(3800, 64.1), Map.entry(4000, 65.1),
            Map.entry(4500, 66.1), Map.entry(5000, 67.1), Map.entry(5500, 68.1), Map.entry(6000, 69.1),
            Map.entry(7000, 231.1), Map.entry(8000, 232.1), Map.entry(9000, 233.1), Map.entry(10000, 70.1),
            Map.entry(12000, 224.1), Map.entry(14000, 225.1), Map.entry(16000, 226.1), Map.entry(18000, 227.1),
            Map.entry(20000, 202.1), Map.entry(25000, 228.1), Map.entry(30000, 229.1), Map.entry(35000, 230.1),
            Map.entry(40000, 71.1), Map.entry(45000, 1009.1), Map.entry(55000, 1010.1), Map.entry(70000, 1012.1),
            Map.entry(85000, 1013.1)
    );

    public final Map<Integer, Double> cheeseTitleList = Map.ofEntries(
            Map.entry(5, 5.1), Map.entry(20, 6.1), Map.entry(100, 7.1), Map.entry(200, 8.1), Map.entry(300, 35.1),
            Map.entry(400, 36.1), Map.entry(500, 37.1), Map.entry(600, 26.1), Map.entry(700, 27.1), Map.entry(800, 28.1),
            Map.entry(900, 29.1), Map.entry(1000, 30.1), Map.entry(1100, 31.1), Map.entry(1200, 32.1), Map.entry(1300, 33.1),
            Map.entry(1400, 34.1), Map.entry(1500, 38.1), Map.entry(1600, 39.1), Map.entry(1700, 40.1), Map.entry(1800, 41.1),
            Map.entry(2000, 72.1), Map.entry(2300, 73.1), Map.entry(2700, 74.1), Map.entry(3200, 75.1), Map.entry(3800, 76.1),
            Map.entry(4600, 77.1), Map.entry(6000, 78.1), Map.entry(7000, 79.1), Map.entry(8000, 80.1), Map.entry(9001, 81.1),
            Map.entry(10000, 82.1), Map.entry(14000, 83.1), Map.entry(18000, 84.1), Map.entry(22000, 85.1),
            Map.entry(26000, 86.1), Map.entry(30000, 87.1), Map.entry(34000, 88.1), Map.entry(38000, 89.1),
            Map.entry(42000, 90.1), Map.entry(46000, 91.1), Map.entry(50000, 92.1), Map.entry(55000, 234.1),
            Map.entry(60000, 235.1), Map.entry(65000, 236.1), Map.entry(70000, 237.1), Map.entry(75000, 238.1),
            Map.entry(80000, 93.1)
    );

    public final Map<Integer, Double> bootcampTitleList = Map.ofEntries(
            Map.entry(1, 256.1), Map.entry(3, 257.1), Map.entry(5, 258.1), Map.entry(7, 259.1), Map.entry(10, 260.1),
            Map.entry(15, 261.1), Map.entry(20, 262.1), Map.entry(25, 263.1), Map.entry(30, 264.1), Map.entry(40, 265.1),
            Map.entry(50, 266.1), Map.entry(60, 267.1), Map.entry(70, 268.1), Map.entry(80, 269.1), Map.entry(90, 270.1),
            Map.entry(100, 271.1), Map.entry(120, 272.1), Map.entry(140, 273.1), Map.entry(160, 274.1),
            Map.entry(180, 275.1), Map.entry(200, 276.1), Map.entry(250, 277.1), Map.entry(300, 278.1),
            Map.entry(350, 279.1), Map.entry(400, 280.1), Map.entry(500, 281.1), Map.entry(600, 282.1),
            Map.entry(700, 283.1), Map.entry(800, 284.1), Map.entry(900, 285.1), Map.entry(1000, 286.1)
    );

    public final Map<Integer, Double> shamanTitleList = Map.ofEntries(
            Map.entry(10, 1.1), Map.entry(100, 2.1), Map.entry(1000, 3.1), Map.entry(2000, 4.1),
            Map.entry(3000, 13.1), Map.entry(4000, 14.1), Map.entry(5000, 15.1), Map.entry(6000, 16.1),
            Map.entry(7000, 17.1), Map.entry(8000, 18.1), Map.entry(9000, 19.1), Map.entry(10000, 20.1),
            Map.entry(11000, 21.1), Map.entry(12000, 22.1), Map.entry(13000, 23.1), Map.entry(14000, 24.1),
            Map.entry(15000, 25.1), Map.entry(16000, 94.1), Map.entry(18000, 95.1), Map.entry(20000, 96.1),
            Map.entry(22000, 97.1), Map.entry(24000, 98.1), Map.entry(26000, 99.1), Map.entry(28000, 100.1),
            Map.entry(30000, 101.1), Map.entry(35000, 102.1), Map.entry(40000, 103.1), Map.entry(45000, 104.1),
            Map.entry(50000, 105.1), Map.entry(55000, 106.1), Map.entry(60000, 107.1), Map.entry(65000, 108.1),
            Map.entry(70000, 109.1), Map.entry(75000, 110.1), Map.entry(80000, 111.1), Map.entry(85000, 112.1),
            Map.entry(90000, 113.1), Map.entry(100000, 200.1), Map.entry(140000, 114.1)
    );

    public final Map<Integer, Double> hardModeTitleList = Map.ofEntries(
            Map.entry(500, 213.1), Map.entry(2000, 214.1), Map.entry(4000, 215.1), Map.entry(7000, 216.1),
            Map.entry(10000, 217.1), Map.entry(14000, 218.1), Map.entry(18000, 219.1), Map.entry(22000, 220.1),
            Map.entry(26000, 221.1), Map.entry(30000, 222.1), Map.entry(40000, 223.1)
    );

    public final Map<Integer, Double> divineModeTitleList = Map.ofEntries(
            Map.entry(500, 324.1), Map.entry(2000, 325.1), Map.entry(4000, 326.1), Map.entry(7000, 327.1),
            Map.entry(10000, 328.1), Map.entry(14000, 329.1), Map.entry(18000, 330.1), Map.entry(22000, 331.1),
            Map.entry(26000, 332.1), Map.entry(30000, 333.1), Map.entry(40000, 334.1)
    );

    public final Map<Integer, Double> shamanTitleListNoSkills = Map.ofEntries(
            Map.entry(500, 594.1), Map.entry(2000, 538.1), Map.entry(10000, 593.1), Map.entry(20000, 592.1),
            Map.entry(40000, 591.1), Map.entry(80000, 590.1)
    );

    public final Map<Integer, Double> hardModeTitleListNoSkills = Map.ofEntries(
            Map.entry(1000, 588.1), Map.entry(3000, 587.1), Map.entry(5000, 586.1), Map.entry(10000, 585.1),
            Map.entry(20000, 584.1)
    );

    public final Map<Integer, Double> divineModeTitleListNoSkills = Map.ofEntries(
            Map.entry(1000, 582.1), Map.entry(3000, 581.1), Map.entry(5000, 580.1), Map.entry(10000, 579.1),
            Map.entry(20000, 576.1)
    );

    public final Map<Integer, Double> shopTitleList = Map.ofEntries(
            Map.entry(1, 115.1), Map.entry(2, 116.1), Map.entry(4, 117.1), Map.entry(6, 118.1), Map.entry(8, 119.1),
            Map.entry(10, 120.1), Map.entry(12, 121.1), Map.entry(14, 122.1), Map.entry(16, 123.1), Map.entry(18, 124.1),
            Map.entry(20, 125.1), Map.entry(22, 126.1)
    );

    /**
     * Creates a new instance of the server.
     */
    public Server() {
        this.packetHandler = new PacketHandler(RecvPacket.class);
        this.commandHandler = new CommandLoader();
        this.players = new Object2ObjectOpenHashMap<>();
        this.rooms = new Object2ObjectOpenHashMap<>();
        this.gameReports = new Object2ObjectOpenHashMap<>();
        this.tempBlackList = new ArrayList<>();
        this.minigameList = new ArrayList<>();
        this.shopGifts = new Int2ObjectOpenHashMap<>();
        this.chats = new Object2ObjectOpenHashMap<>();
        this.whisperMessages = new Object2ObjectOpenHashMap<>();
        this.chatMessages = new Object2ObjectOpenHashMap<>();
        this.sonarPlayerMovement = new HashMap<>();

        // Cache
        this.cachedAccounts = new Object2ObjectOpenHashMap<>();
        this.cachedTribes = new Object2ObjectOpenHashMap<>();
        this.cachedSanctions = new Object2ObjectOpenHashMap<>();

        // Timers
        this.deleteModopwetReportTimer = new Timer(true, 24);
        this.createCafeTopicTimer = new HashMap<>();
        this.createCafePostTimer = new HashMap<>();
        this.createAccountTimer = new HashMap<>();
        this.changeDailyQuestTimer = new HashMap<>();
        this.canChangeDailyQuestTimer = new HashMap<>();

        // Variables
        this.lastGiftID = 0;

        this.loadVanillaMapDB();
        this.loadSpecialMapsDB();
    }

    /**
     * Checks if the given room name exists.
     *
     * @param roomName The room name.
     * @return True if exist or else False.
     */
    public boolean checkExistingRoom(String roomName) {
        return this.rooms.containsKey(roomName);
    }

    /**
     * Checks if the given player is connected in the game.
     *
     * @param playerName The given player's name.
     * @return True if he is connected or else false.
     */
    public boolean checkIsConnected(String playerName) {
        return this.players.containsKey(playerName);
    }

    /**
     * Gets the latest sanction of the given player.
     *
     * @param playerName Player's name.
     * @param sanctionType Punishment type.
     * @return A pair of punishment duration and punishment reason.
     */
    public Sanction getLatestSanction(String playerName, String sanctionType) {
        Pair<String, String> myPair = new Pair<>(playerName, sanctionType);
        if(this.cachedSanctions.get(myPair) != null) {
            Sanction mySanction = this.cachedSanctions.get(myPair);
            if(mySanction.getType().equals(sanctionType) && mySanction.getState().equals("Active")) {
                long time = mySanction.getExpirationDate();
                long currentTime = getUnixTime();
                if(mySanction.getIsPermanent() || time > currentTime) {
                    return mySanction;
                } else {
                    if(sanctionType.startsWith("ban") && this.players.containsKey(playerName)) {
                        this.players.get(playerName).isTaxed = true;
                    }
                    mySanction.setState("Expired");
                    mySanction.save();
                    return null;
                }
            }
        }

        Sanction mySanction = DBUtils.findLatestSanction(playerName, sanctionType);
        if(mySanction != null) {
            this.cachedSanctions.put(myPair, mySanction);
            return this.getLatestSanction(playerName, sanctionType);
        }

        return null;
    }

    /**
     * Gets the account object from the given player name.
     *
     * @param playerName The player name.
     * @return An account object.
     */
    public Account getPlayerAccount(String playerName) {
        if (this.players.containsKey(playerName)) {
            if(this.players.get(playerName).isGuest()) return null;
            return this.players.get(playerName).getAccount();
        }

        if (this.cachedAccounts.containsKey(playerName)) {
            return this.cachedAccounts.get(playerName);
        }

        return DBUtils.findAccountByNickname(playerName);
    }

    /**
     * Gets the player from given session id.
     * @param sessionId The session id.
     * @return The session id.
     */
    public Client getPlayerBySessionId(int sessionId) {
        for(Client player : this.players.values()) {
            if(sessionId == player.getSessionId()) return player;
        }

        return null;
    }

    /**
     * Checks if given room has enough players and recommends a new one if there are many players.
     *
     * @param roomName  The player's room name.
     * @return The room that has enough players.
     */
    public String getRecommendedRoom(String roomName) {
        if (roomName.isEmpty()) roomName = "1";

        String baseKey = roomName.replaceAll("\\d+$", "");
        String numberPart = roomName.replaceAll("\\D", "");
        int number = numberPart.isEmpty() ? 1 : Integer.parseInt(numberPart);

        if (numberPart.isEmpty()) {
            roomName = baseKey + "1";
        }

        String result = roomName;
        while (rooms.containsKey(result)) {
            Room room = rooms.get(result);
            if (room.getMaximumPlayers() > 0 && room.getPlayersCount() >= room.getMaximumPlayers()) {
                result = baseKey + (++number);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Gets the all rooms that are in the given community or international rooms and are in the same game mode.
     * @param mode The game mode.
     * @param commu The community.
     * @return A list of rooms.
     */
    public List<Room> getRoomsByGameMode(int mode, String commu) {
        List<Room> rooms = new ArrayList<>();
        for (Room room : this.rooms.values()) {
            if(room.isTribeHouse() || room.isEditeur() || room.isTotem() || !room.getRoomPassword().isEmpty()) continue;
            if(room.getRoomCommunity().equals(commu) || room.getRoomCommunity().equals("int")) {
                if((mode == 1 ? room.isNormal() : mode == 3 ? room.isVanilla() : mode == 8 ? room.isSurvivor() : mode == 9 ? room.isRacing() : mode == 11 ? room.isMusic() : mode == 2 ? room.isBootcamp() : mode == 10 ? room.isDefilante() : mode == 16 ? room.isVillage() : mode == 18 && room.isMinigame())) {
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }

    /**
     * Gets the tribe object by given tribe name.
     *
     * @param tribeName The given tribe name.
     * @return A tribe object.
     */
    public Tribe getTribeByName(String tribeName) {
        if (this.cachedTribes.containsKey(tribeName)) {
            return this.cachedTribes.get(tribeName);
        }

        Tribe myTribe = DBUtils.findTribeByName(tribeName);
        if (myTribe == null) return null;

        this.cachedTribes.put(tribeName, myTribe);
        return myTribe;
    }

    /**
     * Gets the number of all rooms in the game.
     *
     * @return Room count.
     */
    public int getRoomsCount() {
        return this.rooms.size();
    }

    /**
     * Gets the number of all players in the game.
     *
     * @return Player count.
     */
    public int getPlayersCount() {
        return this.players.size();
    }

    /**
     * Disconnects every player that have the given ip address.
     * @param ip The given IP address.
     */
    public void disconnectIPAddress(String ip, Client target) {
        for (Client player : new ArrayList<>(this.players.values())) {
            if (player.getIpAddress().equals(ip) && player != target) {
                player.closeConnection();
            }
        }
    }

    /**
     * Initializes the server.
     */
    public void startServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!this.isClosed) {
                this.closeServer();
            }
        }));

        // load modopwet reports.
        List<Report> reports = DBUtils.findAllReports();
        for(Report report : reports) {
            this.gameReports.put(report.getPlayerName(), report);
        }

        // Load the minigames
        try (Stream<Path> stream = Files.walk(Path.of("./lua/minigames"))) {
            stream.filter(Files::isRegularFile).filter(path -> path.toString().toLowerCase().endsWith(".lua")).forEach(path -> this.minigameList.add(path.getFileName().toString().replace(".lua", "")));
        } catch (IOException ignored) {

        }

        if (!Application.getPropertiesInfo().is_debug) {
            Application.getLogger().info(String.format("Authorization Key: %s", Application.getSwfInfo().authorization_key));
            Application.getLogger().info(String.format("Connection Key: %s", Application.getSwfInfo().connection_key));
            Application.getLogger().info(String.format("Login Keys: %s", Application.getSwfInfo().login_keys));
            Application.getLogger().info(String.format("Packet Keys: %s", Application.getSwfInfo().packet_keys));
            Application.getLogger().info(String.format("Game Version: %d", Application.getSwfInfo().version));
            Application.getLogger().info(String.format("Game Ports: %s", Application.getSwfInfo().ports));
            if (!Application.getSwfInfo().swf_url.isEmpty()) {
                Application.getLogger().info(String.format("Game URL: %s", Application.getSwfInfo().swf_url));
            }
        }

        if (!Application.getSwfInfo().ports.isEmpty()) {
            ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
            ChannelPipeline pipeline = bootstrap.getPipeline();
            pipeline.addLast("encoder", new Encoder());
            pipeline.addLast("decoder", new Decoder());
            pipeline.addLast("handler", new ClientHandler(this));

            for (Integer port : Application.getSwfInfo().ports) {
                this.channels.add(bootstrap.bind(new InetSocketAddress(port)));
            }
        } else {
            Application.getLogger().error(Application.getTranslationManager().get("startfailure"));
        }
    }

    /**
     * Shutdowns the server.
     */
    public void closeServer() {
        for (Timer timer : new Timer[] {this.deleteModopwetReportTimer}) {
            if (timer != null) {
                timer.cancel();
            }
        }

        for (Timer timer : createCafeTopicTimer.values()) {
            if (timer != null) {
                timer.cancel();
            }
        }
        for (Timer timer : createCafePostTimer.values()) {
            if (timer != null) {
                timer.cancel();
            }
        }
        for (Timer timer : createAccountTimer.values()) {
            if (timer != null) {
                timer.cancel();
            }
        }
        for (Timer timer : changeDailyQuestTimer.values()) {
            if (timer != null) {
                timer.cancel();
            }
        }
        for (Timer timer : canChangeDailyQuestTimer.values()) {
            if (timer != null) {
                timer.cancel();
            }
        }

        for (Client player : this.players.values()) {
            player.saveDatabase();
        }

        for(var reports : this.getGameReports().values()) {
            reports.save();
        }

        for (Channel channel : this.channels) {
            channel.unbind();
        }

        this.isClosed = true;
        System.exit(0);
    }

    /**
     * Sends a message in #Server channel.
     *
     * @param message The message to send.
     * @param isTab   Is sent in general chat instead of #Server.
     * @param other   Send to everyone except the given player.
     */
    public void sendServerMessage(String message, boolean isTab, Client other) {
        for (Client client : this.players.values()) {
            if (client.getAccount().getPrivLevel() >= 9) {
                if (other != null) {
                    if (client != other) {
                        client.sendPacket(new C_ServerMessage(isTab, message));
                    }
                } else {
                    client.sendPacket(new C_ServerMessage(isTab, message));
                }
            }
        }
    }

    /**
     * Sends restart message in every player.
     */
    public void sendServerRestart(int type, int sec) {
        if (sec > 0 || type != 5) {
            this.sendServerRestartMessage(type == 0 ? 120 : type == 1 ? 60 : type == 2 ? 30 : type == 3 ? 20 : type == 4 ? 10 : sec);
            if (this.rebootTimer != null) this.rebootTimer.cancel();
            this.rebootTimer = new Timer();
            this.rebootTimer.schedule(() -> this.sendServerRestart(type == 5 ? type : type + 1, type == 4 ? 9 : type == 5 ? sec - 1 : 0), type == 0 ? 60 : type == 1 ? 30 : type == 2 || type == 3 ? 10 : 1, TimeUnit.SECONDS);
        }

        if (sec == 0 && type == 5) {
            this.closeServer();
        }
    }

    /**
     * Sends a message in the staff channel.
     *
     * @param channelId    The staff channel id.
     * @param rawMessage   The message to send.
     * @param isInt        Send the message in every staff player (Exceptions are #Modo and #Arbitre).
     * @param clientName   Sender's nickname.
     * @param clientLangue Sender's community.
     */
    public void sendStaffChannelMessage(int channelId, String rawMessage, boolean isInt, String clientName, String clientLangue) {
        for (Client client : this.players.values()) {
            if (channelId > 1 && channelId < 6 && client.hasStaffPermission("Modo", "StaffChannel")) {
                if (isInt) {
                    client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
                } else if (client.playerCommunity.equals(clientLangue)) {
                    client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
                }
            } else if (channelId == 9 && client.hasStaffPermission("FunCorp", "StaffChannel")) {
                client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
            } else if (channelId == 8 && client.hasStaffPermission("MapCrew", "StaffChannel")) {
                client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
            } else if (channelId == 7 && client.hasStaffPermission("LuaDev", "StaffChannel")) {
                client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
            } else if (channelId == 10 && client.hasStaffPermission("FashionSquad", "StaffChannel")) {
                client.sendPacket(new C_StaffChannelMessage(channelId, clientName, rawMessage));
            }
        }
    }

    /**
     * Sends a staff notification when a member connect or disconnect from the game.
     * @param message The message to sent (connection/disconnectio).
     * @param author The staff client.
     */
    public void sendStaffLoginMessage(String message, Client author) {
        int privLevel = author.getAccount().getPrivLevel();
        if(author.getAccount().getPrivLevel() < 5) return;

        List<String> staffInfo = new ArrayList<>();
        int channelId = (privLevel == 5 ? 9 : (privLevel == 6) ? 7 : (privLevel == 7) ? 10 : (privLevel == 8) ? 8 : 4);
        for (Client client : this.players.values()) {
            if((client.getAccount().getPrivLevel() == privLevel)) {
                client.sendPacket(new C_StaffChannelMessage(channelId, message, author.playerCommunity, author.getPlayerName()));
                staffInfo.add(client.playerCommunity + "_" + client.getPlayerName() + "_" + client.playerCommunity + '-' + client.getRoomName());
            }
        }

        if(message.equals("$Connexion_Ami"))
            author.sendPacket(new C_OnlineStaffTeam(channelId, staffInfo));
    }

    /**
     * Saves a new loginlog every time a player login.
     *
     * @param playerName The player name.
     * @param ipAddress  The player's IP Address.
     * @param ipCountry  The player's country.
     * @param langue     The player's community.
     */
    public void recordLoginLog(String playerName, String ipAddress, String ipCountry, String langue) {
        new Loginlog(playerName, getUnixTime(), ipAddress, ipCountry, langue);
    }

    /**
     * Adds player to the given room.
     *
     * @param player   The player.
     * @param roomName The room name.
     */
    public void addClientToRoom(Client player, String roomName, Room.RoomDetails roomDetails) {
        if (this.rooms.containsKey(roomName)) {
            this.rooms.get(roomName).addPlayer(player);
        } else {
            Room room = new Room(this, roomName, player.getPlayerName(), roomDetails);
            this.rooms.put(roomName, room);
            room.addPlayer(player);
        }
    }

    /**
     * Loads the vanilla xml database.
     */
    private void loadVanillaMapDB() {
        try (Stream<Path> stream = Files.walk(Paths.get("config/maps/vanilla"))) {
            Server.vanillaMapXmlList = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".xml"))
                    .collect(Collectors.toMap(
                            path -> {
                                String fileName = path.getFileName().toString();
                                String numberPart = fileName.substring(0, fileName.lastIndexOf('.'));
                                return Integer.parseInt(numberPart);
                            },
                            path -> {
                                try {
                                    return Files.readString(path);
                                } catch (IOException e) {
                                    return "";
                                }
                            }
                    ));
        } catch (IOException ignored) {
        }

        Application.getLogger().info(Application.getTranslationManager().get("loadedvanillamaps", Server.vanillaMapXmlList.size()));
    }

    /**
     * Loads the vanilla xml database.
     */
    private void loadSpecialMapsDB() {
        try (Stream<Path> stream = Files.walk(Paths.get("config/maps/special"))) {
            Server.specialMapXmlList = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".xml"))
                    .collect(Collectors.toMap(
                            path -> {
                                String fileName = path.getFileName().toString();
                                String numberPart = fileName.substring(0, fileName.lastIndexOf('.'));
                                return Integer.parseInt(numberPart);
                            },
                            path -> {
                                try {
                                    return Files.readString(path);
                                } catch (IOException e) {
                                    return "";
                                }
                            }
                    ));
        } catch (IOException ignored) {
        }
    }

    /**
     * Sends a message when server is going to restart.
     * @param seconds The seconds to write in the message.
     */
    private void sendServerRestartMessage(int seconds) {
        for (Client player : this.players.values()) {
            player.sendPacket(new C_ServerRestart(seconds));
        }
    }

    /**
     * Creates a new client session.
     * @param channel The network channel associated with the client.
     */
    public void addClientSession(final Channel channel) {
        Client client = new Client(this, channel);
        this.clientSessions.put(channel.getId(), client);
        channel.setAttachment(client);
    }

    /**
     * Removes the client session.
     * @param channel The network channel associated with the client.
     */
    public void removeClientSession(final Channel channel) {
        this.clientSessions.remove(channel.getId());
    }
}
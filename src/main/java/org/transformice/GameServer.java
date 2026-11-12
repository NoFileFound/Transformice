package org.transformice;

// Imports
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.fusesource.jansi.AnsiConsole;
import org.psframework.admin.PSAdminConfig;
import org.psframework.database.PSDatabaseConfig;
import org.psframework.game.PSGameServer;
import org.psframework.logging.PSLogger;
import org.psframework.network.PSPacketHandler;
import org.psframework.network.PSSendPacket;
import org.psframework.utils.PSGeoIP;
import org.psframework.utils.PSJson;
import org.psframework.utils.PSRandom;
import org.psframework.utils.PSSMTP;
import org.psframework.utils.PSTimer;
import org.transformice.command.CommandLoader;
import org.transformice.config.TransformiceConfig;
import org.transformice.config.VillageNPCConfig;
import org.transformice.config.inventory.ConsumableConfig;
import org.transformice.config.shop.ShopConfig;
import org.transformice.config.titleconfig.TitleConfig;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.GameDatabaseManager;
import org.transformice.database.GameDatabaseManagerFactory;
import org.transformice.database.data.IpTempBanEntry;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.SanctionEntry;
import org.transformice.modules.ModuleCafe;
import org.transformice.modules.ModuleDelichoc;
import org.transformice.modules.ModuleEvents;
import org.transformice.modules.ModuleLegacy;
import org.transformice.modules.ModuleModopwet;
import org.transformice.modules.ModuleShop;
import org.transformice.network.*;
import org.transformice.utils.PlayerQueue;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.chat.PacketSendStaffChatMessage;
import org.transformice.packets.send.informations.PacketSendServerRestart;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

public final class GameServer extends PSGameServer {
    private static final char[] FILTER_CHARS = "!@#$%&*?".toCharArray();
    private final TransformiceConfig tfmConfig;
    private Map<Integer, ConsumableConfig> inventoryConfig;
    private Map<String, VillageNPCConfig> villageNPCConfig;
    private TitleConfig titlesConfig;
    private ShopConfig shopConfig;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final List<Channel> channels;
    private final PSPacketHandler generalPacketHandler;
    private final Map<ChannelId, NetSession> clientSessions;
    private final Map<String, GamePlayer> players;
    private final Map<String, GameRoom> rooms;
    private final Map<String, List<String>> chats;
    private final Map<String, PSTimer> accountCreationTimer;
    private final Map<String, PSTimer> marriageTimers;
    private final Map<String, PSTimer> redistributionSkillTimer;
    private final Map<String, PSTimer> dailyQuestGenerateTimer;
    private final Map<String, PSTimer> dailyQuestChangeTimer;
    private final Map<String, Map<String, PSTimer>> tempRestrictedRoomsTimer;
    private final Map<String, Map<String, Deque<String[]>>> chatMessages;
    private final Map<String, Map<String, Deque<String[]>>> whisperMessages;
    private final Map<String, List<String>> sonarPlayerMovement;
    private final Set<String> maximumPlayerCount;
    private final Pattern chatFilterPattern;
    private final PlayerQueue playerQueue;
    private final CommandLoader commandManager;
    private final ModuleDelichoc parseDelichoc;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final ModuleCafe parseCafe;
    private final ModuleEvents parseEvents;
    private final ModuleModopwet parseModopwet;
    private final ModuleLegacy parseLegacyPacketHandler;
    private final ModuleShop parseShop;
    private final PSSMTP pssmtp;
    private GameDatabaseManager databaseManager;
    private PSGeoIP geoIP;
    private PSTimer rebootTimer;

    // Vanilla maps
    private final Map<Integer, byte[]> vanillaMaps;
    private final Map<Integer, byte[]> specialMaps;
    public static final Set<Integer> VANILLA_MAP_LIST = IntStream.rangeClosed(0, 245).filter(i -> i != 135 && i != 169 && (i < 193 || i > 199)).boxed().collect(Collectors.toUnmodifiableSet());
    public static final Set<Integer> VANILLA_NO_SHAMAN_MAPS = Set.of(8, 10, 14, 22, 23, 28, 29, 33, 42, 55, 57, 58, 61, 70, 77, 78, 87, 88, 122, 123, 124, 125, 126, 148, 149, 150, 151, 172, 173, 174, 175, 178, 179, 180, 188, 189, 190, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 218, 219, 220, 221, 222, 224, 225, 231, 232, 233, 234, 241, 242, 243, 244, 245);

    /**
     * Creates a new game server.
     * @param gamePorts The game server's ports.
     * @param databaseConfig The database's configuration.
     * @param adminConfig The admin panel's configuration.
     * @param logger The game server's logger.
     */
    public GameServer(ArrayList<Integer> gamePorts, PSDatabaseConfig databaseConfig, PSAdminConfig adminConfig, PSLogger logger, TransformiceConfig tfmConfig) {
        super(gamePorts, databaseConfig, adminConfig, logger);
        this.tfmConfig = tfmConfig;
        this.inventoryConfig = new HashMap<>();
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        this.channels = new ArrayList<>();
        this.generalPacketHandler = new PSPacketHandler("org.transformice.packets.recv");
        this.clientSessions = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.rooms = new ConcurrentHashMap<>();
        this.chats = new HashMap<>();
        this.playerQueue = new PlayerQueue(this.tfmConfig.getGameConfig().getMaxPlayers());
        this.chatMessages = new HashMap<>();
        this.whisperMessages = new HashMap<>();
        this.chatFilterPattern = Pattern.compile("(?iu)(" + this.tfmConfig.getGameConfig().getChatFilter().stream().map(word -> word.codePoints().mapToObj(cp -> Pattern.quote(new String(Character.toChars(cp)))).collect(Collectors.joining("[\\p{P}\\p{S}]*"))).collect(Collectors.joining("|")) + ")");
        this.vanillaMaps = new HashMap<>();
        this.specialMaps = new HashMap<>();
        this.maximumPlayerCount = new HashSet<>();
        if (this.tfmConfig.getSMTPConfig().getHost().isEmpty())
            this.pssmtp = null;
        else
            this.pssmtp = new PSSMTP(this.tfmConfig.getSMTPConfig().getHost(), this.tfmConfig.getSMTPConfig().getPort(), this.tfmConfig.getSMTPConfig().getUsername(), this.tfmConfig.getSMTPConfig().getPassword(), this.tfmConfig.getSMTPConfig().getUsername(), true, false);
        this.sonarPlayerMovement = new HashMap<>();

        // Instances
        this.commandManager = new CommandLoader(this);
        this.parseCafe = new ModuleCafe(this);
        this.parseEvents = new ModuleEvents(this);
        this.parseModopwet = new ModuleModopwet(this);
        this.parseLegacyPacketHandler = new ModuleLegacy(this);
        this.parseShop = new ModuleShop(this);
        this.parseDelichoc = new ModuleDelichoc(this);

        // Timers
        this.accountCreationTimer = new ConcurrentHashMap<>();
        this.marriageTimers = new ConcurrentHashMap<>();
        this.redistributionSkillTimer = new HashMap<>();
        this.tempRestrictedRoomsTimer = new ConcurrentHashMap<>();
        this.dailyQuestGenerateTimer = new HashMap<>();
        this.dailyQuestChangeTimer = new HashMap<>();
    }

    /**
     * Loads the database in the server.
     */
    @Override
    public void loadDatabase() {
        try {
            this.geoIP = new PSGeoIP(Path.of("./config/GeoIP.dat"));
            this.logger.debug("[GeoIP] GeoIP Loaded successfully.");
            this.database.connect();
            this.logger.info(String.format("[Database] Successfully connected at the database: %s", this.database.getConnectionUrl()));
            this.databaseManager = GameDatabaseManagerFactory.create(this.database, this.tfmConfig.getIsDebug());
            this.databaseManager.initialize();
            this.logger.info("[Database] Main Database initialized.");
            for (Path directory : List.of(Path.of("config/vanilla_maps"), Path.of("config/special_maps"))) {
                try (Stream<Path> files = Files.list(directory)) {
                    files.filter(path -> path.toString().endsWith(".xml"))
                            .forEach(path -> {
                                try {
                                    int mapCode = Integer.parseInt(path.getFileName().toString().replaceFirst("\\.xml$", ""));
                                    Map<Integer, byte[]> maps = directory.endsWith("vanilla_maps") ? this.vanillaMaps : this.specialMaps;
                                    maps.put(mapCode, Files.readAllBytes(path));
                                } catch (IOException e) {
                                    throw new RuntimeException("Failed to load map: " + path, e);
                                }
                            });
                }
            }
            this.logger.info("[Database] Vanilla maps loaded successfully.");
            this.generalPacketHandler.registerPackets();
            this.logger.debug("[PacketHandler] Registered successfully.");
            this.parseModopwet.loadDatabase();
            if (this.tfmConfig.getIsDebug())
                this.logger.warn("[Debug] The server is in debug-mode. [All players from now on will have admin rights.]");

            this.inventoryConfig = PSJson.readFile(new File("./config/inventory.json"), new TypeReference<>() {});
            this.villageNPCConfig = PSJson.readFile(new File("./config/village_npcs.json"), new TypeReference<>() {});
            this.titlesConfig = PSJson.readFile(new File("./config/titles.json"), new TypeReference<>() {});
            this.logger.info("[Inventory] Loaded the inventory resources.");
            this.shopConfig = PSJson.readFile(new File("./config/shop.json"), new TypeReference<>() {});
            this.logger.info("[Shop] Loaded total {} shop banners.", this.shopConfig.getShopBanners().size());
            this.logger.info("[Shop] Loaded total {} shop emoji.", this.shopConfig.getShopEmoji().size());
            this.logger.info("[Shop] Loaded total {} shop regular items.", this.shopConfig.getShopItems().size());
            this.logger.info("[Shop] Loaded total {} shop shaman items.", this.shopConfig.getShopShamanItems().size());
            this.logger.info("[Shop] Loaded total {} shop promotions.", this.tfmConfig.getGameConfig().getShopPromotions().size());
            this.logger.info("[Shop] Loaded total {} shop outfits.", this.tfmConfig.getGameConfig().getShopOutfits().size());
        } catch (Exception ex) {
            this.logger.error("Unable to initialize the game server. It is not safe to continue.", ex);
            System.exit(1);
        }
    }

    /**
     * Saves the database.
     */
    @Override
    public void saveDatabase() {
        // Saves the player data.
        for (GamePlayer player : this.players.values()) {
            try {
                if (player.getIsGuest())
                    continue;

                this.databaseManager.savePlayer(player.getPlayerData());
            } catch (Exception ex) {
                this.logger.error(String.format("[Database] Failed to save player %s.", player.getPlayerName()), ex);
            }
        }

        // saves the modopwet reports.
        this.parseModopwet.saveDatabase();

        // deletes the logs as in the original game.
        this.databaseManager.deleteAllCommandLogs();
        this.databaseManager.deleteAllLoginLogs();


        this.database.disconnect();
    }

    /**
     * Starts the game server.
     */
    @Override
    public void start() {
        AnsiConsole.systemInstall();
        this.loadDatabase();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("decoder", new NetDecoder());
                            pipeline.addLast("handler", new NetHandler(GameServer.this));
                        }
                    });

            for (Integer port : this.ports) {
                Channel channel = bootstrap.bind(new InetSocketAddress(port)).sync().channel();
                this.channels.add(channel);
            }

            this.logger.info(String.format("[GameServer] The server is listening on ports: %s", this.ports));
            this.logger.info("The server is started.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the game server.
     */
    @Override
    public void stop() {
        this.logger.info("[GameServer] Shutting down the game server.");
        for (Channel channel : this.channels)
            channel.close();

        for (Channel channel : this.channels)
            channel.closeFuture().syncUninterruptibly();

        this.saveDatabase();
        this.bossGroup.shutdownGracefully().syncUninterruptibly();
        this.workerGroup.shutdownGracefully().syncUninterruptibly();
        this.logger.info("[GameServer] Shutdown completed.");
        AnsiConsole.systemUninstall();
    }

    /**
     * Sends a packet to everyone in the server.
     * @param packet The packet to send.
     */
    public void broadcastPacket(PSSendPacket packet) {
        for (GamePlayer player : this.players.values())
            player.broadcastPacket(packet);
    }

    /**
     * Gets the account creation timer.
     * @return The account creation timer.
     */
    public PSTimer getAccountCreationTimer(String ipAddress) {
        return this.accountCreationTimer.computeIfAbsent(ipAddress, _ -> new PSTimer(this.scheduler, 24, TimeUnit.HOURS));
    }

    /**
     * Gets the cafe's module.
     * @return The cafe's module.
     */
    public ModuleCafe getParseCafe() {
        return this.parseCafe;
    }

    /**
     * Gets the events's module.
     * @return The events's module.
     */
    public ModuleEvents getParseEvents() {
        return this.parseEvents;
    }

    /**
     * Gets the modopwet's instance.
     * @return The modopwet's instance.
     */
    public ModuleModopwet getParseModopwet() {
        return this.parseModopwet;
    }

    /**
     * Gets the shop's instance.
     * @return The shop's instance.
     */
    public ModuleShop getParseShop() {
        return this.parseShop;
    }

    /**
     * Gets the delichoc's instance.
     * @return The delichoc's instance.
     */
    public ModuleDelichoc getParseDelichoc() {
        return this.parseDelichoc;
    }

    /**
     * Gets the global chats.
     * @return The global chats.
     */
    public Map<String, List<String>> getChats() {
        return this.chats;
    }

    /**
     * Gets the list of connected sessions to the server.
     * @return The list of connected sessions.
     */
    public Map<ChannelId, NetSession> getClientSessions() {
        return this.clientSessions;
    }

    /**
     * Gets the command handler.
     * @return The command handler.
     */
    public CommandLoader getCommandManager() {
        return this.commandManager;
    }

    /**
     * Gets the server config.
     * @return The server config.
     */
    public TransformiceConfig getConfig() {
        return this.tfmConfig;
    }

    /**
     * Gets the Database instance.
     * @return The Database instance.
     */
    public GameDatabaseManager getDatabase() {
        return this.databaseManager;
    }

    /**
     * Gets the GeoIP instance.
     * @return The GeoIP instance.
     */
    public PSGeoIP getGeoIP() {
        return this.geoIP;
    }

    /**
     * Gets the maximumPlayerCount.
     * @return The maximumPlayerCount.
     */
    public Set<String> getMaximumPlayerCount() {
        return this.maximumPlayerCount;
    }

    /**
     * Checks if the room is official.
     * @param roomName The room's name.
     * @return True if the room is official or else False.
     */
    public boolean getIsOfficialRoom(String roomName) {
        if (roomName == null || roomName.length() < 3)
            return false;

        String rooms = roomName.substring(3);
        String count = rooms.replaceAll("[^0-9]", "");
        boolean found = rooms.matches("[0-9]+");
        for (String room : List.of("vanilla", "survivor", "racing", "music", "bootcamp", "defilante", "village")) {
            if ((rooms.startsWith(room) && !count.isEmpty()) || found) {
                try {
                    long value = Long.parseLong(count);
                    found = value >= 1 && value <= 1000000 && !rooms.equals(room);
                } catch (NumberFormatException exception) {
                    found = false;
                }

                break;
            }
        }

        return found;
    }

    /**
     * Gets the marriage timer based on the player.
     * @param username The player.
     * @return The player's marriage timer.
     */
    public PSTimer getMarriageTimer(String username) {
        return this.marriageTimers.computeIfAbsent(username, _ -> new PSTimer(this.scheduler, 1, TimeUnit.HOURS));
    }

    /**
     * Gets the restricted room timer based on the player.
     * @param username The player.
     * @param roomName The room's name.
     * @return The player's temp restricted room timer.
     */
    public PSTimer getTempRestrictedRoomTimer(String username, String roomName) {
        return this.tempRestrictedRoomsTimer.computeIfAbsent(username, _ -> new ConcurrentHashMap<>()).put(roomName, new PSTimer(this.scheduler, 1, TimeUnit.HOURS));
    }

    /**
     * Gets the next counter by variable name from the database.
     * @param variable The variable.
     * @return The next counter starts from 1.
     */
    public int getNextDatabaseCounterId(String variable) {
        return this.database.getNextCounterId(variable);
    }

    /**
     * Gets the packet handler.
     * @return The packet handler.
     */
    public PSPacketHandler getPacketHandler() {
        return this.generalPacketHandler;
    }

    /**
     * Gets the legacy packet handler.
     * @return The legacy packet handler.
     */
    public ModuleLegacy getLegacyPacketHandler() {
        return this.parseLegacyPacketHandler;
    }

    /**
     * Gets the player queue.
     * @return The player queue.
     */
    public PlayerQueue getPlayerQueue() {
        return this.playerQueue;
    }

    /**
     * Gets the list of players.
     * @return The list of players.
     */
    public Map<String, GamePlayer> getPlayers() {
        return this.players;
    }

    /**
     * Gets the player from given session id.
     * @param sessionId The session id.
     * @return The session id.
     */
    public GamePlayer getPlayerBySessionId(int sessionId) {
        return this.players.values().stream().filter(player -> player.getSessionId() == sessionId).findFirst().orElse(null);
    }

    /**
     * Checks if given room has enough players and recommends a new one if there are many players.
     *
     * @param roomName  The player's room name.
     * @return The room that has enough players.
     */
    public String getRecommendedRoom(String roomName, String community) {
        if (roomName.isEmpty())
            roomName = community + "-1";

        String roomKey = community + "-" + roomName;
        GameRoom room = this.rooms.get(roomKey);
        if (room == null || room.getMaximumPlayers() == 0 || room.getPlayers().size() < room.getMaximumPlayers())
            return roomName;

        int x = 0;
        while (true) {
            x++;
            room = this.rooms.get(roomKey + x);
            if (room == null || room.getMaximumPlayers() == 0 || room.getPlayers().size() < room.getMaximumPlayers())
                return roomName + x;
        }
    }

    /**
     * Gets the list of rooms.
     * @return The list of rooms.
     */
    public Map<String, GameRoom> getRooms() {
        return this.rooms;
    }

    /**
     * Gets the all rooms that are in the given community or international rooms and are in the same game mode.
     * @param mode The game mode.
     * @param community The community.
     * @return A list of rooms.
     */
    public List<GameRoom> getRoomsByGameMode(int mode, String community) {
        community = Utils.getCommunityFromLanguage(community);
        List<GameRoom> roomList = new ArrayList<>();
        RoomType roomType = (mode == 1 ? RoomType.NORMAL : mode == 3 ? RoomType.VANILLA : mode == 8 ? RoomType.SURVIVOR : mode == 9 ? RoomType.RACING : mode == 11 ? RoomType.MUSIC : mode == 2 ? RoomType.BOOTCAMP : mode == 10 ? RoomType.DEFILANTE : mode == 16 ? RoomType.VILLAGE : RoomType.NONE);
        for (GameRoom room : this.rooms.values()) {
            if (room.getRoomCommunity().equals("INT") && room.getRoomType().equals(roomType) && !room.getRoomNameSuffix().startsWith("@") && room.roomPassword.isEmpty()) {
                roomList.add(room);
            }
        }

        for (GameRoom room : this.rooms.values()) {
            if (room.getRoomCommunity().equals(community) && room.getRoomType().equals(roomType) && !room.getRoomNameSuffix().startsWith("@") && room.roomPassword.isEmpty()) {
                roomList.add(room);
            }
        }

        return roomList;
    }

    /**
     * Gets the server's scheduler.
     * @return The server's scheduler.
     */
    public ScheduledExecutorService getScheduler() {
        return this.scheduler;
    }

    /**
     * Gets the server's inventory config.
     * @return The server's inventory config.
     */
    public Map<Integer, ConsumableConfig> getInventoryConfig() {
        return this.inventoryConfig;
    }

    /**
     * Gets the shop's config.
     * @return The shop's config.
     */
    public ShopConfig getShopConfig() {
        return this.shopConfig;
    }

    /**
     * Gets the player's skill redistribution timer.
     * @param username The player's name.
     * @return The player's skill redistribution timer.
     */
    public PSTimer getSkillRedistributionTimer(String username) {
        return this.redistributionSkillTimer.computeIfAbsent(username, _ -> new PSTimer(this.scheduler, 5, TimeUnit.MINUTES));
    }

    /**
     * Gets the daily quest's generator timer.
     * @param username The player's username.
     * @return The daily quest's generator timer.
     */
    public PSTimer getDailyQuestGenerateTimer(String username) {
        return this.dailyQuestGenerateTimer.computeIfAbsent(username, _ -> new PSTimer(this.scheduler, 24, TimeUnit.HOURS));
    }

    /**
     * Gets the daily quest's change timer.
     * @param username The player's username.
     * @return The daily quest's change timer.
     */
    public PSTimer getDailyQuestChangeTimer(String username) {
        return this.dailyQuestChangeTimer.computeIfAbsent(username, _ -> new PSTimer(this.scheduler, 24, TimeUnit.HOURS));
    }

    /**
     * Gets the server's smtp.
     * @return The server's SMTP.
     */
    public PSSMTP getPssmtp() {
        return this.pssmtp;
    }

    /**
     * Gets the chat messages.
     * @return The chat messages.
     */
    public Map<String, Map<String, Deque<String[]>>> getChatMessages() {
        return this.chatMessages;
    }

    /**
     * Gets the whisper messages.
     * @return The whisper messages.
     */
    public Map<String, Map<String, Deque<String[]>>> getWhisperMessages() {
        return this.whisperMessages;
    }

    /**
     * Gets the village npcs config.
     * @return The village npcs config.
     */
    public Map<String, VillageNPCConfig> getVillageNPCConfig() {
        return this.villageNPCConfig;
    }

    /**
     * Gets the XML map from the special maps.
     * @param mapCode The special map's code.
     * @return The special map's XML.
     */
    public byte[] getSpecialMapXml(int mapCode) {
        return this.specialMaps.getOrDefault(mapCode, null);
    }

    /**
     * Gets the players's movement.
     * @return The players's movement.
     */
    public Map<String, List<String>> getSonarPlayerMovement() {
        return this.sonarPlayerMovement;
    }

    /**
     * Gets the title's config.
     * @return The title's config.
     */
    public TitleConfig getTitleConfig() {
        return this.titlesConfig;
    }

    /**
     * Gets the XML map from the vanilla maps.
     * @param mapCode The vanilla's map code.
     * @return The vanilla's map xml.
     */
    public byte[] getVanillaMapXml(int mapCode) {
        return this.vanillaMaps.getOrDefault(mapCode, new byte[0]);
    }

    /**
     * Removes the bad words from the message.
     * @param message The message.
     * @return The formatted message.
     */
    public String formatText(String message) {
        Matcher matcher = this.chatFilterPattern.matcher(message);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            StringBuilder replacement = new StringBuilder();
            for (int i = 0; i < matcher.group().length(); i++)
                replacement.append(FILTER_CHARS[PSRandom.nextInt(FILTER_CHARS.length)]);

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement.toString()));
        }

        return matcher.appendTail(result).toString();
    }

    /**
     * Bans the player.
     * @param playerName The player's name.
     * @param banHours The ban hours.
     * @param banReason The ban's reason.
     * @param moderatorPlayer The moderator's instance.
     * @param isSilent Is the ban message silent.
     * @return True if the ban was successful or else False.
     */
    public boolean sendPlayerBan(String playerName, int banHours, String banReason, GamePlayer moderatorPlayer, boolean isSilent) {
        if (playerName.equals(moderatorPlayer.getPlayerName()))
            return false;

        if (banHours <= 0) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "Invalid ban duration."));
            return false;
        }

        GamePlayer player = this.players.get(playerName);
        if (player != null && player.getIsGuest()) {
            banHours = Math.max(banHours, 24);
            this.sendIPBan(player.getIpAddress(), banHours, banReason);
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got banned.", playerName)));
            this.sendServerMessageOthers(moderatorPlayer, String.format("%s banned the player %s for %sh (%s).", moderatorPlayer.getPlayerName(), playerName, banHours, banReason), StaffRoleType.PUBLIC_MODO);
            return true;
        }

        PlayerEntity playerEntity = this.getDatabase().loadPlayerByName(playerName);
        if (playerEntity == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return false;
        }

        SanctionEntry lastSanction = this.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_BAN);
        if (lastSanction != null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("Player [%s] is already banned, please wait.", playerName)));
            return false;
        }

        long endDate = Utils.getUnixTime() + (banHours * 3600L);
        SanctionEntry sanction = new SanctionEntry(this.getNextDatabaseCounterId("lastSanctionId"), playerName, player != null ? player.getIpAddress() : "", SanctionEntry.SanctionType.SANCTION_BAN, moderatorPlayer.getPlayerName(), banReason, endDate);
        this.getDatabase().saveSanction(sanction);
        if (player != null) {
            if (!isSilent)
                player.getPlayerRoom().broadcastPacket(player, new PacketSendStaffChatMessage(0, "", "$Message_Ban", new ArrayList<>(List.of(playerName, String.valueOf(banHours), banReason)), true));

            this.sendIPBan(player.getIpAddress(), banHours, banReason);
            player.disconnect(true);
        }

        moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got banned.", playerName)));
        this.sendServerMessageOthers(moderatorPlayer, String.format("%s %s banned the player %s for %sh (%s).", moderatorPlayer.getPlayerName(), (player != null ? "" : "offline"), playerName, banHours, banReason), StaffRoleType.PUBLIC_MODO);
        return true;
    }

    /**
     * Bans an ip address.
     * @param ipAddress The given ip address.
     * @param banHours The ban hours.
     * @param reason The reason.
     */
    public void sendIPBan(String ipAddress, int banHours, String reason) {
        if (this.databaseManager.isIpBlacklisted(ipAddress))
            return;

        this.databaseManager.saveIpTempBan(new IpTempBanEntry(ipAddress, banHours, reason));
    }

    /**
     * Unbans an ip address.
     * @param ipAddress The given ip address.
     * @param player The moderator's instance.
     */
    public void sendIPUnban(String ipAddress, GamePlayer player) {
        var info = this.databaseManager.loadTempIpBanByIp(ipAddress);
        if (info == null) {
            player.broadcastPacket(new PacketSendServerMessage(true, String.format("The provided IP address (%s) is not banned", ipAddress)));
            return;
        }

        this.databaseManager.deleteIpTempBan(info);
    }

    /**
     * Kicks the player.
     * @param playerName The player's name.
     * @param kickReason The kick's reason.
     * @param moderatorPlayer The kick's moderator.
     */
    public void sendPlayerKick(String playerName, String kickReason, GamePlayer moderatorPlayer) {
        if (playerName.equals(moderatorPlayer.getPlayerName()))
            return;

        GamePlayer player = this.players.get(playerName);
        if (player == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The player is not connected."));
            return;
        }

        player.disconnect(true);
        moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got kicked.", playerName)));
        this.sendServerMessageOthers(moderatorPlayer, String.format("%s kicked the player %s (%s).", moderatorPlayer.getPlayerName(), playerName, kickReason), StaffRoleType.PUBLIC_MODO);
    }

    /**
     * Mutes the player.
     * @param playerName The player's name.
     * @param muteHours The mute's duration.
     * @param muteReason The mute's reason.
     * @param moderatorPlayer The mute's moderator.
     * @param isSilent is the mute message silent.
     */
    public void sendPlayerMute(String playerName, int muteHours, String muteReason, GamePlayer moderatorPlayer, boolean isSilent) {
        if (playerName.equals(moderatorPlayer.getPlayerName()))
            return;

        if (muteHours <= 0) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "Invalid mute duration."));
            return;
        }

        GamePlayer player = this.players.get(playerName);
        PlayerEntity playerEntity = this.getDatabase().loadPlayerByName(playerName);
        if (playerEntity == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return;
        }

        SanctionEntry lastSanction = this.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_MUTE);
        if (lastSanction != null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("Player [%s] is already muted, please wait.", playerName)));
            return;
        }

        long endDate = Utils.getUnixTime() + (muteHours * 3600L);
        SanctionEntry sanction = new SanctionEntry(this.getNextDatabaseCounterId("lastSanctionId"), playerName, player != null ? player.getIpAddress() : "", SanctionEntry.SanctionType.SANCTION_MUTE, moderatorPlayer.getPlayerName(), muteReason, endDate);
        this.getDatabase().saveSanction(sanction);
        if (player != null) {
            if (!isSilent)
                player.getPlayerRoom().broadcastPacket(player, new PacketSendTranslatedMessage("", "<ROSE>$MuteInfo2</ROSE>", new String[]{playerName, String.valueOf(muteHours), muteReason}));

            player.broadcastPacket(new PacketSendTranslatedMessage("", "<ROSE>$MuteInfo1</ROSE>", new String[]{String.valueOf(muteHours), muteReason}));
        }

        moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got muted.", playerName)));
        this.sendServerMessageOthers(moderatorPlayer, String.format("%s %s muted the player %s for %sh (%s)", moderatorPlayer.getPlayerName(), (player != null ? "" : "offline"), playerName, muteHours, muteReason), StaffRoleType.PUBLIC_MODO);
    }

    /**
     * Unbans the player.
     * @param playerName The player's name.
     * @param unbanReason The unban reason.
     * @param moderatorPlayer The unban's moderator.
     */
    public void sendPlayerUnban(String playerName, String unbanReason, GamePlayer moderatorPlayer) {
        PlayerEntity playerEntity = this.getDatabase().loadPlayerByName(playerName);
        if (playerEntity == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return;
        }

        SanctionEntry lastSanction = this.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_BAN);
        if (lastSanction == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s is not banned yet.", playerName)));
            return;
        }

        lastSanction.sanctionState = SanctionEntry.SanctionState.CANCELED;
        lastSanction.cancelDate = Utils.getUnixTime();
        lastSanction.cancelAuthor = moderatorPlayer.getPlayerName();
        lastSanction.cancelReason = unbanReason;
        this.getDatabase().saveSanction(lastSanction);
        moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got unbanned.", playerName)));
        this.sendServerMessageOthers(moderatorPlayer, String.format("%s unbanned the player %s (%s).", moderatorPlayer.getPlayerName(), playerName, unbanReason), StaffRoleType.PUBLIC_MODO);
    }

    /**
     * Unmutes the player.
     * @param playerName The player's name.
     * @param unmuteReason The unmute's reason.
     * @param moderatorPlayer The unmute's moderator.
     */
    public void sendPlayerUnmute(String playerName, String unmuteReason, GamePlayer moderatorPlayer) {
        PlayerEntity playerEntity = this.getDatabase().loadPlayerByName(playerName);
        if (playerEntity == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return;
        }

        SanctionEntry lastSanction = this.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_MUTE);
        if (lastSanction == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s is not muted yet.", playerName)));
            return;
        }

        lastSanction.sanctionState = SanctionEntry.SanctionState.CANCELED;
        lastSanction.cancelDate = Utils.getUnixTime();
        lastSanction.cancelAuthor = moderatorPlayer.getPlayerName();
        lastSanction.cancelReason = unmuteReason;
        this.getDatabase().saveSanction(lastSanction);
        moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("The player %s got unmuted.", playerName)));
        this.sendServerMessageOthers(moderatorPlayer, String.format("%s unmuted the player %s (%s).", moderatorPlayer.getPlayerName(), playerName, unmuteReason), StaffRoleType.PUBLIC_MODO);
    }

    /**
     * Sends restart message in every player.
     */
    public synchronized void sendServerRestart(int sec) {
        if (sec <= 0) {
            this.stop();
        }

        this.rebootTimer = new PSTimer(this.getScheduler());
        for (GamePlayer player : this.players.values())
            player.broadcastPacket(new PacketSendServerRestart(sec >= 120 ? 120 : sec >= 60 ? 60 : sec >= 30 ? 30 : sec >= 20 ? 20 : Math.min(sec, 10)));

        this.rebootTimer.schedule(() -> this.sendServerRestart(sec >= 120 ? sec - 60 : sec >= 60 ? sec - 30 : sec >= 30 ? sec - 10 : sec >= 20 ? sec - 10 : sec - 1), sec >= 120 ? 60 : sec >= 60 ? 30 : sec >= 30 ? 10 : sec >= 20 ? 10 : 1, TimeUnit.SECONDS);
    }

    /**
     * Sends a message in the staff channel.
     *
     * @param channelId The staff channel id.
     * @param channelMessage The message to send.
     * @param isInternational Send the message in every staff player (Exceptions are #Modo and #Arbitre).
     * @param playerName Sender's nickname.
     * @param playerCommunity Sender's community.
     */
    public void sendStaffChannelMessage(byte channelId, String channelMessage, boolean isInternational, String playerName, String playerCommunity) {
        GamePlayer sender = this.players.get(playerName);
        if (channelId == 0 || channelId == 6) {
            for (GamePlayer player : sender.getPlayerRoom().getPlayers().values()) {
                if (channelId == 0 || player.getPlayerCommunity().equals(sender.getPlayerCommunity())) {
                    player.broadcastPacket(new PacketSendStaffChatMessage(0, "", channelMessage));
                }
            }

            return;
        }

        if (channelId == 1 || channelId == 11) {
            this.broadcastPacket(new PacketSendStaffChatMessage(channelId, playerName, channelMessage));
            return;
        }

        for (GamePlayer player : this.players.values()) {
            boolean hasPermission = switch (channelId) {
                case 3, 4 -> player.hasStaffPermission(StaffRoleType.TRIAL_MODO, true) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, true) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, true);
                case 2, 5 -> player.hasStaffPermission(StaffRoleType.ARBITRE, true) || player.hasStaffPermission(StaffRoleType.TRIAL_MODO, true) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, true) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, true);
                case 8 -> player.hasStaffPermission(StaffRoleType.LUADEV, true);
                case 7 -> player.hasStaffPermission(StaffRoleType.MAPCREW, true);
                case 9 -> player.hasStaffPermission(StaffRoleType.FUNCORP, true);
                case 10 -> player.hasStaffPermission(StaffRoleType.FASHIONSQUAD, true);
                default -> false;
            };

            if (!hasPermission || channelId <= 5 && !isInternational && !player.getPlayerCommunity().equals(playerCommunity))
                continue;

            player.broadcastPacket(new PacketSendStaffChatMessage(channelId, playerName, channelMessage));
        }
    }

    /**
     * Sends a #Server message to every else in the server.
     * @param player The excluded player.
     * @param message The message to send.
     * @param staffRole The minimum required staff role.
     */
    public void sendServerMessageOthers(GamePlayer player, String message, StaffRoleType staffRole) {
        for (GamePlayer target : this.players.values()) {
            if (target == player)
                continue;

            List<StaffRoleType> targetRoles = target.getPlayerData().getStaffRoles();
            boolean isAdmin = targetRoles.contains(StaffRoleType.ADMIN);
            boolean isModerator = isAdmin || targetRoles.contains(StaffRoleType.PUBLIC_MODO) || targetRoles.contains(StaffRoleType.PRIVATE_MODO) || targetRoles.contains(StaffRoleType.TRIAL_MODO);
            boolean canSee = isAdmin || (staffRole == StaffRoleType.MAPCREW && (isModerator || targetRoles.contains(StaffRoleType.MAPCREW))) || ((staffRole == StaffRoleType.PUBLIC_MODO || staffRole == StaffRoleType.PRIVATE_MODO) && isModerator);
            if (canSee)
                target.broadcastPacket(new PacketSendServerMessage(false, message));
        }
    }
}
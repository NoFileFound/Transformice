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
import org.transformice.properties.ConfigLoader;
import org.transformice.properties.configs.BadStringsConfig;
import org.transformice.properties.configs.JapanExpoConfig;
import org.transformice.properties.configs.PartnersConfig;
import org.transformice.properties.configs.shop.PromotionsConfig;
import org.transformice.properties.configs.shop.ShopOutfitsConfig;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.chat.C_StaffChannelMessage;
import org.transformice.packets.send.informations.C_ServerRestart;
import org.transformice.packets.send.level.C_OnlineStaffTeam;

public final class Server {
    public int lastGiftID;
    public int lastCollectibleId;
    public int lastNPCSessionId = -1;
    public int lastMonsterId = -1;
    public int leftistPlayers = 0;
    public int rightistPlayers = 0;
    private final List<Channel> channels = new ArrayList<>();
    private final Map<Integer, Client> clientSessions = new HashMap<>();
    private boolean isClosed;
    @Getter public int lastClientSessionId;
    @Getter private Map<String, List<String>> sonarPlayerMovement;
    @Getter private final PacketHandler packetHandler;
    @Getter private final CommandLoader commandHandler;
    @Getter private final List<String> tempBlackList;
    @Getter private final ArrayList<String> minigameList;
    @Getter private final ArrayList<Integer> blacklistedPackets = new ArrayList<>(List.of(6684));
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
    public static final List<Integer> vanillaMapList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245));
    public static final List<Integer> vanillaNoShamMapList = new ArrayList<>(List.of(8, 10, 14, 22, 23, 28, 29, 33, 42, 55, 57, 58, 61, 70, 77, 78, 87, 88, 122, 123, 124, 125, 126, 148, 149, 150, 151, 172, 173, 174, 175, 178, 179, 180, 188, 189, 190, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 218, 219, 220, 221, 222, 224, 225, 231, 232, 233, 234, 241, 242, 243, 244, 245));

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

    public final Map<Integer, Double> bootcampTitleList;
    {
        bootcampTitleList = new HashMap<>();
        bootcampTitleList.put(1, 256.1);
        bootcampTitleList.put(3, 257.1);
        bootcampTitleList.put(5, 258.1);
        bootcampTitleList.put(7, 259.1);
        bootcampTitleList.put(10, 260.1);
        bootcampTitleList.put(15, 261.1);
        bootcampTitleList.put(20, 262.1);
        bootcampTitleList.put(25, 263.1);
        bootcampTitleList.put(30, 264.1);
        bootcampTitleList.put(40, 265.1);
        bootcampTitleList.put(50, 266.1);
        bootcampTitleList.put(60, 267.1);
        bootcampTitleList.put(70, 268.1);
        bootcampTitleList.put(80, 269.1);
        bootcampTitleList.put(90, 270.1);
        bootcampTitleList.put(100, 271.1);
        bootcampTitleList.put(120, 272.1);
        bootcampTitleList.put(140, 273.1);
        bootcampTitleList.put(160, 274.1);
        bootcampTitleList.put(180, 275.1);
        bootcampTitleList.put(200, 276.1);
        bootcampTitleList.put(250, 277.1);
        bootcampTitleList.put(300, 278.1);
        bootcampTitleList.put(350, 279.1);
        bootcampTitleList.put(400, 280.1);
        bootcampTitleList.put(500, 281.1);
        bootcampTitleList.put(600, 282.1);
        bootcampTitleList.put(700, 283.1);
        bootcampTitleList.put(800, 284.1);
        bootcampTitleList.put(900, 285.1);
        bootcampTitleList.put(1000, 286.1);
        bootcampTitleList.put(1001, 256.2);
        bootcampTitleList.put(1003, 257.2);
        bootcampTitleList.put(1005, 258.2);
        bootcampTitleList.put(1007, 259.2);
        bootcampTitleList.put(1010, 260.2);
        bootcampTitleList.put(1015, 261.2);
        bootcampTitleList.put(1020, 262.2);
        bootcampTitleList.put(1025, 263.2);
        bootcampTitleList.put(1030, 264.2);
        bootcampTitleList.put(1040, 265.2);
        bootcampTitleList.put(1050, 266.2);
        bootcampTitleList.put(1060, 267.2);
        bootcampTitleList.put(1070, 268.2);
        bootcampTitleList.put(1080, 269.2);
        bootcampTitleList.put(1090, 270.2);
        bootcampTitleList.put(1100, 271.2);
        bootcampTitleList.put(1120, 272.2);
        bootcampTitleList.put(1140, 273.2);
        bootcampTitleList.put(1160, 274.2);
        bootcampTitleList.put(1180, 275.2);
        bootcampTitleList.put(1200, 276.2);
        bootcampTitleList.put(1250, 277.2);
        bootcampTitleList.put(1300, 278.2);
        bootcampTitleList.put(1350, 279.2);
        bootcampTitleList.put(1400, 280.2);
        bootcampTitleList.put(1500, 281.2);
        bootcampTitleList.put(1600, 282.2);
        bootcampTitleList.put(1700, 283.2);
        bootcampTitleList.put(1800, 284.2);
        bootcampTitleList.put(1900, 285.2);
        bootcampTitleList.put(2000, 286.2);
        bootcampTitleList.put(2001, 256.3);
        bootcampTitleList.put(2003, 257.3);
        bootcampTitleList.put(2005, 258.3);
        bootcampTitleList.put(2007, 259.3);
        bootcampTitleList.put(2010, 260.3);
        bootcampTitleList.put(2015, 261.3);
        bootcampTitleList.put(2020, 262.3);
        bootcampTitleList.put(2025, 263.3);
        bootcampTitleList.put(2030, 264.3);
        bootcampTitleList.put(2040, 265.3);
        bootcampTitleList.put(2050, 266.3);
        bootcampTitleList.put(2060, 267.3);
        bootcampTitleList.put(2070, 268.3);
        bootcampTitleList.put(2080, 269.3);
        bootcampTitleList.put(2090, 270.3);
        bootcampTitleList.put(2100, 271.3);
        bootcampTitleList.put(2120, 272.3);
        bootcampTitleList.put(2140, 273.3);
        bootcampTitleList.put(2160, 274.3);
        bootcampTitleList.put(2180, 275.3);
        bootcampTitleList.put(2200, 276.3);
        bootcampTitleList.put(2250, 277.3);
        bootcampTitleList.put(2300, 278.3);
        bootcampTitleList.put(2350, 279.3);
        bootcampTitleList.put(2400, 280.3);
        bootcampTitleList.put(2500, 281.3);
        bootcampTitleList.put(2600, 282.3);
        bootcampTitleList.put(2700, 283.3);
        bootcampTitleList.put(2800, 284.3);
        bootcampTitleList.put(2900, 285.3);
        bootcampTitleList.put(3000, 286.3);
        bootcampTitleList.put(3001, 256.4);
        bootcampTitleList.put(3003, 257.4);
        bootcampTitleList.put(3005, 258.4);
        bootcampTitleList.put(3007, 259.4);
        bootcampTitleList.put(3010, 260.4);
        bootcampTitleList.put(3015, 261.4);
        bootcampTitleList.put(3020, 262.4);
        bootcampTitleList.put(3025, 263.4);
        bootcampTitleList.put(3030, 264.4);
        bootcampTitleList.put(3040, 265.4);
        bootcampTitleList.put(3050, 266.4);
        bootcampTitleList.put(3060, 267.4);
        bootcampTitleList.put(3070, 268.4);
        bootcampTitleList.put(3080, 269.4);
        bootcampTitleList.put(3090, 270.4);
        bootcampTitleList.put(3100, 271.4);
        bootcampTitleList.put(3120, 272.4);
        bootcampTitleList.put(3140, 273.4);
        bootcampTitleList.put(3160, 274.4);
        bootcampTitleList.put(3180, 275.4);
        bootcampTitleList.put(3200, 276.4);
        bootcampTitleList.put(3250, 277.4);
        bootcampTitleList.put(3300, 278.4);
        bootcampTitleList.put(3350, 279.4);
        bootcampTitleList.put(3400, 280.4);
        bootcampTitleList.put(3500, 281.4);
        bootcampTitleList.put(3600, 282.4);
        bootcampTitleList.put(3700, 283.4);
        bootcampTitleList.put(3800, 284.4);
        bootcampTitleList.put(3900, 285.4);
        bootcampTitleList.put(4000, 286.4);
        bootcampTitleList.put(4001, 256.5);
        bootcampTitleList.put(4003, 257.5);
        bootcampTitleList.put(4005, 258.5);
        bootcampTitleList.put(4007, 259.5);
        bootcampTitleList.put(4010, 260.5);
        bootcampTitleList.put(4015, 261.5);
        bootcampTitleList.put(4020, 262.5);
        bootcampTitleList.put(4025, 263.5);
        bootcampTitleList.put(4030, 264.5);
        bootcampTitleList.put(4040, 265.5);
        bootcampTitleList.put(4050, 266.5);
        bootcampTitleList.put(4060, 267.5);
        bootcampTitleList.put(4070, 268.5);
        bootcampTitleList.put(4080, 269.5);
        bootcampTitleList.put(4090, 270.5);
        bootcampTitleList.put(4100, 271.5);
        bootcampTitleList.put(4120, 272.5);
        bootcampTitleList.put(4140, 273.5);
        bootcampTitleList.put(4160, 274.5);
        bootcampTitleList.put(4180, 275.5);
        bootcampTitleList.put(4200, 276.5);
        bootcampTitleList.put(4250, 277.5);
        bootcampTitleList.put(4300, 278.5);
        bootcampTitleList.put(4350, 279.5);
        bootcampTitleList.put(4400, 280.5);
        bootcampTitleList.put(4500, 281.5);
        bootcampTitleList.put(4600, 282.5);
        bootcampTitleList.put(4700, 283.5);
        bootcampTitleList.put(4800, 284.5);
        bootcampTitleList.put(4900, 285.5);
        bootcampTitleList.put(5000, 286.5);
        bootcampTitleList.put(5001, 256.6);
        bootcampTitleList.put(5003, 257.6);
        bootcampTitleList.put(5005, 258.6);
        bootcampTitleList.put(5007, 259.6);
        bootcampTitleList.put(5010, 260.6);
        bootcampTitleList.put(5015, 261.6);
        bootcampTitleList.put(5020, 262.6);
        bootcampTitleList.put(5025, 263.6);
        bootcampTitleList.put(5030, 264.6);
        bootcampTitleList.put(5040, 265.6);
        bootcampTitleList.put(5050, 266.6);
        bootcampTitleList.put(5060, 267.6);
        bootcampTitleList.put(5070, 268.6);
        bootcampTitleList.put(5080, 269.6);
        bootcampTitleList.put(5090, 270.6);
        bootcampTitleList.put(5100, 271.6);
        bootcampTitleList.put(5120, 272.6);
        bootcampTitleList.put(5140, 273.6);
        bootcampTitleList.put(5160, 274.6);
        bootcampTitleList.put(5180, 275.6);
        bootcampTitleList.put(5200, 276.6);
        bootcampTitleList.put(5250, 277.6);
        bootcampTitleList.put(5300, 278.6);
        bootcampTitleList.put(5350, 279.6);
        bootcampTitleList.put(5400, 280.6);
        bootcampTitleList.put(5500, 281.6);
        bootcampTitleList.put(5600, 282.6);
        bootcampTitleList.put(5700, 283.6);
        bootcampTitleList.put(5800, 284.6);
        bootcampTitleList.put(5900, 285.6);
        bootcampTitleList.put(6000, 286.6);
        bootcampTitleList.put(6001, 256.7);
        bootcampTitleList.put(6003, 257.7);
        bootcampTitleList.put(6005, 258.7);
        bootcampTitleList.put(6007, 259.7);
        bootcampTitleList.put(6010, 260.7);
        bootcampTitleList.put(6015, 261.7);
        bootcampTitleList.put(6020, 262.7);
        bootcampTitleList.put(6025, 263.7);
        bootcampTitleList.put(6030, 264.7);
        bootcampTitleList.put(6040, 265.7);
        bootcampTitleList.put(6050, 266.7);
        bootcampTitleList.put(6060, 267.7);
        bootcampTitleList.put(6070, 268.7);
        bootcampTitleList.put(6080, 269.7);
        bootcampTitleList.put(6090, 270.7);
        bootcampTitleList.put(6100, 271.7);
        bootcampTitleList.put(6120, 272.7);
        bootcampTitleList.put(6140, 273.7);
        bootcampTitleList.put(6160, 274.7);
        bootcampTitleList.put(6180, 275.7);
        bootcampTitleList.put(6200, 276.7);
        bootcampTitleList.put(6250, 277.7);
        bootcampTitleList.put(6300, 278.7);
        bootcampTitleList.put(6350, 279.7);
        bootcampTitleList.put(6400, 280.7);
        bootcampTitleList.put(6500, 281.7);
        bootcampTitleList.put(6600, 282.7);
        bootcampTitleList.put(6700, 283.7);
        bootcampTitleList.put(6800, 284.7);
        bootcampTitleList.put(6900, 285.7);
        bootcampTitleList.put(7000, 286.7);
        bootcampTitleList.put(7001, 256.8);
        bootcampTitleList.put(7003, 257.8);
        bootcampTitleList.put(7005, 258.8);
        bootcampTitleList.put(7007, 259.8);
        bootcampTitleList.put(7010, 260.8);
        bootcampTitleList.put(7015, 261.8);
        bootcampTitleList.put(7020, 262.8);
        bootcampTitleList.put(7025, 263.8);
        bootcampTitleList.put(7030, 264.8);
        bootcampTitleList.put(7040, 265.8);
        bootcampTitleList.put(7050, 266.8);
        bootcampTitleList.put(7060, 267.8);
        bootcampTitleList.put(7070, 268.8);
        bootcampTitleList.put(7080, 269.8);
        bootcampTitleList.put(7090, 270.8);
        bootcampTitleList.put(7100, 271.8);
        bootcampTitleList.put(7120, 272.8);
        bootcampTitleList.put(7140, 273.8);
        bootcampTitleList.put(7160, 274.8);
        bootcampTitleList.put(7180, 275.8);
        bootcampTitleList.put(7200, 276.8);
        bootcampTitleList.put(7250, 277.8);
        bootcampTitleList.put(7300, 278.8);
        bootcampTitleList.put(7350, 279.8);
        bootcampTitleList.put(7400, 280.8);
        bootcampTitleList.put(7500, 281.8);
        bootcampTitleList.put(7600, 282.8);
        bootcampTitleList.put(7700, 283.8);
        bootcampTitleList.put(7800, 284.8);
        bootcampTitleList.put(7900, 285.8);
        bootcampTitleList.put(8000, 286.8);
    }

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

    public final Map<Integer, Double> shopTitleList;
    {
        shopTitleList = new HashMap<>();
        shopTitleList.put(1, 115.1);
        shopTitleList.put(2, 116.1);
        shopTitleList.put(5, 117.1);
        shopTitleList.put(10, 118.1);
        shopTitleList.put(15, 119.1);
        shopTitleList.put(20, 120.1);
        shopTitleList.put(25, 121.1);
        shopTitleList.put(30, 122.1);
        shopTitleList.put(35, 123.1);
        shopTitleList.put(40, 124.1);
        shopTitleList.put(45, 125.1);
        shopTitleList.put(50, 126.1);
        shopTitleList.put(51, 115.2);
        shopTitleList.put(52, 116.2);
        shopTitleList.put(55, 117.2);
        shopTitleList.put(60, 118.2);
        shopTitleList.put(65, 119.2);
        shopTitleList.put(70, 120.2);
        shopTitleList.put(75, 121.2);
        shopTitleList.put(80, 122.2);
        shopTitleList.put(85, 123.2);
        shopTitleList.put(90, 124.2);
        shopTitleList.put(95, 125.2);
        shopTitleList.put(100, 126.2);
        shopTitleList.put(101, 115.3);
        shopTitleList.put(102, 116.3);
        shopTitleList.put(105, 117.3);
        shopTitleList.put(110, 118.3);
        shopTitleList.put(115, 119.3);
        shopTitleList.put(120, 120.3);
        shopTitleList.put(125, 121.3);
        shopTitleList.put(130, 122.3);
        shopTitleList.put(135, 123.3);
        shopTitleList.put(140, 124.3);
        shopTitleList.put(145, 125.3);
        shopTitleList.put(150, 126.3);
        shopTitleList.put(151, 115.4);
        shopTitleList.put(152, 116.4);
        shopTitleList.put(155, 117.4);
        shopTitleList.put(160, 118.4);
        shopTitleList.put(165, 119.4);
        shopTitleList.put(170, 120.4);
        shopTitleList.put(175, 121.4);
        shopTitleList.put(180, 122.4);
        shopTitleList.put(185, 123.4);
        shopTitleList.put(190, 124.4);
        shopTitleList.put(195, 125.4);
        shopTitleList.put(200, 126.4);
        shopTitleList.put(201, 115.5);
        shopTitleList.put(202, 116.5);
        shopTitleList.put(205, 117.5);
        shopTitleList.put(210, 118.5);
        shopTitleList.put(215, 119.5);
        shopTitleList.put(220, 120.5);
        shopTitleList.put(225, 121.5);
        shopTitleList.put(230, 122.5);
        shopTitleList.put(235, 123.5);
        shopTitleList.put(240, 124.5);
        shopTitleList.put(245, 125.5);
        shopTitleList.put(250, 126.5);
        shopTitleList.put(251, 115.6);
        shopTitleList.put(252, 116.6);
        shopTitleList.put(255, 117.6);
        shopTitleList.put(260, 118.6);
        shopTitleList.put(265, 119.6);
        shopTitleList.put(270, 120.6);
        shopTitleList.put(275, 121.6);
        shopTitleList.put(280, 122.6);
        shopTitleList.put(285, 123.6);
        shopTitleList.put(290, 124.6);
        shopTitleList.put(295, 125.6);
        shopTitleList.put(300, 126.6);
        shopTitleList.put(301, 115.7);
        shopTitleList.put(302, 116.7);
        shopTitleList.put(305, 117.7);
        shopTitleList.put(310, 118.7);
        shopTitleList.put(315, 119.7);
        shopTitleList.put(320, 120.7);
        shopTitleList.put(325, 121.7);
        shopTitleList.put(330, 122.7);
        shopTitleList.put(335, 123.7);
        shopTitleList.put(340, 124.7);
        shopTitleList.put(345, 125.7);
        shopTitleList.put(350, 126.7);
        shopTitleList.put(351, 115.8);
        shopTitleList.put(352, 116.8);
        shopTitleList.put(355, 117.8);
        shopTitleList.put(360, 118.8);
        shopTitleList.put(365, 119.8);
        shopTitleList.put(370, 120.8);
        shopTitleList.put(375, 121.8);
        shopTitleList.put(380, 122.8);
        shopTitleList.put(385, 123.8);
        shopTitleList.put(390, 124.8);
        shopTitleList.put(395, 125.8);
        shopTitleList.put(400, 126.8);
    }

    // shop badges.
    public final Map<Integer, Integer> shopBadgeList;
    {
        shopBadgeList = new HashMap<>();
        shopBadgeList.put(67, 149);
        shopBadgeList.put(68, 150);
        shopBadgeList.put(69, 151);
        shopBadgeList.put(346, 506);
        shopBadgeList.put(178, 301);
        shopBadgeList.put(186, 308);
        shopBadgeList.put(198, 320);
        shopBadgeList.put(207, 330);
        shopBadgeList.put(27, 2);
        shopBadgeList.put(8, 3);
        shopBadgeList.put(2, 4);
        shopBadgeList.put(9, 5);
        shopBadgeList.put(28, 8);
        shopBadgeList.put(18, 10);
        shopBadgeList.put(6, 11);
        shopBadgeList.put(19, 12);
        shopBadgeList.put(29, 13);
        shopBadgeList.put(30, 14);
        shopBadgeList.put(31, 15);
        shopBadgeList.put(11, 19);
        shopBadgeList.put(32, 20);
        shopBadgeList.put(24, 21);
        shopBadgeList.put(17, 22);
        shopBadgeList.put(14, 23);
        shopBadgeList.put(12, 24);
        shopBadgeList.put(20, 25);
        shopBadgeList.put(23, 26);
        shopBadgeList.put(34, 27);
        shopBadgeList.put(3, 31);
        shopBadgeList.put(21, 32);
        shopBadgeList.put(36, 36);
        shopBadgeList.put(15, 37);
        shopBadgeList.put(5, 38);
        shopBadgeList.put(22, 39);
        shopBadgeList.put(4, 40);
        shopBadgeList.put(38, 41);
        shopBadgeList.put(39, 43);
        shopBadgeList.put(41, 44);
        shopBadgeList.put(43, 45);
        shopBadgeList.put(44, 48);
        shopBadgeList.put(7, 49);
        shopBadgeList.put(46, 52);
        shopBadgeList.put(47, 53);
        shopBadgeList.put(25, 56);
        shopBadgeList.put(13, 60);
        shopBadgeList.put(48, 61);
        shopBadgeList.put(26, 62);
        shopBadgeList.put(49, 63);
        shopBadgeList.put(50, 66);
        shopBadgeList.put(52, 67);
        shopBadgeList.put(53, 68);
        shopBadgeList.put(54, 70);
        shopBadgeList.put(55, 72);
        shopBadgeList.put(56, 128);
        shopBadgeList.put(57, 135);
        shopBadgeList.put(58, 136);
        shopBadgeList.put(59, 137);
        shopBadgeList.put(60, 138);
        shopBadgeList.put(61, 140);
        shopBadgeList.put(62, 141);
        shopBadgeList.put(63, 143);
        shopBadgeList.put(64, 146);
        shopBadgeList.put(65, 148);
        shopBadgeList.put(70, 152);
        shopBadgeList.put(71, 155);
        shopBadgeList.put(72, 156);
        shopBadgeList.put(73, 157);
        shopBadgeList.put(74, 160);
        shopBadgeList.put(76, 165);
        shopBadgeList.put(77, 167);
        shopBadgeList.put(78, 171);
        shopBadgeList.put(79, 173);
        shopBadgeList.put(80, 175);
        shopBadgeList.put(81, 176);
        shopBadgeList.put(82, 177);
        shopBadgeList.put(83, 178);
        shopBadgeList.put(84, 179);
        shopBadgeList.put(85, 180);
        shopBadgeList.put(86, 183);
        shopBadgeList.put(87, 185);
        shopBadgeList.put(88, 186);
        shopBadgeList.put(89, 187);
        shopBadgeList.put(90, 189);
        shopBadgeList.put(91, 191);
        shopBadgeList.put(92, 192);
        shopBadgeList.put(93, 194);
        shopBadgeList.put(94, 195);
        shopBadgeList.put(95, 196);
        shopBadgeList.put(96, 197);
        shopBadgeList.put(97, 199);
        shopBadgeList.put(98, 200);
        shopBadgeList.put(99, 201);
        shopBadgeList.put(100, 203);
        shopBadgeList.put(101, 204);
        shopBadgeList.put(102, 205);
        shopBadgeList.put(103, 206);
        shopBadgeList.put(104, 207);
        shopBadgeList.put(105, 208);
        shopBadgeList.put(106, 210);
        shopBadgeList.put(107, 211);
        shopBadgeList.put(108, 212);
        shopBadgeList.put(109, 213);
        shopBadgeList.put(110, 214);
        shopBadgeList.put(111, 215);
        shopBadgeList.put(112, 216);
        shopBadgeList.put(113, 217);
        shopBadgeList.put(114, 220);
        shopBadgeList.put(115, 222);
        shopBadgeList.put(116, 223);
        shopBadgeList.put(117, 224);
        shopBadgeList.put(119, 226);
        shopBadgeList.put(120, 227);
        shopBadgeList.put(121, 228);
        shopBadgeList.put(122, 229);
        shopBadgeList.put(123, 231);
        shopBadgeList.put(124, 232);
        shopBadgeList.put(125, 233);
        shopBadgeList.put(126, 234);
        shopBadgeList.put(127, 235);
        shopBadgeList.put(128, 236);
        shopBadgeList.put(129, 237);
        shopBadgeList.put(130, 238);
        shopBadgeList.put(131, 239);
        shopBadgeList.put(132, 241);
        shopBadgeList.put(133, 242);
        shopBadgeList.put(134, 243);
        shopBadgeList.put(135, 244);
        shopBadgeList.put(136, 245);
        shopBadgeList.put(137, 246);
        shopBadgeList.put(138, 247);
        shopBadgeList.put(139, 248);
        shopBadgeList.put(140, 250);
        shopBadgeList.put(141, 251);
        shopBadgeList.put(142, 252);
        shopBadgeList.put(143, 253);
        shopBadgeList.put(144, 254);
        shopBadgeList.put(145, 256);
        shopBadgeList.put(146, 258);
        shopBadgeList.put(147, 259);
        shopBadgeList.put(148, 260);
        shopBadgeList.put(149, 262);
        shopBadgeList.put(150, 263);
        shopBadgeList.put(151, 265);
        shopBadgeList.put(152, 266);
        shopBadgeList.put(153, 268);
        shopBadgeList.put(154, 270);
        shopBadgeList.put(155, 271);
        shopBadgeList.put(156, 273);
        shopBadgeList.put(157, 274);
        shopBadgeList.put(158, 275);
        shopBadgeList.put(159, 277);
        shopBadgeList.put(160, 278);
        shopBadgeList.put(161, 279);
        shopBadgeList.put(162, 280);
        shopBadgeList.put(163, 282);
        shopBadgeList.put(164, 283);
        shopBadgeList.put(166, 284);
        shopBadgeList.put(165, 285);
        shopBadgeList.put(167, 289);
        shopBadgeList.put(170, 292);
        shopBadgeList.put(171, 294);
        shopBadgeList.put(173, 295);
        shopBadgeList.put(172, 296);
        shopBadgeList.put(175, 298);
        shopBadgeList.put(176, 299);
        shopBadgeList.put(177, 300);
        shopBadgeList.put(179, 302);
        shopBadgeList.put(180, 304);
        shopBadgeList.put(183, 305);
        shopBadgeList.put(184, 306);
        shopBadgeList.put(185, 307);
        shopBadgeList.put(187, 309);
        shopBadgeList.put(188, 311);
        shopBadgeList.put(189, 312);
        shopBadgeList.put(190, 313);
        shopBadgeList.put(191, 314);
        shopBadgeList.put(192, 315);
        shopBadgeList.put(193, 316);
        shopBadgeList.put(196, 318);
        shopBadgeList.put(197, 319);
        shopBadgeList.put(199, 321);
        shopBadgeList.put(201, 322);
        shopBadgeList.put(200, 323);
        shopBadgeList.put(203, 325);
        shopBadgeList.put(204, 326);
        shopBadgeList.put(205, 327);
        shopBadgeList.put(206, 329);
        shopBadgeList.put(208, 331);
        shopBadgeList.put(209, 332);
        shopBadgeList.put(210, 333);
        shopBadgeList.put(213, 335);
        shopBadgeList.put(214, 336);
        shopBadgeList.put(215, 337);
        shopBadgeList.put(217, 339);
        shopBadgeList.put(216, 340);
        shopBadgeList.put(221, 342);
        shopBadgeList.put(222, 343);
        shopBadgeList.put(223, 344);
        shopBadgeList.put(225, 346);
        shopBadgeList.put(226, 352);
        shopBadgeList.put(227, 353);
        shopBadgeList.put(228, 354);
        shopBadgeList.put(229, 355);
        shopBadgeList.put(233, 358);
        shopBadgeList.put(234, 359);
        shopBadgeList.put(235, 360);
        shopBadgeList.put(236, 361);
        shopBadgeList.put(237, 363);
        shopBadgeList.put(238, 365);
        shopBadgeList.put(239, 367);
        shopBadgeList.put(240, 368);
        shopBadgeList.put(241, 369);
        shopBadgeList.put(243, 371);
        shopBadgeList.put(244, 372);
        shopBadgeList.put(245, 375);
        shopBadgeList.put(246, 383);
        shopBadgeList.put(247, 384);
        shopBadgeList.put(248, 385);
        shopBadgeList.put(249, 387);
        shopBadgeList.put(250, 388);
        shopBadgeList.put(251, 389);
        shopBadgeList.put(252, 392);
        shopBadgeList.put(253, 393);
        shopBadgeList.put(254, 394);
        shopBadgeList.put(255, 395);
        shopBadgeList.put(256, 396);
        shopBadgeList.put(258, 400);
        shopBadgeList.put(259, 401);
        shopBadgeList.put(260, 402);
        shopBadgeList.put(261, 403);
        shopBadgeList.put(262, 405);
        shopBadgeList.put(264, 407);
        shopBadgeList.put(265, 408);
        shopBadgeList.put(266, 409);
        shopBadgeList.put(267, 411);
        shopBadgeList.put(268, 413);
        shopBadgeList.put(270, 414);
        shopBadgeList.put(269, 415);
        shopBadgeList.put(271, 416);
        shopBadgeList.put(272, 417);
        shopBadgeList.put(273, 419);
        shopBadgeList.put(274, 420);
        shopBadgeList.put(275, 421);
        shopBadgeList.put(276, 422);
        shopBadgeList.put(277, 424);
        shopBadgeList.put(278, 425);
        shopBadgeList.put(279, 427);
        shopBadgeList.put(280, 428);
        shopBadgeList.put(281, 429);
        shopBadgeList.put(282, 430);
        shopBadgeList.put(283, 431);
        shopBadgeList.put(285, 433);
        shopBadgeList.put(286, 434);
        shopBadgeList.put(289, 436);
        shopBadgeList.put(290, 437);
        shopBadgeList.put(291, 439);
        shopBadgeList.put(292, 440);
        shopBadgeList.put(293, 441);
        shopBadgeList.put(295, 443);
        shopBadgeList.put(296, 444);
        shopBadgeList.put(297, 445);
        shopBadgeList.put(298, 446);
        shopBadgeList.put(299, 447);
        shopBadgeList.put(300, 448);
        shopBadgeList.put(301, 450);
        shopBadgeList.put(302, 451);
        shopBadgeList.put(303, 452);
        shopBadgeList.put(304, 453);
        shopBadgeList.put(305, 455);
        shopBadgeList.put(307, 456);
        shopBadgeList.put(308, 458);
        shopBadgeList.put(309, 459);
        shopBadgeList.put(310, 460);
        shopBadgeList.put(311, 462);
        shopBadgeList.put(312, 463);
        shopBadgeList.put(314, 465);
        shopBadgeList.put(315, 466);
        shopBadgeList.put(316, 467);
        shopBadgeList.put(317, 470);
        shopBadgeList.put(318, 472);
        shopBadgeList.put(319, 473);
        shopBadgeList.put(320, 474);
        shopBadgeList.put(321, 475);
        shopBadgeList.put(322, 476);
        shopBadgeList.put(323, 478);
        shopBadgeList.put(324, 479);
        shopBadgeList.put(325, 480);
        shopBadgeList.put(326, 481);
        shopBadgeList.put(328, 484);
        shopBadgeList.put(329, 485);
        shopBadgeList.put(330, 486);
        shopBadgeList.put(331, 489);
        shopBadgeList.put(332, 490);
        shopBadgeList.put(333, 491);
        shopBadgeList.put(334, 492);
        shopBadgeList.put(335, 493);
        shopBadgeList.put(336, 494);
        shopBadgeList.put(338, 496);
        shopBadgeList.put(339, 497);
        shopBadgeList.put(340, 499);
        shopBadgeList.put(341, 500);
        shopBadgeList.put(342, 501);
        shopBadgeList.put(343, 503);
        shopBadgeList.put(344, 504);
        shopBadgeList.put(347, 507);
        shopBadgeList.put(348, 508);
        shopBadgeList.put(349, 511);
        shopBadgeList.put(350, 512);
        shopBadgeList.put(351, 513);
        shopBadgeList.put(352, 514);
        shopBadgeList.put(355, 516);
        shopBadgeList.put(356, 517);
        shopBadgeList.put(357, 518);
        shopBadgeList.put(358, 519);
    }

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
        long time = System.currentTimeMillis();
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

        if (!Application.getSwfInfo().ports.isEmpty()) {
            ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
            ChannelPipeline pipeline = bootstrap.getPipeline();
            pipeline.addLast("encoder", new Encoder());
            pipeline.addLast("decoder", new Decoder());
            pipeline.addLast("handler", new ClientHandler(this));

            for (Integer port : Application.getSwfInfo().ports) {
                this.channels.add(bootstrap.bind(new InetSocketAddress(port)));
            }
            Application.getLogger().info(Application.getTranslationManager().get("startdone", System.currentTimeMillis() - time));
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

        ConfigLoader.getProperty(JapanExpoConfig.class).saveFile();
        ConfigLoader.getProperty(BadStringsConfig.class).saveFile();
        ConfigLoader.getProperty(PartnersConfig.class).saveFile();
        ConfigLoader.getProperty(PromotionsConfig.class).saveFile();
        ConfigLoader.getProperty(ShopOutfitsConfig.class).saveFile();

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
            if (client.hasStaffPermission("Arbitre", "ServerMsg")) {
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
            if (channelId > 1 && channelId < 6 && client.hasStaffPermission("Modo", "StaffChannel") || client.hasStaffPermission("TrialModo", "StaffChannel")) {
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
            } else if (channelId == 2 || channelId == 5 && client.hasStaffPermission("Arbitre", "StaffChannel")) {
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
        if (author.getAccount().getStaffRoles().isEmpty()) return;

        List<String> staffInfo = new ArrayList<>();

        String staffTeam = "";
        int channelId = -1;
        if(author.hasStaffPermission("Modo", "StaffChannel")) {
            staffTeam = "Modo";
            channelId = 4;
        } else if (author.hasStaffPermission("TrialModo", "StaffChannel")) {
            staffTeam = "TrialModo";
            channelId = 4;
        } else if(author.hasStaffPermission("Arbitre", "StaffChannel")) {
            staffTeam = "Arbitre";
            channelId = 2;
        } else if (author.hasStaffPermission("MapCrew", "StaffChannel")) {
            staffTeam = "MapCrew";
            channelId = 8;
        } else if (author.hasStaffPermission("FashionSquad", "StaffChannel")) {
            staffTeam = "FashionSquad";
            channelId = 10;
        } else if (author.hasStaffPermission("LuaDev", "StaffChannel")) {
            staffTeam = "LuaDev";
            channelId = 7;
        } else if (author.hasStaffPermission("FunCorp", "StaffChannel")) {
            staffTeam = "FunCorp";
            channelId = 9;
        }

        for (Client client : this.players.values()) {
            if (client.hasStaffPermission(staffTeam, "StaffChannel") && channelId != -1) {
                client.sendPacket(new C_StaffChannelMessage(
                        channelId,
                        message,
                        author.playerCommunity,
                        author.getPlayerName()
                ));

                staffInfo.add(
                        client.playerCommunity + "_" +
                                client.getPlayerName() + "_" +
                                client.playerCommunity + "-" +
                                client.getRoomName()
                );
            }
        }

        if (message.equals("$Connexion_Ami")) {
            author.sendPacket(new C_OnlineStaffTeam(channelId, staffInfo));
        }
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
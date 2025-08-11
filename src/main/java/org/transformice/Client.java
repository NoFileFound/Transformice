package org.transformice;

// Imports
import com.maxmind.geoip2.record.Country;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.bytearray.ByteArray;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.TimeOutDebugLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.Sanction;
import org.transformice.database.collections.Tribe;
import org.transformice.database.embeds.Adventure;
import org.transformice.libraries.GeoIP;
import org.transformice.libraries.Pair;
import org.transformice.libraries.SrcRandom;
import org.transformice.libraries.Timer;
import org.transformice.luapi.LuaApiLib;
import org.transformice.modules.*;
import org.transformice.packets.SendPacket;
import org.transformice.packets.TribullePacket;
import org.transformice.packets.send.lua.C_BindKeyboard;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.informations.C_LoginQueue;
import org.transformice.packets.send.informations.C_PunishmentTax;
import org.transformice.packets.send.informations.C_SendLetter;
import org.transformice.packets.send.informations.C_SendTotemObjects;
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.informations.C_VerifiedEmailAddress;
import org.transformice.packets.send.language.C_ShowCommunityPartners;
import org.transformice.packets.send.legacy.editor.C_MapValidated;
import org.transformice.packets.send.legacy.login.C_BanMessageLogin;
import org.transformice.packets.send.legacy.player.C_CatchTheCheeseMap;
import org.transformice.packets.send.legacy.player.C_PlayerDied;
import org.transformice.packets.send.legacy.player.C_PlayerSaveRemainingNotification;
import org.transformice.packets.send.legacy.player.C_PlayerSync;
import org.transformice.packets.send.legacy.player.C_PlayerUnlockTitle;
import org.transformice.packets.send.legacy.room.C_AddAnchor;
import org.transformice.packets.send.login.C_LoginSouris;
import org.transformice.packets.send.login.C_PlayerIdentity;
import org.transformice.packets.send.lua.C_InitializeLuaScripting;
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.modopwet.C_ModopwetRoomPasswordMsg;
import org.transformice.packets.send.newpackets.C_DecoratePlayerList;
import org.transformice.packets.send.newpackets.C_DisableInitialItemCooldown;
import org.transformice.packets.send.newpackets.C_PlayerGetCheese;
import org.transformice.packets.send.newpackets.C_PurchasedEmojis;
import org.transformice.packets.send.newpackets.C_RoomDetailsMessage;
import org.transformice.packets.send.newpackets.C_RoomPlayerList;
import org.transformice.packets.send.newpackets.C_SetCheeseSpriteSuffix;
import org.transformice.packets.send.player.C_CreateNewNPC;
import org.transformice.packets.send.player.C_EnableMeep;
import org.transformice.packets.send.player.C_PlayerShamanInfo;
import org.transformice.packets.send.player.C_PlayerVictory;
import org.transformice.packets.send.player.C_VampireMode;
import org.transformice.packets.send.room.*;
import org.transformice.packets.send.room.info.C_RoomServer;
import org.transformice.packets.send.room.info.C_RoomType;
import org.transformice.packets.send.transformation.C_EnableTransformation;
import org.transformice.packets.send.transformice.C_CollectibleActionPacket;
import org.transformice.packets.send.transformice.C_ExportMapCheeseAmount;
import org.transformice.packets.send.transformice.C_SetShopNews;
import org.transformice.packets.send.tribulle.*;

public final class Client {
    public Client lastWatchedClient;
    public int bubblesCount;
    public int defilantePoints;
    public int drawColor;
    public int cheeseCount;
    public int iceCount;
    public int lastPingResponse;
    public double lastJumpPower = 1.0;
    public int loginAttempts;
    public int verCode;
    public int currentGameMode;
    public int playerHealth;
    public int playerScore;
    public int nickNameColor;
    public long lastSonarTime;
    public boolean canRedistributeSkills = true;
    public boolean hasSent2FAEmail;
    public boolean canShamanRespawn;
    public boolean canMeep;
    public boolean canTransform;
    public boolean isAfk;
    public boolean isDead;
    public boolean isDisintegration;
    public boolean isEnteredInHole;
    public boolean isFacingLeft;
    public boolean isFacingRight;
    public boolean isHidden;
    public boolean isLuaAdmin;
    public boolean isMumuted;
    public boolean isOpenCafe;
    public boolean isOpenFriendList;
    public boolean isOpenModopwet;
    public boolean isOpenTrade;
    public boolean isOpenTribe;
    public boolean isOpportunist;
    public boolean isPrisoned;
    public boolean isShaman;
    public boolean isSubscribedModoNotifications;
    public boolean isTradeConfirm;
    public boolean isTaxed;
    public boolean isUsedTotem;
    public boolean isNewPlayer;
    public boolean isVampire;
    public boolean isJumping;
    public boolean isDebugCoords;
    public boolean isDebugTeleport;
    public byte silenceType;
    public String playerCommunity;
    public String playerType;
    public String osLanguage;
    public String osName;
    public String registerCaptcha;
    public String silenceMessage;
    public String currentMarriageInvite;
    public String currentMessage;
    public String currentTradeName;
    public String currentTribeInvite;
    public String tmpEmailAddress;
    public String tmpEmailAddressCode;
    public String tmpMouseLook = "";
    public String token2FA;
    public String lastNpcName;
    public Pair<Integer, String> tempTotemInfo = new Pair<>(0, "");
    public Pair<Integer, Integer> mulodromeInfo = new Pair<>(0, 0);
    private boolean isClosed;
    private long loginTime;
    private final Channel channel;
    private final ArrayList<Integer> cheeseIdxs;
    @Getter private Thread luaThread;
    @Getter private Account account;
    @Getter private Server server;
    @Getter private int sessionId;
    @Getter private int currentPlace;
    @Getter private boolean isGuest;
    @Getter private boolean isVip;
    @Getter private String ipAddress;
    @Getter private String playerName;
    @Getter private String lastRoomName;
    @Getter private String roomName;
    @Getter final private String countryLangue;
    @Getter final private String countryName;
    @Getter private final ArrayList<Client> currentWatchers;
    @Getter private final List<String> voteBans;
    @Getter private final List<String> modopwetChatNotificationCommunities;
    @Getter private final List<String> invitedTribeHouses;
    @Getter private final Map<Integer, Integer> tradeConsumables;
    @Getter private final Map<String, Integer> modoCommunitiesCount;
    @Getter @Setter private Room room;
    @Getter @Setter private Pair<Integer, Integer> position = new Pair<>(-1, -1);
    @Getter @Setter private Pair<Integer, Integer> velocity;
    @Getter @Setter private String funCorpNickname;
    @Getter @Setter private Integer funCorpNickcolor;
    @Getter @Setter private Integer funCorpMousecolor;
    @Getter @Setter private long playerStartTimeMillis;

    // Modules
    @Getter private final ParseCafe parseCafeInstance;
    @Getter private final ParseDailyQuests parseDailyQuestsInstance;
    @Getter private final ParseInventory parseInventoryInstance;
    @Getter private final ParseModopwet parseModopwetInstance;
    @Getter private final ParseShop parseShopInstance;
    @Getter private final ParseSkills parseSkillsInstance;
    @Getter private final ParseTribulle parseTribulleInstance;

    // Timers
    public Timer keepAliveTimer;
    public Timer reloadCafeTimer;
    public Timer marriageTimer;
    public Timer chatMessageTimer;
    public Timer skipMusicTimer;
    public Timer redistributeTimer;

    /**
     * Creates a new player in the server.
     * @param server The server.
     * @param channel The channel where player is connected.
     */
    public Client(Server server, Channel channel) {
        Country country = GeoIP.getCountry(((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress());

        this.server = server;
        this.channel = channel;
        this.ipAddress = ((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress();
        this.isClosed = false;
        this.countryLangue = (country != null) ? country.getIsoCode() : "en";
        this.countryName = (country != null) ? country.getName() : "null (proxy)";
        this.funCorpNickname = "";
        this.funCorpMousecolor = -1;
        this.funCorpNickcolor = -1;
        this.verCode = -1;
        this.loginAttempts = 0;
        this.tradeConsumables = new HashMap<>();
        this.currentWatchers = new ArrayList<>();
        this.invitedTribeHouses = new ArrayList<>();
        this.modopwetChatNotificationCommunities = new ArrayList<>();
        this.modoCommunitiesCount = new HashMap<>();
        this.cheeseIdxs = new ArrayList<>();
        this.voteBans = new ArrayList<>();

        // Modules
        this.parseCafeInstance = new ParseCafe(this);
        this.parseDailyQuestsInstance = new ParseDailyQuests(this);
        this.parseInventoryInstance = new ParseInventory(this);
        this.parseModopwetInstance = new ParseModopwet(this);
        this.parseShopInstance = new ParseShop(this);
        this.parseSkillsInstance = new ParseSkills(this);
        this.parseTribulleInstance = new ParseTribulle(this);

        // Timers
        this.keepAliveTimer = new Timer(Application.getPropertiesInfo().timers.keep_alive.enable, Application.getPropertiesInfo().timers.keep_alive.delay);
        this.reloadCafeTimer = new Timer(Application.getPropertiesInfo().timers.reload_cafe.enable, Application.getPropertiesInfo().timers.reload_cafe.delay);
        this.marriageTimer = new Timer(Application.getPropertiesInfo().timers.marriage.enable, 1);
        this.chatMessageTimer = new Timer(Application.getPropertiesInfo().timers.chat_message.enable, Application.getPropertiesInfo().timers.chat_message.delay);
        this.skipMusicTimer = new Timer(Application.getPropertiesInfo().timers.skip_music.enable, Application.getPropertiesInfo().timers.skip_music.delay);
        this.redistributeTimer = new Timer(true, 5);
        if(!this.server.createAccountTimer.containsKey(this.ipAddress)) {
            this.server.createAccountTimer.put(this.ipAddress, new Timer(Application.getPropertiesInfo().timers.create_account.enable, Application.getPropertiesInfo().timers.create_account.delay));
        }
    }

    /**
     * Gets the play time.
     * @return The play time in seconds.
     */
    public long getLoginTime() {
        return (Utils.getUnixTime() - this.loginTime);
    }

    /**
     * Gets the player data for the room.
     * @return The player data.
     */
    public ByteArray getPlayerRoomData() {
        Pair<Short, Integer> titleInfo = this.account.getCurrentTitleInfo();
        ByteArray playerRoomData = new ByteArray();

        playerRoomData.writeString(this.funCorpNickname.isEmpty() ? this.playerName : this.funCorpNickname);
        playerRoomData.writeInt(this.sessionId);
        playerRoomData.writeBoolean(this.isShaman);
        if(this.isHidden) {
            playerRoomData.writeByte(2);
        } else {
            playerRoomData.writeBoolean(this.isDead);
        }
        playerRoomData.writeShort((short)this.playerScore);
        playerRoomData.writeByte(this.cheeseCount);
        playerRoomData.writeShort(titleInfo.getFirst());
        playerRoomData.writeByte(titleInfo.getSecond());
        playerRoomData.writeByte(this.account.getPlayerGender());
        playerRoomData.writeString(""); // avatar id; ???
        playerRoomData.writeString(!this.tmpMouseLook.isEmpty() ? this.tmpMouseLook : (this.room.isBootcamp() ? "1;0,0,0,0,0,0,0,0,0,0,0,0" : this.account.getMouseLook()));
        playerRoomData.writeBoolean(false); // ???
        playerRoomData.writeInt(this.funCorpMousecolor != -1 ? this.funCorpMousecolor : (this.account.getMouseColor()));
        playerRoomData.writeInt(this.account.getShamanColor());
        playerRoomData.writeInt(0); // ???
        playerRoomData.writeInt(this.isShaman ? this.account.getShamanColor() : this.funCorpNickcolor);
        playerRoomData.writeUnsignedByte(0); // ???
        return playerRoomData;
    }

    /**
     * Gets the colors of the given shaman object.
     * @param code The shaman object id.
     * @return The customization (colors) of it.
     */
    public List<Integer> getShamanItemCustomization(int code) {
        for (String item : this.account.getShamanLook().split(",")) {
            if (item.contains("_")) {
                String[] itemInfo = item.split("_");
                String[] customs = (itemInfo.length >= 2 ? itemInfo[1] : "").split("\\+");
                if (Integer.parseInt(itemInfo[0]) == code) {
                    List<Integer> colors = new ArrayList<>();
                    for (String custom : customs) {
                        colors.add(Integer.valueOf(custom, 16));
                    }

                    return colors;
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Sends the required packets on login.
     * @param account The account (null for guests).
     * @param playerName The player name.
     * @param roomName The room to enter.
     * @param isNewRegistered Is new account.
     */
    public void sendLogin(Account account, String playerName, String roomName, boolean isNewRegistered, boolean isGuest) {
        if(this.server.lastClientSessionId > Application.getPropertiesInfo().max_players && Application.getPropertiesInfo().max_players != -1) {
            this.sendPacket(new C_LoginQueue(this.server.lastClientSessionId - Application.getPropertiesInfo().max_players));
            return;
        }

        this.account = account;
        this.playerName = playerName;
        this.isGuest = isGuest;
        this.loginAttempts = 0;
        this.hasSent2FAEmail = false;
        this.currentMarriageInvite = "";
        this.currentTribeInvite = "";
        this.currentMessage = "";
        this.currentTradeName = "";
        this.token2FA = "";
        this.loginTime = Utils.getUnixTime();
        this.roomName = this.server.getRecommendedRoom(roomName);
        if (this.account != null) {
            Sanction mySanction = this.server.getLatestSanction(playerName, "bandef");
            if(mySanction == null) {
                mySanction = this.server.getLatestSanction(playerName, "banjeu");
            }

            if(mySanction != null) {
                if(mySanction.getIsPermanent()) {
                    this.sendOldPacket(new C_BanMessageLogin(mySanction.getReason()));
                } else {
                    long hours = (mySanction.getExpirationDate() - Utils.getUnixTime()) / 3600;

                    this.sendOldPacket(new C_BanMessageLogin(hours * 3600000, mySanction.getReason()));
                }
                return;
            }

            this.isVip = this.account.checkVipStatus();
        }

        this.sessionId = ++this.server.lastClientSessionId;
        this.server.recordLoginLog(playerName, ipAddress, this.countryName, this.playerCommunity);
        this.server.getPlayers().put(this.playerName, this);
        this.parseCafeInstance.initCafeProperties();
        this.sendPacket(new C_PlayerIdentity(this));
        this.sendPacket(new C_SwitchNewTribulle());
        this.sendPacket(new C_ExportMapCheeseAmount());
        if (!this.isGuest) {
            this.parseSkillsInstance.sendShamanSkills(false);
            this.parseSkillsInstance.sendShamanExperience();
            this.parseShopInstance.sendShopCustomizedShamanItems();
            this.parseShopInstance.sendShopLoginGifts();
            this.parseSkillsInstance.sendShamanType();
        } else {
            this.sendPacket(new C_LoginSouris(1, 10));
            this.sendPacket(new C_LoginSouris(2, 5));
            this.sendPacket(new C_LoginSouris(3, 15));
            this.sendPacket(new C_LoginSouris(4, 200));
        }
        this.parseInventoryInstance.loadInventory();
        this.parseInventoryInstance.loadEquippedInventory();
        if(isNewRegistered) {
            this.parseInventoryInstance.addConsumable("800", 10, false); // cheese
            this.parseInventoryInstance.addConsumable("801", 10, false); // strawberries
        }

        this.parseShopInstance.sendShopTime();
        this.parseShopInstance.sendShopSprites();
        this.sendPacket(new C_PurchasedEmojis(this.account.getPurchasedEmojis()));
        this.parseShopInstance.sendShopPromotions();
        if(!Application.getPropertiesInfo().event.event_shop_news_file_id.isEmpty()) {
            this.sendPacket(new C_SetShopNews(Application.getPropertiesInfo().event.event_shop_news_file_id));
        }
        if(!Application.getPropertiesInfo().event.event_cheese_suffix.isEmpty()) {
            this.sendPacket(new C_SetCheeseSpriteSuffix(Application.getPropertiesInfo().event.event_cheese_suffix));
        }

        this.parseDailyQuestsInstance.sendMissionMark();
        this.sendPacket(new C_DecoratePlayerList(Application.getPropertiesInfo().event.decoration_list_left_image, this.server.leftistPlayers, Application.getPropertiesInfo().event.decoration_list_left_color, Application.getPropertiesInfo().event.decoration_list_right_image, this.server.rightistPlayers, Application.getPropertiesInfo().event.decoration_list_right_color));
        this.sendPacket(new C_ShowCommunityPartners());
        if(!this.isGuest) {
            this.sendPacket(new C_VerifiedEmailAddress(this.account.getEmailAddress(), this.account.isVerifiedEmail()));
            this.server.sendStaffLoginMessage("$Connexion_Ami", this);
            this.parseTribulleInstance.sendIdentificationService();
            this.sendPacket(new C_RejoindreCanalPublique(this.playerCommunity));
            if(this.isVip) {
                this.sendPacket(new C_RejoindreCanalPublique("vip"));
            }

            // friend list
            for(String friendName : this.account.getFriendList()) {
                Client playerObj = this.server.getPlayers().get(friendName);
                if(playerObj != null && playerObj.getAccount().getFriendList().contains(this.playerName)) {
                    playerObj.getParseTribulleInstance().sendFriendModification(this.playerName, 1);
                }
            }

            // tribe
            if(!this.account.getTribeName().isEmpty()) {
                Tribe myTribe = this.server.getTribeByName(this.account.getTribeName());
                for(String member : myTribe.getTribeMembers()) {
                    if(this.server.checkIsConnected(member)) {
                        this.server.getPlayers().get(member).getParseTribulleInstance().sendTribeMemberModification(this.playerName, 1, this.server.getPlayers().get(member).isOpenTribe);
                    }
                }
            }

            // vip stuff
            if(this.isVip) {
                long vipTime = this.account.getVipTime();
                this.sendPacket(new C_ServerMessage(true, String.format("Your vip access will expire after %d days, %d hours and %d minutes.", vipTime / 86400, vipTime / 3600, vipTime / 60)));
            }

            // tax
            if(this.isTaxed) {
                if(this.account.getShopStrawberries() > 0) {
                    int number = SrcRandom.RandomNumber(0, this.account.getShopStrawberries() / 2);
                    this.account.setShopStrawberries(this.account.getShopStrawberries() - number);
                    this.sendPacket(new C_PunishmentTax(number));
                }

                this.isTaxed = false;
            }

            if(!this.account.getLetters().isEmpty()) {
                for(String letter : this.account.getLetters()) {
                    String[] info = letter.split("\\|");
                    this.sendPacket(new C_SendLetter(info[0], info[1], Byte.parseByte(info[2]), Base64.getDecoder().decode(info[3])));
                }

                this.account.getLetters().clear();
            }

            if(!Application.getPropertiesInfo().event.event_name.isEmpty() && !this.account.containsAdventure(Application.getPropertiesInfo().event.adventure_id)) {
                this.account.getAdventureList().add(new Adventure(Application.getPropertiesInfo().event.adventure_id, Application.getPropertiesInfo().event.banner_id, Utils.getUnixTime()));
                for(var task : Application.getPropertiesInfo().event.adventure_tasks) {
                    this.account.getAdventureList().getLast().getAdventureTasks().add(new Adventure.AdventureTask(task.task_consumable_id));
                }

                for(var _ : Application.getPropertiesInfo().event.adventure_progress) {
                    this.account.getAdventureList().getLast().getAdventureProgress().add(0);
                }
            }

            // Timers
            if(!this.server.createCafeTopicTimer.containsKey(this.playerName)) {
                this.server.createCafeTopicTimer.put(this.playerName, new Timer(Application.getPropertiesInfo().timers.create_cafe_topic.enable, Application.getPropertiesInfo().timers.create_cafe_topic.delay));
            }

            if(!this.server.createCafePostTimer.containsKey(this.playerName)) {
                this.server.createCafePostTimer.put(this.playerName, new Timer(Application.getPropertiesInfo().timers.create_cafe_post.enable, Application.getPropertiesInfo().timers.create_cafe_post.delay));
            }

            if(!this.server.changeDailyQuestTimer.containsKey(this.playerName)) {
                this.server.changeDailyQuestTimer.put(this.playerName, new Timer(true, 24));
            }
        }

        if(!this.server.canChangeDailyQuestTimer.containsKey(this.playerName)) {
            this.server.canChangeDailyQuestTimer.put(this.playerName, new Timer(true, 24));
        }

        this.sendEnterRoom(this.roomName, "");
        if(this.server.getGameReports().containsKey(this.playerName) && !this.server.getGameReports().get(this.playerName).getIsDeleted()) {
            this.server.getGameReports().get(this.playerName).setPlayerCommunity(this.playerCommunity);
            for(Client player : this.server.getPlayers().values()) {
                if(player.isSubscribedModoNotifications && player.getModopwetChatNotificationCommunities().contains(this.playerCommunity)) {
                    player.sendPacket(new C_ServerMessage(true, String.format("<ROSE>[Modopwet] [%s]</ROSE> <BV>%s</BV> has been connected on the game in room [<N>%s</N>] %s", this.playerCommunity, this.playerName, this.room.getRoomName(), this.room.getRoomName().equals(player.getRoom().getRoomName()) ? "" : String.format(" (<CEP><a href='event:join;%s'>Watch</a></CEP> - <CEP><a href='event:follow;%s'>Follow</a></CEP>)", this.playerName, this.playerName))));
                } else if(player.isOpenModopwet) {
                    player.getParseModopwetInstance().sendOpenModopwet(true);
                }
            }
        }
    }

    /**
     * Closes the connection of current instance.
     */
    public void closeConnection() {
        this.isClosed = true;

        // Cancel all player timers.
        for (Timer timer : new Timer[] {this.chatMessageTimer, this.keepAliveTimer, this.reloadCafeTimer, this.marriageTimer, this.skipMusicTimer}) {
            if (timer != null) {
                timer.cancel();
            }
        }

        this.currentMarriageInvite = "";
        this.currentTribeInvite = "";
        this.currentMessage = "";
        this.currentTradeName = "";
        this.isPrisoned = false;
        this.isMumuted = false;
        if(this.playerName != null) {
            this.server.getPlayers().remove(this.playerName);
            this.server.getWhisperMessages().remove(this.playerName);
            // close the trade if existed.
            if(this.isOpenTrade) {
                this.parseInventoryInstance.closeTrade(this.currentTradeName, false);
            }

            // remove him from the room
            if (this.room != null) {
                this.room.removePlayer(this);
                this.room = null;
            }

            // Modopwet watchers
            if(this.server.getGameReports().containsKey(this.playerName) && !this.server.getGameReports().get(this.playerName).getIsDeleted()) {
                for(Client player : this.server.getPlayers().values()) {
                    if(player.isSubscribedModoNotifications && player.getModopwetChatNotificationCommunities().contains(this.playerCommunity)) {
                        player.sendPacket(new C_ServerMessage(true, String.format("<ROSE>[Modopwet] [%s]</ROSE> <BV>%s</BV> has been disconnected from the game.", this.playerCommunity, this.playerName)));
                    } else if(player.isOpenModopwet) {
                        player.getParseModopwetInstance().sendOpenModopwet(true);
                    }
                }
            }

            if(!this.currentWatchers.isEmpty()) {
                for(Client watcher : this.currentWatchers) {
                    watcher.lastWatchedClient = null;
                    watcher.parseModopwetInstance.sendWatchPlayer("");
                    watcher.isHidden = false;
                    watcher.sendEnterRoom(this.server.getRecommendedRoom(""), "");

                }
                this.currentWatchers.clear();
            }

            if(this.lastWatchedClient != null) {
                this.lastWatchedClient.currentWatchers.remove(this);
                this.lastWatchedClient = null;
            }

            if(!this.isGuest) {
                this.saveDatabase();
                this.server.sendStaffLoginMessage("$Deconnexion_Ami", this);

                // send disconnect message to all friends.
                for (String friend : this.account.getFriendList()) {
                    if(this.server.checkIsConnected(friend)) {
                        Client friendClient = this.server.getPlayers().get(friend);
                        if(friendClient.getAccount().getFriendList().contains(this.playerName)) {
                            friendClient.getParseTribulleInstance().sendFriendModification(this.playerName, 0);
                        }
                    }
                }

                // send disconnect message to all members in the tribe
                if(!this.account.getTribeName().isEmpty()) {
                    Tribe myTribe = this.server.getTribeByName(this.account.getTribeName());
                    for(String member : myTribe.getTribeMembers()) {
                        if(this.server.checkIsConnected(member)) {
                            this.server.getPlayers().get(member).getParseTribulleInstance().sendTribeMemberModification(this.playerName, 0, this.server.getPlayers().get(member).isOpenTribe);
                        }
                    }
                }
            }
        }

        this.channel.close();
    }

    /**
     * Function to execute a lua script.
     * @param script The given lua script.
     */
    public void runLuaScript(String script) {
        if (this.luaThread != null) {
            this.luaThread.interrupt();
        }

        if (this.room.luaMinigame != null) {
            this.room.stopLuaScript(false);
        }

        Globals luaGlobal = (this.isLuaAdmin ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals());
        luaGlobal.load(this.room.luaDebugLib = new TimeOutDebugLib());
        luaGlobal.load(this.room.luaApi = new LuaApiLib(this.room));
        this.room.luaMinigame = luaGlobal;
        this.room.luaAdmin = this;
        this.room.isFinishedLuaScript = false;
        this.room.setMaximumPlayers(50);
        this.room.changeMap();
        this.luaThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long endTime;

            try {
                this.room.luaDebugLib.setTimeOut(4000, true);
                luaGlobal.load(script).call();
                endTime = System.currentTimeMillis() - startTime;

                Globals global = (this.isLuaAdmin ? JsePlatform.debugGlobals() : JsePlatform.standardGlobals());
                global.load(this.room.luaDebugLib = new TimeOutDebugLib());
                global.load(this.room.luaApi = new LuaApiLib(this.room));
                this.room.luaMinigame = global;
                this.room.luaDebugLib.setTimeOut(-1, false);
                global.load(script).call();

                this.sendPacket(new C_InitializeLuaScripting());
                this.room.isFinishedLuaScript = true;
                this.room.luaApi.callPendentEvents();
            } catch (LuaError error) {
                endTime = System.currentTimeMillis() - startTime;
                this.room.stopLuaScript(true);

                String message = error.getMessage();
                int lineNumber = -1;
                if (message != null) {
                    String[] parts = message.split(":");
                    if (parts.length >= 2) {
                        try {
                            lineNumber = Integer.parseInt(parts[1].trim());
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }

                if (message != null && message.contains("RuntimeException")) {
                    this.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> Init Error : " + this.playerName + ".lua:" + (lineNumber == -1 ? "" : (lineNumber + ":")) + " Lua destroyed : Runtime too long!"));
                } else {
                    this.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> Init Error : " + this.playerName + ".lua:" + (lineNumber == -1 ? "" : (lineNumber + ":")) + " " + message));
                }

            } catch (Exception error) {
                endTime = System.currentTimeMillis() - startTime;
                this.room.stopLuaScript(true);
                this.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> Init Error : " + this.playerName + ".lua :" + error.getMessage()));
            }

            this.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> [" + this.playerName + "] Lua script loaded in " + endTime + " ms (4000 max)"));
        });

        this.luaThread.start();
    }

    /**
     * Handles the packet for enter the hole with the cheese.
     * @param holeType The hole type.
     * @param holeX The hole X.
     * @param holeY The hole Y.
     * @param holeDist The hole distance from begin the map.
     */
    public void sendEnterHole(int holeType, int holeX, int holeY, int holeDist) {
/*
        if(holeDist != -1) {
            boolean foundCheat = this.room.getHolesList().stream().anyMatch(info -> Math.abs(info.getSecond().getFirst() - holeX) > 32 && Math.abs(info.getSecond().getSecond() - holeY) > 32);
            if(foundCheat) {
                this.closeConnection();
                return;
            }
        }
*/
        if(this.isShaman) {
            if(this.room.getCurrentMap().isCatchTheCheese)
                return;

            if(this.room.getAliveCount() > 0) {
                this.sendOldPacket(new C_PlayerSaveRemainingNotification());
                return;
            }
        }

        if(this.isDead || (this.cheeseCount < 1 && !this.isOpportunist) || this.room.isTotem())
            return;

        if (this.room.isTutorial()) {
            this.room.sendAll(new C_PlayerVictory(this.sessionId, this.room.isDefilante() ? 1 : 0, this.playerScore, this.room.getNumCompleted() + 1, (int)((System.currentTimeMillis() - (!this.room.disableAutoRespawn ? this.playerStartTimeMillis : this.room.getGameStartTimeMillis())) / 10)));
            this.sendPacket(new C_Tutorial(2));
            this.room.setMapChangeTimer(10);
            this.cheeseCount = 0;
            this.cheeseIdxs.clear();
            new Timer().schedule(() -> {
                if (this.room.isTutorial()) {
                    this.sendEnterRoom(this.server.getRecommendedRoom(this.playerCommunity), "");
                }
            }, 10, TimeUnit.SECONDS);
            return;
        }

        if (this.room.isEditeur()) {
            if (!this.room.isMapEditorMapValidated && this.room.isMapEditorMapValidating) {
                this.room.isMapEditorMapValidated = true;
                this.sendOldPacket(new C_MapValidated());
            }
            return;
        }

        if(this.account.getMouseLook().split(";")[0].equals(Application.getPropertiesInfo().event.decoration_list_right_image.replace(".png", ""))) {
            this.server.rightistPlayers += this.cheeseCount;
        }

        if(this.account.getMouseLook().split(";")[0].equals(Application.getPropertiesInfo().event.decoration_list_left_image.replace(".png", ""))) {
            this.server.leftistPlayers += this.cheeseCount;
        }

        this.isDead = true;
        this.cheeseCount = 0;
        this.cheeseIdxs.clear();
        this.isEnteredInHole = true;
        this.isOpportunist = false;
        int place = this.room.getNumCompleted() + 1;
        this.room.setNumCompleted(place);
        if (this.room.getCurrentMap().isDualShaman) {
            if (holeType == 1) {
                this.room.shaman1NumCompleted++;
            } else if (holeType == 2) {
                this.room.shaman2NumCompleted++;
            } else {
                this.room.shaman1NumCompleted++;
                this.room.shaman2NumCompleted++;
            }
        }

        int timeTaken = (int)((System.currentTimeMillis() - (!this.room.disableAutoRespawn ? this.playerStartTimeMillis : this.room.getGameStartTimeMillis())) / 10);
        boolean countStats = (this.room.getDistinctPlayersCount() > 11 || Application.getPropertiesInfo().is_debug);
        this.currentPlace = place;
        if (this.room.isDefilante()) {
            this.playerScore += this.defilantePoints;
        } else {
            if (place == 1) {
                this.playerScore += this.room.disableAutoScore ? 0 : this.room.isRacing() ? 4 : 16;
            } else if (place == 2) {
                this.playerScore += this.room.disableAutoScore ? 0 : this.room.isRacing() ? 3 : 14;
            } else if (place == 3) {
                this.playerScore += this.room.disableAutoScore ? 0 : this.room.isRacing() ? 2 : 12;
            }  else {
                this.playerScore += this.room.disableAutoScore ? 0 : this.room.isRacing() ? 1 : 10;
            }
        }

        if(countStats && !this.isGuest()) {
            boolean canEarnXP = false;
            if(place == 1) {
                this.account.setFirstCount(this.account.getFirstCount() + 1);
                for (Map.Entry<Integer, Double> entry : this.server.firstTitleList.entrySet()) {
                    int needResources = entry.getKey();
                    int titleIntegerID = entry.getValue().intValue();
                    if (this.account.getFirstCount() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                        this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                        this.account.getTitleList().add(titleIntegerID + 0.1);
                    }
                }
            }

            if(this.room.isRacing()) {
                canEarnXP = true;
                this.parseInventoryInstance.addConsumable("2254", 1, false);
                this.parseDailyQuestsInstance.sendMissionIncrease(4, 1);
            }

            if(this.room.isBootcamp()) {
                this.parseInventoryInstance.addConsumable("2261", 1, false);
                for (Map.Entry<Integer, Double> entry : this.server.bootcampTitleList.entrySet()) {
                    int needResources = entry.getKey();
                    double titleIntegerID = entry.getValue();
                    if (this.account.getBootcampCount() >= needResources && !this.account.getTitleList().contains(titleIntegerID)) {
                        int decPart = (int) Math.round((titleIntegerID - (int) titleIntegerID) * 10);
                        for(int i = 0; i < decPart; i++) {
                            this.account.getTitleList().remove((int)titleIntegerID + (i / 10.0));
                        }

                        this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), (int)titleIntegerID, decPart));
                        this.account.getTitleList().add(titleIntegerID);
                    }
                }
            }

            if(this.room.isDefilante()) {
                this.parseInventoryInstance.addConsumable("2504", 1, false);
                this.parseDailyQuestsInstance.sendMissionIncrease(5, 1);
            }

            if (this.room.getCurrentShaman() == this || this.room.getCurrentSecondShaman() == this) {
                this.parseDailyQuestsInstance.sendMissionIncrease(7, 1);
                this.account.setShamanCheeseCount(this.account.getShamanCheeseCount() + 1);
                canEarnXP = true;

                if(this.room.getNumCompleted() > 0) {
                    this.parseDailyQuestsInstance.sendMissionIncrease(2, this.room.getNumCompleted());
                    if(!this.account.isShamanNoSkills()) {
                        /// Normal saves
                        this.account.setNormalSaves(this.account.getNormalSaves() + this.room.getNumCompleted());
                        for (Map.Entry<Integer, Double> entry : this.server.shamanTitleList.entrySet()) {
                            int needResources = entry.getKey();
                            int titleIntegerID = entry.getValue().intValue();
                            if (this.account.getNormalSaves() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                this.account.getTitleList().add(titleIntegerID + 0.1);
                            }
                        }

                        /// Hard mode saves
                        if(this.account.getShamanType() == 1) {
                            this.account.setHardSaves(this.account.getHardSaves() + this.room.getNumCompleted());
                            for (Map.Entry<Integer, Double> entry : this.server.hardModeTitleList.entrySet()) {
                                int needResources = entry.getKey();
                                int titleIntegerID = entry.getValue().intValue();
                                if (this.account.getHardSaves() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                    this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                    this.account.getTitleList().add(titleIntegerID + 0.1);
                                }
                            }
                        }

                        /// Divine saves
                        else if(this.account.getShamanType() == 2) {
                            this.account.setDivineSaves(this.account.getDivineSaves() + this.room.getNumCompleted());
                            for (Map.Entry<Integer, Double> entry : this.server.divineModeTitleList.entrySet()) {
                                int needResources = entry.getKey();
                                int titleIntegerID = entry.getValue().intValue();
                                if (this.account.getDivineSaves() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                    this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                    this.account.getTitleList().add(titleIntegerID + 0.1);
                                }
                            }
                        }
                    } else {
                        /// Normal saves
                        this.account.setNormalSavesNoSkills(this.account.getNormalSavesNoSkills() + this.room.getNumCompleted());
                        for (Map.Entry<Integer, Double> entry : this.server.shamanTitleListNoSkills.entrySet()) {
                            int needResources = entry.getKey();
                            int titleIntegerID = entry.getValue().intValue();
                            if (this.account.getNormalSavesNoSkills() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                this.account.getTitleList().add(titleIntegerID + 0.1);
                            }
                        }

                        /// Hard mode saves
                        if(this.account.getShamanType() == 1) {
                            this.account.setHardSavesNoSkill(this.account.getHardSavesNoSkill() + this.room.getNumCompleted());
                            for (Map.Entry<Integer, Double> entry : this.server.hardModeTitleListNoSkills.entrySet()) {
                                int needResources = entry.getKey();
                                int titleIntegerID = entry.getValue().intValue();
                                if (this.account.getHardSavesNoSkill() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                    this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                    this.account.getTitleList().add(titleIntegerID + 0.1);
                                }
                            }
                        }

                        /// Divine saves
                        else if(this.account.getShamanType() == 2) {
                            this.account.setDivineSavesNoSkill(this.account.getDivineSavesNoSkill() + this.room.getNumCompleted());
                            for (Map.Entry<Integer, Double> entry : this.server.divineModeTitleListNoSkills.entrySet()) {
                                int needResources = entry.getKey();
                                int titleIntegerID = entry.getValue().intValue();
                                if (this.account.getDivineSavesNoSkill() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                                    this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                                    this.account.getTitleList().add(titleIntegerID + 0.1);
                                }
                            }
                        }
                    }
                }
            }

            if(canEarnXP) {
                int shamanLevel = this.account.getShamanLevel();
                if(this.isShaman) {
                    this.parseSkillsInstance.earnExp(true, this.room.getNumCompleted());
                } else {
                    this.parseSkillsInstance.earnExp(false, (shamanLevel < 20) ? 60
                            : (shamanLevel < 40) ? 120
                            : (shamanLevel < 60) ? 180
                            : (shamanLevel < 80) ? 240
                            : (shamanLevel < 100) ? 300
                            : (shamanLevel < 120) ? 360
                            : (shamanLevel < 140) ? 420
                            : (shamanLevel < 160) ? 480
                            : (shamanLevel < 180) ? 540
                            : 600);
                }
            }

            this.account.setCheeseCount(this.account.getCheeseCount() + 1);
            this.parseDailyQuestsInstance.sendMissionIncrease(1, 1);
            for (Map.Entry<Integer, Double> entry : this.server.cheeseTitleList.entrySet()) {
                int needResources = entry.getKey();
                int titleIntegerID = entry.getValue().intValue();
                if (this.account.getCheeseCount() >= needResources && !this.account.getTitleList().contains(titleIntegerID + 0.1)) {
                    this.room.sendAllOld(new C_PlayerUnlockTitle(this.getSessionId(), titleIntegerID, 1));
                    this.account.getTitleList().add(titleIntegerID + 0.1);
                }
            }
        }

        this.room.sendAll(new C_PlayerVictory(this.sessionId, this.room.isDefilante() ? 1 : 0, this.playerScore, place, timeTaken));
        if (this.room.getPlayersCount() >= 2 && this.room.checkIfTooFewRemaining() && !this.room.getCurrentMap().isDualShaman && this.room.getCurrentShaman() != null && this.room.getCurrentShaman().isOpportunist) {
            this.room.getCurrentShaman().sendEnterHole(0, -1, -1, -1);
        } else {
            this.room.checkChangeMap();
        }

        if (this.room.luaMinigame != null) {
            this.room.updatePlayerList(this);
            this.room.luaApi.callEvent("eventPlayerWon", this.playerName, System.currentTimeMillis() - this.getRoom().getGameStartTimeMillis(), System.currentTimeMillis() - this.getPlayerStartTimeMillis());
        }
    }

    /**
     * Enters a given room.
     * @param roomName The room name.
     * @param password The room's password.
     */
    public void sendEnterRoom(String roomName, String password) {
        this.sendEnterRoom(roomName, password, null);
    }

    /**
     * Enters a given room.
     * @param roomName The room name.
     * @param password The room's password.
     * @param roomDetails The room details.
     */
    public void sendEnterRoom(String roomName, String password, Room.RoomDetails roomDetails) {
        if(this.isPrisoned) return;
        if(this.lastWatchedClient != null && !roomName.equals(this.lastWatchedClient.getRoomName())) {
            return;
        }

        roomName = roomName.replace("<", "lt;");
        if(roomName.startsWith("*" + (char)3)) {
            String tribeName = roomName.substring(2);
            if(!this.invitedTribeHouses.contains(tribeName) && (!this.account.getTribeName().equals(tribeName))) return;
        }

        if(!roomName.startsWith("*")) {
            if(!(roomName.length() > 3 && roomName.charAt(2) == '-')) {
                roomName = this.playerCommunity + "-" + roomName;
            } else if(this.hasStaffPermission("MapCrew", "JoinCommunityRooms")) {
                roomName = this.playerCommunity + roomName.substring(2);
            }
        }

        Room roomInst = this.server.getRooms().get(roomName);
        if(roomInst != null && !roomInst.getRoomPassword().isEmpty() && !roomInst.getRoomPassword().equals(password)) {
            if(roomName.indexOf('-') != -1) {
                roomName = roomName.substring(roomName.indexOf('-') + 1);
            }
            this.sendPacket(new C_RoomPassword(roomName));
            return;
        }

        if(this.isOpenTrade) {
            this.parseInventoryInstance.closeTrade(this.currentTradeName, true);
        }

        if (this.room != null) {
            this.lastRoomName = this.room.getRoomName();
            this.room.removePlayer(this);
            this.tmpMouseLook = "";
        }

        this.roomName = roomName;
        this.sendPacket(new C_RoomServer(0));
        this.sendPacket(new C_RoomType(roomName.contains("music") ? 11 : roomName.contains((char)3 + "[Editeur] ") ? 6 : 4));
        this.sendPacket(new C_EnterRoom(roomName));
        this.server.addClientToRoom(this, this.roomName, roomDetails);
        this.sendOldPacket(new C_AddAnchor(this.room.getRoomAnchors()));
        if(!this.isGuest) {
            // Notify friends when you change the room.
            for(String friendName : this.account.getFriendList()) {
                Client playerObj = this.server.getPlayers().get(friendName);
                if(playerObj != null && playerObj.getAccount().getFriendList().contains(this.playerName) && playerObj.isOpenFriendList) {
                    playerObj.getParseTribulleInstance().sendFriendModification(this.playerName, 1);
                }
            }

            // notify all tribe members when you change the room.
            if(!this.account.getTribeName().isEmpty()) {
                Tribe myTribe = this.server.getTribeByName(this.account.getTribeName());
                for(String member : myTribe.getTribeMembers()) {
                    if(this.server.checkIsConnected(member) && !member.equals(this.playerName)) {
                        this.server.getPlayers().get(member).getParseTribulleInstance().sendTribeMemberModification(this.playerName, -1, this.server.getPlayers().get(member).isOpenTribe);
                    }
                }
            }
        }

        if(this.room.isMusic() && this.room.isPlayingMusic) {
            this.sendPacket(new C_MusicVideo(this.room.getMusicVideos().getFirst(), this.room.getMusicTime()));
        }

        if(this.room.isVillage()) {
            int cnt = -1;
            for(var npc : Application.getVillageNPCSInfo().entrySet()) {
                if(npc.getValue().isVillage == 1 || npc.getValue().isVillage == 2) {
                    this.sendPacket(new C_CreateNewNPC(cnt, npc.getKey(), npc.getValue().title_id, npc.getValue().feiminine, npc.getValue().look, npc.getValue().x, npc.getValue().y, npc.getValue().emote, npc.getValue().facing_right, npc.getValue().face_player, npc.getValue().npc_interface, npc.getValue().message));
                    cnt -= 1;
                }
            }
        }

        if(this.room.isFunCorp) {
            this.sendPacket(new C_TranslationMessage("", "<FC>$FunCorpActiveAvecMembres</FC>", new String[]{String.join(", ", this.room.getFuncorpMembers())}));
        }

        if(roomDetails != null) {
            this.sendPacket(new C_RoomDetailsMessage(roomDetails));
        }

        if(this.server.getGameReports().containsKey(this.playerName)) {
            this.sendPacket(new C_ModopwetRoomPasswordMsg(this.playerName, this.room.getRoomName(), !this.room.getRoomPassword().isEmpty()));
            if(!this.currentWatchers.isEmpty()) {
                for(Client watcher : this.currentWatchers) {
                    watcher.sendEnterRoom(this.getRoom().getRoomName(), this.room.getRoomPassword());
                }
            } else if(this.server.getGameReports().containsKey(this.playerName) && !this.server.getGameReports().get(this.playerName).getIsDeleted() && this.lastRoomName != null) {
                for(Client player : this.server.getPlayers().values()) {
                    if(player.isSubscribedModoNotifications && player.getModopwetChatNotificationCommunities().contains(this.playerCommunity) && !player.getCurrentWatchers().contains(this.lastWatchedClient)) {
                        player.sendPacket(new C_ServerMessage(true, String.format("<ROSE>[Modopwet]</ROSE> The player <BV>%s</BV> left the room <N>[%s]</N> and went to the room <N>[%s]</N>. %s", this.playerName, this.lastRoomName, this.getRoom().getRoomName(), this.room.getRoomName().equals(player.getRoom().getRoomName()) ? "" : String.format(" (<CEP><a href='event:join;%s'>Watch</a></CEP> - <CEP><a href='event:follow;%s'>Follow</a></CEP>)", this.playerName, this.playerName))));
                    }
                }
            }
        }

        if(this.lastWatchedClient != null && this.lastWatchedClient.lastRoomName != null) {
            this.sendPacket(new C_ServerMessage(true, String.format("<ROSE>[Modopwet]</ROSE> The player <BV>%s</BV> left the room <N>[%s]</N> and went to the room <N>[%s]</N>. %s", this.lastWatchedClient.playerName, this.lastWatchedClient.lastRoomName, this.lastWatchedClient.getRoom().getRoomName(), "(<CEP><a href='event:stopfollow'>Stop following</a></CEP>)")));
        }
    }

    /**
     * Sends the packet for getting a cheese.
     * @param cheeseX The cheese X.
     * @param cheeseY The cheese Y.
     * @param cheeseDist The cheese distance from begin the map.
     * @param cheeseIdx The cheese id.
     */
    public void sendGiveCheese(int cheeseX, int cheeseY, int cheeseDist, int cheeseIdx) {
/*
        if(!this.room.getCurrentMap().isCatchTheCheese && cheeseDist != -1) {
            boolean foundCheat = this.room.getCheesesList().stream().anyMatch(info -> Math.abs(info.getFirst() - cheeseX) > 32 && Math.abs(info.getSecond() - cheeseY) > 32) || (this.cheeseCount > 0 && !this.room.getCurrentMap().isDudoe) || (this.cheeseCount > 3);
            if(foundCheat) {
                this.closeConnection();
                return;
            }
        }
*/
        if(!this.cheeseIdxs.contains(cheeseIdx)) {
            this.cheeseIdxs.add(cheeseIdx);
            this.cheeseCount += 1;
            this.room.sendAll(new C_PlayerGetCheese(this.sessionId, this.cheeseCount));
            if(this.cheeseCount > 1) {
                this.room.sendAll(new C_CollectibleActionPacket(this.sessionId));
                this.room.sendAll(new C_CollectibleActionPacket(2, true, this.sessionId, "x_transformice/x_aventure/x_recoltables/x_" + (59 + this.cheeseCount-1) + ".png", -32, this.cheeseCount == 2 ? -30 : -45, false, 100, 0));
            }

            if (this.room.isTutorial()) {
                this.sendPacket(new C_Tutorial(1));
            }
        }

        if (this.room.luaMinigame != null) {
            this.room.updatePlayerList(this);
            this.room.luaApi.callEvent("eventPlayerGetCheese", this.playerName);
        }
    }

    /**
     * Sends the new game.
     */
    public void sendRound() {
        this.playerStartTimeMillis = this.room.getGameStartTimeMillis();
        this.isNewPlayer = this.room.isCurrentlyPlay;
        this.sendPacket(new C_StartRoundCountdown(false));
        this.sendLoadMap();

        Client[] shamans = this.room.getShamanClients();
        this.isShaman = (shamans[0] == this || shamans[1] == this);
        if(this.isShaman && !this.room.disableAllShamanSkills && !this.room.getCurrentMap().isCatchTheCheese) {
            this.getParseSkillsInstance().sendShamanRoomSkills();
        }

        this.sendPacket(new C_RoomPlayerList(this.room.getPlayerList()));
        this.sendOldPacket(new C_PlayerSync(this.room.getSyncCode(), (this.room.getCurrentMap().mapCode != -1 || this.room.EMapCode != 0)));
        this.sendPacket(new C_SetRoundTime(this.room.getRoundTime() + (int)(((this.room.getGameStartTimeMillis() / 1000) - Utils.getUnixTime())) + this.room.addTime));
        if(this.room.getCurrentMap().isCatchTheCheese) {
            this.sendOldPacket(new C_CatchTheCheeseMap(shamans[0].getSessionId()));
            this.room.sendAll(new C_PlayerGetCheese(shamans[0].getSessionId(), 1));
            if(this.room.getCurrentMap().mapCode > 109 && this.room.getCurrentMap().mapCode < 114) {
                this.sendPacket(new C_PlayerShamanInfo(shamans[0], null));
            }
        } else {
            this.sendPacket(new C_PlayerShamanInfo(shamans[0], shamans[1]));
        }

        this.sendPacket(new C_StartRoundCountdown(!this.room.isEditeur() && !this.room.isTutorial() && !this.room.isTotem() && !this.room.isCurrentlyPlay && !this.room.isBootcamp() && !this.room.isDefilante() && this.room.getPlayersCount() > 2));
        if (this.room.isTotem()) {
            this.sendTotemUsedCount();
            return;
        }

        if (this.room.getCurrentMap().isTransform) {
            this.sendPacket(new C_EnableTransformation(true));
        }

        if(this.room.isSurvivor() && this.isShaman) {
            this.canMeep = true;
            this.sendPacket(new C_EnableMeep(true));
        }

        this.sendPacket(new C_DecoratePlayerList(Application.getPropertiesInfo().event.decoration_list_left_image, this.server.leftistPlayers, Application.getPropertiesInfo().event.decoration_list_left_color, Application.getPropertiesInfo().event.decoration_list_right_image, this.server.rightistPlayers, Application.getPropertiesInfo().event.decoration_list_right_color));
        if(this.hasStaffPermission("Modo", "")) {
            this.sendPacket(new C_DisableInitialItemCooldown());
        }

        if(this.room.isBootcamp()) {
            this.sendPacket(new C_BindKeyboard(71, true, true));
        }
    }

    /**
     * Resets the current player's game for the next game.
     */
    public void sendRoundReset() {
        this.isDead = false;
        this.isAfk = true;
        this.isShaman = false;
        this.cheeseCount = 0;
        this.isUsedTotem = false;
        this.isEnteredInHole = false;
        this.canShamanRespawn = false;
        this.bubblesCount = 0;
        this.isOpportunist = false;
        this.isDisintegration = false;
        this.iceCount = 2;
        this.isVampire = false;
        this.defilantePoints = 0;
        this.isNewPlayer = false;
        this.canTransform = false;
        this.currentPlace = 0;
        this.cheeseIdxs.clear();
        this.position = new Pair<>(-1, -1);
        this.nickNameColor = -1;

        // debug
        this.isDebugTeleport = false;
    }

    /**
     * Handles the player's death.
     */
    public void sendPlayerDeath() {
        if (!this.room.disableAutoScore) this.playerScore++;
        this.isDead = true;
        this.cheeseCount = 0;
        this.cheeseIdxs.clear();
        this.room.sendAllOld(new C_PlayerDied(this.sessionId, this.playerScore));
        if (this.room.getAliveCount() < 0 || this.isAfk || this.room.getCurrentMap().isDualShaman) {
            this.canShamanRespawn = false;
        }

        if((this.room.checkIfTooFewRemaining() && !this.canShamanRespawn) || this.room.checkIfShamansAreDead()) {
            this.room.send20SecRemainingTimer();
        }

        if (this.canShamanRespawn) {
            this.canShamanRespawn = false;
            this.room.respawnPlayer(this.playerName);
        }
    }

    /**
     * Broadcast a packet in current player.
     * @param packet The given packet.
     */
    public void sendPacket(SendPacket packet) {
        if(this.isClosed) {
            throw new RuntimeException(Application.getTranslationManager().get("packeterror2"));
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

        Application.getLogger().debug(Application.getTranslationManager().get("sendpacket", this.ipAddress, packet.getC(), packet.getCC()));
        this.channel.write(ChannelBuffers.wrappedBuffer(_packet.toByteArray()));
    }

    /**
     * Broadcast a legacy (legacy) packet in current player.
     * @param packet The given packet.
     */
    public void sendOldPacket(SendPacket packet) {
        if(this.isClosed) {
            throw new RuntimeException(Application.getTranslationManager().get("packeterror2"));
        }

        ByteArray data = new ByteArray();
        ByteArray _packet = new ByteArray();

        data.writeUnsignedShort((packet.getPacket().length > 0 ? packet.getPacket().length + 3 : 2));
        data.writeString(String.valueOf((char) packet.getC()) + (char) packet.getCC(), false);
        data.writeByte(1);
        data.writeBytes(packet.getPacket());

        int length;
        for(length = data.getLength() + 2; length >= 128; length >>= 7) {
            _packet.writeUnsignedByte(((length & 127) | 128));
        }
        _packet.writeUnsignedByte(length);
        _packet.writeUnsignedByte(1);
        _packet.writeUnsignedByte(1);
        _packet.writeBytes(data.toByteArray());

        Application.getLogger().debug(Application.getTranslationManager().get("sendlegacypacket", this.ipAddress, packet.getC(), packet.getCC(), _packet));
        this.channel.write(ChannelBuffers.wrappedBuffer(_packet.toByteArray()));
    }

    /**
     * Broadcast a tribulle packet in current player.
     * @param packet The given packet.
     * @param isLegacy Is using the legacy tribulle.
     */
    public void sendTribullePacket(TribullePacket packet, boolean isLegacy) {
        if(this.isClosed) {
            throw new RuntimeException(Application.getTranslationManager().get("packeterror2"));
        }

        byte[] data = packet.getPacket();
        ByteArray _packet = new ByteArray();

        int length;
        for(length = data.length + 4; length >= 128; length >>= 7) {
            _packet.writeUnsignedByte(((length & 127) | 128));
        }
        _packet.writeUnsignedByte(length);
        _packet.writeUnsignedByte(60);
        _packet.writeUnsignedByte((isLegacy ? 1 : 3));
        _packet.writeShort(packet.getTribulleCode());
        _packet.writeBytes(data);

        Application.getLogger().debug(Application.getTranslationManager().get("sendtribullepacket", this.ipAddress, packet.getTribulleCode()));
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
        if(this.hasStaffPermission("Sentinelle", "")) {
            privileges.add(7);
        }

        if(this.hasStaffPermission("FunCorp", "")) {
            privileges.add(13);
        }

        if(this.hasStaffPermission("LuaCrew", "")) {
            privileges.add(12);
        }

        if(this.hasStaffPermission("FashionSquad", "")) {
            privileges.add(15);
        }

        if(this.hasStaffPermission("MapCrew", "")) {
            privileges.add(11);
        }

        if(this.hasStaffPermission("Arbitre", "")) {
            privileges.add(3);
        }

        if(this.hasStaffPermission("Modo", "") || this.hasStaffPermission("TrialModo", "")) {
            privileges.add(3);
            privileges.add(5);
        }

        if(this.hasStaffPermission("Admin", "")) {
            privileges.add(10);
        }

        return new ArrayList<>(new HashSet<>(privileges));
    }

    /**
     * Checks if current client has permission.
     * @param position The staff position.
     * @param permissionType The permission.
     * @return If the client has permission.
     */
    public boolean hasStaffPermission(String position, String permissionType) {
        if(this.isGuest) return false;

        if(this.account.getStaffRoles().contains("Admin")) return true;
        if(this.account.getHasPublicAuthorization() && permissionType.equals("StaffChannel")) return true;

        return switch (position) {
            case "Sentinelle" -> this.account.getStaffRoles().contains("Sentinelle");
            case "FunCorp" -> this.account.getStaffRoles().contains("FunCorp");
            case "LuaDev" -> this.account.getStaffRoles().contains("LuaDev");
            case "FashionSquad" -> this.account.getStaffRoles().contains("FashionSquad");
            case "MapCrew" -> this.account.getStaffRoles().contains("MapCrew");
            case "Arbitre" -> this.account.getStaffRoles().contains("Arbitre");
            case "TrialModo" -> this.account.getStaffRoles().contains("TrialModo");
            case "Modo" -> this.account.getStaffRoles().contains("PrivateModo") || this.account.getStaffRoles().contains("PublicModo");
            default -> false;
        };
    }

    /**
     * Sends the user totem items count in the totem editing room.
     */
    public void sendTotemUsedCount() {
        String totemInfo = (String)this.account.getTotemInfo()[1];
        if(!totemInfo.isEmpty()) {
            this.tempTotemInfo = new Pair<>((int)this.account.getTotemInfo()[0], (String)this.account.getTotemInfo()[1]);
            this.sendPacket(new C_SendTotemObjects(this.tempTotemInfo.getFirst()));
        } else {
            this.sendPacket(new C_SendTotemObjects(this.tempTotemInfo.getFirst()));
        }
    }

    /**
     * Sends the vampire mode.
     * @param others Send to others.
     */
    public void sendVampireMode(boolean others) {
        this.isVampire = true;
        if(others) {
            this.room.sendAllOthers(this, new C_VampireMode(this.getSessionId(), true, true));
        } else {
            this.room.sendAll(new C_VampireMode(this.getSessionId(), true, true));
        }

        if (this.room.luaMinigame != null) {
            this.room.updatePlayerList(this);
            this.room.luaApi.callEvent("eventPlayerVampire", this.playerName);
        }
    }

    /**
     * Saves the client's database.
     */
    public void saveDatabase() {
        if(this.isGuest) return;

        this.account.checkVipStatus();
        this.account.setLastIPAddress(this.ipAddress);
        this.account.setLastOn(Utils.getTribulleTime());
        this.account.setPlayedTime(this.account.getPlayedTime() + this.getLoginTime());
        this.account.save();

        this.server.getCachedAccounts().remove(this.playerName);
    }

    /**
     * Sends the new map packet.
     */
    private void sendLoadMap() {
        this.sendPacket(new C_LoadMap(
                this.room.getCurrentMap().mapCode,
                this.room.getPlayersCount(),
                this.room.getLastRoundId(),
                (!this.room.getCurrentMap().mapXml.isEmpty()) ? Utils.compressZlib(this.room.getCurrentMap().mapXml.getBytes()) : new byte[]{},
                this.room.getCurrentMap().mapName,
                this.room.getCurrentMap().mapPerma,
                this.room.getCurrentMap().isInverted,
                this.room.getCurrentMap().isConj,
                this.room.getCurrentMap().isAIE,
                (this.room.isEditeur() ? null : this.room.getRoomDetails())
        ));
    }
}
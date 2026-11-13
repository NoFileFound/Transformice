package org.transformice;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSBase64;
import org.psframework.utils.PSByteArray;
import org.psframework.utils.PSTimer;
import org.transformice.config.titleconfig.TitleData;
import org.transformice.data.objects.CheesePosition;
import org.transformice.data.objects.GameCommunity;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.PlayerState;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.data.objects.RoomPlayerInfo;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.SanctionEntry;
import org.transformice.database.data.TribeEntry;
import org.transformice.modules.ModuleInventory;
import org.transformice.modules.ModuleQuests;
import org.transformice.modules.ModuleSkillTree;
import org.transformice.modules.ModuleTribulle;
import org.transformice.network.NetSession;
import org.transformice.packets.LegacyPacketOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.informations.PacketSendPunishmentTax;
import org.transformice.packets.send.informations.PacketSendTotemItemCount;
import org.transformice.packets.send.language.PacketSendGamePartners;
import org.transformice.packets.send.legacy.PacketSendPlayerBanScreenLogin;
import org.transformice.packets.send.legacy.PacketSendPlayerDeath;
import org.transformice.packets.send.legacy.PacketSendPlayerSaveRemainingNotification;
import org.transformice.packets.send.legacy.PacketSendTotem;
import org.transformice.packets.send.legacy.PacketSendUnlockTitle;
import org.transformice.packets.send.login.PacketSendLoginSouris;
import org.transformice.packets.send.player.PacketSendPlayerAvailableEmoji;
import org.transformice.packets.send.player.PacketSendPlayerChangeTitle;
import org.transformice.packets.send.player.PacketSendPlayerCollectibleAction;
import org.transformice.packets.send.player.PacketSendPlayerEnterHole;
import org.transformice.packets.send.player.PacketSendPlayerUnlockBadge;
import org.transformice.packets.send.login.PacketSendPlayerIdentity;
import org.transformice.packets.send.room.PacketSendRoomGetCheese;
import org.transformice.packets.send.room.PacketSendShamanInfo;
import org.transformice.packets.send.room.PacketSendTutorialStep;
import org.transformice.packets.send.room.PacketSendRoomPasswordPopUp;
import org.transformice.packets.send.room.PacketSendRoomRoundTime;
import org.transformice.packets.send.transformice.PacketSendMapEditorExportPrice;

public final class GamePlayer {
    private static final AtomicInteger NEXT_PLAYER_ID = new AtomicInteger(0);
    private final GameServer server;
    private final NetSession session;
    private final PlayerEntity playerData;
    private final ModuleTribulle parseTribulle;
    private final ModuleInventory parseInventory;
    private final ModuleSkillTree parseSkills;
    private final ModuleQuests parseDailyQuests;
    private final PSTimer chatMessageTimer;
    private final List<String> invitedTribeHouses;
    private final List<Integer> cheeseIdxs;
    private final Map<String, Set<String>> voteBans;
    private final Set<GamePlayer> modopwetWatchers;
    private final int playerId;
    private final int[] mulodromePosition;
    private final long loginTime;
    private final boolean isGuest;
    private final Map<Integer, Integer> tradeConsumables;
    private GameRoom playerRoom;
    private boolean isTaxed;
    private boolean isResetTotem;
    private long playerStartTimeMillis;
    private String roomName;
    private String lastRoomName;
    private String playerCommunity;
    public short playerScore;
    public int ambulanceCount;
    public int bubbleCount;
    public int iceCount;
    public int currentPlace;
    public int cheeseCount;
    public int defilantePoints;
    public int funcorpNickColor;
    public int funcorpMouseColor;
    public int silenceType;
    public int completedRounds;
    public int positionX;
    public int positionY;
    public int velocityX;
    public int velocityY;
    public int lastPingValue;
    public int drawColor;
    public int lastPingPayload;
    public long lastPingTimeStart;
    public long lastSonarTime;
    public int lastPingTimeEnd;
    public int lastGameModeId;
    public int halloweenHealthPoints;
    public boolean canMeep;
    public boolean canTransform;
    public boolean canShamanRespawn;
    public boolean canTotem;
    public boolean isAfk;
    public boolean isDead;
    public boolean isDebugMouse;
    public boolean isDebugKeyboard;
    public boolean isDisintegration;
    public boolean isEnteredInHole;
    public boolean isFacingLeft;
    public boolean isFacingRight;
    public boolean isHidden;
    public boolean isMumuted;
    public boolean isModopwetSortedByTime;
    public boolean isModopwetByOnePersonReports;
    public boolean isModopwetForwardSorted;
    public boolean isModopwetSubscribed;
    public boolean isNewPlayer;
    public boolean isOpportunist;
    public boolean isOpenCafe;
    public boolean isOpenFriendList;
    public boolean isOpenModopwet;
    public boolean isOpenTrade;
    public boolean isOpenTribe;
    public boolean isPrisoned;
    public boolean isTradeConfirm;
    public boolean isShaman;
    public boolean isVampire;
    public boolean isLuaAdmin;
    public boolean isJumping;
    public Object[] tempTotem;
    public String currentTribeInvite;
    public String currentMarriageInvite;
    public String currentTradeName;
    public String funcorpPlayerName;
    public String funcorpPlayerLook;
    public String lastChatMessage;
    public String silenceMessage;
    public String modopwetCurrentCommunity;
    public String currentOpenedNpc;
    public GamePlayer lastWatchedPlayer;
    public List<String> modopwetSubscribedCommunities;
    public Thread luaThread;

    /**
     * Creates a new player.
     * @param session The player's session.
     * @param isGuest Is the player a guest or a registered account.
     * @param playerData The player's data.
     */
    public GamePlayer(NetSession session, boolean isGuest, PlayerEntity playerData) {
        this.server = (GameServer) session.getServer();
        this.session = session;
        this.session.setLoginAttempts(0);
        this.playerData = playerData;
        this.isGuest = isGuest;
        this.invitedTribeHouses = new ArrayList<>();
        this.cheeseIdxs = new ArrayList<>();
        this.modopwetWatchers = new HashSet<>();
        this.modopwetSubscribedCommunities = new ArrayList<>();
        this.voteBans = new HashMap<>();
        this.tradeConsumables = new HashMap<>();
        this.playerRoom = null;
        this.roomName = "";
        this.playerCommunity = this.session.getCurrentCommunity().toUpperCase();
        this.lastRoomName = "";
        this.currentTribeInvite = "";
        this.currentMarriageInvite = "";
        this.silenceMessage = "";
        this.tempTotem = new Object[]{0, ""};
        this.lastChatMessage = "";
        this.funcorpPlayerName = "";
        this.funcorpPlayerLook = "";
        this.currentOpenedNpc = "";
        this.modopwetCurrentCommunity = "ALL";
        this.loginTime = Utils.getUnixTime();
        this.iceCount = 2;
        this.funcorpNickColor = -1;
        this.funcorpMouseColor = -1;
        this.mulodromePosition = new int[]{-1, -1};
        this.lastWatchedPlayer = null;

        // Player identification
        this.playerId = NEXT_PLAYER_ID.incrementAndGet();
        this.server.getPlayers().put(this.getPlayerName(), this);

        // Instances
        this.parseTribulle = new ModuleTribulle(this.server, this);
        this.parseInventory = new ModuleInventory(this.server, this);
        this.parseSkills = new ModuleSkillTree(this.server, this);
        this.parseDailyQuests = new ModuleQuests(this.server, this);

        // Timers
        this.chatMessageTimer = new PSTimer(this.server.getScheduler(), 3, TimeUnit.SECONDS);
    }

    /**
     * Sends a packet to the player.
     * @param packet The packet to send.
     */
    public void broadcastPacket(PSSendPacket packet) {
        this.session.sendData(packet);
    }

    /**
     * Sends a legacy packet to the player.
     * @param packet The legacy packet to send.
     */
    public void broadcastLegacyPacket(PSSendPacket packet) {
        this.session.sendOldData(packet);
    }

    /**
     * Checks if a player should unlock a title.
     * @param score The player's data score.
     * @param titleType The title's type.
     */
    public void checkTitleProgress(int score, String titleType) {
        Map<Integer, TitleData> titles = switch (titleType) {
            case "first" -> this.server.getTitleConfig().getFirstTitleList();
            case "cheese" -> this.server.getTitleConfig().getCheeseTitleList();
            case "bootcamp" -> this.server.getTitleConfig().getBootcampTitleList();
            case "shop" -> this.server.getTitleConfig().getShopTitleList();
            case "shaman" -> this.server.getTitleConfig().getShamanTitleList();
            case "hardmode" -> this.server.getTitleConfig().getHardModeTitleList();
            case "divinemode" -> this.server.getTitleConfig().getDivineModeTitleList();
            case "shamannoskills" -> this.server.getTitleConfig().getShamanTitleListNoSkills();
            case "hardmodenoskills" -> this.server.getTitleConfig().getHardModeTitleListNoSkills();
            case "divinemodenoskills" -> this.server.getTitleConfig().getDivineModeTitleListNoSkills();
            default -> throw new IllegalStateException("Unexpected value: " + titleType);
        };

        if (titles.containsKey(score)) {
            this.sendUnlockTitle(titles.get(score).getTitleId());
        }
    }

    /**
     * Disconnects the player from the game.
     * @param isClientSide is the function being called from the client.
     */
    public void disconnect(boolean isClientSide) {
        if (isClientSide) {
            this.session.disconnect();
        }
        else {
            this.chatMessageTimer.cancelTask(true);
            if (this.playerRoom != null)
                this.playerRoom.removePlayer(this);

            this.server.getParseModopwet().handlePlayerDisconnect(this);
            if (!this.isGuest) {
                this.parseInventory.sendTradeClose(this.currentTradeName, false);
                this.parseTribulle.sendTribulleDestruction();
                this.playerData.playedTime += Utils.getUnixTime() - this.loginTime;
                this.playerData.lastConnectedTime = (System.currentTimeMillis() / 60000);
                this.parseInventory.saveInventory();
                this.server.getDatabase().savePlayer(this.playerData);
            }

            this.server.getPlayers().remove(this.getPlayerName(), this);
        }
    }

    /**
     * Gets the chat message timer.
     * @return The chat message timer.
     */
    public PSTimer getChatMessageTimer() {
        return this.chatMessageTimer;
    }

    /**
     * Gets the player's cheese indexes.
     * @return The player's cheese indexes.
     */
    public List<Integer> getCheeseIdxs() {
        return this.cheeseIdxs;
    }

    /**
     * Gets the player's country code.
     * @return The player's country code.
     */
    public String getCountryCode() {
        return this.session.getCountryCode();
    }

    /**
     * Gets the player's watchers.
     * @return The player's watchers.
     */
    public Set<GamePlayer> getModopwetWatchers() {
        return this.modopwetWatchers;
    }

    /**
     * Gets the player's global id.
     * @return The player's global id.
     */
    public long getGlobalId() {
        return this.playerData.getAccountId();
    }

    /**
     * Gets the invited tribe houses.
     * @return The invited tribe houses.
     */
    public List<String> getInvitedTribeHouses() {
        return this.invitedTribeHouses;
    }

    /**
     * Gets the player's IP address.
     * @return The player's IP address.
     */
    public String getIpAddress() {
        return this.session.getIpAddress();
    }

    /**
     * Checks if the player is guest.
     * @return True if the player is guest or else False.
     */
    public boolean getIsGuest() {
        return this.isGuest;
    }

    /**
     * Checks if the player is mumuted.
     * @return True if the player is mumuted or else False.
     */
    public boolean getIsMumuted() {
        return this.isMumuted;
    }

    /**
     * Gets the player's login time.
     * @return The player's login time.
     */
    public long getLoginTime() {
        return this.loginTime;
    }

    /**
     * Gets the player's team id and position in the mulodrome.
     * @return The player's team id and position.
     */
    public int[] getMulodromePosition() {
        return this.mulodromePosition;
    }

    /**
     * Gets the player's room name.
     * @return The player's room name.
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Gets the player's last room.
     * @return The player's last room.
     */
    public String getLastRoomName() {
        return this.lastRoomName;
    }

    /**
     * Gets the player's session id.
     * @return The player's session id.
     */
    public int getSessionId() {
        return this.playerId;
    }

    /**
     * Gets the player's community.
     * @return The player's community.
     */
    public String getPlayerCommunity() {
        return this.playerCommunity;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getPlayerName() {
        return this.playerData.getUsername();
    }

    /**
     * Gets the player's data.
     * @return The player's data.
     */
    public PlayerEntity getPlayerData() {
        return this.playerData;
    }

    /**
     * Gets the player's room.
     * @return The player's room.
     */
    public GameRoom getPlayerRoom() {
        return this.playerRoom;
    }

    /**
     * Gets the player's information in the room.
     * @return The player's information in the room.
     */
    public RoomPlayerInfo getPlayerRoomInfo() {
        return new RoomPlayerInfo(
                this.funcorpPlayerName.isEmpty() ? this.getPlayerName() : this.funcorpPlayerName,
                this.getSessionId(),
                this.isShaman,
                (this.isHidden ? PlayerState.HIDDEN : this.isDead ? PlayerState.DEAD : PlayerState.ALIVE),
                this.playerScore,
                this.cheeseCount,
                this.playerData.currentTitle,
                this.playerData.playerGender,
                String.valueOf(this.getGlobalId()),
                (!this.funcorpPlayerLook.isEmpty() ? this.funcorpPlayerLook : (this.playerRoom.getRoomType() == RoomType.BOOTCAMP ? "1;0,0,0,0,0,0,0,0,0,0,0,0" : this.playerData.playerLook)),
                (this.funcorpMouseColor != -1 ? this.funcorpMouseColor : this.playerData.mouseColor),
                this.playerData.shamanColor,
                this.funcorpNickColor,
                (this.isShaman ? this.playerData.shamanColor : this.funcorpNickColor),
                0
        );
    }

    /**
     * Gets the inventory instance.
     * @return The inventory instance.
     */
    public ModuleInventory getParseInventory() {
        return this.parseInventory;
    }

    /**
     * Gets the daily quests module.
     * @return The daily quests's instance.
     */
    public ModuleQuests getParseDailyQuests() {
        return this.parseDailyQuests;
    }

    /**
     * Gets the tribulle's instance of the current player.
     * @return The current instance.
     */
    public ModuleTribulle getParseTribulle() {
        return this.parseTribulle;
    }

    /**
     * Gets the skills's instance of the current player.
     * @return The current instance.
     */
    public ModuleSkillTree getParseSkills() {
        return this.parseSkills;
    }

    /**
     * Gets the trade's consumables.
     * @return The trade's consumables.
     */
    public Map<Integer, Integer> getTradeConsumables() {
        return this.tradeConsumables;
    }

    /**
     * Gets the player's voted bans against him.
     * @return The player's voted bans.
     */
    public Map<String, Set<String>> getVoteBans() {
        return this.voteBans;
    }

    /**
     * Checks if current client has permission.
     * @param staffRole The staff position.
     * @param isStaffChannelPerm Is the permission about sending messages in staff channels.
     * @return If the client has permission.
     */
    public boolean hasStaffPermission(StaffRoleType staffRole, boolean isStaffChannelPerm) {
        if (this.isGuest)
            return false;

        if (this.server.getConfig().getIsDebug())
            return true;

        if (this.playerData.getStaffRoles().contains(StaffRoleType.ADMIN))
            return true;

        if (this.playerData.hasPublicAuthorization && isStaffChannelPerm)
            return true;

        return this.playerData.getStaffRoles().contains(staffRole);
    }

    /**
     * Sends the player's death.
     */
    public void sendDeath() {
        if (this.isDead)
            return;

        if (!this.playerRoom.disableAutoScore && this.playerRoom.getRoomType() != RoomType.DEFILANTE)
            this.playerScore++;

        this.playerRoom.broadcastLegacyPacket(new PacketSendPlayerDeath(this.getSessionId(), this.playerScore, 0));
        this.isDead = true;
        this.cheeseCount = 0;
        this.completedRounds = 0;
        if (this.playerRoom.getAliveCount() <= 0 || this.isAfk || this.playerRoom.getCurrentMap().isDualShaman || this.playerRoom.getCurrentMap().isCatchTheCheese)
            this.canShamanRespawn = false;

        if ((this.playerRoom.checkIfTooFewRemaining() && !this.canShamanRespawn) || this.playerRoom.checkIfShamansAreDead())
            this.playerRoom.send20SecRemainingTimer();

        if (this.canShamanRespawn) {
            this.playerRoom.sendRespawnPlayer(this);
            this.playerRoom.broadcastPacket(new PacketSendShamanInfo(this.getSessionId(), 0, this.playerData.getShamanType().ordinal(), 0, this.playerData.shamanLevel, 0, this.parseSkills.getShamanBadge(), 0, this.playerData.shamanSkillsEnabled, true));
            this.canShamanRespawn = false;
        }

        this.playerRoom.getParseLua().handleEventPlayerDeath(this);
    }

    /**
     * Handles the packet for enter the hole with the cheese.
     * @param holeType The hole type.
     * @param holeX The hole X.
     * @param holeY The hole Y.
     * @param holeDist The hole distance from begin the map.
     */
    public void sendEnterHole(int holeType, int holeX, int holeY, int holeDist) {
        if (this.isDead || (this.cheeseCount < 1 && !this.isOpportunist) || this.playerRoom.getRoomType() == RoomType.TOTEM)
            return;

/// TODO: [Anticheat] Implement anticheat.

        if (this.isShaman) {
            if (this.playerRoom.getCurrentMap().isCatchTheCheese)
                return;

            if (this.playerRoom.getAliveCount() > 0) {
                this.broadcastLegacyPacket(new PacketSendPlayerSaveRemainingNotification());
                return;
            }
        }

        if (this.playerRoom.getRoomType() == RoomType.EDITOR) {
            if (!this.playerRoom.isMapEditorMapValidated && this.playerRoom.isMapEditorMapValidating) {
                this.playerRoom.isMapEditorMapValidated = true;
                this.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorMapValidated) {
                    @Override
                    public PSByteArray build() throws PSSendPacketException {
                        return new PSByteArray();
                    }
                });
            }
        }

        int cheeseTmp = this.cheeseCount;

        this.isDead = true;
        this.cheeseCount = 0;
        this.cheeseIdxs.clear();
        this.isEnteredInHole = true;
        this.isOpportunist = false;
        if (this.playerRoom.getCurrentMap().isDualShaman) {
            if (holeType == 1)
                this.playerRoom.firstShamanNumCompleted++;
            else if (holeType == 2)
                this.playerRoom.secondShamanNumCompleted++;
            else {
                this.playerRoom.firstShamanNumCompleted++;
                this.playerRoom.secondShamanNumCompleted++;
            }
        } else {
            this.playerRoom.firstShamanNumCompleted++;
        }

        int place = (this.playerRoom.firstShamanNumCompleted + this.playerRoom.secondShamanNumCompleted);
        int timeElapsed = (int) ((System.currentTimeMillis() - (!this.playerRoom.disableAutoRespawn ? this.playerStartTimeMillis : this.playerRoom.getGameStartTimeMillis())) / 10);
        int timeElapsedSinceRespawn = (int) ((System.currentTimeMillis() - this.playerStartTimeMillis) / 10);
        this.currentPlace = place;
        boolean isDefilante = (this.playerRoom.getRoomType() == RoomType.DEFILANTE);
        boolean isRacing = (this.playerRoom.getRoomType() == RoomType.RACING || this.playerRoom.getIsMulodrome());
        if (!this.playerRoom.disableAutoScore && !this.isShaman)
            this.playerScore += (short) (isDefilante ? this.defilantePoints : (isRacing) ? (place == 1 ? 4 : place == 2 ? 3 : place == 3 ? 2 : 1) : (place == 1 ? 16 : place == 2 ? 14 : place == 3 ? 12 : 10));

        if (this.playerRoom.getIsCountingStats()) {
            if (place == 1) {
                this.playerData.firstCount++;
                this.checkTitleProgress(this.playerData.firstCount, "first");
            }

            this.parseDailyQuests.increaseMission(MissionType.BRING_BACK_CHEESE, cheeseTmp);
            this.playerData.cheeseCount++;
            this.checkTitleProgress(this.playerData.cheeseCount, "cheese");
            this.playerData.shopCheese++;
            if (this.playerRoom.getRoomType() == RoomType.BOOTCAMP) {
                this.playerData.bootcampCount++;
                this.checkTitleProgress(this.playerData.bootcampCount, "bootcamp");
            }

            if (!this.isShaman) {
                int shamanLevel = this.playerData.shamanLevel;
                int xp = shamanLevel < 20 ? 60 : shamanLevel < 40 ? 120 : shamanLevel < 60 ? 180 : shamanLevel < 80 ? 240 : shamanLevel < 100 ? 300 : shamanLevel < 120 ? 360 : shamanLevel < 140 ? 420 : shamanLevel < 160 ? 480 : shamanLevel < 180 ? 540 : 600;
                this.parseSkills.earnExp(false, xp);
                if (isRacing) {
                    this.parseDailyQuests.increaseMission(MissionType.FINISH_MAPS_IN_RACING, 1);
                    int consumable = (this.completedRounds / 5);
                    if (consumable > 0) {
                        this.parseInventory.addConsumable(String.valueOf(consumable), 1, false);
                    }
                } else if(isDefilante) {
                    this.parseDailyQuests.increaseMission(MissionType.FINISH_MAPS_IN_DEFILANTE, 1);
                    int consumable = (this.defilantePoints / 10);
                    if (consumable > 0) {
                        this.parseInventory.addConsumable(String.valueOf(consumable), 1, false);
                    }
                }
            } else {
                this.parseDailyQuests.increaseMission(MissionType.BRING_BACK_CHEESE_AS_SHAMAN, cheeseTmp);
                this.playerData.shamanCheeseCount++;
            }
        }

        if (this.playerRoom.getIsMulodrome()) {
            int points = place == 1 ? 4 : place == 2 ? 3 : place == 3 ? 2 : 1, redCount = 0, blueCount = 0;
            if (this.playerRoom.getMulodromeRedTeam().contains(this.getPlayerName()))
                redCount += points;
            else if (this.playerRoom.getMulodromeBlueTeam().contains(this.getPlayerName()))
                blueCount += points;

            this.playerRoom.sendMulodromeDelta(blueCount, redCount);
        }

        this.playerRoom.broadcastPacket(new PacketSendPlayerEnterHole(this.getSessionId(), holeType, this.playerScore, this.currentPlace, timeElapsed));
        if (this.playerRoom.getRoomType() == RoomType.TUTORIAL) {
            this.broadcastPacket(new PacketSendTutorialStep(2));
            this.playerRoom.getPlayers().values().forEach(client -> client.broadcastPacket(new PacketSendRoomRoundTime((short) 10)));
            new PSTimer(this.server.getScheduler()).schedule(() -> this.sendEnterRoom("", ""), 10, TimeUnit.SECONDS);
            return;
        }

        if (this.playerRoom.getPlayers().size() >= 2 && this.playerRoom.checkIfTooFewRemaining() && !this.playerRoom.getCurrentMap().isDualShaman && this.playerRoom.getCurrentShaman() != null && this.playerRoom.getCurrentShaman().isOpportunist) {
            this.playerRoom.getCurrentShaman().sendEnterHole(0, -1, -1, -1);
        } else if (this.playerRoom.checkIfTooFewRemaining()) {
            this.playerRoom.send20SecRemainingTimer();
        } else {
            this.playerRoom.checkChangeMap();
        }

        this.playerRoom.getParseLua().handleEventPlayerWin(this, timeElapsed, timeElapsedSinceRespawn);
    }

    /**
     * Enters a room.
     * @param roomName The room name to enter.
     * @param password The room password if it has.
     * @param withoutShamanSkills The shaman skills are disabled.
     * @param withoutAdventureMaps The event maps are disabled.
     * @param withoutPhysicalConsumables The physical consumables are disabled.
     * @param withMiceCollisions The mice collisions are enabled.
     * @param withFallDamage The fall damage is enabled.
     * @param roundDuration The round duration.
     * @param miceWeight The mice weight.
     * @param maximumPlayers The maximum players.
     * @param mapRotation The map rotation.
     */
    public void sendEnterRoom(String roomName, String password, boolean withoutShamanSkills, boolean withoutPhysicalConsumables, boolean withoutAdventureMaps, boolean withMiceCollisions, boolean withFallDamage, int roundDuration, int miceWeight, int maximumPlayers, List<Integer> mapRotation) {
        if (this.isPrisoned)
            return;

        if (roomName.isEmpty())
            roomName = this.server.getRecommendedRoom("", this.playerCommunity);

        roomName = Utils.formatRoomName(roomName, this.playerCommunity, !this.playerData.getStaffRoles().isEmpty());
        if (this.roomName.equals(roomName))
            return;

        var restrictionTimer = this.server.getTempRestrictedRoomTimer(this.getPlayerName(), roomName);
        if (restrictionTimer != null && restrictionTimer.getRemainingTime() > 0)
            return;

        this.roomName = roomName;
        if (this.roomName.startsWith("int-*\u0003")) {
            String tribeName = this.roomName.substring(2);
            if (!this.invitedTribeHouses.contains(tribeName) && !this.playerData.tribeName.equals(tribeName))
                this.roomName = this.server.getRecommendedRoom("", this.playerCommunity);
        }

        String roomPrefix = this.roomName.substring(this.roomName.indexOf('-') + 1);
        if (roomPrefix.startsWith("\u0003[Editeur]") || roomPrefix.startsWith("\u0003[Totem]") || roomPrefix.startsWith("\u0003[Tutorial]")) {
            String playerName = this.roomName.substring(this.roomName.indexOf(']') + 2);
            if (!playerName.equals(this.getPlayerName()))
                this.roomName = this.server.getRecommendedRoom("", this.playerCommunity);
        }

        GameRoom roomInstance = this.server.getRooms().get(this.roomName);
        if (roomInstance != null) {
            if (!roomInstance.roomPassword.isEmpty() && !roomInstance.roomPassword.equals(password)) {
                this.broadcastPacket(new PacketSendRoomPasswordPopUp(this.roomName));
                return;
            }

            if (roomInstance.getMaximumPlayers() > 0 && roomInstance.getPlayers().size() > roomInstance.getMaximumPlayers()) {
                this.roomName = this.server.getRecommendedRoom(this.roomName, this.playerCommunity);
                roomInstance = this.server.getRooms().get(this.roomName);
            }
        }

        this.parseInventory.sendTradeClose(this.currentTradeName, false);
        if (this.playerRoom != null) {
            this.lastRoomName = this.playerRoom.getRoomName();
            this.playerRoom.removePlayer(this);
        }

        if (!this.isGuest) {
            String playerName = this.getPlayerName();
            var players = this.server.getPlayers();
            for (String friendName : this.playerData.getFriendList()) {
                GamePlayer friend = players.get(friendName);
                if (friend != null && friend.isOpenFriendList && friend.playerData.getFriendList().contains(playerName)) {
                    friend.parseTribulle.sendFriendModification(playerName, 1);
                }
            }

            String tribeName = this.playerData.tribeName;
            if (!tribeName.isEmpty()) {
                TribeEntry tribe = this.server.getDatabase().loadTribeByName(tribeName);
                if (tribe != null) {
                    for (String memberName : tribe.getTribeMembers()) {
                        if (memberName.equals(playerName))
                            continue;

                        GamePlayer member = players.get(memberName);
                        if (member != null) {
                            member.parseTribulle.sendTribeMemberModification(playerName, -1, member.isOpenTribe);
                        }
                    }
                }
            }
        }

        if (roomInstance == null) {
            roomInstance = new GameRoom(this.server, this.getPlayerName(), this.roomName, password, withoutShamanSkills, withoutPhysicalConsumables, withoutAdventureMaps, withMiceCollisions, withFallDamage, roundDuration, miceWeight, maximumPlayers, mapRotation);
            this.server.getRooms().put(roomInstance.getRoomName(), roomInstance);
        }

        roomInstance.addPlayer(this);
        this.server.getParseModopwet().handlePlayerChangeRoom(this);
    }

    /**
     * Enters a room.
     * @param roomName The room name to enter.
     * @param password The room password if it has.
     */
    public void sendEnterRoom(String roomName, String password) {
        this.sendEnterRoom(roomName, password, false, false, false, false, false, 100, 0, 0, List.of());
    }

    /**
     * Sends a cheese to the player.
     * @param posX The cheese's X position.
     * @param posY The cheese's Y position.
     * @param cheeseIdx The cheese's index.
     * @param contextId The context id.
     * @param distance The cheese's distance.
     */
    public void sendGiveCheese(int posX, int posY, int cheeseIdx, int contextId, int distance) {
        if (this.isDead)
            return;

        if (distance != -1 && !this.playerRoom.getCurrentMap().isCatchTheCheese) {
            CheesePosition target = null;
            long mnDist = Long.MAX_VALUE;
            for (CheesePosition cheese : this.playerRoom.getCheesePositions()) {
                long dx = cheese.getPosX() - posX;
                long dy = cheese.getPosY() - posY;
                long dist = dx * dx + dy * dy;
                if (dist < mnDist) {
                    mnDist = dist;
                    target = cheese;
                }
            }

            if (target == null || mnDist > 32L * 32L || distance > 32) {
                this.sendDeath();
                this.playerRoom.checkChangeMap();
                return;
            }

/// TODO: [Anticheat] Implement anticheat.
        }

        if (!this.cheeseIdxs.contains(cheeseIdx)) {
            this.cheeseIdxs.add(cheeseIdx);
            this.cheeseCount++;
            this.playerRoom.broadcastPacket(new PacketSendRoomGetCheese(this.getSessionId(), this.cheeseCount));
            if (this.cheeseCount > 1) {
                this.playerRoom.broadcastPacket(new PacketSendPlayerCollectibleAction(3));
                this.playerRoom.broadcastPacket(new PacketSendPlayerCollectibleAction(2, this.getSessionId(), "x_transformice/x_aventure/x_recoltables/x_" + (59 + this.cheeseCount - 1) + ".png", -32, this.cheeseCount == 2 ? -30 : -45, false, 100, 0));
            }

            if (this.playerRoom.getRoomType() == RoomType.TUTORIAL)
                this.broadcastPacket(new PacketSendTutorialStep(1));

            this.playerRoom.numGetCheese++;
            if (this.playerRoom.getCurrentMap().isCatchTheCheese) {
                if (this.playerRoom.numGetCheese >= 10) {
                    this.playerRoom.getCurrentShaman().sendDeath();
                }
            }

            this.playerRoom.getParseLua().handleEventPlayerGetCheese(this);
        }
    }

    /**
     * Removes the cheese from the player.
     * @param cheeseCount The cheese count to remove.
     */
    public void sendRemoveCheese(int cheeseCount) {
        if (this.isDead)
            return;

        if (cheeseCount > this.cheeseCount || cheeseCount <= 0)
            return;

        this.cheeseIdxs.subList(this.cheeseIdxs.size() - cheeseCount, this.cheeseIdxs.size()).clear();
        this.cheeseCount -= cheeseCount;
        this.playerRoom.broadcastPacket(new PacketSendPlayerCollectibleAction(this.getSessionId()));
        this.playerRoom.broadcastPacket(new PacketSendRoomGetCheese(this.getSessionId(), this.cheeseCount));
        if (this.cheeseCount > 1)
            this.playerRoom.broadcastPacket(new PacketSendPlayerCollectibleAction(2, this.getSessionId(), "x_transformice/x_aventure/x_recoltables/x_" + (59 + this.cheeseCount - 1) + ".png", -32, this.cheeseCount == 2 ? -30 : -45, false, 100, 0));
    }

    /**
     * Changes the player's title.
     * @param titleInfo The player's title info.
     */
    public void sendChangeTitle(PlayerTitleInfo titleInfo) {
        this.playerData.currentTitle = titleInfo;
        this.broadcastPacket(new PacketSendPlayerChangeTitle(this.playerData.playerGender, titleInfo.titleId));
    }

    /**
     * Handles when the player logins in the game.
     * @param roomName The room's name where the player will join.
     */
    public void sendLogin(String roomName) {
        var mySanction = this.server.getDatabase().loadLastSanction(this.getPlayerName(), SanctionEntry.SanctionType.SANCTION_BAN);
        if (mySanction != null) {
            var state = mySanction.sanctionState;
            if (state == SanctionEntry.SanctionState.ACTIVE) {
                if (mySanction.isPermanent) {
                    this.broadcastLegacyPacket(new PacketSendPlayerBanScreenLogin(mySanction.getReason(), -1));
                    this.session.setHasBanned(true);
                    return;
                }

                long remainingTime = mySanction.getExpirationDate() - Utils.getUnixTime();
                if (remainingTime > 0) {
                    long hours = (remainingTime + 3599) / 3600;
                    this.broadcastLegacyPacket(new PacketSendPlayerBanScreenLogin(mySanction.getReason(), hours * 3600000));
                    this.session.setHasBanned(true);
                    return;
                }
            } else if (state == SanctionEntry.SanctionState.TAXED) {
                this.isTaxed = true;
            }
        }

        ArrayList<Integer> privileges = new ArrayList<>();
        if (this.hasStaffPermission(StaffRoleType.ADMIN, false))
            privileges.add(10);

        if (this.hasStaffPermission(StaffRoleType.ARBITRE, false))
            privileges.add(3);

        if (this.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || this.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false) || this.hasStaffPermission(StaffRoleType.TRIAL_MODO, false)) {
            privileges.add(3);
            privileges.add(5);
        }

        if (this.hasStaffPermission(StaffRoleType.MAPCREW, false))
            privileges.add(11);

        if (this.hasStaffPermission(StaffRoleType.FASHIONSQUAD, false))
            privileges.add(15);

        if (this.hasStaffPermission(StaffRoleType.LUADEV, false))
            privileges.add(12);

        if (this.hasStaffPermission(StaffRoleType.FUNCORP, false))
            privileges.add(13);

        this.broadcastPacket(new PacketSendPlayerIdentity(this.getGlobalId(), this.getPlayerName(), this.playerData.playedTime, GameCommunity.fromCode(this.playerCommunity), this.getSessionId(), !this.isGuest, privileges, this.playerData.hasPublicAuthorization));
        this.broadcastPacket(new PacketSendMapEditorExportPrice(this.server.getConfig().getGameConfig().getMapEditorExportCheesePrice()));
        if (this.isGuest) {
            this.broadcastPacket(new PacketSendLoginSouris(1, 10));
            this.broadcastPacket(new PacketSendLoginSouris(2, 5));
            this.broadcastPacket(new PacketSendLoginSouris(3, 15));
            this.broadcastPacket(new PacketSendLoginSouris(4, 200));
        }

        this.parseSkills.sendLoginShamanSkillsInitialization();
        this.server.getParseShop().sendLoginShopInitialization(this);
        this.server.getMaximumPlayerCount().add(this.getPlayerName());
        this.parseInventory.sendLoginInventoryInitialization();
        this.broadcastPacket(new PacketSendPlayerAvailableEmoji(this.playerData.getShopEmojis()));
        this.parseDailyQuests.sendDailyMissions();
        this.server.getParseEvents().sendEventPlayerInitialization(this);
        this.broadcastPacket(new PacketSendGamePartners(this.server.getConfig().getGameConfig().getPartners()));
        if (this.isTaxed && this.playerData.shopCheese > 20) {
            this.playerData.shopCheese -= 20;
            this.broadcastPacket(new PacketSendPunishmentTax(20));
            if (mySanction != null) {
                mySanction.sanctionState = SanctionEntry.SanctionState.EXPIRED;
                this.server.getDatabase().saveSanction(mySanction);
            }

            this.isTaxed = false;
        }

        this.server.getParseModopwet().handlePlayerConnect(this);
        this.sendEnterRoom(roomName, "");
        this.parseTribulle.sendTribulleInitialization();
    }

    /**
     * Initializes the player's totem edition.
     */
    public void sendTotemEditor() {
        if (this.playerData.totemInfo[0].equals(0))
            this.broadcastPacket(new PacketSendTotemItemCount(0));
        else if (this.isResetTotem) {
            this.tempTotem = new Object[]{0, ""};
            this.broadcastPacket(new PacketSendTotemItemCount(0));
            this.isResetTotem = false;
        } else {
            this.tempTotem = this.playerData.totemInfo;
            this.broadcastPacket(new PacketSendTotemItemCount((short) (((Number) this.tempTotem[0]).intValue())));
            this.broadcastLegacyPacket(new PacketSendTotem(this.getSessionId(), 400, 204, PSBase64.decode((String)this.tempTotem[1])));
        }
    }

    /**
     * Unlocks a new badge.
     * @param badgeId The badge's id.
     */
    public void sendUnlockBadge(int badgeId) {
        this.playerData.getPlayerBadges().merge(badgeId, 1, Integer::sum);
        this.playerRoom.broadcastPacket(new PacketSendPlayerUnlockBadge(this.getSessionId(), badgeId));
    }

    /**
     * Unlocks a new title.
     * @param titleId The title's id.
     */
    public void sendUnlockTitle(int titleId) {
        int titleStars = 0;
        for (PlayerTitleInfo title : this.playerData.getUnlockedTitles()) {
            if (title.titleId == titleId) {
                titleStars = title.titleStars + 1;
                break;
            }
        }

        if (titleStars > 8)
            return;

        this.playerData.getUnlockedTitles().removeIf(title -> title.titleId == titleId);
        this.playerData.getUnlockedTitles().add(new PlayerTitleInfo((short) titleId, (byte) titleStars));
        this.playerRoom.broadcastLegacyPacket(new PacketSendUnlockTitle(this.getSessionId(), titleId, titleStars));
    }

    /**
     * Sets the mulodrome team and position.
     * @param teamId The team's id to set.
     * @param positionId The team's position to set.
     */
    public void setMulodromePosition(int teamId, int positionId) {
        this.mulodromePosition[0] = teamId;
        this.mulodromePosition[1] = positionId;
    }

    /**
     * Checks if the player should reset his totem.
     * @param isResetTotem True if the player will reset his totem.
     */
    public void setIsResetTotem(boolean isResetTotem) {
        this.isResetTotem = isResetTotem;
    }

    /**
     * Sets the player's community.
     * @param playerCommunity The player's community to set.
     */
    public void setPlayerCommunity(String playerCommunity) {
        this.playerCommunity = playerCommunity;
    }

    /**
     * Assigns the player to a room.
     * @param playerRoom The room to assign.
     */
    public void setPlayerRoom(GameRoom playerRoom) {
        this.playerRoom = playerRoom;
    }

    /**
     * Sets the time when player is going to start in the game.
     * @param playerStartTimeMillis The time when player is going to start.
     */
    public void setPlayerStartTimeMillis(long playerStartTimeMillis) {
        this.playerStartTimeMillis = playerStartTimeMillis;
    }
}
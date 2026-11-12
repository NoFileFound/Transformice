package org.transformice;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.psframework.network.PSSendPacket;
import org.psframework.utils.PSRandom;
import org.psframework.utils.PSTimer;
import org.psframework.utils.PSZlib;
import org.transformice.data.objects.AddBonusType;
import org.transformice.data.objects.CheesePosition;
import org.transformice.data.objects.HolePosition;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.RoomPlayerInfo;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.SkillType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.MapEntry;
import org.transformice.database.data.TribeEntry;
import org.transformice.modules.ModuleLua;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendChatMessage;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;
import org.transformice.packets.send.legacy.PacketSendRoomAnchors;
import org.transformice.packets.send.legacy.PacketSendCatchTheCheeseMap;
import org.transformice.packets.send.legacy.PacketSendMapVotePopup;
import org.transformice.packets.send.legacy.PacketSendPlayerDisconnect;
import org.transformice.packets.send.legacy.PacketSendPlayerSynchronizer;
import org.transformice.packets.send.lua.PacketSendLuaNicknameColor;
import org.transformice.packets.send.mulodrome.PacketSendMulodromeEnd;
import org.transformice.packets.send.mulodrome.PacketSendMulodromeResult;
import org.transformice.packets.send.mulodrome.PacketSendMulodromeWinner;
import org.transformice.packets.send.player.PacketSendCreateNpc;
import org.transformice.packets.send.player.PacketSendEnableMeep;
import org.transformice.packets.send.player.PacketSendEnableTransformation;
import org.transformice.packets.send.player.PacketSendPlayerPet;
import org.transformice.packets.send.player.PacketSendPlayerSetVampire;
import org.transformice.packets.send.room.*;

public final class GameRoom {
    private final GameServer server;
    private final String roomFullName;
    private final String roomCommunity;
    private final String roomName;
    private final String roomAuthor;
    private final Map<String, GamePlayer> players;
    private final List<String> funCorpMembers;
    private final List<String> roomAnchors;
    private final Set<String> mulodromeBlueTeam;
    private final Set<String> mulodromeRedTeam;
    private final List<Map<String, String>> musicVideos;
    private final List<Integer> mapRotation;
    private final List<PSTimer> consumablesTimers;
    private final Set<CheesePosition> cheesePositions;
    private final Set<HolePosition> holePositions;
    private final List<String> roomFunCorpPlayersChangedSize;
    private final List<String> roomFunCorpPlayersTransformationAbility;
    private final List<String> roomFunCorpPlayersMeepAbility;
    private final Map<String, String> roomFunCorpPlayersLinked;
    private final Map<String, Integer> roomFunCorpPlayersNickColor;
    private final Map<String, Integer> roomFunCorpPlayersMouseColor;
    private final RoomType roomType;
    private final ModuleLua parseLua;
    private final PSTimer voteCloseTimer;
    private final PSTimer killAfkTimer;
    private final PSTimer changeMapTimer;
    private final PSTimer autoRespawnTimer;
    private final PSTimer startTimerLeft;
    private final PSTimer endSnowTimer;
    private final int roundDuration;
    private final int miceWeight;
    private final boolean disableAdventureMaps;
    private final boolean enableMiceCollisions;
    private final boolean enableFallDamage;
    private MapDetails currentMap;
    private GamePlayer currentShaman;
    private GamePlayer currentSecondShaman;
    public GamePlayer currentSync;
    public GamePlayer forceNextShaman;
    private int additionalTime;
    private int lastObjectID;
    private int lastBonusId;
    private int lastRoundId;
    private int lastImageId;
    public int musicSkipVotes;
    private int roundCount;
    private int mulodromeRoundCount;
    private int mulodromeBlueTeamCount;
    private int mulodromeRedTeamCount;
    private int vampireCount;
    public int receivedYesVotes;
    public int receivedNoVotes;
    public int mapEditorMapCode;
    private int mapRotationIdx;
    private short roundTime;
    private boolean isCurrentlyPlay;
    private boolean initVotingMode;
    private boolean isMulodrome;
    private boolean isVotingBox;
    private boolean isVotingMode;
    private boolean isEventTime;
    private boolean isChanged20secTimer;
    private boolean isSnowing;
    private long gameStartTimeMillis;
    public int[] lastHandyMouseObjectId;
    public MapDetails forceNextMap;
    public int lastCloudObjectId;
    public int lastCompanionBoxId;
    public int deathMice;
    public int maximumPlayers;
    public short firstShamanNumCompleted;
    public short secondShamanNumCompleted;
    public short numGetCheese;
    public int musicTime;
    public boolean canChangeMusic;
    public boolean disableAfkDeath;
    public boolean disableAllShamanSkills;
    public boolean disableAutoNewGame;
    public boolean disableAutoRespawn;
    public boolean disableAutoScore;
    public boolean disableAutoShaman;
    public boolean disableAutoTimeLeft;
    public boolean disablePhysicalConsumables;
    public boolean isMapEditorMapValidating;
    public boolean isMapEditorMapValidated;
    public boolean isFunCorp;
    public boolean isPlayingMusic;
    public boolean isFunCorpHighlighedRoom;
    public String roomPassword;
    public String mapEditorMapXml;

    /**
     * Creates a new room name.
     * @param server The game server's instance.
     * @param roomAuthor The room author's name.
     * @param roomName The room's name.
     * @param roundDuration The round's duration in percentage.
     */
    public GameRoom(GameServer server, String roomAuthor, String roomName, String roomPassword, boolean withoutShamanSkills, boolean withoutPhysicalConsumables, boolean withoutAdventureMaps, boolean withMiceCollisions, boolean withFallDamage, int roundDuration, int miceWeight, int maximumPlayers, List<Integer> mapRotation) {
        if (roundDuration < 50)
            roundDuration = 50;

        this.server = server;
        this.roomFullName = roomName;
        this.roomCommunity = this.roomFullName.substring(0, this.roomFullName.indexOf('-'));
        this.roomName = this.roomFullName.substring(this.roomFullName.indexOf('-') + 1);
        this.roomAuthor = roomAuthor;
        this.parseLua = new ModuleLua(this.server, this);
        this.players = new ConcurrentHashMap<>();
        this.funCorpMembers = new ArrayList<>();
        this.roomAnchors = new ArrayList<>();
        this.mulodromeBlueTeam = new HashSet<>();
        this.mulodromeRedTeam = new HashSet<>();
        this.musicVideos = new ArrayList<>();
        this.cheesePositions = new HashSet<>();
        this.holePositions = new HashSet<>();
        this.mapRotation = mapRotation;
        this.roomFunCorpPlayersChangedSize = new ArrayList<>();
        this.roomFunCorpPlayersLinked = new HashMap<>();
        this.roomFunCorpPlayersMeepAbility = new ArrayList<>();
        this.roomFunCorpPlayersMouseColor = new HashMap<>();
        this.roomFunCorpPlayersNickColor = new HashMap<>();
        this.roomFunCorpPlayersTransformationAbility = new ArrayList<>();
        this.currentShaman = null;
        this.currentSecondShaman = null;
        this.currentSync = null;
        this.forceNextShaman = null;
        this.additionalTime = 0;
        this.forceNextMap = null;
        this.lastCloudObjectId = -1;
        this.lastCompanionBoxId = -1;
        this.lastObjectID = 0;
        this.lastBonusId = 0;
        this.lastRoundId = 0;
        this.mapRotationIdx = 0;
        this.roundDuration = roundDuration;
        this.roundTime = (short)(120 * (roundDuration / 100.0));
        this.maximumPlayers = maximumPlayers;
        this.musicTime = 0;
        this.roundCount = -1;
        this.mulodromeRoundCount = 0;
        this.mulodromeBlueTeamCount = 0;
        this.mulodromeRedTeamCount = 0;
        this.vampireCount = 0;
        this.mapEditorMapCode = -1;
        this.firstShamanNumCompleted = 0;
        this.secondShamanNumCompleted = 0;
        this.numGetCheese = 0;
        this.musicSkipVotes = 0;
        this.deathMice = 0;
        this.lastHandyMouseObjectId = new int[]{-1, -1};
        this.miceWeight = miceWeight;
        this.disableAfkDeath = false;
        this.disableAllShamanSkills = withoutShamanSkills;
        this.disableAutoNewGame = false;
        this.disableAutoShaman = false;
        this.disableAutoRespawn = true;
        this.disableAutoScore = false;
        this.disableAutoTimeLeft = false;
        this.disablePhysicalConsumables = withoutPhysicalConsumables;
        this.disableAdventureMaps = withoutAdventureMaps;
        this.enableMiceCollisions = withMiceCollisions;
        this.enableFallDamage = withFallDamage;
        this.canChangeMusic = true;
        this.isCurrentlyPlay = false;
        this.isFunCorp = false;
        this.isMulodrome = false;
        this.initVotingMode = false;
        this.isVotingBox = false;
        this.isVotingMode = false;
        this.isPlayingMusic = false;
        this.isEventTime = false;
        this.mapEditorMapXml = "";
        this.isMapEditorMapValidating = false;
        this.isMapEditorMapValidated = false;
        this.isChanged20secTimer = false;
        this.voteCloseTimer = new PSTimer(this.server.getScheduler());
        this.killAfkTimer = new PSTimer(this.server.getScheduler());
        this.changeMapTimer = new PSTimer(this.server.getScheduler());
        this.autoRespawnTimer = new PSTimer(this.server.getScheduler());
        this.startTimerLeft = new PSTimer(this.server.getScheduler());
        this.endSnowTimer = new PSTimer(this.server.getScheduler());
        this.consumablesTimers = new ArrayList<>();
        this.gameStartTimeMillis = System.currentTimeMillis();
        this.roomPassword = roomPassword;
        if (this.roomName.startsWith("*\u0003")) {
            this.roomType = RoomType.TRIBE_HOUSE;
            this.disableAutoRespawn = false;
            this.disableAutoShaman = true;
            this.disableAutoTimeLeft = true;
            this.disableAutoNewGame = true;
            this.disableAfkDeath = true;
        } else if (this.roomName.startsWith("\u0003[Editeur] ")) {
            this.roomType = RoomType.EDITOR;
            this.disableAutoTimeLeft = true;
            this.disableAfkDeath = true;
        } else if (this.roomName.startsWith("\u0003[Totem] ")) {
            this.roomType = RoomType.TOTEM;
            this.disableAutoTimeLeft = true;
            this.disableAfkDeath = true;
        } else if (this.roomName.startsWith("\u0003[Tutorial] ")) {
            this.roomType = RoomType.TUTORIAL;
            this.disableAutoShaman = true;
            this.disableAutoTimeLeft = true;
        } else if (this.roomName.startsWith("vanilla") || this.roomName.startsWith("*vanilla")) {
            this.roomType = RoomType.VANILLA;
        } else if (this.roomName.startsWith("survivor") || this.roomName.startsWith("*survivor")) {
            this.roomType = RoomType.SURVIVOR;
            this.roundTime = (short)(90 * (roundDuration / 100.0));
        } else if (this.roomName.startsWith("racing") || this.roomName.startsWith("*racing")) {
            this.roomType = RoomType.RACING;
            this.disableAutoShaman = true;
            this.roundTime = (short)(63 * (roundDuration / 100.0));
        } else if (this.roomName.startsWith("bootcamp") || this.roomName.startsWith("*bootcamp")) {
            this.roomType = RoomType.BOOTCAMP;
            this.roundTime = (short)(360 * (roundDuration / 100.0));
            this.disableAutoTimeLeft = true;
            this.disableAutoRespawn = false;
            this.disableAutoShaman = true;
            this.disableAfkDeath = true;
        } else if (this.roomName.startsWith("defilante") || this.roomName.startsWith("*defilante")) {
            this.roomType = RoomType.DEFILANTE;
            this.disableAutoShaman = true;
        } else if (this.roomName.startsWith("village") || this.roomName.startsWith("*village") || this.getRoomNameSuffix().equals("801")) {
            this.roomType = RoomType.VILLAGE;
            this.disableAutoRespawn = false;
            this.roundTime = 0;
            this.disableAutoTimeLeft = true;
            this.disableAutoShaman = true;
            this.disableAutoNewGame = true;
            this.disableAfkDeath = true;
        } else if (this.roomName.startsWith("music") || this.roomName.startsWith("*music")) {
            this.roomType = RoomType.MUSIC;
        } else {
            this.roomType = RoomType.NORMAL;
        }

        this.sendChangeMap();
    }

    /**
     * Creates a new room name.
     * @param server The game server's instance.
     * @param roomAuthor The room author's name.
     * @param roomName The room's name.
     */
    public GameRoom(GameServer server, String roomAuthor, String roomName) {
        this(server, roomAuthor, roomName, "", false, false, false, false, false, 100, 0, 0, List.of());
    }

    /**
     * Adds a player to the room.
     * @param player The player to add.
     */
    public void addPlayer(GamePlayer player) {
        this.players.put(player.getPlayerName(), player);
        this.server.getParseEvents().handleEventRoomPlayerCountChange(this);
        player.setPlayerRoom(this);
        player.isDead = this.isCurrentlyPlay;
        if (player.getPlayerData().getStaffRoles().contains(StaffRoleType.FUNCORP) && this.isFunCorp)
            this.funCorpMembers.add(player.getPlayerName());

        player.broadcastPacket(new PacketSendRoomServerType(0));
        player.broadcastPacket(new PacketSendRoomType(this.roomName.startsWith("music") ? 11 : this.roomName.startsWith("\u0003[Editeur] ") ? 6 : 4));
        player.broadcastPacket(new PacketSendRoomEnter(this.server.getIsOfficialRoom(this.roomName), this.roomName, this.roomCommunity));
        if (this.roomType == RoomType.MUSIC && this.isPlayingMusic)
            player.broadcastPacket(new PacketSendMusicVideo(this.musicVideos.getFirst(), this.musicTime));

        if (this.isFunCorp)
            player.broadcastPacket(new PacketSendTranslatedMessage("", "<FC>$FunCorpActiveAvecMembres</FC>", new String[]{String.join(", ", this.funCorpMembers)}));

        if (this.hasAnyRoomModifications())
            player.broadcastPacket(new PacketSendRoomInformation(this.disableAllShamanSkills, this.disablePhysicalConsumables, this.disableAdventureMaps, this.enableMiceCollisions, this.enableFallDamage, this.roundDuration, this.miceWeight, this.maximumPlayers, this.mapRotation));

        this.sendInitPlayerRound(player);
        this.broadcastPacket(player, new PacketSendRoomNewPlayer(player.getPlayerRoomInfo(), false, !player.isHidden));
        if (this.roomType == RoomType.RACING || this.roomType == RoomType.DEFILANTE) {
            GamePlayer highestScore = this.getHighScore();
            this.broadcastPacket(new PacketSendRoomRoundCount(this.roundCount, (highestScore != null ? highestScore.getSessionId() : 0)));
        }

        if (this.roomType == RoomType.VILLAGE) {
            int cnt = -1;
            for (var npcConfig : this.server.getVillageNPCConfig().entrySet()) {
                player.broadcastPacket(new PacketSendCreateNpc(--cnt, npcConfig.getKey(), npcConfig.getValue().getTitleId(), npcConfig.getValue().getIsFeminine(), npcConfig.getValue().getLook(), npcConfig.getValue().getX(), npcConfig.getValue().getY(), npcConfig.getValue().getEmote(), npcConfig.getValue().getIsFacingRight(), npcConfig.getValue().getIsFacingPlayer(), npcConfig.getValue().getNpcInterface(), npcConfig.getValue().getMessage()));
            }
        }

        this.parseLua.handleEventNewPlayer(player);
    }

    /**
     * Removes a player from the room.
     * @param player The player to remove.
     */
    public void removePlayer(GamePlayer player) {
        this.players.remove(player.getPlayerName(), player);
        player.playerScore = 0;
        this.sendResetPlayerRound(player);
        this.broadcastLegacyPacket(new PacketSendPlayerDisconnect(player.getSessionId()));
        if (this.currentSync == player)
            this.currentSync = this.getSync();

        if (this.isFunCorp) {
            player.funcorpPlayerName = "";
            player.funcorpPlayerLook = "";
            player.funcorpMouseColor = -1;
            player.funcorpNickColor = -1;
        }

        if (this.funCorpMembers.contains(player.getPlayerName()) && this.isFunCorp)  {
            this.funCorpMembers.remove(player.getPlayerName());
            if (this.funCorpMembers.isEmpty())
                this.sendDisableFunCorp();
        }

        if (this.isMulodrome) {
            this.mulodromeRedTeam.remove(player.getPlayerName());
            this.mulodromeBlueTeam.remove(player.getPlayerName());
            if (this.mulodromeRedTeam.isEmpty() && this.mulodromeBlueTeam.isEmpty()) {
                this.mulodromeRoundCount = 0;
                this.isMulodrome = false;
                this.broadcastPacket(new PacketSendMulodromeEnd());
            }
        }

        this.server.getParseEvents().handleEventRoomPlayerCountChange(this);
        this.parseLua.handleEventLeavePlayer(player);
        player.setPlayerRoom(null);
        if (this.players.isEmpty()) {
            this.changeMapTimer.cancelTask(false);
            this.killAfkTimer.cancelTask(false);
            this.autoRespawnTimer.cancelTask(false);
            this.voteCloseTimer.cancelTask(false);
            this.startTimerLeft.cancelTask(false);
            this.endSnowTimer.cancelTask(false);
            this.parseLua.sendRoomDestruction();
            for (PSTimer timer : this.consumablesTimers)
                timer.cancelTask(false);

            this.consumablesTimers.clear();
            this.server.getParseEvents().handleEventRoomDestruction(this);
            this.server.getRooms().remove(this.roomFullName, this);
        }
    }

    /**
     * Broadcasts a packet for everyone in the room.
     * @param packet The packet to send.
     */
    public void broadcastPacket(PSSendPacket packet) {
        for (GamePlayer player : this.players.values()) {
            player.broadcastPacket(packet);
        }
    }

    /**
     * Broadcasts a packet for everyone in the room except the given player.
     * @param senderPlayer The given player.
     * @param packet The packet to send.
     */
    public void broadcastPacket(GamePlayer senderPlayer, PSSendPacket packet) {
        for (GamePlayer player : this.players.values()) {
            if (!player.equals(senderPlayer)) {
                player.broadcastPacket(packet);
            }
        }
    }

    /**
     * Broadcasts a legacy packet for everyone in the room.
     * @param packet The packet to send.
     */
    public void broadcastLegacyPacket(PSSendPacket packet) {
        for (GamePlayer player : this.players.values()) {
            player.broadcastLegacyPacket(packet);
        }
    }

    /**
     * Broadcasts a legacy packet for everyone in the room except the given player.
     * @param senderPlayer The given player.
     * @param packet The packet to send.
     */
    public void broadcastLegacyPacket(GamePlayer senderPlayer, PSSendPacket packet) {
        for (GamePlayer player : this.players.values()) {
            if(!player.equals(senderPlayer))
                player.broadcastLegacyPacket(packet);
        }
    }

    /**
     * Checks can change the map.
     */
    public void checkChangeMap() {
        if (this.disableAutoNewGame || !this.disableAutoRespawn)
            return;

        if (this.players.values().stream().filter(player -> !player.isHidden).allMatch(player -> player.isDead))
            this.sendChangeMap();
    }

    /**
     * Checks if half of players are dead in this room.
     * @return Are half of players are killed/dead in this room.
     */
    public boolean checkIfTooFewRemaining() {
        return ((this.players.values().stream().filter(player -> !player.isHidden).filter(player -> !player.isDead).filter(player -> !player.isNewPlayer).filter(player -> !player.isEnteredInHole).count() * 100 + (this.players.size() >> 1)) / this.players.size()) >= 50;
    }

    /**
     * Checks if shaman/s in current map is dead.
     * @return True if shaman/s in current map is dead.
     */
    public boolean checkIfShamansAreDead() {
        if (!this.currentMap.isDualShaman)
            return (this.currentShaman != null && this.currentShaman.isDead && !this.currentShaman.canShamanRespawn);

        if (this.currentSecondShaman != null && this.currentShaman != null)
            return this.currentSecondShaman.isDead && this.currentShaman.isDead;

        return false;
    }

    /**
     * Checks if can skip the current music.
     */
    public void checkMusicSkip() {
        if (this.roomType == RoomType.MUSIC && this.isPlayingMusic) {
            int count = this.players.size();
            if (count % 2 != 0)
                count++;

            if (this.musicSkipVotes >= count / 2) {
                this.getMusicPlaylist().removeFirst();
                this.musicTime = 0;
                this.musicSkipVotes = 0;
                if (!this.musicVideos.isEmpty()) {
                    this.broadcastPacket(new PacketSendMusicVideo(this.musicVideos.getFirst(), 0));
                } else {
                    this.isPlayingMusic = false;
                }
            }
        }
    }

    /**
     * Gets the number of alive mice in the room.
     * @return The number of alive mice.
     */
    public int getAliveCount() {
        return (int)this.players.values().stream().filter(player -> !player.isDead && !player.isEnteredInHole && !player.isHidden && !player.isNewPlayer && !player.isShaman).count();
    }

    /**
     * Gets the current's map information.
     * @return The current's map information
     */
    public MapDetails getCurrentMap() {
        return this.currentMap;
    }

    /**
     * Gets the round's current shaman.
     * @return The round's current shaman.
     */
    public GamePlayer getCurrentShaman() {
        return this.currentShaman;
    }

    /**
     * Gets the cheeses's positions.
     * @return The cheeses's positions.
     */
    public Set<CheesePosition> getCheesePositions() {
        return this.cheesePositions;
    }

    /**
     * Gets the time when the round started.
     * @return The time when the round started.
     */
    public long getGameStartTimeMillis() {
        return this.gameStartTimeMillis;
    }
    /**
     * Gets the distinct player count.
     * @return The total players that have different ip addresses.
     */
    public int getDistinctPlayerCount() {
        return (int) this.players.values().stream().filter(player -> !player.isHidden).map(GamePlayer::getIpAddress).distinct().count();
    }

    /**
     * Gets the holes's positions.
     * @return The holes's positions.
     */
    public Set<HolePosition> getHolePositions() {
        return this.holePositions;
    }

    /**
     * Checks if the event maps are disabled in this room.
     * @return True if the event maps are disabled in this room or else False.
     */
    public boolean getIsDisableAdventureMaps() {
        return this.disableAdventureMaps;
    }

    /**
     * Checks if the fall damage is enabled.
     * @return True if the fall damage is enabled or else False.
     */
    public boolean getIsEnableFalLDamage() {
        return this.enableFallDamage;
    }

    /**
     * Checks if the mice collisions are enabled in the room.
     * @return True if the mice collisions are enabled or else False.
     */
    public boolean getIsEnableMiceCollisions() {
        return this.enableMiceCollisions;
    }

    /**
     * Checks if the room is in mulodrome phase.
     * @return True if the room is in mulodrome phase or else False.
     */
    public boolean getIsMulodrome() {
        return this.isMulodrome;
    }

    /**
     * Checks if the stats are going to be working in the room.
     * @return True if the stats will count or else False.
     */
    public boolean getIsCountingStats() {
        return !this.isMulodrome && !this.parseLua.getIsRunningLua() && !this.isFunCorp && this.roomPassword.isEmpty() && this.getDistinctPlayerCount() > this.server.getConfig().getGameConfig().getMinimumPlayersToCountStats() && !this.hasAnyRoomModifications();
    }

    /**
     * Checks if is snowing in the current room.
     * @return True if its snowing or else False.
     */
    public boolean getIsSnowing() {
        return this.isSnowing;
    }

    /**
     * Gets the room's last object id.
     * @return The room's last object id.
     */
    public int getLastObjectId() {
        return this.lastObjectID;
    }

    /**
     * Gets the last round id.
     * @return The last round id.
     */
    public int getLastRoundId() {
        return this.lastRoundId;
    }

    /**
     * Gets the map rotation.
     * @return The map rotation.
     */
    public List<Integer> getMapRotation() {
        return this.mapRotation;
    }

    /**
     * Gets the mice weight.
     * @return The mice weight.
     */
    public int getMiceWeight() {
        return this.miceWeight;
    }

    /**
     * Gets the room's maximum players.
     * @return The room's maximum players.
     */
    public int getMaximumPlayers() {
        return this.maximumPlayers;
    }

    /**
     * Gets the mulodrome's blue team.
     * @return The player names that are in mulodrome's blue team.
     */
    public Set<String> getMulodromeBlueTeam() {
        return this.mulodromeBlueTeam;
    }

    /**
     * Gets the players in the mulodrome's red team.
     * @return The players in the mulodrome's red team.
     */
    public Set<String> getMulodromeRedTeam() {
        return this.mulodromeRedTeam;
    }

    /**
     * Gets the music playlist.
     * @return The music playlist.
     */
    public List<Map<String, String>> getMusicPlaylist() {
        return this.musicVideos;
    }

    /**
     * Gets the next available object id.
     * @return The next available object id.
     */
    public int getNextObjectId() {
        return ++this.lastObjectID;
    }

    /**
     * Gets the lua module.
     * @return The lua module.
     */
    public ModuleLua getParseLua() {
        return this.parseLua;
    }

    /**
     * Gets the room's players.
     * @return The room's players.
     */
    public Map<String, GamePlayer> getPlayers() {
        return this.players;
    }

    /**
     * Gets the room's name.
     * @return The room's name.
     */
    public String getRoomName() {
        return this.roomFullName;
    }

    /**
     * Gets the room's community.
     * @return The room's community.
     */
    public String getRoomCommunity() {
        return this.roomCommunity;
    }

    /**
     * Gets the room's name without the community prefix.
     * @return The room's name wtihout the community prefix.
     */
    public String getRoomNameSuffix() {
        return this.roomName;
    }

    /**
     * Gets the room's creator.
     * @return The room's creator.
     */
    public String getRoomAuthor() {
        return this.roomAuthor;
    }

    /**
     * Gets the round's duration.
     * @return The round's duration.
     */
    public int getRoundDuration() {
        return this.roundDuration;
    }

    /**
     * Gets the list of players who have changed their size in a funcorp room.
     * @return The list of players who changed their size.
     */
    public List<String> getRoomFunCorpPlayersChangedSize() {
        return this.roomFunCorpPlayersChangedSize;
    }

    /**
     * Gets the players who have been linked in a funcorp room.
     * @return The players and their link.
     */
    public Map<String, String> getRoomFunCorpPlayersLinked() {
        return this.roomFunCorpPlayersLinked;
    }

    /**
     * Gets the list of players who have the meep ability enabled in a funcorp room.
     * @return The list of players who have the meep ability.
     */
    public List<String> getRoomFunCorpPlayersMeepAbility() {
        return this.roomFunCorpPlayersMeepAbility;
    }

    /**
     * Gets the players who have changed mouse colors in a funcorp room.
     * @return The map of players and their mouse colors.
     */
    public Map<String, Integer> getRoomFunCorpPlayersMouseColor() {
        return this.roomFunCorpPlayersMouseColor;
    }

    /**
     * Gets the players who have changed nick colors in a funcorp room.
     * @return The map of players and their nickname colors.
     */
    public Map<String, Integer> getRoomFunCorpPlayersNickColor() {
        return this.roomFunCorpPlayersNickColor;
    }

    /**
     * Gets the list of players who have the transformation enabled in a funcorp room.
     * @return The list of players who have the transformation ability.
     */
    public List<String> getRoomFunCorpPlayersTransformationAbility() {
        return this.roomFunCorpPlayersTransformationAbility;
    }

    /**
     * Gets the room's type.
     * @return The room's type.
     */
    public RoomType getRoomType() {
        return this.roomType;
    }

    /**
     * Gets the round's time.
     * @return The round's time.
     */
    public int getRoundTime() {
        return (!this.isChanged20secTimer ? this.roundTime + this.additionalTime : 20);
    }

    /**
     * Gets the room's server.
     * @return The room's server.
     */
    public GameServer getServer() {
        return this.server;
    }

    /**
     * Gets the synchronizer.
     * @return The synchronizer.
     */
    public GamePlayer getSync() {
        if (this.currentSync == null || !this.players.containsValue(this.currentSync)) {
            this.currentSync = null;
            int lowestPing = Integer.MAX_VALUE;
            for (GamePlayer player : this.players.values()) {
                if (player.lastPingValue < lowestPing) {
                    lowestPing = player.lastPingValue;
                    this.currentSync = player;
                }
            }
        }

        return this.currentSync;
    }

    /**
     * Gets the vampire's count in the room.
     * @return The vampire's count.
     */
    public int getVampireCount() {
        return this.vampireCount;
    }

    /**
     * Checks if the room has any modifications.
     * @return True if the room has modifications or else False.
     */
    public boolean hasAnyRoomModifications() {
        return this.disableAllShamanSkills || this.disablePhysicalConsumables || this.disableAdventureMaps || this.enableMiceCollisions || this.enableFallDamage || this.roundDuration != 100 || this.miceWeight != 0 || !this.mapRotation.isEmpty() || this.maximumPlayers > 0;
    }

    /**
     * Sends the last 20 seconds before the round ends if there are too little alive mice.
     */
    public void send20SecRemainingTimer() {
        if (this.disableAutoTimeLeft)
            return;

        long time = TimeUnit.SECONDS.toMillis(this.roundTime + this.additionalTime) - (System.currentTimeMillis() - this.getGameStartTimeMillis());
        if (time > 20 && !this.isChanged20secTimer) {
            this.changeMapTimer.cancelTask(true);
            this.changeMapTimer.schedule(this::sendChangeMap, 20, TimeUnit.SECONDS);
            this.players.values().forEach(player -> player.broadcastPacket(new PacketSendRoomRoundTime((short) 20)));
            this.isChanged20secTimer = true;
        }
    }

    /**
     * Adds a new anchor to the current room.
     * @param anchorData The anchor's information.
     */
    public void sendAddAnchor(List<String> anchorData) {
        List<String> newAnchors = new ArrayList<>();
        for (String anchor : anchorData) {
            if (!this.roomAnchors.contains(anchor)) {
                this.roomAnchors.add(anchor);
                newAnchors.add(anchor);
            }
        }

        if (!newAnchors.isEmpty())
            this.broadcastLegacyPacket(new PacketSendRoomAnchors(newAnchors));
    }

    /**
     * Adds a new bonus to the room.
     * @param bonusType The bonus's type.
     * @param posX The position on X-axis.
     * @param posY The position on Y-axis.
     * @param angle The angle.
     * @param isVisible is the bonus visible.
     */
    public void sendAddBonus(AddBonusType bonusType, int posX, int posY, int angle, boolean isVisible) {
        this.broadcastPacket(new PacketSendAddBonus(bonusType, posX, posY, angle, this.lastBonusId, isVisible));
        this.lastBonusId++;
    }

    /**
     * Spawns a shaman object.
     * @param objectId The object's id. (-1 makes it default).
     * @param shamanObjectId The shaman's object id.
     * @param posX The position on X.
     * @param posY The position on Y.
     * @param angle The angle.
     * @param velX The velocity on X.
     * @param velY The velocity on Y.
     * @param hasContactListener Does the object have contact listener.
     * @param miceCollidable Is the object collidable with the players.
     * @param sender The sender.
     * @param sendAll Should send the object to everyone.
     */
    public void sendAddShamanObject(int objectId, int shamanObjectId, int posX, int posY, int angle, int velX, int velY, boolean hasContactListener, boolean miceCollidable, GamePlayer sender, boolean sendAll) {
        byte[] colors = this.server.getParseShop().getCustomShamanColors(sender, shamanObjectId);
        if (objectId == -1)
            objectId = this.getNextObjectId();

        if (sendAll) {
            this.broadcastPacket(new PacketSendShamanSpawnObject(objectId, shamanObjectId, posX, posY, angle, velX, velY, hasContactListener, miceCollidable, colors));
        } else {
            this.broadcastPacket(sender, new PacketSendShamanSpawnObject(objectId, shamanObjectId, posX, posY, angle, velX, velY, hasContactListener, miceCollidable, colors));
        }

        this.parseLua.handleEventPlayerEndInvocation(sender, shamanObjectId, posX, posY, angle, velX, velY, hasContactListener, miceCollidable, colors);
    }

    /**
     * Adds a physical object.
     * @param objectType The object's type.
     * @param posX The position of X.
     * @param posY The position of Y.
     * @param width The object width.
     * @param height The object height.
     */
    public void sendAddPhysicObject(int objectType, int posX, int posY, int width, int height) {
        ///  TODO: IMPLEMENT
    }

    /**
     * Makes the player a vampire.
     * @param player The player to make a vampire.
     */
    public void sendAddVampire(GamePlayer player) {
        if (player.isVampire)
            return;

        player.isVampire = true;
        this.broadcastPacket(new PacketSendPlayerSetVampire(player.getSessionId(), true, true));
        this.vampireCount++;
    }

    /**
     * Sends a chat message.
     * @param playerName The message's author.
     * @param message The message's context.
     * @param isMumuted Is the player going to talk to himself.
     */
    public void sendChatMessage(String playerName, String message, boolean isMumuted) {
        PSSendPacket packet = new PacketSendChatMessage(playerName, message);
        GamePlayer player = this.players.get(playerName);
        if (player != null) {
            this.parseLua.handleChatMessage(player, message);
            if (message.startsWith("!")) {
                this.parseLua.handleChatCommand(player, message.substring(1));
                if (this.parseLua.getDisableChatCommandListDisplay().contains(message.substring(1).split(" ")[0])) {
                    return;
                }
            }
        }

        if (isMumuted) {
            if(player != null) {
                player.broadcastPacket(packet);
            }
        } else {
            this.broadcastPacket(packet);
        }
    }

    /**
     * Creates a new consumable timer.
     * @param objectId The consumable's id.
     */
    public void sendCreateConsumableTimer(int objectId) {
        PSTimer timer = new PSTimer(this.server.getScheduler());
        timer.schedule(() -> this.broadcastPacket(new PacketSendRemoveObject(objectId, true)), 10, TimeUnit.SECONDS);
        this.consumablesTimers.add(timer);
    }

    /**
     * Disables the FunCorp in the current room.
     */
    public void sendDisableFunCorp() {
        this.isFunCorp = false;
        this.isFunCorpHighlighedRoom = false;
        for (GamePlayer player : this.players.values()) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "<FC>$FunCorpDesactive</FC>"));
            player.funcorpPlayerName = "";
            player.funcorpMouseColor = -1;
            player.funcorpNickColor = -1;
            player.funcorpPlayerLook = "";
            if (this.roomFunCorpPlayersChangedSize.contains(player.getPlayerName()))
                this.broadcastPacket(new PacketSendPlayerChangeSize(player.getSessionId(), 100, false));
        }

        this.roomFunCorpPlayersChangedSize.clear();
        this.roomFunCorpPlayersMeepAbility.clear();
        this.roomFunCorpPlayersMouseColor.clear();
        this.roomFunCorpPlayersNickColor.clear();
        this.roomFunCorpPlayersTransformationAbility.clear();
        this.roomFunCorpPlayersLinked.clear();
        this.funCorpMembers.clear();
    }

    /**
     * Enables the FunCorp in the current room.
     */
    public void sendEnableFunCorp() {
        this.isFunCorp = true;
        this.isFunCorpHighlighedRoom = true;
        for (GamePlayer player : this.players.values()) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "<FC>$FunCorpActive</FC>"));
            if (player.hasStaffPermission(StaffRoleType.FUNCORP, false))
                this.funCorpMembers.add(player.getPlayerName());
        }
    }

    /**
     * Initializes the player on the room when a new round appears.
     * @param player The player to initialize.
     */
    public void sendInitPlayerRound(GamePlayer player) {
        player.setPlayerStartTimeMillis(this.gameStartTimeMillis);
        player.isNewPlayer = this.isCurrentlyPlay;
        player.isAfk = true;
        player.canTotem = true;
        player.broadcastPacket(new PacketSendRoomRoundCountDown(false));
        player.broadcastPacket(new PacketSendRoomMap(this.currentMap.mapCode, this.players.size(), this.lastRoundId, this.currentMap.mapXml, this.currentMap.mapName, (this.roomType == RoomType.EDITOR ? (this.isMapEditorMapValidating ? 100 : -1) : this.currentMap.mapPerma), this.currentMap.isInverted, this.currentMap.isConj, this.currentMap.isAIE || this.enableFallDamage, this.enableMiceCollisions, this.miceWeight));
        GamePlayer[] shamans = this.getShamanClients();
        player.isShaman = player == shamans[0] || player == shamans[1];
        if (player.isShaman && !this.disableAllShamanSkills && !this.parseLua.disableAllShamanSkills && !this.currentMap.isCatchTheCheese)
            player.getParseSkills().sendShamanRoomSkills();

        player.broadcastPacket(new PacketSendRoomPlayerList(this.getPlayerRoomInfos()));
        player.broadcastLegacyPacket(new PacketSendPlayerSynchronizer(this.getSync().getSessionId(), (this.roomType != RoomType.EDITOR || this.mapEditorMapCode != -1)));
        player.broadcastLegacyPacket(new PacketSendRoomAnchors(this.roomAnchors));
        player.broadcastPacket(new PacketSendRoomRoundTime((short) (this.roundTime + (int) ((this.gameStartTimeMillis / 1000) - Utils.getUnixTime()) + this.additionalTime)));
        if (this.currentMap.isCatchTheCheese) {
            player.broadcastLegacyPacket(new PacketSendCatchTheCheeseMap(shamans[0].getSessionId()));
            this.broadcastPacket(new PacketSendRoomGetCheese(shamans[0].getSessionId(), 1));
            if (this.currentMap.mapCode > 109 && this.currentMap.mapCode < 114) {
                player.broadcastPacket(new PacketSendShamanInfo(shamans[0].getSessionId(), 0, shamans[0].getPlayerData().getShamanType().ordinal(), 0, shamans[0].getPlayerData().shamanLevel, 0, shamans[0].getParseSkills().getShamanBadge(), 0, shamans[0].getPlayerData().shamanSkillsEnabled, true));
            }
        } else {
            player.broadcastPacket(new PacketSendShamanInfo(shamans[0] != null ? shamans[0].getSessionId() : 0, shamans[1] != null ? shamans[1].getSessionId() : 0, shamans[0] != null ? shamans[0].getPlayerData().getShamanType().ordinal() : 0, shamans[1] != null ? shamans[1].getPlayerData().getShamanType().ordinal() : 0, shamans[0] != null ? shamans[0].getPlayerData().shamanLevel : 0, shamans[1] != null ? shamans[1].getPlayerData().shamanLevel : 0, shamans[0] != null ? shamans[0].getParseSkills().getShamanBadge() : 0, shamans[1] != null ? shamans[1].getParseSkills().getShamanBadge() : 0, shamans[0] == null || shamans[0].getPlayerData().shamanSkillsEnabled, shamans[1] == null || shamans[1].getPlayerData().shamanSkillsEnabled));
        }

        player.broadcastPacket(new PacketSendRoomRoundCountDown((this.roomType == RoomType.NORMAL || this.roomType == RoomType.VANILLA || this.roomType == RoomType.SURVIVOR || this.roomType == RoomType.RACING || this.roomType == RoomType.DEFILANTE) && !this.isCurrentlyPlay && this.getDistinctPlayerCount() > 3));
        if (this.roomType == RoomType.TOTEM)
            player.sendTotemEditor();

        if (this.currentMap.isTransform || this.roomFunCorpPlayersTransformationAbility.contains(player.getPlayerName())) {
            player.canTransform = true;
            player.broadcastPacket(new PacketSendEnableTransformation(true));
        }

        if ((this.roomType == RoomType.SURVIVOR && player.isShaman) || this.roomFunCorpPlayersTransformationAbility.contains(player.getPlayerName())) {
            player.canMeep = true;
            player.broadcastPacket(new PacketSendEnableMeep(true));
        }

        if (this.roomType != RoomType.TOTEM && this.roomType != RoomType.EDITOR) {
            if (player.getPlayerData().petInfo[1] > Utils.getUnixTime())
                this.broadcastPacket(new PacketSendPlayerPet(player.getSessionId(), (int)player.getPlayerData().petInfo[0]));
            else
                player.getPlayerData().petInfo = new long[]{0, 0};
        }
    }

    /**
     * Reset's the player's round.
     * @param player The given player.
     */
    public void sendResetPlayerRound(GamePlayer player) {
        player.ambulanceCount = 0;
        player.bubbleCount = 0;
        player.cheeseCount = 0;
        player.currentPlace = 0;
        player.defilantePoints = 0;
        player.completedRounds = 0;
        player.positionX = -1;
        player.positionY = -1;
        player.canMeep = false;
        player.canShamanRespawn = false;
        player.canTotem = true;
        player.canTransform = false;
        player.iceCount = 2;
        player.isAfk = true;
        player.isDead = false;
        player.isDisintegration = false;
        player.isEnteredInHole = false;
        player.isNewPlayer = true;
        player.isShaman = false;
        player.isOpportunist = false;
        player.isVampire = false;
        player.getCheeseIdxs().clear();
    }

    /**
     * Sends the current map in the room.
     */
    public void sendChangeMap() {
        this.changeMapTimer.cancelTask(false);
        this.killAfkTimer.cancelTask(false);
        this.autoRespawnTimer.cancelTask(false);
        this.voteCloseTimer.cancelTask(false);
        this.startTimerLeft.cancelTask(false);
        this.endSnowTimer.cancelTask(false);
        for (PSTimer timer : this.consumablesTimers)
            timer.cancelTask(false);

        this.consumablesTimers.clear();
        if (this.isEventTime) {
            this.server.getParseEvents().handleEventRoomEnded(this);
            this.isEventTime = false;
        }

        if (this.getDistinctPlayerCount() > 3 && !this.isFunCorp) {
            if (this.initVotingMode) {
                if (!this.isVotingBox && this.currentMap.mapPerma == 0) {
                    this.isVotingMode = true;
                    this.isVotingBox = true;
                    this.voteCloseTimer.schedule(this::sendCloseMapVoting, 8, TimeUnit.SECONDS);
                    this.broadcastLegacyPacket(new PacketSendMapVotePopup(this.currentMap.mapName, this.currentMap.mapYesVotes, this.currentMap.mapNoVotes));
                    return;
                }

                this.isVotingMode = false;
                this.sendCloseMapVoting();
                return;
            }

            if (this.isVotingMode) {
                MapEntry map = this.server.getDatabase().loadMapByCode(this.currentMap.mapCode);
                map.mapYesVotes += this.receivedYesVotes;
                map.mapNoVotes += this.receivedNoVotes;
                if (map.mapYesVotes + map.mapNoVotes >= 100) {
                    double rating = (double) map.mapYesVotes / map.mapNoVotes * 100;
                    if (rating < 50)
                        map.mapCategory = 44;
                }

                this.server.getDatabase().saveMap(map);
                this.receivedYesVotes = 0;
                this.receivedNoVotes = 0;
                this.isVotingMode = false;
            }
        }

        this.initVotingMode = true;
        this.lastRoundId = ++this.lastRoundId % Byte.MAX_VALUE;
        if (this.roomType == RoomType.SURVIVOR) {
            for (GamePlayer player : this.players.values()) {
                if (!player.isDead && !player.isNewPlayer && (this.currentMap.mapPerma == 11 ? !player.isVampire : !player.isShaman)) {
                    player.playerScore += 10;
                }
            }
        }

        if (this.getIsCountingStats()) {
            if (this.roomType == RoomType.VANILLA || this.roomType == RoomType.NORMAL || this.roomType == RoomType.MUSIC) {
                if (this.currentShaman != null) {
                    this.currentShaman.playerScore += this.firstShamanNumCompleted;
                    this.currentShaman.getParseSkills().earnExp(true, this.firstShamanNumCompleted);
                }

                if (this.currentSecondShaman != null) {
                    this.currentSecondShaman.playerScore += this.secondShamanNumCompleted;
                    this.currentSecondShaman.getParseSkills().earnExp(true, this.secondShamanNumCompleted);
                }
            }

            if (this.roomType == RoomType.RACING) {
                for (GamePlayer player : this.players.values()) {
                    if (!player.isNewPlayer) {
                        player.getPlayerData().getRacingStats().roundsCount++;
                        if (player.getPlayerData().getRacingStats().roundsCount % this.server.getConfig().getGameConfig().getRoomStats().getRacingPlayRounds() == 0)
                            player.sendUnlockBadge(124);

                        if (player.isEnteredInHole) {
                            player.getPlayerData().getRacingStats().roundsCompleted++;
                            if (player.getPlayerData().getRacingStats().roundsCompleted % this.server.getConfig().getGameConfig().getRoomStats().getRacingCompleteRounds() == 0)
                                player.sendUnlockBadge(125);

                            if (player.currentPlace <= 3) {
                                player.getPlayerData().getRacingStats().roundsCompletedFirstSecondThird++;
                                if (player.getPlayerData().getRacingStats().roundsCompletedFirstSecondThird % this.server.getConfig().getGameConfig().getRoomStats().getRacingGetFirstSecondThird() == 0)
                                    player.sendUnlockBadge(127);

                                if (player.currentPlace == 1) {
                                    player.getPlayerData().getRacingStats().roundsCompletedFirst++;
                                    if (player.getPlayerData().getRacingStats().roundsCompletedFirst % this.server.getConfig().getGameConfig().getRoomStats().getRacingGetFirst() == 0)
                                        player.sendUnlockBadge(126);
                                }
                            }
                        }
                    }
                }
            }

            if (this.roomType == RoomType.SURVIVOR) {
                for (GamePlayer player : this.players.values()) {
                    if (!player.isNewPlayer) {
                        player.getPlayerData().getSurvivorStats().roundsCount++;
                        if (player.getPlayerData().getSurvivorStats().roundsCount % this.server.getConfig().getGameConfig().getRoomStats().getSurvivorPlayRounds() == 0)
                            player.sendUnlockBadge(120);

                        if (player.isShaman) {
                            player.getPlayerData().getSurvivorStats().roundsShaman++;
                            if (player.getPlayerData().getSurvivorStats().roundsShaman % this.server.getConfig().getGameConfig().getRoomStats().getSurvivorBeShaman() == 0)
                                player.sendUnlockBadge(121);

                            player.getPlayerData().getSurvivorStats().roundsKilledMice += this.deathMice;
                            player.getParseDailyQuests().increaseMission(MissionType.KILL_MICE_IN_SURVIVOR, this.deathMice);
                            if (player.getPlayerData().getSurvivorStats().roundsKilledMice % this.server.getConfig().getGameConfig().getRoomStats().getSurvivorKillMice() == 0)
                                player.sendUnlockBadge(122);

                        } else if (!player.isDead) {
                            player.getPlayerData().getSurvivorStats().roundsSurvived++;
                            if (player.getPlayerData().getSurvivorStats().roundsSurvived % this.server.getConfig().getGameConfig().getRoomStats().getSurvivorSurvive() == 0)
                                player.sendUnlockBadge(123);
                        }
                    }
                }
            }

            if (this.roomType == RoomType.DEFILANTE) {
                for (GamePlayer player : this.players.values()) {
                    if (!player.isNewPlayer) {
                        player.getPlayerData().getDefilanteStats().roundsCount++;
                        if (player.getPlayerData().getDefilanteStats().roundsCount % this.server.getConfig().getGameConfig().getRoomStats().getDefilantePlayRounds() == 0)
                            player.sendUnlockBadge(288);

                        player.getPlayerData().getDefilanteStats().defilantePointsCount += player.defilantePoints;
                        if (player.getPlayerData().getDefilanteStats().defilantePointsCount % this.server.getConfig().getGameConfig().getRoomStats().getDefilanteCollectPoints() == 0)
                            player.sendUnlockBadge(286);

                        if (player.currentPlace <= 3) {
                            player.getPlayerData().getDefilanteStats().roundsCompletedFirstSecondThird++;
                            if (player.getPlayerData().getDefilanteStats().roundsCompletedFirstSecondThird % this.server.getConfig().getGameConfig().getRoomStats().getDefilanteCompleteRoundsFirstSecondThird() == 0)
                                player.sendUnlockBadge(287);
                        }
                    }
                }
            }
        }

        this.currentShaman = null;
        this.currentSecondShaman = null;
        this.currentSync = null;
        this.firstShamanNumCompleted = 0;
        this.secondShamanNumCompleted = 0;
        this.numGetCheese = 0;
        this.additionalTime = 0;
        this.lastCloudObjectId = -1;
        this.lastCompanionBoxId = -1;
        this.lastObjectID = 0;
        this.lastBonusId = 0;
        this.vampireCount = 0;
        this.deathMice = 0;
        this.lastHandyMouseObjectId = new int[]{-1, -1};
        this.roomAnchors.clear();
        this.cheesePositions.clear();
        this.holePositions.clear();
        this.gameStartTimeMillis = System.currentTimeMillis();
        this.canChangeMusic = true;
        this.isCurrentlyPlay = false;
        this.isChanged20secTimer = false;
        this.currentMap = this.selectMap();
        this.getSync();
        if (!this.isEventTime) {
            this.getShamanClients();
            if (!this.disableAllShamanSkills && !this.parseLua.disableAllShamanSkills) {
                if (this.currentShaman != null && this.currentShaman.getPlayerData().shamanSkillsEnabled) {
                    this.additionalTime += this.currentShaman.getPlayerData().getShamanSkills().getOrDefault(SkillType.SKILL_ADDITIONAL_TIME, 0) * 5;
                }

                if (this.currentSecondShaman != null && this.currentSecondShaman.getPlayerData().shamanSkillsEnabled) {
                    this.additionalTime += this.currentSecondShaman.getPlayerData().getShamanSkills().getOrDefault(SkillType.SKILL_ADDITIONAL_TIME, 0) * 5;
                }
            }
        }

        this.players.values().forEach(this::sendResetPlayerRound);
        this.players.values().forEach(this::sendInitPlayerRound);
        if (this.isMulodrome) {
            this.mulodromeRoundCount++;
            this.broadcastPacket(new PacketSendMulodromeResult(this.mulodromeRoundCount, this.mulodromeBlueTeamCount, this.mulodromeRedTeamCount));
            if (this.mulodromeRoundCount <= 10) {
                for (GamePlayer player : this.players.values()) {
                    if (this.mulodromeBlueTeam.contains(player.getPlayerName()))
                        this.broadcastPacket(new PacketSendLuaNicknameColor(player.getSessionId(), 0x979EFF));
                    else if (this.mulodromeRedTeam.contains(player.getPlayerName()))
                        this.broadcastPacket(new PacketSendLuaNicknameColor(player.getSessionId(), 0xFF9396));
                }
            } else {
                this.broadcastPacket(new PacketSendMulodromeWinner(this.mulodromeBlueTeamCount, this.mulodromeRedTeamCount));
                this.sendMulodromeEnd();
            }
        }

        if (!this.isEventTime) {
            if (this.roomType == RoomType.RACING || this.roomType == RoomType.DEFILANTE) {
                GamePlayer highestScore = this.getHighScore();
                this.roundCount = ++this.roundCount % 10;
                this.broadcastPacket(new PacketSendRoomRoundCount(this.roundCount, (highestScore != null ? highestScore.getSessionId() : 0)));
                if (this.roundCount == 9) {
                    for (GamePlayer player : this.players.values())
                        player.playerScore = 0;
                }
            }

            if (this.roomType == RoomType.SURVIVOR && (this.currentMap.mapPerma == 11 || this.currentMap.mapPerma == 21)) {
                new PSTimer(this.server.getScheduler()).schedule(this::sendVampireMode, 5, TimeUnit.SECONDS);
            }
        } else {
            this.server.getParseEvents().handleEventRoomPostInitialization(this);
        }

        this.startTimerLeft.schedule(() -> {
            this.isCurrentlyPlay = true;
            this.broadcastPacket(new PacketSendRoomRoundCountDown(false));
        }, 3, TimeUnit.SECONDS);

        if (!this.disableAutoNewGame)
            this.changeMapTimer.schedule(this::sendChangeMap, this.roundTime + this.additionalTime, TimeUnit.SECONDS);

        if (!this.disableAfkDeath)
            this.killAfkTimer.schedule(this::sendKillAfkPlayers, 30, TimeUnit.SECONDS);

        if (!this.disableAutoRespawn)
            this.autoRespawnTimer.scheduleAtFixedRate(this::sendRespawnMice, 2, 2, TimeUnit.SECONDS);

        this.parseLua.handleEventMapChange(this);
    }

    /**
     * Changes the mulodrome current results.
     * @param bluePoints The blue team's points.
     * @param redPoints The red team's points.
     */
    public void sendMulodromeDelta(int bluePoints, int redPoints) {
        this.mulodromeBlueTeamCount += bluePoints;
        this.mulodromeRedTeamCount += redPoints;
        this.broadcastPacket(new PacketSendMulodromeResult(this.mulodromeRoundCount, this.mulodromeBlueTeamCount, this.mulodromeRedTeamCount));
    }

    /**
     * Starts the mulodrome session.
     */
    public void sendMulodromeStart() {
        if (this.isMulodrome)
            return;

        this.isMulodrome = true;
        this.disableAutoShaman = true;
        this.mulodromeRoundCount = 0;
        this.disableAutoTimeLeft = true;
        this.broadcastPacket(new PacketSendMulodromeEnd());
        this.sendChangeMap();
    }

    /**
     * Ends the mulodrome session.
     */
    public void sendMulodromeEnd() {
        if (!this.isMulodrome)
            return;

        this.isMulodrome = false;
        this.mulodromeRoundCount = 0;
        this.mulodromeBlueTeamCount = 0;
        this.mulodromeRedTeamCount = 0;
        this.mulodromeRedTeam.clear();
        this.mulodromeBlueTeam.clear();
        this.disableAutoTimeLeft = false;
        this.broadcastPacket(new PacketSendMulodromeEnd());
    }

    /**
     * Makes the player non-vampire.
     * @param player The player's instance.
     */
    public void sendRemoveVampire(GamePlayer player) {
        if (!player.isVampire)
            return;

        player.isVampire = false;
        this.broadcastPacket(new PacketSendPlayerSetVampire(player.getSessionId(), false, false));
        this.vampireCount--;
    }

    /**
     * Respawns a player in the current room.
     * @param player The player to respawn.
     */
    public void sendRespawnPlayer(GamePlayer player) {
        if (player.isDead) {
            player.isDead = false;
            player.isAfk = false;
            player.isEnteredInHole = false;
            player.cheeseCount = 0;
            player.setPlayerStartTimeMillis(System.currentTimeMillis());
            this.broadcastPacket(new PacketSendRoomNewPlayer(player.getPlayerRoomInfo(), false, true));
            this.parseLua.handleEventPlayerRespawn(player);
        }
    }

    /**
     * Starts to snow.
     * @param millis Milliseconds to snow (delay).
     * @param power Snow power.
     * @param enabled Enable snowing.
     */
    public void sendStartSnow(int millis, int power, boolean enabled) {
        this.isSnowing = enabled;
        this.broadcastPacket(new PacketSendRoomSnow(enabled, power));
        if (enabled)
            this.endSnowTimer.schedule(() -> {if (this.isSnowing) {this.sendStartSnow(0, power, false);}}, millis, TimeUnit.SECONDS);
    }

    /**
     * Changes the round time.
     * @param time The round time to set.
     */
    public void setRoundTime(int time) {
        this.changeMapTimer.schedule(this::sendChangeMap, time + 1, TimeUnit.SECONDS);
        this.players.values().forEach(client -> client.broadcastPacket(new PacketSendRoomRoundTime((short) (time + 1))));
    }

    /**
     * Retrieves the player with the highest score in the room.
     * @return The Player object.
     */
    private GamePlayer getHighScore() {
        GamePlayer best = null;
        for (GamePlayer player : this.players.values())
            if (!player.isHidden && (best == null || player.playerScore >= best.playerScore))
                best = player;

        return best;
    }

    /**
     * Retrieves the player with the second-highest score in the room.
     * @return The Player object.
     */
    private GamePlayer getSecondHighScore() {
        GamePlayer first = null, second = null;
        for (GamePlayer player : this.players.values()) {
            if (player.isHidden)
                continue;

            if (first == null || player.playerScore >= first.playerScore) {
                if (first == null || player.playerScore != first.playerScore)
                    second = first;

                first = player;
            } else if (second == null || player.playerScore >= second.playerScore) {
                second = player;
            }
        }

        return second;
    }

    /**
     * Gets the shaman session ids.
     * @return An array containing the shaman session ids.
     */
    private GamePlayer[] getShamanClients() {
        if (this.disableAutoShaman || this.currentMap.isNoShaman || this.isEventTime) {
            this.currentShaman = null;
            this.currentSecondShaman = null;
            return new GamePlayer[]{null, null};
        }

        if (this.currentShaman == null && this.forceNextShaman != null) {
            this.currentShaman = this.forceNextShaman;
            this.forceNextShaman = null;
        }

        this.currentShaman = (this.currentShaman != null) ? this.currentShaman : this.getHighScore();
        if (this.currentMap.isDualShaman)
            this.currentSecondShaman = (this.currentSecondShaman != null) ? this.currentSecondShaman : this.getSecondHighScore();

        return new GamePlayer[]{this.currentShaman, this.currentSecondShaman};
    }

    /**
     * Gets the room information for every player in the current room.
     * @return The players' room information.
     */
    private List<RoomPlayerInfo> getPlayerRoomInfos() {
        List<RoomPlayerInfo> data = new ArrayList<>(this.players.size());
        for (GamePlayer player : this.players.values())
            data.add(player.getPlayerRoomInfo());

        return data;
    }

    /**
     * Selects the next map.
     * @return A map object.
     */
    private MapDetails selectMap() {
        if (this.roomType == RoomType.TOTEM)
            return new MapDetails(444, null, false);

        if (this.roomType == RoomType.TUTORIAL)
            return new MapDetails(900, null, false);

        if (this.roomType == RoomType.VILLAGE)
            return new MapDetails(801, "_Atelier 801", this.server.getSpecialMapXml(801));

        if (this.roomType == RoomType.EDITOR)
            return new MapDetails(this.mapEditorMapCode, "-", this.mapEditorMapXml.getBytes());

        boolean isInverted = PSRandom.nextInt(0, 100) >= 90;
        if (this.parseLua.getIsRunningLua()) {
            MapDetails map = this.parseLua.getLuaMap();
            isInverted = this.parseLua.setAutoMapFlipMode;
            if (map != null)
                return map;
        }

        if (this.forceNextMap != null) {
            MapDetails map = this.forceNextMap;
            this.forceNextMap = null;
            return map;
        }

        if (this.roomType == RoomType.TRIBE_HOUSE) {
            TribeEntry tribe = this.server.getDatabase().loadTribeByName(this.getRoomNameSuffix());
            if (tribe != null) {
                int mapCode = tribe.tribeHouseMap;
                if (mapCode == 0)
                    return new MapDetails(0, "", this.server.getSpecialMapXml(0));

                MapEntry map = this.server.getDatabase().loadMapByCode(mapCode);
                return new MapDetails(map, isInverted);
            }

            return new MapDetails(0, "", this.server.getVanillaMapXml(0));
        }

        MapDetails eventMap = this.server.getParseEvents().getEventMap(this);
        if (eventMap != null) {
            this.isEventTime = true;
            return eventMap;
        }

        if (this.roomType == RoomType.VANILLA || (this.roomType == RoomType.MUSIC && PSRandom.nextInt(0, 100) >= 50)) {
            int mapCode = GameServer.VANILLA_MAP_LIST.stream().skip(PSRandom.nextInt(GameServer.VANILLA_MAP_LIST.size())).findFirst().orElse(0);
            return new MapDetails(mapCode, this.server.getVanillaMapXml(mapCode), isInverted);
        }

        int mapCategory = switch (this.roomType) {
            case SURVIVOR -> PSRandom.nextInt(0, 100) >= 70 ? 11 : 10;
            case RACING -> 17;
            case DEFILANTE -> 18;
            case BOOTCAMP -> PSRandom.nextInt(0, 100) >= 50 ? 13 : 3;
            case NORMAL -> {
                int value;
                do {
                    value = PSRandom.nextInt(0, 8);
                } while (value == 3);
                yield value;
            }
            case MUSIC -> 19;
            default -> -1;
        };

        if (!this.mapRotation.isEmpty()) {
            mapCategory = this.mapRotation.get(this.mapRotationIdx);
            this.mapRotationIdx++;
            if (this.mapRotationIdx >= this.mapRotation.size())
                this.mapRotationIdx = 0;
        }

        if (mapCategory != -1) {
            MapEntry map = this.server.getDatabase().loadRandomMapByCategory(mapCategory);
            if (map != null)
                return new MapDetails(map, isInverted);
        }

        return new MapDetails(0, null, false);
    }

    /**
     * Closes the map voting phase.
     */
    private void sendCloseMapVoting() {
        this.initVotingMode = false;
        this.isVotingBox = false;
        this.sendChangeMap();
    }

    /**
     * Kills all afk players in the room.
     */
    private void sendKillAfkPlayers() {
        for (GamePlayer player : this.players.values())
            if (!player.isDead && player.isAfk)
                player.sendDeath();

        this.checkChangeMap();
    }

    /**
     * Respawns the dead players in rooms where auto respawn is enabled.
     */
    private void sendRespawnMice() {
        for (GamePlayer player : this.players.values())
            if (player.isDead)
                this.sendRespawnPlayer(player);
    }

    /**
     * Sends the vampire mode in survivor rooms.
     */
    private void sendVampireMode() {
        GamePlayer player = this.players.values().stream().skip(PSRandom.nextInt(this.players.size())).findFirst().orElse(null);
        if (player != null && this.getDistinctPlayerCount() > 1)
            this.sendAddVampire(player);
    }

    /**
     * Creates a new map.
     */
    public static class MapDetails {
        public final int mapPerma;
        public final int mapCode;
        public final String mapName;
        public final byte[] mapXml;
        public final int mapYesVotes;
        public final int mapNoVotes;
        public final boolean isDudoe;
        public final boolean isConj;
        public final boolean isAIE;
        public final boolean isNoShaman;
        public final boolean isDualShaman;
        public final boolean isCatchTheCheese;
        public final boolean isTransform;
        public final boolean isInverted;
        public final boolean isGravity;

        /**
         * Creates a new vanilla map details.
         * @param mapCode The vanilla map's code.
         */
        public MapDetails(int mapCode, byte[] mapXml, boolean isInverted) {
            this.mapCode = mapCode;
            this.mapPerma = -1;
            this.mapName = "";
            this.mapXml = PSZlib.compress(mapXml);
            this.isDudoe = ((mapCode >= 176 && mapCode <= 183) || mapCode == 216);
            this.isConj = ((mapCode >= 101 && mapCode <= 107) || (mapCode >= 211 && mapCode <= 213));
            this.isNoShaman = GameServer.VANILLA_NO_SHAMAN_MAPS.contains(mapCode);
            this.isDualShaman = ((mapCode >= 44 && mapCode <= 53) || (mapCode >= 138 && mapCode <= 143) || mapCode == 223 || mapCode == 227);
            this.isCatchTheCheese = ((mapCode >= 108 && mapCode <= 113) || mapCode == 144 || mapCode == 170 || mapCode == 171 || mapCode == 214 || mapCode == 215 || mapCode == 239 || mapCode == 240);
            this.isTransform = mapCode >= 200 && mapCode <= 210;
            this.isInverted = isInverted;
            this.isGravity = mapCode == 81;
            this.isAIE = false;
            this.mapYesVotes = 0;
            this.mapNoVotes = 0;
        }

        /**
         * Creates a new event map details.
         * @param mapCode The vanilla map's code.
         */
        public MapDetails(int mapCode, String mapAuthor, byte[] mapXml) {
            this.mapCode = mapCode;
            this.mapPerma = -1;
            this.mapName = mapAuthor;
            this.mapXml = mapXml != null ? PSZlib.compress(mapXml) : new byte[0];
            this.isDudoe = false;
            this.isConj = false;
            this.isNoShaman = true;
            this.isDualShaman = false;
            this.isCatchTheCheese = false;
            this.isTransform = false;
            this.isInverted = false;
            this.isGravity = false;
            this.isAIE = false;
            this.mapYesVotes = 0;
            this.mapNoVotes = 0;
        }

        /**
         * Creates a new regular map details.
         * @param mapInfo The map's information.
         */
        public MapDetails(MapEntry mapInfo, boolean isInverted) {
            this.mapPerma = mapInfo.mapCategory;
            this.mapCode = mapInfo.getMapCode();
            this.mapName = mapInfo.getMapAuthor();
            this.mapXml = PSZlib.compress(mapInfo.mapXML.getBytes());
            this.mapYesVotes = mapInfo.mapYesVotes;
            this.mapNoVotes = mapInfo.mapNoVotes;
            this.isDudoe = mapInfo.mapXML.contains("dodue");
            this.isConj = mapInfo.mapXML.contains("conju");
            this.isAIE = mapInfo.mapXML.contains("aie");
            this.isNoShaman = !mapInfo.mapXML.contains("DC") && !mapInfo.mapXML.contains("DC2") && mapPerma != 4;
            this.isDualShaman = mapInfo.mapXML.contains("DC2");
            this.isCatchTheCheese = false;
            this.isTransform = false;
            this.isInverted = PSRandom.nextInt(1, 100) > 90;
            this.isGravity = mapInfo.mapXML.contains("G=");
        }
    }
}
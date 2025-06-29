package org.transformice;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import org.bytearray.ByteArray;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TimeOutDebugLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.transformice.libraries.Pair;
import org.transformice.libraries.SrcRandom;
import org.transformice.libraries.Timer;
import org.transformice.luapi.LuaApiLib;
import org.transformice.packets.SendPacket;

// Packets
import org.transformice.packets.send.chat.C_ChatMessage;
import org.transformice.packets.send.legacy.editor.C_MapVotePopup;
import org.transformice.packets.send.legacy.player.C_PlayerDisconnect;
import org.transformice.packets.send.legacy.player.C_PlayerShamanPerfomance;
import org.transformice.packets.send.lua.C_AddImage;
import org.transformice.packets.send.lua.C_CleanupLuaScripting;
import org.transformice.packets.send.lua.C_DisableProperties;
import org.transformice.packets.send.lua.C_InitializeLuaScripting;
import org.transformice.packets.send.lua.C_SetNicknameColor;
import org.transformice.packets.send.cafe.C_MulodromeEnd;
import org.transformice.packets.send.cafe.C_MulodromeResult;
import org.transformice.packets.send.cafe.C_MulodromeWinner;
import org.transformice.packets.send.player.C_GiveCurrency;
import org.transformice.packets.send.player.C_ShamanRespawn;
import org.transformice.packets.send.room.C_InvokeSnow;
import org.transformice.packets.send.transformice.C_SpawnPet;
import org.transformice.packets.send.newpackets.C_NewPlayer;
import org.transformice.packets.send.room.C_RoundsCount;
import org.transformice.packets.send.room.C_SetRoundTime;
import org.transformice.packets.send.room.C_SpawnObject;
import org.transformice.packets.send.room.C_StartRoundCountdown;

public final class Room {
    public int addTime;
    public int companionBox = -1;
    public int lastCloudID = -1;
    public int lastImageID = -1;
    public int musicSkipVotes;
    public boolean canAddPassword;
    public boolean canChangeMusic;
    public boolean autoMapFlipMode = true;
    public boolean disableAfkDeath = false;
    public boolean disableAutoRespawn = true;
    public boolean disableAutoShaman = false;
    public boolean disableAutoScore = false;
    public boolean disableAutoNewGame = false;
    public boolean disableAutoTimeLeft = false;
    public boolean disableMortCommand = false;
    public boolean disableDebugCommand = false;
    public boolean disableMinimalistMode = false;
    public boolean disableWatchCommand = false;
    public boolean disableEventLog = true;
    public boolean disableAllShamanSkills;
    public boolean isFunCorp;
    public boolean isFunCorpHighlighedRoom;
    public boolean isMapEditorMapValidating;
    public boolean isMapEditorMapValidated;
    public boolean isPlayingMusic;
    public boolean isFinishedLuaScript;
    public int[] lastHandymouse = new int[] {-1, -1};
    public Globals luaMinigame;
    public TimeOutDebugLib luaDebugLib = null;
    public LuaApiLib luaApi;
    public Client luaAdmin = null;
    public int shaman1NumCompleted;
    public int shaman2NumCompleted;
    public boolean isCurrentlyPlay;
    private int mulodromeRoundCount;
    private int blueTeamCount;
    private int redTeamCount;
    private boolean initVotingMode;
    private boolean isVotingBox;
    private boolean isVotingMode;
    private final Server server;
    @Getter private Client currentShaman;
    @Getter private Client currentSecondShaman;
    @Getter private int lastRoundId;
    @Getter private int lastObjectID;
    @Getter private int roundsCount;
    @Getter private MapDetails currentMap;
    @Getter private short roundTime;
    @Getter private long gameStartTimeMillis;
    @Getter private List<String> funcorpMembers;
    @Getter private boolean isChanged20secTimer = false;
    @Getter private boolean isEditeur = false;
    @Getter private boolean isNormal = false;
    @Getter private boolean isVanilla = false;
    @Getter private boolean isSurvivor = false;
    @Getter private boolean isRacing = false;
    @Getter private boolean isBootcamp = false;
    @Getter private boolean isDefilante = false;
    @Getter private boolean isMusic = false;
    @Getter private boolean isMinigame = false;
    @Getter private boolean isTribeHouse = false;
    @Getter private boolean isTotem = false;
    @Getter private boolean isTutorial = false;
    @Getter private boolean isVillage = false;
    @Getter private boolean isMulodrome = false;
    @Getter private boolean isLuaMinigame = false;
    @Getter private boolean isSnowing;
    @Getter private String minigameName = "";
    @Getter private final String roomName;
    @Getter private final String roomCommunity;
    @Getter private final String roomCreator;
    @Getter private final RoomDetails roomDetails;
    @Getter private final List<String> roomAnchors;
    @Getter private final List<Pair<Integer, Integer>> cheesesList;
    @Getter private final List<Pair<Integer, Pair<Integer, Integer>>> holesList;
    @Getter private final List<String> roomFunCorpPlayersChangedSize;
    @Getter private final List<String> roomFunCorpPlayersTransformationAbility;
    @Getter private final List<String> roomFunCorpPlayersMeepAbility;
    @Getter private final List<String> disabledChatCommandsDisplay;
    @Getter private final List<Pair<String, String>> roomFunCorpPlayersLinked;
    @Getter private final Map<String, Integer> roomFunCorpPlayersNickColor;
    @Getter private final Map<String, Integer> roomFunCorpPlayersMouseColor;
    @Getter private final Map<Integer, Timer> luaTimers;
    @Getter private final List<Map<String, String>> musicVideos;
    @Getter private final Object2ObjectMap<String, Client> players;
    @Getter private final List<String> redTeam;
    @Getter private final List<String> blueTeam;
    @Getter @Setter private Client currentSync;
    @Getter @Setter private Client forceNextShaman;
    @Getter @Setter private int maximumPlayers;
    @Getter @Setter private int numCompleted;
    @Getter @Setter private short musicTime;
    @Getter @Setter private long luaStartTimeMillis;
    @Getter @Setter private String mapEditorXml;
    @Getter @Setter private String roomPassword;

    // Timers
    public Timer luaLoopTimer;
    private final Timer autoRespawnTimer;
    private final Timer changeMapTimer;
    private final Timer checkChangeMapTimer;
    private final Timer closeRoomRoundJoinTimer;
    private final Timer killAfkTimer;
    private final Timer mapStartTimer;
    private final Timer voteCloseTimer;
    private final List<Timer> consumablesTimers;
    private final Timer endSnowTimer;

    /**
     * Creates a new room.
     * @param server The server instance.
     * @param roomName The room name.
     * @param roomCreator The room author.
     */
    public Room(Server server, String roomName, String roomCreator, RoomDetails roomDetails) {
        this.server = server;
        this.roomName = roomName;
        this.roomCommunity = (roomName.startsWith("*") ? "int" : roomName.substring(0, roomName.indexOf('-')));
        this.roomCreator = roomCreator;
        this.roomDetails = (roomDetails == null) ? new RoomDetails() : roomDetails;
        this.roomAnchors = new ArrayList<>();
        this.players = new Object2ObjectOpenHashMap<>();
        this.musicVideos = new ArrayList<>();
        this.cheesesList = new ArrayList<>();
        this.holesList = new ArrayList<>();
        this.maximumPlayers = this.roomDetails.maximumPlayers;
        this.roomPassword = this.roomDetails.roomPassword;
        this.disableAllShamanSkills = this.roomDetails.withoutShamanSkills;
        this.isFunCorp = false;
        this.funcorpMembers = new ArrayList<>();
        this.roomFunCorpPlayersChangedSize = new ArrayList<>();
        this.roomFunCorpPlayersTransformationAbility = new ArrayList<>();
        this.roomFunCorpPlayersMeepAbility = new ArrayList<>();
        this.roomFunCorpPlayersLinked = new ArrayList<>();
        this.roomFunCorpPlayersNickColor = new HashMap<>();
        this.roomFunCorpPlayersMouseColor = new HashMap<>();
        this.disabledChatCommandsDisplay = new ArrayList<>();
        this.blueTeam = new ArrayList<>();
        this.redTeam = new ArrayList<>();
        this.isCurrentlyPlay = false;
        this.gameStartTimeMillis = getUnixTime() * 1000;
        this.canChangeMusic = true;
        this.mapEditorXml = "";

        // Timers
        this.autoRespawnTimer = new Timer();
        this.changeMapTimer = new Timer();
        this.checkChangeMapTimer = new Timer();
        this.closeRoomRoundJoinTimer = new Timer();
        this.killAfkTimer = new Timer();
        this.mapStartTimer = new Timer();
        this.voteCloseTimer = new Timer();
        this.consumablesTimers = new ArrayList<>();
        this.luaTimers = new HashMap<>();
        this.endSnowTimer = new Timer();

        // Room checks
        this.canAddPassword = false;
        this.roundTime = (short)(120 * (this.roomDetails.roundDuration / 100.0));
        String roomNameCheck = this.roomName.startsWith("*") ? this.roomName.substring(1) : this.roomName.substring(this.roomName.indexOf('-') + 1);
        if (this.roomName.startsWith("*" + (char)3)) {
            this.disableAutoRespawn = false;
            this.disableAutoShaman = true;
            this.disableAutoTimeLeft = true;
            this.isTribeHouse = true;
        } else if (roomNameCheck.startsWith((char)3 + "[Editeur] ")) {
            this.isEditeur = true;
            this.disableAutoTimeLeft = true;
        } else if (roomNameCheck.startsWith((char)3 + "[Totem] ")) {
            this.isTotem = true;
            this.disableAutoTimeLeft = true;
        } else if(roomNameCheck.startsWith((char)3 + "[Tutorial] ")) {
            this.isTutorial = true;
            this.disableAutoShaman = true;
            this.disableAutoTimeLeft = true;
        } else if (roomNameCheck.startsWith("vanilla")) {
            this.isVanilla = true;
        } else if (roomNameCheck.startsWith("survivor")) {
            this.isSurvivor = true;
            this.roundTime = (short)(90 * (this.roomDetails.roundDuration / 100.0));
        } else if (roomNameCheck.startsWith("racing")) {
            this.isRacing = true;
            this.disableAutoShaman = true;
            this.roundTime = (short)(63 * (this.roomDetails.roundDuration / 100.0));
        } else if(roomNameCheck.startsWith("bootcamp")) {
            this.isBootcamp = true;
            this.roundTime = (short)(360 * (this.roomDetails.roundDuration / 100.0));
            this.disableAutoTimeLeft = true;
            this.disableAutoRespawn = false;
            this.disableAutoShaman = true;
            this.disableAfkDeath = true;
        } else if (roomNameCheck.startsWith("defilante")) {
            this.isDefilante = true;
            this.disableAutoShaman = true;
            this.disableAutoScore = true;
        } else if (roomNameCheck.startsWith("music")) {
            this.isMusic = true;
        } else if (roomNameCheck.startsWith("village") || roomNameCheck.equals("801")) {
            this.isVillage = true;
            this.disableAutoRespawn = false;
            this.roundTime = 0;
            this.disableAutoTimeLeft = true;
            this.disableAutoShaman = true;
            this.disableAutoNewGame = true;
        } else {
            this.isNormal = true;
        }

        if (roomNameCheck.matches("^((?!(bootcamp(\\d+(?!\\w+))|bootcamp(?!\\w+))|(racing(\\d+(?!\\w+))|racing(?!\\w+))|(defilante(\\d+(?!\\w+))|defilante(?!\\w+))|(vanilla(\\d+(?!\\w+))|vanilla(?!\\w+))|(survivor(\\d+(?!\\w+))|survivor(?!\\w+)|(music(\\d+(?!\\w+))|music(?!\\w+))|(village(\\d+(?!\\w+))|village(?!\\w+))))[\\s\\S])*$") && !roomNameCheck.matches("\\d+")) {
            this.canAddPassword = true;
        }

        if (this.roomName.matches("(\\*#|#)([a-z]+)(\\d+(([\\w\\s]+)|)|)")) {
            Matcher m = Pattern.compile("(\\*#|#)([a-z]+)(\\d+(([\\w\\s]+)|)|)").matcher(this.roomName);
            if(m.find()) {
                this.minigameName = m.group(2);
            }
        }

        this.changeMap();
        this.checkChangeMapTimer.schedule(this::checkChangeMap, 6, TimeUnit.SECONDS);
    }

    /**
     * Adds an image.
     * @param imageName The image file name.
     * @param target The target to attach the image.
     * @param xPosition X position of the image.
     * @param yPosition Y position of the image.
     * @param xScale X scale of the image.
     * @param yScale Y scale of the image.
     * @param rotation The image rotation.
     * @param alpha The image alpha.
     * @param anchorX The image anchor X.
     * @param anchorY The image anchor Y.
     * @param fadeIn Is the image fade in.
     * @param targetPlayer The target player to show the image.
     */
    public void addImage(String imageName, String target, int xPosition, int yPosition, int xScale, int yScale, int rotation, int alpha, int anchorX, int anchorY, boolean fadeIn, String targetPlayer) {
        int imgInfo = target.startsWith("#") ? 1 : target.startsWith("$") ? 2 : target.startsWith("%") ? 3 : target.startsWith("?") ? 4 : target.startsWith("_") ? 5 : target.startsWith("!") ? 6 : target.startsWith("&") ? 7 : target.startsWith(":") ? 8 : target.startsWith("~") ? 9 : target.startsWith("+") ? 10 : 0;
        target = target.substring(1);
        int targetSession = (this.players.get(target) != null) ? this.players.get(target).getSessionId() : -1;
        try {
            if(targetSession == -1) {
                targetSession = Integer.parseInt(target);
            }
        } catch (NumberFormatException _) {
        }

        this.lastImageID++;
        if(targetPlayer.isEmpty()) {
            this.sendAll(new C_AddImage(this.lastImageID, imageName, imgInfo, targetSession, xPosition, yPosition, xScale, yScale, rotation, alpha, anchorX, anchorY, fadeIn));
        } else {
            if(this.players.get(targetPlayer) != null) {
                this.players.get(targetPlayer).sendPacket(new C_AddImage(this.lastImageID, imageName, imgInfo, targetSession, xPosition, yPosition, xScale, yScale, rotation, alpha, anchorX, anchorY, fadeIn));
            }
        }
    }

    /**
     * Adds a player to current room.
     * @param player The player to add.
     */
    public void addPlayer(Client player) {
        player.setRoom(this);
        player.isDead = this.isCurrentlyPlay;
        this.players.put(player.getPlayerName(), player);
        player.sendRound();

        this.sendAllOthers(player, new C_NewPlayer(player.getPlayerRoomData(), false, !player.isHidden));
        if (!this.minigameName.isEmpty() && this.luaMinigame == null) {
            this.findLuaMinigame(player);
        }

        if (this.luaMinigame != null) {
            player.sendPacket(new C_InitializeLuaScripting());
            player.sendPacket(new C_DisableProperties(this.disableWatchCommand, this.disableDebugCommand, this.disableMinimalistMode));
            this.updatePlayerList(player);
            this.luaApi.callEvent("eventNewPlayer", player.getPlayerName());
        }
    }

    /**
     * Changes the current map.
     */
    public void changeMap() {
        for (Timer timer : new Timer[] {this.changeMapTimer, this.mapStartTimer, this.closeRoomRoundJoinTimer, this.killAfkTimer, this.voteCloseTimer, this.autoRespawnTimer}) {
            if (timer != null) {
                timer.cancel();
            }
        }

        for (Timer timer : this.consumablesTimers) {
            timer.cancel();
        }
        this.consumablesTimers.clear();
        if (this.initVotingMode) {
            if (!this.isVotingBox && (this.currentMap.mapPerma == 0 && this.currentMap.mapCode != -1) && this.getPlayersCount() >= 2) {
                this.isVotingMode = true;
                this.isVotingBox = true;
                this.voteCloseTimer.schedule(this::closeVoting, 8, TimeUnit.SECONDS);
                for (Client player : this.players.values()) {
                    player.sendOldPacket(new C_MapVotePopup(this.currentMap.mapName, this.currentMap.mapYesVotes, this.currentMap.mapNoVotes));
                }
                return;
            }
        }

        if (this.isVotingMode) {
            /// TODO: Save the vote into a db.
        }

        this.initVotingMode = true;
        this.lastRoundId = ++this.lastRoundId % Byte.MAX_VALUE;
        if (this.isSurvivor) {
            for (Client player : this.players.values()) {
                if (!player.isDead && (this.currentMap.mapPerma == 11 ? !player.isVampire : !player.isShaman)) {
                    player.playerScore += 10;
                }
            }
        }

        if (this.currentMap != null && !this.currentMap.isCatchTheCheese) {
            int numCom = this.currentMap.isDualShaman ? this.shaman1NumCompleted - 1 : this.numCompleted - 1;
            int numCom2 = this.currentMap.isDualShaman ? this.shaman2NumCompleted - 1 : 0;
            if (numCom < 0) numCom = 0;
            if (numCom2 < 0) numCom2 = 0;
            if (this.currentShaman != null) {
                for (Client player : this.players.values()) {
                    player.sendOldPacket(new C_PlayerShamanPerfomance(this.currentShaman.getPlayerName(), numCom));
                }

                if (!this.disableAutoScore) this.currentShaman.playerScore = numCom;
                if (numCom > 0) {
                    this.currentShaman.getParseSkillsInstance().earnExp(true, numCom);
                    this.currentShaman.getParseInventoryInstance().addConsumable(this.currentShaman.getAccount().isShamanNoSkills() ? "2620" : "2253", Math.max(3 + this.currentShaman.getAccount().getShamanType(), numCom / (6 - this.currentShaman.getAccount().getShamanType())), false);
                }
            }

            if (this.currentSecondShaman != null) {
                for (Client player : this.players.values()) {
                    player.sendOldPacket(new C_PlayerShamanPerfomance(this.currentSecondShaman.getPlayerName(), numCom2));
                }

                if (!this.disableAutoScore) this.currentSecondShaman.playerScore = numCom2;
                if (numCom2 > 0) {
                    this.currentSecondShaman.getParseSkillsInstance().earnExp(true, numCom2);
                    this.currentSecondShaman.getParseInventoryInstance().addConsumable(this.currentSecondShaman.getAccount().isShamanNoSkills() ? "2620" : "2253", Math.max(3 + this.currentSecondShaman.getAccount().getShamanType(), numCom2 / (6 - this.currentSecondShaman.getAccount().getShamanType())), false);
                }
            }
        }

        if (this.isSurvivor && (this.getPlayersCount() >= 11 || Application.getPropertiesInfo().is_debug)) {
            this.giveSurvivorStats();
            if (this.currentShaman != null && !this.currentShaman.isDead && this.getDeathCountNoShaman() > 0) {
                int count = 0;
                while (count < Math.min(5, this.getDeathCountNoShaman() / 4)) {
                    this.currentShaman.getParseInventoryInstance().addConsumable("2260", 1, false);
                    count += 1;
                }

                for (Client player : this.players.values()) {
                    if (!player.isDead && !player.isShaman) {
                        player.getAccount().setShopCheeses(player.getAccount().getShopCheeses() + 1);
                        player.cheeseCount += 1;
                        player.sendPacket(new C_GiveCurrency(0, 1));
                        player.getParseSkillsInstance().earnExp(false, 60);
                    }
                }
            }
        }

        if (this.isRacing && (this.getPlayersCount() >= 11 || Application.getPropertiesInfo().is_debug)) {
            this.giveRacingStats();
        }

        if (this.isDefilante && (this.getPlayersCount() >= 11 || Application.getPropertiesInfo().is_debug)) {
            this.giveDefilanteStats();
        }

        this.currentShaman = null;
        this.currentSecondShaman = null;
        this.currentSync = null;
        this.lastObjectID = 0;
        this.numCompleted = 0;
        this.shaman1NumCompleted = 0;
        this.shaman2NumCompleted = 0;
        this.addTime = 0;
        this.lastCloudID = -1;
        this.companionBox = -1;
        this.isChanged20secTimer = false;
        this.lastHandymouse = new int[] {-1, -1};
        this.gameStartTimeMillis = System.currentTimeMillis();
        this.isCurrentlyPlay = false;
        this.currentMap = this.selectMap();
        this.cheesesList.clear();
        this.holesList.clear();
        this.canChangeMusic = true;
        this.getSyncCode();
        this.getShamanClients();
        if (!this.disableAllShamanSkills) {
            if (this.currentShaman != null) {
                this.currentShaman.getParseSkillsInstance().sendShamanRoomSkillsPre();
            }

            if (this.currentSecondShaman != null) {
                this.currentSecondShaman.getParseSkillsInstance().sendShamanRoomSkillsPre();
            }
        }

        for (Client player : this.players.values()) {
            player.sendRoundReset();
        }

        for (Client player : this.players.values()) {
            player.sendRound();
        }

        for (Client player : this.players.values()) {
            if(player.getAccount().getPetType() != -1) {
                long time = getUnixTime();
                if(time < player.getAccount().getLastPetTime()) {
                    player.getAccount().setPetType(-1);
                    player.getAccount().setLastPetTime(time);
                }
            } else {
                this.sendAll(new C_SpawnPet(player.getSessionId(), player.getAccount().getPetType()));
            }
        }

        if (this.isSurvivor && this.currentMap.mapPerma == 11) {
            new Timer().schedule(this::sendVampireMode, 5, TimeUnit.SECONDS);
        }

        if (this.isMulodrome) {
            this.mulodromeRoundCount++;
            this.sendMulodromeRound();
            if (this.mulodromeRoundCount <= 10) {
                for (Client player : this.players.values()) {
                    if (this.blueTeam.contains(player.getPlayerName())) {
                        this.sendAll(new C_SetNicknameColor(player.getSessionId(), 9936639));
                    } else if (this.redTeam.contains(player.getPlayerName())) {
                        this.sendAll(new C_SetNicknameColor(player.getSessionId(), 16749462));
                    }
                }
            }
        }

        if (this.isRacing || this.isDefilante) {
            this.roundsCount = ++this.roundsCount % 10;
            Client player = this.players.get(this.getHighScore());
            this.sendAll(new C_RoundsCount(this.roundsCount, player != null ? player.getSessionId() : 0));
        }

        this.mapStartTimer.schedule(() -> {for (Client player : this.players.values()) { player.sendPacket(new C_StartRoundCountdown(false)); } }, 3, TimeUnit.SECONDS);
        this.closeRoomRoundJoinTimer.schedule(() -> this.isCurrentlyPlay = true, 3, TimeUnit.SECONDS);
        if (!this.disableAutoNewGame && !this.isTribeHouse) {
            this.changeMapTimer.schedule(this::changeMap, this.roundTime + this.addTime, TimeUnit.SECONDS);
        }
        this.killAfkTimer.schedule(this::killAfk, 30, TimeUnit.SECONDS);
        if (!this.disableAutoRespawn || this.isTribeHouse) {
            this.autoRespawnTimer.schedule(() -> this.respawnMice(true), 2, TimeUnit.SECONDS);
        }

        if (this.luaMinigame != null) {
            try {
                this.luaMinigame.get("tfm").get("get").get("room").set("objectList", new LuaTable());
                this.luaMinigame.get("tfm").get("get").get("room").set("playerList", this.getLuaPlayerList());
                this.luaMinigame.get("tfm").get("get").get("room").set("currentMap", !this.currentMap.mapXml.isEmpty() ? ("@" + this.currentMap.mapCode) : String.valueOf(this.currentMap.mapCode));
                this.luaMinigame.get("tfm").get("get").get("room").set("mirroredMap", LuaBoolean.valueOf(this.currentMap.isInverted));
                if (!this.currentMap.mapXml.isEmpty()) {
                    this.luaMinigame.get("tfm").get("get").get("room").set("xmlMapInfo", new LuaTable());
                    this.luaMinigame.get("tfm").get("get").get("room").get("xmlMapInfo").set("permCode", this.currentMap.mapPerma);
                    this.luaMinigame.get("tfm").get("get").get("room").get("xmlMapInfo").set("author", this.currentMap.mapName);
                    this.luaMinigame.get("tfm").get("get").get("room").get("xmlMapInfo").set("mapCode", this.currentMap.mapCode);
                    this.luaMinigame.get("tfm").get("get").get("room").get("xmlMapInfo").set("xml", this.currentMap.mapXml);
                }

            } catch (LuaError _) {}
            this.luaApi.callEvent("eventNewGame");
            try {
                this.luaMinigame.get("tfm").get("get").get("room").set("xmlMapInfo", LuaValue.NIL);
            } catch (LuaError _) {}
        }
    }

    /**
     * Removes a player from the current room.
     * @param player The player to remove.
     */
    public void removePlayer(Client player) {
        if(!this.players.containsValue(player)) return;

        this.players.remove(player.getPlayerName());
        player.sendRoundReset();
        player.playerScore = 0;
        this.sendAllOld(new C_PlayerDisconnect(player.getSessionId()));
        if(this.currentSync == player) {
            this.currentSync = null;
            this.getSyncCode();
        }

        if(this.isFunCorp) {
            player.setFunCorpNickname("");
            player.setFunCorpNickcolor(-1);
            player.setFunCorpMousecolor(-1);
        }

        if(this.isMulodrome) {
            this.redTeam.remove(player.getPlayerName());
            this.blueTeam.remove(player.getPlayerName());
            if (this.redTeam.isEmpty() && this.blueTeam.isEmpty()) {
                this.mulodromeRoundCount = 10;
                this.sendMulodromeRound();
            }
        }

        if (this.luaMinigame != null) {
            try {
                LuaTable table = this.luaMinigame.get("tfm").get("get").get("room").get("playerList").checktable();
                for (int index = 0; index < table.keys().length; index++) {
                    if (table.keys()[index].checkjstring().equals(player.getPlayerName())) {
                        table.remove(index);
                        break;
                    }
                }

            } catch (LuaError _) {

            }
            this.luaApi.callEvent("eventPlayerLeft", player.getPlayerName());
        }

        if(this.players.isEmpty()) {
            for (Timer timer : new Timer[] {this.changeMapTimer, this.mapStartTimer, this.closeRoomRoundJoinTimer, this.killAfkTimer, this.voteCloseTimer, this.autoRespawnTimer, this.endSnowTimer}) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            this.server.getRooms().remove(this.roomName);
        }
    }

    /**
     * Checks can change the map.
     */
    public void checkChangeMap() {
        if(this.isTribeHouse || this.isVillage || this.disableAutoNewGame || !this.disableAutoRespawn) return;

        if (this.players.values().stream().filter(player -> !player.isHidden).allMatch(player -> player.isDead)) {
            this.changeMap();
        }
    }

    /**
     * Checks if shaman/s in current map is dead.
     * @return True if shaman/s in current map is dead.
     */
    public boolean checkIfShamansAreDead() {
        if(!this.currentMap.isDualShaman) {
            return (this.currentShaman != null && this.currentShaman.isDead && !this.currentShaman.canShamanRespawn);
        }

        if(this.currentSecondShaman != null && this.currentShaman != null) {
            return this.currentSecondShaman.isDead && this.currentShaman.isDead;
        }

        return false;
    }

    /**
     * Checks if half of players are dead in this room.
     * @return Are half of players are killed/dead in this room.
     */
    public boolean checkIfTooFewRemaining() {
        return ((this.players.values().stream().filter(player -> !player.isHidden).filter(player -> !player.isDead).filter(player -> !player.isEnteredInHole).count() * 100 + (players.size() >> 1)) / players.size()) >= 50;
    }

    /**
     * Gets the number of alive mice in the room.
     * @return The number of alive mice.
     */
    public int getAliveCount() {
        return (int)this.players.values().stream().filter(player -> !player.isDead && !player.isEnteredInHole && !player.isHidden && !player.isNewPlayer).count();
    }

    /**
     * Gets the number of killed mice in the room by the shaman.
     * @return The number of dead mice.
     */
    public int getDeathCountNoShaman() {
        return (int)this.players.values().stream().filter(player -> !player.isShaman && player.isDead && !player.isNewPlayer).count();
    }

    /**
     * Gets the lua information on all players from the current room.
     * @return The lua table contains tfm.get.room.playerList[i] info.
     */
    public LuaTable getLuaPlayerList() {
        LuaTable table = new LuaTable();
        for (Client client : this.players.values()) {
            this.updatePlayerList(client, table);
        }

        return table;
    }

    /**
     * Gets the room information for every player in the current room.
     * @return The players' room information.
     */
    public List<ByteArray> getPlayerList() {
        List<ByteArray> result = new ArrayList<>(this.players.size());
        for (Client player : this.players.values()) {
            result.add(player.getPlayerRoomData());
        }
        return result;
    }

    /**
     * Gets the total number of players in current room.
     * @return The total players.
     */
    public int getPlayersCount() {
        return this.players.size();
    }

    /**
     * Gets the unique number of players in current room.
     * @return The distinct total players.
     */
    public int getDistinctPlayersCount() {
        List<String> list = new ArrayList<>();
        for (Client player : this.players.values()) {
            if (!list.contains(player.getIpAddress())) {
                list.add(player.getIpAddress());
            }
        }

        return list.size();
    }

    /**
     * Gets the shaman session ids.
     * @return An array containing the shaman session ids.
     */
    public Client[] getShamanClients() {
        if (this.disableAutoShaman || this.currentMap.isNoShaman) {
            this.currentShaman = null;
            this.currentSecondShaman = null;
            return new Client[]{null, null};
        }

        if (this.currentShaman == null && this.forceNextShaman != null) {
            this.currentShaman = this.forceNextShaman;
            this.forceNextShaman = null;
        }

        this.currentShaman = (this.currentShaman != null) ? this.currentShaman : this.getHighScore();
        if (this.currentMap.isDualShaman) {
            this.currentSecondShaman = (this.currentSecondShaman != null) ? this.currentSecondShaman : this.getSecondHighScore();
        }

        return new Client[]{this.currentShaman, this.currentSecondShaman};
    }

    /**
     * Gets the synchronizer session id.
     * @return The synchronizer session id.
     */
    public int getSyncCode() {
        if (this.currentSync == null) {
            if (this.getPlayersCount() > 0) {
                Object[] values = this.players.values().toArray();
                this.currentSync = (Client)values[SrcRandom.RandomNumber(0, values.length - 1)];
            }
        }

        return this.currentSync == null ? 0 : this.currentSync.getSessionId();
    }

    /**
     * Creates the mulodrome match.
     */
    public void initMulodrome() {
        this.isMulodrome = true;
        this.isRacing = true;
        this.disableAutoShaman = true;
        this.mulodromeRoundCount = 0;
        this.disableAutoTimeLeft = true;
        this.sendAll(new C_MulodromeEnd());
        this.changeMap();
    }

    /**
     * Respawns the given player in the current room.
     * @param playerName The player to respawn.
     */
    public void respawnPlayer(String playerName) {
        Client player = this.players.get(playerName);
        if ((player != null && player.isDead) && !player.isHidden) {
            player.isDead = false;
            player.isAfk = false;
            player.isEnteredInHole = false;
            player.cheeseCount = 0;
            player.setPlayerStartTimeMillis(getUnixTime() * 1000);
            this.sendAll(new C_NewPlayer(player.getPlayerRoomData(), !this.isBootcamp, false));
            if(player.isShaman) {
                this.sendAll(new C_ShamanRespawn(player.getSessionId(), player.getAccount().getShamanType(), player.getParseSkillsInstance().getShamanBadge(), player.getAccount().getPlayerSkills().size(), player.getAccount().isShamanNoSkills()));
            }

            if (player.getRoom().luaMinigame != null) {
                player.getRoom().luaApi.callEvent("eventPlayerRespawn", player.getPlayerName());
            }
        }
    }

    /**
     * Sends the last 20 seconds of the game.
     */
    public void send20SecRemainingTimer() {
        if(!this.isChanged20secTimer) {
            if(!this.disableAutoTimeLeft && this.roundTime + ((this.gameStartTimeMillis / 1000) - (getUnixTime())) > 21) {
                this.isChanged20secTimer = true;
                this.changeMapTimer.cancel();
                this.changeMapTimer.schedule(this::changeMap, 20, TimeUnit.SECONDS);
                for (Client player : this.players.values()) {
                    player.sendPacket(new C_SetRoundTime((short)20));
                }
            }
        }
    }

    /**
     * Sends a chat message to everyone in the current room.
     * @param playerName The player name.
     * @param message The message.
     * @param isOnly Send message only to himself.
     */
    public void sendChatMessage(String playerName, String message, boolean isOnly) {
        SendPacket packet = new C_ChatMessage(playerName, message);
        if (isOnly) {
            Client player = this.players.get(playerName);
            if (player != null) {
                player.sendPacket(packet);
            }

        } else {
            this.players.values().forEach(player -> player.sendPacket(packet));
        }
    }

    /**
     * Broadcasts a packet for everyone in the room.
     * @param packet The packet to send.
     */
    public void sendAll(SendPacket packet) {
        for (Client player : new HashMap<>(this.players).values()) {
            player.sendPacket(packet);
        }
    }

    /**
     * Broadcasts a packet for everyone in the room.
     * @param packet The packet to send.
     */
    public void sendAllOld(SendPacket packet) {
        for (Client player : new HashMap<>(this.players).values()) {
            player.sendOldPacket(packet);
        }
    }

    /**
     * Broadcasts a packet for everyone in the room except the given player.
     * @param senderPlayer The given player.
     * @param packet The packet to send.
     */
    public void sendAllOthers(Client senderPlayer, SendPacket packet) {
        for (Client player : new HashMap<>(this.players).values()) {
            if (!player.equals(senderPlayer)) {
                player.sendPacket(packet);
            }
        }
    }

    /**
     * Broadcasts a legacy packet for everyone in the room except the given player.
     * @param senderPlayer The given player.
     * @param packet The packet to send.
     */
    public void sendAllOthersOld(Client senderPlayer, SendPacket packet) {
        for (Client player : new HashMap<>(this.players).values()) {
            if (!player.equals(senderPlayer)) {
                player.sendOldPacket(packet);
            }
        }
    }

    /**
     * Sends a mulodrome round.
     */
    private void sendMulodromeRound() {
        this.sendAll(new C_MulodromeResult(this.mulodromeRoundCount, this.blueTeamCount, this.redTeamCount));
        if (this.mulodromeRoundCount > 10) {
            this.sendAll(new C_MulodromeEnd());
            this.sendAll(new C_MulodromeWinner(this.blueTeamCount, this.redTeamCount));
            this.isMulodrome = false;
            this.mulodromeRoundCount = 0;
            this.blueTeamCount = 0;
            this.blueTeam.clear();
            this.redTeamCount = 0;
            this.redTeam.clear();
            this.isRacing = false;
            this.disableAutoTimeLeft = false;
            this.disableAutoShaman = false;
        }
    }

    /**
     * Spawns the object in the current room.
     * @param objectId The object id.
     * @param code The shaman object id.
     * @param posX The X coordinate of the object.
     * @param posY The Y coordinate of the object.
     * @param angle The angle of the object.
     * @param velocityX The X's velocity of the object.
     * @param velocityY The Y's velocity of the object.
     * @param isMiceCollidable Is the object [spaced].
     * @param hasContactListener Have contact listener.
     * @param colors Object colors.
     * @param sender The person who spawned the object.
     * @param sendAll Send to all or everyone in the room except the sender.
     */
    public void sendPlaceObject(int objectId, int code, int posX, int posY, int angle, int velocityX, int velocityY, boolean isMiceCollidable, boolean hasContactListener, byte[] colors, Client sender, boolean sendAll) {
        this.lastObjectID = objectId;
        if(!sendAll) {
            this.sendAllOthers(sender, new C_SpawnObject(objectId, code, posX, posY, angle, velocityX, velocityY, hasContactListener, isMiceCollidable, colors));
        } else {
            this.sendAll(new C_SpawnObject(objectId, code, posX, posY, angle, velocityX, velocityY, hasContactListener, isMiceCollidable, colors));
        }
    }

    /**
     * Changes the map after given seconds.
     * @param seconds The given seconds.
     */
    public void setMapChangeTimer(int seconds) {
        this.changeMapTimer.schedule(this::changeMap, seconds+1, TimeUnit.SECONDS);
        for(Client client : this.players.values()) {
            client.sendPacket(new C_SetRoundTime(seconds+1));
        }
    }

    /**
     * Starts to snow.
     * @param millis Milliseconds to snow (delay).
     * @param power Snow power.
     * @param enabled Enable snowing.
     */
    public void startSnow(int millis, int power, boolean enabled) {
        this.isSnowing = enabled;
        this.sendAll(new C_InvokeSnow(enabled, power));
        if (enabled) {
            this.endSnowTimer.schedule(() -> {if (this.isSnowing) {this.startSnow(0, power, false);}}, millis, TimeUnit.SECONDS);
        }
    }

    /**
     * Stops the functions script.
     * @param changeMap Changes the map after stopping it.
     */
    public void stopLuaScript(boolean changeMap) {
        if (this.luaLoopTimer != null) {
            this.luaLoopTimer.cancel();
        }

        this.luaMinigame = null;
        this.luaAdmin = null;
        this.luaApi = null;
        this.luaDebugLib = null;
        this.isNormal = true;
        this.maximumPlayers = -1;
        this.disableEventLog = true;
        this.lastImageID = 0;
        this.isLuaMinigame = false;
        this.disableAfkDeath = false;
        this.isFinishedLuaScript = false;
        this.disableAutoNewGame = false;
        this.disableAutoScore = false;
        this.disableAllShamanSkills = false;
        this.disableAutoShaman = false;
        this.disableAutoTimeLeft = false;
        this.disableMortCommand = false;
        this.disableDebugCommand = false;
        this.disableMinimalistMode = false;
        this.disableWatchCommand = false;
        this.autoMapFlipMode = true;
        this.disabledChatCommandsDisplay.clear();
        this.luaTimers.clear();

        if (this.isSnowing) {
            this.startSnow(0, 0, false);
            this.endSnowTimer.cancel();
        }

        this.sendAll(new C_CleanupLuaScripting());
        if (changeMap) {
            this.changeMap();
        }
    }

    /**
     * Updates the tfm.get.room.playerList lua table.
     * @param client The player to update.
     */
    public void updatePlayerList(Client client) {
        try {
            this.updatePlayerList(client, this.luaMinigame.get("tfm").get("get").get("room").get("playerList"));
        } catch (LuaError _) {}
    }

    /**
     * Helper function to close the map voting popup.
     */
    private void closeVoting() {
        this.initVotingMode = false;
        this.isVotingBox = false;
        if (this.voteCloseTimer != null) {
            this.voteCloseTimer.cancel();
        }
        this.changeMap();
    }

    /**
     * Locates the minigame lua file and executes it.
     * @param player The given player.
     */
    private void findLuaMinigame(Client player) {
        if (this.luaMinigame != null) {
            this.stopLuaScript(false);
        }

        File file = new File("./lua/minigames/" + this.minigameName + ".lua");
        if (file.exists()) {
            try {
                this.sendAll(new C_InitializeLuaScripting());
                this.maximumPlayers = 50;
                this.isNormal = false;
                this.isLuaMinigame = true;
                this.luaAdmin = player;
                this.isFinishedLuaScript = false;
                Globals luaGlobal = JsePlatform.standardGlobals();
                this.luaMinigame = luaGlobal;
                luaGlobal.load(this.luaDebugLib = new TimeOutDebugLib());
                luaGlobal.load(this.luaApi = new LuaApiLib(this));
                this.luaDebugLib.setTimeOut(-1, false);

                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                }

                luaGlobal.load(sb.toString()).call();
                this.isFinishedLuaScript = true;
                this.luaApi.callPendentEvents();
            } catch (LuaError error) {
                Object[] errorInfo = error.getError();
                this.stopLuaScript(true);
                Application.getLogger().error("Minigame #{}: Init Error : {} {}", this.minigameName, (int) errorInfo[0] == -1 ? "" : (errorInfo[0] + ":"), errorInfo[1]);

            } catch (Exception error) {
                this.stopLuaScript(true);
                Application.getLogger().error("Minigame #{}: Init Error : {}", this.minigameName, error.getMessage());
            }
        }
    }

    /**
     * Retrieves the player with the highest score in the room.
     * @return The Player object.
     */
    private Client getHighScore() {
        Client player = null;
        int highestScore = 0;
        for (Client players : this.players.values()) {
            if (players.playerScore >= highestScore && !players.isHidden) {
                highestScore = players.playerScore;
                player = players;
            }
        }
        return player;
    }

    /**
     * Retrieves the player with the second-highest score in the room.
     * @return The Player object.
     */
    private Client getSecondHighScore() {
        Client player = null;
        int highestScore = (this.getHighScore() != null ? this.getHighScore().playerScore : 0);
        int secondHighScore = 0;
        for (Client players : this.players.values()) {
            if (players.playerScore >= secondHighScore && players.playerScore != highestScore && !players.isHidden) {
                secondHighScore = players.playerScore;
                player = players;
            }
        }

        return player;
    }

    /**
     * Gives the defilante stats.
     */
    private void giveDefilanteStats() {
        for(Client player : this.players.values()) {
            if (!player.isNewPlayer && !player.isGuest()) {
                player.getAccount().getDefilanteStats()[0] = player.getAccount().getDefilanteStats()[0] + 1;
                if(player.isEnteredInHole) {
                    player.getAccount().getDefilanteStats()[1] = player.getAccount().getDefilanteStats()[1] + 1;
                }
                player.getAccount().getDefilanteStats()[2] = player.getAccount().getDefilanteStats()[2] + player.defilantePoints;

                if(player.getAccount().getDefilanteStats()[0] > 1500 && player.getAccount().getDefilanteStats()[0] % 1500 == 0) {
                    player.getAccount().getShopBadges().put(288, player.getAccount().getDefilanteStats()[0] / 1500);
                }

                if(player.getAccount().getDefilanteStats()[1] > 10000 && player.getAccount().getDefilanteStats()[1] % 10000 == 0) {
                    player.getAccount().getShopBadges().put(287, player.getAccount().getDefilanteStats()[1] / 10000);
                }

                if(player.getAccount().getDefilanteStats()[2] > 100000 && player.getAccount().getDefilanteStats()[2] % 100000 == 0) {
                    player.getAccount().getShopBadges().put(286, player.getAccount().getDefilanteStats()[2] / 100000);
                }
            }
        }
    }

    /**
     * Gives the racing stats.
     */
    private void giveRacingStats() {
        for(Client player : this.players.values()) {
            if (!player.isNewPlayer && !player.isGuest()) {
                player.getAccount().getRacingStats()[0] = player.getAccount().getRacingStats()[0] + 1;
                if(player.isEnteredInHole) {
                    player.getAccount().getRacingStats()[1] = player.getAccount().getRacingStats()[1] + 1;
                    if(player.getCurrentPlace() <= 3) {
                        player.getAccount().getRacingStats()[2] = player.getAccount().getRacingStats()[2] + 1;
                    }
                    if(player.getCurrentPlace() == 1) {
                        player.getAccount().getRacingStats()[3] = player.getAccount().getRacingStats()[3] + 1;
                    }
                }

                if(player.getAccount().getRacingStats()[0] > 1500 && player.getAccount().getRacingStats()[0] % 1500 == 0) {
                    player.getAccount().getShopBadges().put(124, player.getAccount().getRacingStats()[0] / 1500);
                }

                if(player.getAccount().getRacingStats()[1] > 10000 && player.getAccount().getRacingStats()[1] % 10000 == 0) {
                    player.getAccount().getShopBadges().put(125, player.getAccount().getRacingStats()[1] / 10000);
                }

                if(player.getAccount().getRacingStats()[2] > 10000 && player.getAccount().getRacingStats()[2] % 10000 == 0) {
                    player.getAccount().getShopBadges().put(127, player.getAccount().getRacingStats()[2] / 10000);
                }

                if(player.getAccount().getRacingStats()[3] > 10000 && player.getAccount().getRacingStats()[3] % 10000 == 0) {
                    player.getAccount().getShopBadges().put(126, player.getAccount().getRacingStats()[3] / 10000);
                }
            }
        }
    }

    /**
     * Gives the survivor stats.
     */
    private void giveSurvivorStats() {
        for(Client player : this.players.values()) {
            if(!player.isNewPlayer && !player.isGuest()) {
                player.getAccount().getSurvivorStats()[0] = player.getAccount().getSurvivorStats()[0] + 1;
                if(player.isShaman) {
                    player.getAccount().getSurvivorStats()[1] = player.getAccount().getSurvivorStats()[1] + 1;
                    player.getAccount().getSurvivorStats()[2] = player.getAccount().getSurvivorStats()[2] + this.getDeathCountNoShaman();
                } else if(!player.isDead) {
                    player.getAccount().getSurvivorStats()[3] = player.getAccount().getSurvivorStats()[3] + 1;
                }

                if(player.getAccount().getSurvivorStats()[0] > 1000 && player.getAccount().getSurvivorStats()[0] % 1000 == 0) {
                    player.getAccount().getShopBadges().put(120, player.getAccount().getSurvivorStats()[0] / 1000);
                }

                if(player.getAccount().getSurvivorStats()[1] > 800 && player.getAccount().getSurvivorStats()[1] % 800 == 0) {
                    player.getAccount().getShopBadges().put(121, player.getAccount().getSurvivorStats()[1] / 800);
                }

                if(player.getAccount().getSurvivorStats()[2] > 20000 && player.getAccount().getSurvivorStats()[2] % 20000 == 0) {
                    player.getAccount().getShopBadges().put(122, player.getAccount().getSurvivorStats()[2] / 20000);
                }

                if(player.getAccount().getSurvivorStats()[3] > 10000 && player.getAccount().getSurvivorStats()[3] % 10000 == 0) {
                    player.getAccount().getShopBadges().put(123, player.getAccount().getSurvivorStats()[3] / 10000);
                }
            }
        }
    }

    /**
     * Kills the mice that are afk in current room.
     */
    private void killAfk() {
        if (!this.isEditeur && !this.disableAutoRespawn && !this.isTotem && !this.isTribeHouse && !this.disableAfkDeath) {
            if ((getUnixTime() - (this.gameStartTimeMillis / 1000)) < 32 && (getUnixTime() - (this.gameStartTimeMillis / 1000) > 28)) {
                for (Client player : this.players.values()) {
                    if (!player.isDead && player.isAfk) {
                        player.sendPlayerDeath();
                    }
                }

                this.checkChangeMap();
            }
        }
    }

    /**
     * Respawns everyone in the room.
     */
    private void respawnMice(boolean loop) {
        for(Client player : this.players.values()) {
            this.respawnPlayer(player.getPlayerName());
        }

        if(loop) {
            this.autoRespawnTimer.schedule(() -> this.respawnMice(true), 2, TimeUnit.SECONDS);
        }
    }

    /**
     * Sends the vampire mode in the current room.
     */
    private void sendVampireMode() {
        Objects.requireNonNullElseGet(this.currentSync, () -> this.players.values().stream().findFirst().get()).sendVampireMode(false);
    }

    /**
     * Updates the lua information on the given player.
     * @param client The given player.
     * @param playerList The lua table to update.
     */
    private void updatePlayerList(Client client, LuaValue playerList) {
        try {
            if (playerList.istable()) {
                LuaTable table = new LuaTable();
                table.set("isJumping", LuaBoolean.valueOf(client.isJumping));
                table.set("title", Math.floor(client.getAccount().getCurrentTitle()));
                table.set("y", client.getPosition().getSecond());
                table.set("x", client.getPosition().getFirst());
                table.set("id", client.getSessionId());
                table.set("isDead", LuaBoolean.valueOf(client.isDead));
                table.set("look", !client.tmpMouseLook.isEmpty() ? client.tmpMouseLook : client.getAccount().getMouseLook());
                table.set("isShaman", LuaBoolean.valueOf(client.isShaman));
                table.set("vx", client.getVelocity().getFirst());
                table.set("score", client.playerScore);
                table.set("inHardMode", client.getAccount().getShamanType() == 1 ? 1 : 0);
                table.set("vy", client.getVelocity().getSecond());
                table.set("movingRight", LuaBoolean.valueOf(client.isFacingRight));
                table.set("hasCheese", LuaBoolean.valueOf(client.cheeseCount > 0));
                table.set("registrationDate", client.getAccount().getRegDate());
                table.set("shamanMode", client.getAccount().getShamanType());
                table.set("playerName", client.getPlayerName());
                table.set("community", client.playerCommunity);
                table.set("tribeName", client.getAccount().getTribeName());
                table.set("movingLeft", LuaBoolean.valueOf(client.isFacingLeft));
                table.set("isFacingRight", LuaBoolean.valueOf(!client.isFacingLeft && client.isFacingRight));
                table.set("isVampire", LuaBoolean.valueOf(client.isVampire));
                playerList.set(client.getPlayerName(), table);
            }

        } catch (LuaError _) {}
    }





    public void startLuaLoop() {
        /*this.luaLoopTimer = new Timer().scheduleAtFixedRate(() -> {
            if (this.luaMinigame != null) {
                this.luaApi.callEvent("eventLoop", System.currentTimeMillis() - this.gameStartTimeMillis, ((!this.isChanged20secTimer ? this.roundTime + this.addTime : 20) * 1000L) + (this.gameStartTimeMillis - System.currentTimeMillis()));
            }
        }, 500, 500, TimeUnit.MILLISECONDS);
         */
    }





    private MapDetails selectMap() {
        /*
        boolean isDebug = true;
        if(isDebug) {
            return new MapDetails(
                    81 // 95
            );
        }

         */
        if(this.isVanilla) {
            int mapCode = Server.vanillaMapList.get(SrcRandom.RandomNumber(0, Server.vanillaMapList.size() - 1));
            MapDetails map = new MapDetails(mapCode);
            while (map == this.currentMap) {
                mapCode = Server.vanillaMapList.get(SrcRandom.RandomNumber(0, Server.vanillaMapList.size() - 1));
                map = new MapDetails(mapCode);
            }

            return map;
        }

        return new MapDetails(
                2,
                1000,
                "Bloom#0001",
                "<C><P MEDATA=\";;;;-0;0:::1-\" dodue=\"\" F=\"5\" /><Z><S><S Y=\"387\" H=\"68\" L=\"800\" P=\"0,0,0.3,0.2,0,0,0,0\" X=\"401\" T=\"18\" /></S><D><T Y=\"346\" X=\"46\" /><F Y=\"347\" X=\"765\" /><P Y=\"351\" T=\"1\" X=\"416\" P=\"0,0\" /><T Y=\"352\" X=\"580\" /><F Y=\"349\" X=\"194\" /><F Y=\"347\" X=\"385\" /></D><O /><L /></Z></C>",
                0,
                0
        );
    }









    public static class RoomDetails {
        public String roomPassword = "";
        public boolean withoutShamanSkills;
        public boolean withoutPhysicalConsumables;
        public boolean withoutAdventureMaps; //// TODO: add without adventure maps.
        public boolean withMiceCollisions;
        public boolean withFallDamage;
        public int roundDuration = 100;
        public int miceWeight;
        public short maximumPlayers;
        public ArrayList<Integer> mapRotation = new ArrayList<>(); /// TODO: add map rotation
    }

    public static class MapDetails {
        public int mapPerma;
        public int mapCode;
        public String mapName;
        public String mapXml = "";
        public int mapYesVotes = -1;
        public int mapNoVotes = -1;
        public boolean isDudoe;
        public boolean isConj;
        public boolean isAIE;
        public boolean isNoShaman;
        public boolean isDualShaman;
        public boolean isCatchTheCheese;
        public boolean isTransform;
        public boolean isInverted;

        // for vanilla
        public MapDetails(int mapCode) {
            this.mapPerma = -1;
            this.mapCode = mapCode;
            this.mapName = "Transformice";
            this.isDudoe = false;
            this.isConj = ((mapCode >= 101 && mapCode <= 107) || mapCode == 211 || mapCode == 212 || mapCode == 213);
            this.isAIE = false;
            this.isNoShaman = Server.vanillaNoShamMapList.contains(mapCode);
            this.isDualShaman = (mapCode >= 44 && mapCode <= 53) || (mapCode >= 138 && mapCode <= 143);
            this.isCatchTheCheese = (mapCode >= 108 && mapCode <= 114);
            this.isTransform = (mapCode >= 200 && mapCode <= 210);
            this.isInverted = false;
        }

        // for normal
        public MapDetails(int mapPerma, int mapCode, String mapAuthor, String mapXml, int mapYesVotes, int mapNoVotes) {
            this.mapPerma = mapPerma;
            this.mapCode = mapCode;
            this.mapName = mapAuthor;
            this.mapXml = mapXml;
            this.mapYesVotes = mapYesVotes;
            this.mapNoVotes = mapNoVotes;
            this.isDudoe = false;
            this.isConj = false;
            this.isAIE = false;
            this.isNoShaman = false;
            this.isDualShaman = false;
            this.isCatchTheCheese = false;
            this.isTransform = false;
            this.isInverted = false;
        }
    }
}
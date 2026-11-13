package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.ShamanType;
import org.transformice.modules.impl.ModuleRoom;
import org.transformice.modules.lua.LuaTfmTable;
import org.transformice.modules.lua.debug.LuaTraceback;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.lua.*;
import org.transformice.packets.send.player.PacketSendPlayerMove;

public final class ModuleLua extends ModuleRoom {
    private final ExecutorService luaExecutor;
    private final Map<Integer, PSTimer> luaTimers;
    private final List<String> disableChatCommandListDisplay;
    private final List<Object[]> pendentEvents;
    private volatile PSTimer luaLoopTimer;
    private volatile Globals luaGlobal;
    private volatile LuaValue luaDebug;
    private volatile GamePlayer luaAdmin;
    private GameRoom.MapDetails luaMap;
    public boolean isDisabledWatchCommand;
    public boolean isDisabledDebugCommand;
    public boolean isDisabledMinimalistMode;
    public boolean isDisabledEventLog;
    public boolean disablePhysicalConsumables;
    public boolean disableAllShamanSkills;
    public boolean disableMortCommand;
    public boolean disablePrespawnPreview;
    public boolean setAutoMapFlipMode;
    private volatile boolean isRunningLua;
    private volatile boolean isInitializingLua;
    private volatile boolean isDestroyed;
    private volatile long luaGeneration;

    /**
     * Creates a lua module.
     * @param server The game server.
     * @param room The game room.
     */
    public ModuleLua(GameServer server, GameRoom room) {
        super(server, room);
        this.luaExecutor = Executors.newSingleThreadExecutor();
        this.luaTimers = new ConcurrentHashMap<>();
        this.disableChatCommandListDisplay = new ArrayList<>();
        this.pendentEvents = new ArrayList<>();
        this.isDisabledEventLog = true;
        this.luaMap = null;
    }

    /**
     * Checks if a lua is running in this room.
     * @return True if a lua is running or else False.
     */
    public boolean getIsRunningLua() {
        return this.isRunningLua;
    }

    /**
     * Gets the lua admin's nickname.
     * @return THe lua admin's nickname.
     */
    public String getLuaAdminName() {
        if (this.luaAdmin == null)
            return "";

        return this.luaAdmin.getPlayerName();
    }

    /**
     * Gets the lua's map.
     * @return The lua's map.
     */
    public GameRoom.MapDetails getLuaMap() {
        return this.luaMap;
    }

    /**
     * Gets the lua's disableChatCommandDisplay commands.
     * @return The lua's disableChatCommandDisplay commands.
     */
    public List<String> getDisableChatCommandListDisplay() {
        return this.disableChatCommandListDisplay;
    }

    /**
     * Executes a lua script in the current room.
     * @param luaScript The lua script to execute.
     * @param player The person who executed the script.
     */
    public synchronized void executeScript(String luaScript, GamePlayer player) {
        RoomType roomType = this.room.getRoomType();
        if (roomType == RoomType.EDITOR || roomType == RoomType.TOTEM || this.isDestroyed)
            return;

        if (this.isRunningLua || this.isInitializingLua)
            this.sendRoomStopScript();

        long generation = ++this.luaGeneration;
        synchronized (this.pendentEvents) {
            this.pendentEvents.clear();
        }

        this.isRunningLua = false;
        this.isInitializingLua = true;
        this.luaAdmin = player;
        this.luaGlobal = JsePlatform.standardGlobals();
        this.luaGlobal.load(new DebugLib());
        this.luaDebug = this.luaGlobal.get("debug");
        this.luaGlobal.load(new LuaTfmTable(this.room, this.server, this.luaAdmin));
        for (GamePlayer client : this.room.getPlayers().values())
            this.sendLuaUpdatePlayerInfo(client, true);

        this.luaGlobal.get("debug").set("traceback", new LuaTraceback(this.luaDebug));
        player.luaThread = new Thread(() -> {
            long startTime = System.nanoTime();
            LuaValue debug = this.luaDebug;

            try {
                LuaValue hook = new VarArgFunction() {
                    @Override
                    public Varargs invoke(Varargs args) {
                        if (generation != luaGeneration || isDestroyed)
                            throw new LuaError("Lua script stopped.");

                        if (System.nanoTime() - startTime > 4_000_000_000L)
                            throw new LuaError("Lua destroyed : Runtime too long!");

                        return LuaValue.NONE;
                    }
                };

                debug.get("sethook").invoke(LuaValue.varargsOf(new LuaValue[] {hook, LuaValue.valueOf(""), LuaValue.valueOf(1000)}));
                this.luaGlobal.load(luaScript).call();
                if (generation != this.luaGeneration || this.isDestroyed)
                    return;

                synchronized (this) {
                    if (generation != this.luaGeneration || this.isDestroyed)
                        return;

                    this.isRunningLua = true;
                    this.isInitializingLua = false;
                    synchronized (this.pendentEvents) {
                        for (Object[] object : this.pendentEvents)
                            this.callEvent((String) object[0], (Object[]) object[1]);

                        this.pendentEvents.clear();
                    }

                    if (generation != this.luaGeneration)
                        return;

                    PSTimer loopTimer = new PSTimer(this.server.getScheduler());
                    this.luaLoopTimer = loopTimer;
                    loopTimer.scheduleAtFixedRate(() -> {
                        if (!this.isRunningLua || generation != this.luaGeneration || this.isDestroyed)
                            return;

                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = currentTime - this.room.getGameStartTimeMillis();
                        long remainingTime = this.room.getRoundTime() * 1000L - elapsedTime;
                        this.callEvent("eventLoop", elapsedTime, remainingTime);
                    }, 500, 500, TimeUnit.MILLISECONDS);

                    this.room.broadcastPacket(new PacketSendInitializeLuaRoom());
                }

                long endTime = (System.nanoTime() - startTime) / 1_000_000L;
                this.sendLuaMessage("<V>[" + this.room.getRoomName() + "]</V> [" + player.getPlayerName() + "] Lua script loaded in " + endTime + " ms (4000 max)");
            } catch (Exception error) {
                if (generation == this.luaGeneration && !this.isDestroyed) {
                    this.sendLuaMessage("<V>[" + this.room.getRoomName() + "]</V> Init Error : " + player.getPlayerName() + ".lua : " + error.getMessage());
                    this.sendRoomStopScript();
                }
            } finally {
                debug.get("sethook").call();
            }
        });

        player.luaThread.start();
    }

    /**
     * Handles when the player contacts a listener.
     * @param player The player who contacted the ground.
     * @param groundId The ID of the ground.
     * @param contactX The horizontal coordinate of the contact point.
     * @param contactY The vertical coordinate of the contact point.
     * @param playerX The player's horizontal coordinate at the time of contact.
     * @param playerY The player's vertical coordinate at the time of contact.
     * @param speedX The player's horizontal speed at the time of contact.
     * @param speedY The player's vertical speed at the time of contact.
     */
    public void handleEventContactListener(GamePlayer player, int groundId, int contactX, int contactY, int playerX, int playerY, int speedX, int speedY) {
        LuaTable contactInfos = new LuaTable();
        contactInfos.set("contactX", LuaValue.valueOf(contactX));
        contactInfos.set("contactY", LuaValue.valueOf(contactY));
        contactInfos.set("playerX", LuaValue.valueOf(playerX));
        contactInfos.set("playerY", LuaValue.valueOf(playerY));
        contactInfos.set("speedX", LuaValue.valueOf(speedX));
        contactInfos.set("speedY", LuaValue.valueOf(speedY));
        this.callEvent("eventContactListener", player.getPlayerName(), groundId, contactInfos);
    }

    /**
     * Handles when the map changes in the room.
     * @param gameRoom The room's instance.
     */
    public void handleEventMapChange(GameRoom gameRoom) {
        this.callEvent("eventNewGame", gameRoom.getLastRoundId());
    }

    /**
     * Handles when the player joins a room.
     * @param player The player's instance.
     */
    public void handleEventNewPlayer(GamePlayer player) {
        if (this.isRunningLua) {
            player.broadcastPacket(new PacketSendInitializeLuaRoom());
            player.broadcastPacket(new PacketSendLuaDisableRoomProperties(this.isDisabledWatchCommand, this.isDisabledDebugCommand, this.isDisabledMinimalistMode));
            this.sendLuaUpdatePlayerInfo(player, true);
            this.callEvent("eventNewPlayer", player.getPlayerName());
        }
    }

    /**
     * Handles whe the player leaves the room.
     * @param player The player's instance.
     */
    public void handleEventLeavePlayer(GamePlayer player) {
        if (this.isRunningLua) {
            this.sendLuaUpdatePlayerInfo(player, false);
            this.callEvent("eventPlayerLeft", player.getPlayerName());
        }
    }

    /**
     * Handles when the player dies.
     * @param player The player's instance
     */
    public void handleEventPlayerDeath(GamePlayer player) {
        this.sendLuaUpdatePlayerInfo(player, true);
        this.callEvent("eventPlayerDied", player.getPlayerName());
    }

    /**
     * Handles when the player makes an emote.
     * @param player The player's instance.
     * @param emoteId The emote's id.
     */
    public void handleEventPlayerEmote(GamePlayer player, int emoteId) {
        this.callEvent("eventEmotePlayed", player.getPlayerName(), emoteId, "");
    }

    /**
     * Handles when the player gets a cheese.
     * @param player The player's instance.
     */
    public void handleEventPlayerGetCheese(GamePlayer player) {
        this.sendLuaUpdatePlayerInfo(player, true);
        this.callEvent("eventPlayerGetCheese", player.getPlayerName());
    }

    /**
     * Handles when the player meeps.
     * @param player The player's instance.
     * @param x The position X.
     * @param y The position Y.
     */
    public void handleEventPlayerMeep(GamePlayer player, int x, int y) {
        this.callEvent("eventPlayerMeep", player.getPlayerName(), x, y);
    }

    /**
     * Handles when the playe respawns.
     * @param player The player's instance.
     */
    public void handleEventPlayerRespawn(GamePlayer player) {
        this.callEvent("eventPlayerRespawn", player.getPlayerName());
    }

    /**
     * Handles when the player grabs a bonus.
     * @param player The player's instance.
     * @param bonusId The bonus id.
     */
    public void handleEventPlayerBonusGrabbed(GamePlayer player, int bonusId) {
        this.callEvent("eventPlayerBonusGrabbed", player.getPlayerName(), bonusId);
    }

    /**
     * Handles when the player starts invocation.
     * @param player The player's instance.
     * @param objectCode The object's code.
     * @param posX The position's X.
     * @param posY The position's Y.
     * @param angle The angle.
     * @param rotation The rotation.
     */
    public void handleEventPlayerStartInvocation(GamePlayer player, int objectCode, int posX, int posY, int angle, String rotation) {
        this.callEvent("eventSummoningStart", player.getPlayerName(), objectCode, posX, posY, angle, rotation);
    }

    /**
     * Handles when the player ends the summoning.
     * @param player The player's instance.
     * @param shamanObjectId The object's id.
     * @param posX The position X.
     * @param posY The position Y.
     * @param angle The angle.
     * @param velX The velocity X.
     * @param velY The velocity Y.
     * @param hasContactListener Has contact listener.
     * @param miceCollidable Is mice collidable.
     */
    public void handleEventPlayerEndInvocation(GamePlayer player, int shamanObjectId, int posX, int posY, int angle, int velX, int velY, boolean hasContactListener, boolean miceCollidable, byte[] colors) {
        LuaTable table = new LuaTable();
        table.set("id", this.room.getLastObjectId());
        table.set("type", LuaValue.valueOf(shamanObjectId));
        table.set("baseType", LuaValue.valueOf(shamanObjectId));
        table.set("x", LuaValue.valueOf(posX));
        table.set("y", LuaValue.valueOf(posY));
        table.set("vx", LuaValue.valueOf(velX));
        table.set("vy", LuaValue.valueOf(velY));
        table.set("angle", LuaValue.valueOf(angle));
        table.set("ghost", LuaValue.valueOf(miceCollidable));
        table.set("colors", LuaValue.valueOf(colors));
        this.callEvent("eventSummoningEnd", player.getPlayerName(), shamanObjectId, posX, posY, angle, table);
    }

    /**
     * Handles when the player stops invocation.
     * @param player The player's instance.
     */
    public void handleEventPlayerStopInvocation(GamePlayer player) {
        this.callEvent("eventSummoningCancel", player.getPlayerName());
    }

    /**
     * Handles when the player enters in the hole.
     * @param player The player's instance.
     * @param timeElapsed Time elapsed.
     * @param timeElapsedSinceRespawn Time elapsed since respawn.
     */
    public void handleEventPlayerWin(GamePlayer player, int timeElapsed, int timeElapsedSinceRespawn) {
        this.callEvent("eventPlayerWon", player.getPlayerName(), timeElapsed, timeElapsedSinceRespawn);
    }

    /**
     * Handles when the player becomes a vampire.
     * @param player The player's instance.
     */
    public void handleEventPlayerVampire(GamePlayer player, String infectorName) {
        this.sendLuaUpdatePlayerInfo(player, true);
        this.callEvent("eventPlayerVampire", player.getPlayerName(), infectorName);
    }

    /**
     * Handles when the player send a chat command.
     * @param player The player's name.
     * @param message The chat's command.
     */
    public void handleChatCommand(GamePlayer player, String message) {
        this.callEvent("eventChatCommand", player.getPlayerName(), message);
    }

    /**
     * Handles when the player send a chat message.
     * @param player The player's name.
     * @param message The chat's message.
     */
    public void handleChatMessage(GamePlayer player, String message) {
        this.callEvent("eventChatMessage", player.getPlayerName(), message);
    }

    /**
     * Handles when the player picks a color.
     * @param player The player's instance.
     * @param colorPickerId The ID of the color picked in which the color has been selected.
     * @param color The color code selected by the player. It will be -1 if no color has been selected.
     */
    public void handleColorPicked(GamePlayer player, int colorPickerId, int color) {
        this.callEvent("eventColorPicked", colorPickerId, player.getPlayerName(), color);
    }

    /**
     * Handles when the player interacts with lua NPC.
     * @param player The player's instance.
     * @param npcName The NPC's name.
     */
    public void handleInteractNPC(GamePlayer player, String npcName) {
        this.callEvent("eventTalkToNPC", player.getPlayerName(), npcName);
    }

    /**
     * Handles when the player presses a key on the keyboard.
     * @param player The player's instance.
     * @param keyCode The code of the typed key.
     * @param down Whether it was the press event, or the release one.
     * @param xPos The horizontal coordinate of the player when he or she typed the key.
     * @param yPos The vertical coordinate of the player when he or she typed the key.
     * @param xVel The horizontal velocity of the player when he or she typed the key.
     * @param yVel The vertical velocity of the player when he or she typed the key.
     */
    public void handleKeyboard(GamePlayer player, int keyCode, boolean down, int xPos, int yPos, int xVel, int yVel) {
        if (player.isDebugKeyboard)
            player.broadcastPacket(new PacketSendServerMessage(true, String.format("Key: %d, Down: %b, PosX: %d, PosY: %d, VelX: %d, VelY: %d", keyCode, down, xPos, yPos, xVel, yVel)));

        this.callEvent("eventKeyboard", player.getPlayerName(), keyCode, down, xPos, yPos, xVel, yVel);
    }

    /**
     * Handles when the player clicks with the mouse.
     * @param player The player's instance.
     * @param posX The X's position.
     * @param posY The Y's position.
     */
    public void handleMouse(GamePlayer player, int posX, int posY) {
        if (player.isDebugMouse) {
            player.broadcastPacket(new PacketSendPlayerMove(posX, posY, false, 0, 0, false));
            player.broadcastPacket(new PacketSendServerMessage(true, String.format("PosX: %d, PosY: %d", posX, posY)));
        }

        this.callEvent("eventMouse", player.getPlayerName(), posX, posY);
    }

    /**
     * Handles when the player answers a popup.
     * @param player The player's instance.
     * @param popupId The popup's id.
     * @param answer The answer.
     */
    public void handlePopupAnswer(GamePlayer player, int popupId, String answer) {
        this.callEvent("eventPopupAnswer", popupId, player.getPlayerName(), answer);
    }

    /**
     * Handles when the player clicks text event.
     * @param player The player's instance.
     * @param textAreaId The text area's id.
     * @param eventName The event's name.
     */
    public void handleTextAreaCallback(GamePlayer player, int textAreaId, String eventName) {
        this.callEvent("eventTextAreaCallback", textAreaId, player.getPlayerName(), eventName);
    }

    /**
     * Sends a #lua message.
     * @param message The message to send.
     */
    public void sendLuaMessage(String message) {
        if (this.luaAdmin == null)
            return;

        this.luaAdmin.broadcastPacket(new PacketSendLuaMessage(message));
    }

    /**
     * Handles when the script gets stopped in the room.
     */
    public synchronized void sendRoomStopScript() {
        RoomType roomType = this.room.getRoomType();
        this.luaGeneration++;
        this.isRunningLua = false;
        this.isInitializingLua = false;
        if (this.luaAdmin != null && this.luaAdmin.luaThread != null)
            this.luaAdmin.luaThread.interrupt();

        this.luaAdmin = null;
        this.isDisabledMinimalistMode = false;
        this.isDisabledDebugCommand = false;
        this.isDisabledWatchCommand = false;
        switch (roomType) {
            case TRIBE_HOUSE -> {
                this.room.disableAutoRespawn = false;
                this.room.disableAutoShaman = true;
                this.room.disableAutoTimeLeft = true;
                this.room.disableAutoNewGame = true;
                this.room.disableAfkDeath = true;
            }
            case TUTORIAL -> {
                this.room.disableAutoShaman = true;
                this.room.disableAutoTimeLeft = true;
            }
            case RACING, DEFILANTE -> this.room.disableAutoShaman = true;
            case BOOTCAMP -> {
                this.room.disableAutoTimeLeft = true;
                this.room.disableAutoRespawn = false;
                this.room.disableAutoShaman = true;
                this.room.disableAfkDeath = true;
            }
            case VILLAGE -> {
                this.room.disableAutoRespawn = false;
                this.room.disableAutoTimeLeft = true;
                this.room.disableAutoShaman = true;
                this.room.disableAutoNewGame = true;
                this.room.disableAfkDeath = true;
            }
        }

        this.room.maximumPlayers = 0;
        this.disableChatCommandListDisplay.clear();
        PSTimer loopTimer = this.luaLoopTimer;
        this.luaLoopTimer = null;
        if (loopTimer != null)
            loopTimer.cancelTask(false);

        for (PSTimer timer : this.luaTimers.values())
            timer.cancelTask(false);

        this.luaTimers.clear();
        synchronized (this.pendentEvents) {
            this.pendentEvents.clear();
        }

        this.room.broadcastPacket(new PacketSendDestructLuaRoom());
        this.room.sendChangeMap();
    }

    /**
     * Handles when the room gets deleted.
     */
    public synchronized void sendRoomDestruction() {
        this.isDestroyed = true;
        this.luaGeneration++;
        this.isRunningLua = false;
        this.isInitializingLua = false;
        if (this.luaAdmin != null && this.luaAdmin.luaThread != null)
            this.luaAdmin.luaThread.interrupt();

        this.luaAdmin = null;
        PSTimer loopTimer = this.luaLoopTimer;
        this.luaLoopTimer = null;
        if (loopTimer != null)
            loopTimer.cancelTask(false);

        for (PSTimer timer : this.luaTimers.values())
            timer.cancelTask(false);

        this.luaTimers.clear();
        synchronized (this.pendentEvents) {
            this.pendentEvents.clear();
        }

        this.luaExecutor.shutdownNow();
    }

    /**
     * Changes the lua map.
     * @param luaMap The lua's map.
     */
    public void setLuaMap(GameRoom.MapDetails luaMap) {
        this.luaMap = luaMap;
    }

    /**
     * Updates the player's info in the lua room.
     * @param player The player's info.
     */
    public void sendLuaUpdatePlayerInfo(GamePlayer player) {
        this.sendLuaUpdatePlayerInfo(player, true);
    }

    /**
     * Calls the given event.
     * @param eventName The event's name.
     * @param args The event's arguments.
     */
    private synchronized void callEvent(String eventName, Object... args) {
        if (this.isDestroyed)
            return;

        if (!this.isRunningLua) {
            if (this.isInitializingLua) {
                synchronized (this.pendentEvents) {
                    if (this.isInitializingLua && !this.isDestroyed)
                        this.pendentEvents.add(new Object[] {eventName, args});
                }
            }

            return;
        }

        long generation = this.luaGeneration;

        try {
            this.luaExecutor.execute(() -> {
                if (!this.isRunningLua || generation != this.luaGeneration || this.isDestroyed)
                    return;

                LuaValue debug = this.luaDebug;
                Globals globals = this.luaGlobal;
                if (debug == null || globals == null)
                    return;

                long startTime = System.nanoTime();

                try {
                    LuaValue event = globals.get(eventName);
                    if (!event.isfunction())
                        return;

                    LuaValue[] luaArgs = Arrays.stream(args).map(arg -> arg instanceof Integer ? LuaValue.valueOf((Integer) arg) : arg instanceof String ? LuaValue.valueOf((String) arg) : arg instanceof Boolean ? LuaValue.valueOf((Boolean) arg) : arg instanceof Long ? LuaInteger.valueOf((Long) arg) : (LuaValue) arg).toArray(LuaValue[]::new);
                    LuaValue hook = new VarArgFunction() {
                        private boolean warned = false;

                        @Override
                        public Varargs invoke(Varargs hookArgs) {
                            if (generation != luaGeneration || isDestroyed)
                                throw new LuaError("Lua script stopped.");

                            long elapsedTime = (System.nanoTime() - startTime) / 1_000_000L;
                            if (elapsedTime > 4000)
                                throw new LuaError("Lua destroyed : Runtime too long!");

                            if (elapsedTime > 60 && !warned) {
                                warned = true;
                                sendLuaMessage("<V>[" + room.getRoomName() + "]</V> Warning: " + "Runtime can't exceed 60 ms in 4 seconds! Currently: " + elapsedTime + " ms.");
                            }

                            return LuaValue.NONE;
                        }
                    };

                    debug.get("sethook").invoke(LuaValue.varargsOf(new LuaValue[] {hook, LuaValue.valueOf(""), LuaValue.valueOf(1000)}));
                    event.invoke(LuaValue.varargsOf(luaArgs));
                    if (generation != this.luaGeneration || this.isDestroyed)
                        return;

                    long endTime = (System.nanoTime() - startTime) / 1_000_000L;
                    if (!this.isDisabledEventLog)
                        this.sendLuaMessage("Event <V>" + eventName + "</V> : " + endTime + " ms");

                } catch (Exception error) {
                    if (generation == this.luaGeneration && !this.isDestroyed) {
                        this.sendLuaMessage("Runtime Error : " + error.getMessage());
                        this.sendRoomStopScript();
                    }
                } finally {
                    debug.get("sethook").call();
                }
            });
        } catch (RejectedExecutionException ignored) {}
    }

    /**
     * Updates the player's in the lua table.
     * @param player The player's instance.
     */
    private void sendLuaUpdatePlayerInfo(GamePlayer player, boolean toAdd) {
        try {
            if (this.luaGlobal == null)
                return;

            LuaValue playerList = this.luaGlobal.get("tfm").get("get").get("room").get("playerList");
            if (!playerList.istable())
                return;

            String playerName = player.getPlayerName();
            if (!toAdd) {
                playerList.set(playerName, LuaValue.NIL);
                return;
            }

            LuaTable table = new LuaTable();
            table.set("isJumping", LuaValue.valueOf(player.isJumping));
            table.set("title", LuaValue.valueOf(player.getPlayerData().currentTitle.titleId));
            table.set("y", LuaValue.valueOf(player.positionY));
            table.set("x", LuaValue.valueOf(player.positionX));
            table.set("id", LuaValue.valueOf(player.getSessionId()));
            table.set("isDead", LuaValue.valueOf(player.isDead));
            table.set("look", LuaValue.valueOf(player.getPlayerData().playerLook));
            table.set("isShaman", LuaValue.valueOf(player.isShaman));
            table.set("vx", LuaValue.valueOf(player.velocityX));
            table.set("score", LuaValue.valueOf(player.playerScore));
            table.set("inHardMode", LuaValue.valueOf(player.getPlayerData().getShamanType() == ShamanType.HARD));
            table.set("vy", LuaValue.valueOf(player.velocityY));
            table.set("movingRight", LuaValue.valueOf(player.isFacingRight));
            table.set("hasCheese", LuaValue.valueOf(player.cheeseCount > 0));
            table.set("registrationDate", LuaValue.valueOf(player.getPlayerData().getRegistrationDate()));
            table.set("shamanMode", LuaValue.valueOf(player.getPlayerData().getShamanType().ordinal()));
            table.set("playerName", LuaValue.valueOf(playerName));
            table.set("community", LuaValue.valueOf(player.getPlayerCommunity()));
            table.set("tribeName", LuaValue.valueOf(player.getPlayerData().tribeName));
            table.set("movingLeft", LuaValue.valueOf(player.isFacingLeft));
            table.set("isFacingRight", LuaValue.valueOf(!player.isFacingLeft && player.isFacingRight));
            table.set("isVampire", LuaValue.valueOf(player.isVampire));
            playerList.set(playerName, table);
        } catch (LuaError ignored) {}
    }
}
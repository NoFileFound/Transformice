package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.modules.impl.ModuleRoom;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.lua.PacketSendInitializeLuaRoom;
import org.transformice.packets.send.lua.PacketSendLuaDisableRoomProperties;
import org.transformice.packets.send.player.PacketSendPlayerMove;

public final class ModuleLua extends ModuleRoom {
    private final Map<Integer, PSTimer> luaTimers;
    private final List<String> disableChatCommandListDisplay;
    private final PSTimer luaLoopTimer;
    public boolean isDisabledWatchCommand;
    public boolean isDisabledDebugCommand;
    public boolean isDisabledMinimalistMode;
    public boolean disablePhysicalConsumables;
    private boolean isRunningLua;
    private GamePlayer luaAdmin;

    /**
     * Creates a lua module.
     *
     * @param server The game server.
     * @param room The game room.
     */
    public ModuleLua(GameServer server, GameRoom room) {
        super(server, room);
        this.isRunningLua = false;
        this.isDisabledWatchCommand = false;
        this.isDisabledDebugCommand = false;
        this.isDisabledMinimalistMode = false;
        this.luaTimers = new HashMap<>();
        this.disableChatCommandListDisplay = new ArrayList<>();
        this.luaLoopTimer = new PSTimer(this.server.getScheduler());
        this.luaAdmin = null;
    }

    /**
     * Checks if a lua is running in this room.
     * @return True if a lua is running or else False.
     */
    public boolean getIsRunningLua() {
        return this.isRunningLua;
    }

    /**
     * Gets the lua's map.
     * @return The lua's map.
     */
    public GameRoom.MapDetails getLuaMap() {
        return null;
    }

    /**
     * Gets the lua's loop timer.
     * @return The lua's loop timer.
     */
    public PSTimer getLuaLoopTimer() {
        return this.luaLoopTimer;
    }


    /**
     * Gets the lua's timers.
     * @return The lua's timers.
     */
    public Map<Integer, PSTimer> getLuaTimers() {
        return this.luaTimers;
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
    public void executeScript(String luaScript, GamePlayer player) {
        this.isRunningLua = true;
        this.luaAdmin = player;
    }

    /**
     * Handles when the player contacts a listener.
     * @param player The player's name.
     * @param groundId The ground's id.
     * @param i1 - Arg 1.
     * @param i2 - Arg 2.
     * @param i3 - Arg 3.
     * @param i4 - Arg 4.
     * @param i5 - Arg 5.
     * @param i6 - Arg 6.
     */
    public void handleEventContactListener(GamePlayer player, int groundId, int i1, int i2, int i3, int i4, int i5, int i6) {
        this.callEvent("eventContactListener", player.getPlayerName(), groundId, i1, i2, i3, i4, i5, i6);
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
        this.callEvent("eventPlayerDied", player.getPlayerName());
    }

    /**
     * Handles when the player makes an emote.
     * @param player The player's instance.
     * @param emoteId The emote's id.
     * @param playerSessionId The opponent's session id.
     */
    public void handleEventPlayerEmote(GamePlayer player, int emoteId, int playerSessionId) {
        this.callEvent("eventEmotePlayed", player.getPlayerName(), emoteId, playerSessionId);
    }

    /**
     * Handles when the player gets a cheese.
     * @param player The player's instance.
     */
    public void handleEventPlayerGetCheese(GamePlayer player) {
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
    public void handleEventPlayerEndInvocation(GamePlayer player, int shamanObjectId, int posX, int posY, int angle, int velX, int velY, boolean hasContactListener, boolean miceCollidable) {
        this.callEvent("eventSummoningEnd", player.getPlayerName(), shamanObjectId, posX, posY, angle, velX, velY, hasContactListener, miceCollidable);
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
     * @param timeTaken The time's taken.
     */
    public void handleEventPlayerWin(GamePlayer player, int timeTaken) {
        this.callEvent("eventPlayerWon", player.getPlayerName(), timeTaken);
    }

    /**
     * Handles when the player send a chat command.
     * @param player The player's name.
     * @param message The chat's message.
     */
    public void handleChatCommand(GamePlayer player, String message) {
        this.callEvent("eventChatCommand", player.getPlayerName(), message);
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
     * Calls the given event.
     * @param event The event name.
     * @param args The event arguments.
     */
    private void callEvent(String event, Object... args) {

    }

    /**
     * Updates the player's in the lua table.
     * @param player The player's instance.
     */
    private void sendLuaUpdatePlayerInfo(GamePlayer player, boolean toAdd) {

    }
}
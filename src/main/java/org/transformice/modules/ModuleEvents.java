package org.transformice.modules;

// Imports
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.psframework.utils.PSRandom;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.config.tfmconfig.EventConfig;
import org.transformice.data.objects.MonsterInfo;
import org.transformice.data.objects.RoomType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.modules.impl.ModuleServer;

// Packets
import org.transformice.packets.send.login.PacketSendSetCheeseSpriteSuffix;
import org.transformice.packets.send.shop.PacketSendShopNewsInfo;

public final class ModuleEvents extends ModuleServer {
    private final EventConfig eventConfig;
    private final Map<GameRoom, RoomEventState> roomEvents;
    private final ArrayList<Integer> halloweenMaps = new ArrayList<>(List.of(669, 816, 926, 3001, 3002, 3003, 3004, 3005, 3060, 3061));
    private final ArrayList<Integer> huggingMaps = new ArrayList<>(List.of(2002, 2801));
    private final ArrayList<Integer> easterMaps = new ArrayList<>(List.of());
    private final ArrayList<Integer> backToSchoolMaps = new ArrayList<>(List.of(3012, 3013, 3014));
    private final ArrayList<Integer> christmasMaps = new ArrayList<>(List.of(3050, 3052, 3053));

    /**
     * Creates a new events's module.
     *
     * @param server The game's server.
     */
    public ModuleEvents(GameServer server) {
        super(server);
        this.eventConfig = server.getConfig().getEventConfig();
        this.roomEvents = new HashMap<>();
    }

    /**
     * Gets the event's map.
     * @param room The room's instance.
     * @return The event's map.
     */
    public GameRoom.MapDetails getEventMap(GameRoom room) {
        if (room == null || room.getIsDisableAdventureMaps() || room.getIsMulodrome() || room.getParseLua().getIsRunningLua() || room.getRoomType() == RoomType.VILLAGE || room.getRoomType() == RoomType.TRIBE_HOUSE)
            return null;

        RoomEventState state = this.roomEvents.get(room);
        if (state == null)
            return null;

        if (state.running)
            return state.eventMap;

        if (!state.forceNextRound && !state.isEligible)
            return null;

        GameRoom.MapDetails eventMap = this.selectEventMap();
        if (eventMap == null)
            return null;

        state.eligibilityGen++;
        state.isEligibleWaiting = false;
        state.isEligible = false;
        state.forceNextRound = false;
        state.running = true;
        state.eventMap = eventMap;
        return eventMap;
    }

    /**
     * Forces an event to start on the room.
     * @param room The room's instance.
     * @return True if the event is going to start next round or else False.
     */
    public boolean forceStartEventNextRound(GameRoom room) {
        if (room == null || room.getIsDisableAdventureMaps())
            return false;

        RoomEventState state = this.roomEvents.computeIfAbsent(room, _ -> new RoomEventState(this.server));
        state.forceNextRound = true;
        return true;
    }

    /**
     * Sends the event initialization packets on the current player.
     * @param player The given player.
     */
    public void sendEventPlayerInitialization(GamePlayer player) {
        if (!this.server.getConfig().getEventConfig().getShopNewsFileId().isEmpty())
            player.broadcastPacket(new PacketSendShopNewsInfo(this.server.getConfig().getEventConfig().getShopNewsFileId()));

        if (!this.server.getConfig().getEventConfig().getCheeseSpriteSuffix().isEmpty())
            player.broadcastPacket(new PacketSendSetCheeseSpriteSuffix(this.server.getConfig().getEventConfig().getCheeseSpriteSuffix()));

        if (!player.getPlayerData().getPlayerAdventures().containsKey(this.eventConfig.getEventId()))
            player.getPlayerData().getPlayerAdventures().put(this.eventConfig.getEventId(), new PlayerEntity.Adventure());
    }

    /**
     * Handles when the player collects an Easter egg in Easter event.
     * @param room The player's room.
     * @param sessionId The player's session id.
     */
    public void handleEventCollectEasterEgg(GameRoom room, int sessionId) {

    }

    /**
     * Handles when the player enters in the adventure area.
     * @param room The player's room.
     * @param sessionId The player's session id.
     * @param adventureId The adventure's id.
     * @param adventureAreaId The adventure's area id.
     */
    public void handleEventEnterAdventureArea(GameRoom room, int sessionId, int adventureId, int adventureAreaId) {

    }

    /**
     * Handles when the monster took damage from a player in the Halloween event.
     * @param room The player's room.
     * @param monsterId The monster's id.
     * @param isFacingRight Is the monster facing right.
     */
    public void handleEventMonsterTookDamage(GameRoom room, int monsterId, boolean isFacingRight) {

    }

    /**
     * Handles the monster synchronization in the Halloween event.
     * @param room The player's room.
     * @param monsterInfoList The monster's list.
     */
    public void handleEventMonsterSynchronization(GameRoom room, List<MonsterInfo> monsterInfoList) {

    }

    /**
     * Handles when the player attacks a monster in the Halloween event.
     * @param room The player's room.
     * @param sessionId The player's session id.
     */
    public void handleEventPlayerAttack(GameRoom room, int sessionId) {

    }

    /**
     * Handles when the player get a collectible.
     * @param player The player's instance.
     * @param adventureId The adventure's id.
     * @param collectibleId The collectible's id.
     * @param posX The collectible's X position.
     * @param posY The collectible's Y position.
     */
    public void handleEventPlayerGetCollectible(GamePlayer player, int adventureId, int collectibleId, int posX, int posY) {

    }

    /**
     * Handles when the player makes an emote.
     * @param room The player's room.
     * @param player The player's instance.
     * @param emoteId The emote type.
     * @param playerSessionId The opponent's session id.
     */
    public void handleEventPlayerEmote(GameRoom room, GamePlayer player, int emoteId, int playerSessionId) {

    }

    /**
     * Handles when the player does something in the event map.
     * @param room The player's room.
     * @param adventureId The event's adventure id.
     * @param actionType The player's action type.
     * @param actions The player's actions.
     */
    public void handleEventPlayerPerformAction(GameRoom room, int adventureId, int actionType, List<String> actions) {

    }

    /**
     * Handles when the player took damage from the monster in the Halloween event.
     * @param room The player's room.
     * @param sessionId The player's session id.
     */
    public void handleEventPlayerTookDamage(GameRoom room, int sessionId) {

    }

    /**
     * Handles when the player's count in the room changes.
     * @param room The room's instance.
     */
    public void handleEventRoomPlayerCountChange(GameRoom room) {
        RoomEventState state = this.roomEvents.computeIfAbsent(room, _ -> new RoomEventState(this.server));
        if (room.getIsDisableAdventureMaps() || room.getPlayers().size() < this.server.getConfig().getGameConfig().getMinimumPlayersToCountStats() || room.getIsMulodrome() || room.getParseLua().getIsRunningLua() || room.getRoomType() == RoomType.VILLAGE || room.getRoomType() == RoomType.TRIBE_HOUSE) {
            state.eligibilityGen++;
            state.isEligibleWaiting = false;
            state.isEligible = false;
            return;
        }

        if (state.running || state.isEligible || state.isEligibleWaiting)
            return;

        state.isEligibleWaiting = true;
        int gen = ++state.eligibilityGen;
        state.eligibilityTimer.schedule(() -> {
            if (gen != state.eligibilityGen)
                return;

            state.isEligibleWaiting = false;
            if (room.getPlayers().size() < this.server.getConfig().getGameConfig().getMinimumPlayersToCountStats())
                return;

            state.isEligible = true;
        }, this.server.getConfig().getEventConfig().getMinimumMinutesToShowEvent(), TimeUnit.MINUTES);
    }

    /**
     * Handles when the room is deleted.
     * @param room The room's instance.
     */
    public void handleEventRoomDestruction(GameRoom room) {
        RoomEventState state = this.roomEvents.remove(room);
        if (state == null)
            return;

        state.eligibilityGen++;
    }

    /**
     * Handles when the event finished in the room.
     * @param room The room's instance.
     */
    public void handleEventRoomEnded(GameRoom room) {
        RoomEventState state = this.roomEvents.get(room);
        if (state == null || !state.running)
            return;

        state.running = false;
        state.eventMap = null;
        state.isEligible = false;
        state.eligibilityGen++;
        state.isEligibleWaiting = false;
        this.handleEventRoomPlayerCountChange(room);
    }

    /**
     * Handles when the post initialization when the event has started.
     * @param room The room's isntance.
     */
    public void handleEventRoomPostInitialization(GameRoom room) {

    }

    /**
     * Selects a map for the event.
     * @return The map for the event.
     */
    private GameRoom.MapDetails selectEventMap() {
        int mapCode = -1;
        String eventName = "";
        switch (this.eventConfig.getEventName()) {
            case EVENT_HALLOWEEN -> {
                mapCode = this.halloweenMaps.get(PSRandom.nextInt(this.halloweenMaps.size()));
                eventName = "Halloween " + Year.now();
            }
            case EVENT_CHRISTMAS -> {
                mapCode = this.christmasMaps.get(PSRandom.nextInt(this.christmasMaps.size()));
                eventName = "Christmas " + Year.now();
            }
            case EVENT_EASTER -> {
                mapCode = this.easterMaps.get(PSRandom.nextInt(this.easterMaps.size()));
                eventName = "Easter " + Year.now();
            }
            case EVENT_HUGGING -> {
                mapCode = this.huggingMaps.get(PSRandom.nextInt(this.huggingMaps.size()));
                eventName = "Hugging " + Year.now();
            }
            case EVENT_SCHOOL -> {
                mapCode = this.backToSchoolMaps.get(PSRandom.nextInt(this.backToSchoolMaps.size()));
                eventName = "Back To School " + Year.now();
            }
        }

        if (mapCode == -1) {
            this.server.getLogger().warn(String.format("[EventHandler] Unable to find a map for this event %s. Skipping...", this.eventConfig.getEventName()));
            return null;
        }

        return new GameRoom.MapDetails(mapCode, "_" + eventName, this.server.getSpecialMapXml(mapCode), false);
    }

    /**
     * The room event state class.
     */
    private static final class RoomEventState {
        private final PSTimer eligibilityTimer;
        private GameRoom.MapDetails eventMap;
        private boolean isEligibleWaiting;
        private boolean isEligible;
        private boolean forceNextRound;
        private boolean running;
        private int eligibilityGen;

        /**
         * Creates a new room event state.
         * @param server The server's instance.
         */
        private RoomEventState(GameServer server) {
            this.eligibilityTimer = new PSTimer(server.getScheduler());
        }
    }
}
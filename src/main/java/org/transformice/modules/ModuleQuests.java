package org.transformice.modules;

// Imports
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.MissionType;
import org.transformice.modules.impl.ModulePlayer;

// Packets
import org.transformice.packets.send.player.PacketSendMissionMark;

public final class ModuleQuests extends ModulePlayer {
    /**
     * Creates a daily quests module.
     *
     * @param server The game server.
     * @param player The game player.
     */
    public ModuleQuests(GameServer server, GamePlayer player) {
        super(server, player);
    }

    /**
     * Handles when the player want to change the mission.
     * @param positionId The mission's id.
     */
    public void sendChangeQuest(int positionId) {
        ///  TODO: IMPLEMENT
    }


    /**
     * Sends the daily missions on the player.
     */
    public void sendDailyMissions() {
        if (this.player.getIsGuest())
            return;

        this.player.broadcastPacket(new PacketSendMissionMark(!this.player.getPlayerData().getPlayerMissions().isEmpty()));
    }

    /**
     * Sends the player's missions.
     */
    public void sendMissionsList() {
        ///  TODO: IMPLEMENT
    }

    /**
     * Increases the player's mission.
     * @param missionType The missions' type.
     * @param amount The increasing amount.
     */
    public void increaseMission(MissionType missionType, int amount) {
        ///  TODO: IMPLEMENT
    }
}
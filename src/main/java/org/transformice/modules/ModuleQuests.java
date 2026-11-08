package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.EventType;
import org.transformice.data.objects.MissionType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.modules.impl.ModulePlayer;

// Packets
import org.transformice.packets.send.player.PacketSendMissionIncrease;
import org.transformice.packets.send.player.PacketSendMissionMark;
import org.transformice.packets.send.player.PacketSendPlayerMissionList;

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
     * @param missionType The mission's id.
     */
    public void sendChangeQuest(MissionType missionType) {
        if (this.server.getDailyQuestChangeTimer(this.player.getPlayerName()).getRemainingTime() > 0 || missionType == MissionType.FINISH_20_DAILY_QUESTS)
            return;

        List<PlayerEntity.Mission> missions = this.player.getPlayerData().getPlayerMissions();
        if (!missions.removeIf(mission -> mission.getMissionType() == missionType))
            return;

        this.server.getDailyQuestChangeTimer(this.player.getPlayerName()).schedule();
        this.generateMissions(1);
        this.sendMissionsList();
    }

    /**
     * Sends the daily missions on the player.
     */
    public void sendDailyMissions() {
        if (this.player.getIsGuest())
            return;

        if (this.server.getDailyQuestGenerateTimer(this.player.getPlayerName()).getRemainingTime() <= 0) {
            this.generateMissions(4 - this.player.getPlayerData().getPlayerMissions().size());
            this.server.getDailyQuestGenerateTimer(this.player.getPlayerName()).schedule();
        }

        this.player.broadcastPacket(new PacketSendMissionMark(!this.player.getPlayerData().getPlayerMissions().isEmpty()));
    }

    /**
     * Sends the player's missions.
     */
    public void sendMissionsList() {
        this.player.broadcastPacket(new PacketSendPlayerMissionList(player.getPlayerData().getPlayerMissions(), this.server.getDailyQuestChangeTimer(this.player.getPlayerName()).getRemainingTime() <= 0));
    }

    /**
     * Increases the player's mission.
     * @param missionType The missions' type.
     * @param amount The increasing amount.
     */
    public void increaseMission(MissionType missionType, int amount) {
        PlayerEntity.Mission myQuest = null;
        for (PlayerEntity.Mission mission : this.player.getPlayerData().getPlayerMissions()) {
            if (mission.getMissionType() == missionType) {
                myQuest = mission;
                break;
            }
        }

        if(myQuest != null) {
            myQuest.missionProgress += amount;
            this.player.broadcastPacket(new PacketSendMissionIncrease(myQuest));
            if (myQuest.missionProgress >= myQuest.getMissionRequirement()) {
                boolean isStrawberries = (missionType == MissionType.FINISH_20_DAILY_QUESTS);
                if (isStrawberries) {
                    this.player.getParseInventory().addConsumable("800", myQuest.getMissionAward(), false);
                } else {
                    this.player.getParseInventory().addConsumable("801", myQuest.getMissionAward(), false);
                }

                this.player.getPlayerData().getPlayerMissions().remove(myQuest);
            }
        }
    }

    /**
     * Generates the player's missions.
     * @param missionCount The mission count to generate.
     */
    private void generateMissions(int missionCount) {
        List<PlayerEntity.Mission> missions = this.player.getPlayerData().getPlayerMissions();
        List<MissionType> missionList = new ArrayList<>(Arrays.asList(MissionType.BRING_BACK_CHEESE, MissionType.SAVE_MICE, MissionType.KILL_MICE_IN_SURVIVOR, MissionType.FINISH_MAPS_IN_RACING, MissionType.FINISH_MAPS_IN_DEFILANTE, MissionType.BUY_SHOP_ITEM, MissionType.BRING_BACK_CHEESE_AS_SHAMAN));
        EventType eventName = this.server.getConfig().getEventConfig().getEventName();
        if (eventName == EventType.EVENT_HALLOWEEN) {
            missionList.addAll(Arrays.asList(MissionType.HALLOWEEN_COLLECT_CANDIES, MissionType.HALLOWEEN_GAMBLE, MissionType.HALLOWEEN_COLLECT_CAT_SKULLS, MissionType.HALLOWEEN_COLLECT_BROOMS, MissionType.HALLOWEEN_COLLECT_DOOR_HANDLES));
        } else if (eventName == EventType.EVENT_CHRISTMAS) {
            missionList.addAll(Arrays.asList(MissionType.CHRISTMAS_COLLECT_PRESENTS, MissionType.CHRISTMAS_TRADE_PRESENTS, MissionType.CHRISTMAS_COLLECT_SOULMATE_COINS, MissionType.CHRISTMAS_TRAVEL_METERS_ON_SNOWBOARD, MissionType.CHRISTMAS_PICK_UP_SNOWBALLS));
        }

        missions.removeIf(mission -> {
            MissionType type = mission.getMissionType();
            boolean isHalloweenMission = type.name().startsWith("HALLOWEEN_");
            boolean isChristmasMission = type.name().startsWith("CHRISTMAS_");
            return (isHalloweenMission && eventName != EventType.EVENT_HALLOWEEN) || (isChristmasMission && eventName != EventType.EVENT_CHRISTMAS);
        });

        missions.sort(Comparator.comparingInt(PlayerEntity.Mission::getMissionId));
        int generatedCount = 0;
        for (int id = 1; id <= 3 && generatedCount < missionCount; id++) {
            final int missionId = id;
            if (missions.stream().anyMatch(mission -> mission.getMissionId() == missionId))
                continue;

            List<MissionType> arr = new ArrayList<>(missionList);
            arr.removeIf(type -> missions.stream().anyMatch(mission -> mission.getMissionType() == type));
            if (arr.isEmpty())
                break;

            MissionType type = arr.get(PSRandom.nextInt(arr.size()));
            int requirement = PSRandom.nextInt(20, 50);
            int award = PSRandom.nextInt(20, 90);
            missions.add(new PlayerEntity.Mission(id, type, 0, requirement, award));
            generatedCount++;
        }

        if (missionCount >= 4 && missions.stream().noneMatch(mission -> mission.getMissionType() == MissionType.FINISH_20_DAILY_QUESTS))
            missions.add(new PlayerEntity.Mission(4, MissionType.FINISH_20_DAILY_QUESTS, 0, 20, 20));

        missions.sort(Comparator.comparingInt(PlayerEntity.Mission::getMissionId));
    }
}
package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.transformice.Server;
import org.transformice.database.embeds.Quest;
import org.transformice.libraries.SrcRandom;
import org.transformice.Application;
import org.transformice.Client;

// Packets
import org.transformice.packets.send.newpackets.C_DailyQuestsMark;
import org.transformice.packets.send.newpackets.C_PlayerCompleteMission;
import org.transformice.packets.send.newpackets.C_PlayerSendMissionList;

public final class ParseDailyQuests {
    private final Client client;
    private final Server server;

    /**
     * Creates the daily quests system in the game on given player.
     * @param client The given player.
     */
    public ParseDailyQuests(final Client client) {
        this.client = client;
        this.server = client.getServer();
    }

    /**
     * Changes the specific mission.
     */
    public void sendChangeMission(int missionId, boolean isOpen) {
        if (missionId == 60801 || this.server.canChangeDailyQuestTimer.get(this.client.getPlayerName()).getRemainingTime() >= 0) {
            return;
        }

        if(this.server.canChangeDailyQuestTimer.get(this.client.getPlayerName()).getRemainingTime() <= 0) {
            this.server.canChangeDailyQuestTimer.get(this.client.getPlayerName()).schedule(() -> {}, TimeUnit.HOURS);
        }

        List<Quest> missions = this.client.getAccount().getPlayerMissions();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == missionId) {
                missions.set(i, this.generateMission());
                if (isOpen) {
                    this.sendMissionList();
                }
                break;
            }
        }
    }

    /**
     * Increases the progress of the given mission.
     * @param missionId The mission id.
     */
    public void sendMissionIncrease(int missionId, int amount) {
        Quest myQuest = null;
        for(Quest mission : this.client.getAccount().getPlayerMissions()) {
            if(mission.getId() == missionId) {
                myQuest = mission;
                break;
            }
        }

        if(myQuest != null) {
            myQuest.setMissionCollected(myQuest.getMissionCollected() + amount);
            this.client.sendPacket(new C_PlayerCompleteMission(myQuest));
            if(myQuest.getMissionCollected() >= myQuest.getMissionTotal()) {
                if(missionId == 60801) {
                    this.client.getAccount().setShopStrawberries(this.client.getAccount().getShopStrawberries() + myQuest.getMissionPrize());
                    this.client.getAccount().getPlayerMissions().add(new Quest(60801, 4, 0, 20, 20));
                } else {
                    this.client.getAccount().setShopCheeses(this.client.getAccount().getShopCheeses() + myQuest.getMissionPrize());
                    this.sendMissionIncrease(60801, 1); // strawberry mission.
                    this.sendChangeMission((int)myQuest.getId(), false);
                }
            }
        }
    }

    /**
     * Sends the mission list.
     */
    public void sendMissionList() {
        if(this.client.getAccount().getPlayerMissions().isEmpty()) {
            for(int i = 0; i < 3; i++) {
                this.client.getAccount().getPlayerMissions().add(this.generateMission());
            }
            this.client.getAccount().getPlayerMissions().add(new Quest(60801, 4, 0, 20, 20));
        } else if(this.client.getAccount().getPlayerMissions().size() < 4) {
            if(this.server.changeDailyQuestTimer.get(this.client.getPlayerName()).getRemainingTime() <= 0) {
                for(int i = this.client.getAccount().getPlayerMissions().size(); i < 3; i++) {
                    this.client.getAccount().getPlayerMissions().add(this.generateMission());
                }
                this.server.changeDailyQuestTimer.get(this.client.getPlayerName()).schedule(() -> {}, TimeUnit.HOURS);
            }
        }

        this.client.sendPacket(new C_PlayerSendMissionList(this.client.getAccount().getPlayerMissions(), this.server.canChangeDailyQuestTimer.get(this.client.getPlayerName()).getRemainingTime() <= 0));
    }

    /**
     * Sends the daily quest mark.
     */
    public void sendMissionMark() {
        this.client.sendPacket(new C_DailyQuestsMark(true)); /// !this.client.getAccount().getPlayerMissions().isEmpty()
    }

    /**
     * Remove certain missions when there are no currently events in the game.
     * @return The list of removed missions.
     */
    private List<Integer> getExceptionalMissions() {
        List<Integer> integerList = new ArrayList<>();

        integerList.add(8);
        integerList.add(9);
        integerList.add(21);
        integerList.add(22);

        if(!Application.getPropertiesInfo().event.event_name.equals("Halloween")) {
            integerList.add(10);
            integerList.add(11);
            integerList.add(12);
            integerList.add(13);
            integerList.add(14);
        }

        if(!Application.getPropertiesInfo().event.event_name.equals("Christmas")) {
            integerList.add(15);
            integerList.add(16);
            integerList.add(17);
            integerList.add(18);
            integerList.add(19);
            integerList.add(20);
        }

        if(!Application.getPropertiesInfo().event.event_name.equals("Valentine")) {
            integerList.add(23);
        }

        return integerList;
    }

    /**
     * Creates a new mission.
     * @return A new mission instance.
     */
    private Quest generateMission() {
        String eventName = Application.getPropertiesInfo().event.event_name;
        do {
            int missionId = SrcRandom.RandomNumber(1, (eventName.isEmpty()) ? 7 : 23, this.getExceptionalMissions());
            if (this.client.getAccount().getPlayerMissions().stream().noneMatch(m -> m.getId() == missionId)) {
                int amount = SrcRandom.RandomNumber(20, 80);
                if(missionId == 6) amount = 1;
                return new Quest(missionId, this.client.getAccount().getPlayerMissions().size(), 0, amount, (amount / 2) + 5);
            }
        }while(true);
    }
}
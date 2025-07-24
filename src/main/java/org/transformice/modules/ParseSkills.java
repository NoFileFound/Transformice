package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.transformice.Client;
import org.transformice.libraries.Timer;

// Packets
import org.transformice.packets.send.informations.C_ShamanType;
import org.transformice.packets.send.level.C_RedistributionDelay;
import org.transformice.packets.send.level.C_RedistributionNotEnoughCheese;
import org.transformice.packets.send.level.C_ShamanEarnedExp;
import org.transformice.packets.send.level.C_ShamanEarnedLevel;
import org.transformice.packets.send.newpackets.C_PlayerGetCheese;
import org.transformice.packets.send.player.C_EnableMeep;
import org.transformice.packets.send.player.C_MovePlayer;
import org.transformice.packets.send.player.C_PlayerAction;
import org.transformice.packets.send.player.C_ShamanEnableSkill;
import org.transformice.packets.send.player.C_ShamanExperience;
import org.transformice.packets.send.player.C_ShamanGainExperience;
import org.transformice.packets.send.player.C_ShamanSkills;
import org.transformice.packets.send.player.sync.C_RemoveObject;
import org.transformice.packets.send.room.C_AddBonus;
import org.transformice.packets.send.room.C_BonfireSkill;
import org.transformice.packets.send.room.C_EvolutionSkill;
import org.transformice.packets.send.room.C_GrapnelSkill;
import org.transformice.packets.send.room.C_GravitationalSkill;
import org.transformice.packets.send.room.C_IcedMouseSkill;
import org.transformice.packets.send.room.C_LeafSkill;
import org.transformice.packets.send.room.C_PlayerChangeSize;
import org.transformice.packets.send.room.C_RemoveAllObjectsSkill;
import org.transformice.packets.send.room.C_ResetShamanSkills;
import org.transformice.packets.send.room.C_RolloutSkill;
import org.transformice.packets.send.room.C_ShameowSkill;
import org.transformice.packets.send.room.C_Skill;
import org.transformice.packets.send.room.C_SpiderMouseSkill;
import org.transformice.packets.send.room.C_TeleportEffect;
import org.transformice.packets.send.transformation.C_EnableTransformation;

public final class ParseSkills {
    private final Client client;

    /**
     * Creates a new instance of skills to the current player.
     * @param client The given player.
     */
    public ParseSkills(Client client) {
        this.client = client;
    }

    /**
     * Gets the default shaman badge depending on the skill tree.
     * @return The default shaman badge.
     */
    public int getShamanBadge() {
        if (this.client.getAccount().getEquippedShamanBadge() != 0) {
            return this.client.getAccount().getEquippedShamanBadge();
        }

        Integer[] badgesCount = new Integer[] {0, 0, 0, 0, 0};
        for (var skill : this.client.getAccount().getPlayerSkills().entrySet()) {
            if (skill.getKey() > -1 && skill.getKey() < 14) {
                badgesCount[0] += skill.getValue();
            } else if (skill.getKey() > 19 && skill.getKey() < 35) {
                badgesCount[1] += skill.getValue();
            } else if (skill.getKey() > 39 && skill.getKey() < 55) {
                badgesCount[2] += skill.getValue();
            } else if (skill.getKey() > 59 && skill.getKey() < 75) {
                badgesCount[4] += skill.getValue();
            } else if (skill.getKey() > 79 && skill.getKey() < 95) {
                badgesCount[3] += skill.getValue();
            }
        }

        List<Integer> maxList = new ArrayList<>(Arrays.asList(badgesCount));
        return -(maxList.indexOf(Collections.max(maxList)));
    }

    /**
     * Gets XP after winning the round.
     * @param isShaman Is the current player a shaman.
     * @param exp Total xp to gain.
     */
    public void earnExp(boolean isShaman, int exp) {
        /// TODO: Find the actual formula for calculate the xp.
        int gainExp = exp * (isShaman ? this.client.getAccount().getShamanType() == 0 ? this.client.getAccount().getShamanLevel() < 30 ? 3 : this.client.getAccount().getShamanLevel() < 60 ? 5 : 10 : this.client.getAccount().getShamanLevel() < 30 ? 5 : this.client.getAccount().getShamanLevel() < 60 ? 10 : 20 : 1);
        this.client.getAccount().setShamanLevelXp(this.client.getAccount().getShamanLevelXp() + gainExp);
        if (this.client.getAccount().getShamanLevelXp() > this.client.getAccount().getShamanLevelNextXp()) {
            this.client.getAccount().setShamanLevel(this.client.getAccount().getShamanLevel() + 1);
            this.client.getAccount().setShamanLevelXp(this.client.getAccount().getShamanLevelXp() - this.client.getAccount().getShamanLevelNextXp());
            if (this.client.getAccount().getShamanLevelXp() < 0) {
                this.client.getAccount().setShamanLevelXp(0);
            }

            this.client.getAccount().setShamanLevelNextXp(this.client.getAccount().getShamanLevel() < 30 ? (32 + (this.client.getAccount().getShamanLevel() - 1) * (this.client.getAccount().getShamanLevel() + 2)) : this.client.getAccount().getShamanLevel() < 60 ? (900 + 5 * (this.client.getAccount().getShamanLevel() - 29) * (this.client.getAccount().getShamanLevel() + 30)) : (14250 + (15 * (this.client.getAccount().getShamanLevel() - 59) * (this.client.getAccount().getShamanLevel() + 60) / 2)));
            this.client.sendPacket(new C_ShamanExperience(this.client.getAccount().getShamanLevel(), 0, this.client.getAccount().getShamanLevelNextXp()));
            this.sendShamanGainExperience(this.client.getAccount().getShamanLevelXp());
            if (isShaman) {
                this.client.sendPacket(new C_ShamanEarnedExp(gainExp, exp));
            }

            if (this.client.getAccount().getShamanLevel() >= 20) {
                this.client.getRoom().sendAll(new C_ShamanEarnedLevel(this.client.getPlayerName(), this.client.getAccount().getShamanLevel()));
            }
        } else {
            this.client.sendPacket(new C_ShamanExperience(this.client.getAccount().getShamanLevel(), this.client.getAccount().getShamanLevelXp(), this.client.getAccount().getShamanLevelNextXp()));
            this.sendShamanGainExperience(this.client.getAccount().getShamanLevelXp());
            if (isShaman) {
                this.client.sendPacket(new C_ShamanEarnedExp(gainExp, exp));
            }
        }
    }

    /**
     * Redistributes the skills.
     */
    public void sendRedistributeSkills() {
        if (this.client.getAccount().getShopCheeses() >= this.client.getAccount().getShamanLevel()) {
            if (!this.client.getAccount().getPlayerSkills().isEmpty()) {
                if(this.client.canRedistributeSkills) {
                    this.client.getAccount().setShopCheeses(this.client.getAccount().getShopCheeses() - this.client.getAccount().getShamanLevel());
                    this.client.getAccount().getPlayerSkills().clear();
                    this.sendShamanSkills(true);
                    this.client.canRedistributeSkills = false;
                    this.client.redistributeTimer.cancel();
                    this.client.redistributeTimer.schedule(() -> this.client.canRedistributeSkills = true, TimeUnit.MINUTES);
                    this.client.getAccount().setTotemInfo(new Object[]{0, ""});
                } else {
                    this.client.sendPacket(new C_RedistributionDelay());
                }
            }
        } else {
            this.client.sendPacket(new C_RedistributionNotEnoughCheese());
        }
    }

    /**
     * Sends the shaman level and shaman xp.
     */
    public void sendShamanExperience() {
        this.client.sendPacket(new C_ShamanExperience(this.client.getAccount().getShamanLevel(), this.client.getAccount().getShamanLevelXp(), this.client.getAccount().getShamanLevelNextXp()));
    }

    /**
     * Sends the xp to gain.
     * @param xp The xp to gain.
     */
    public void sendShamanGainExperience(int xp) {
        this.client.sendPacket(new C_ShamanGainExperience(xp));
    }

    /**
     * Sends the pre-shaman skills like additional time in the current room.
     */
    public void sendShamanRoomSkillsPre() {
        if(this.client.getAccount().isShamanNoSkills()) return;

        // Additional Time
        if (this.client.getAccount().getPlayerSkills().containsKey(0)) {
            this.client.getRoom().addTime +=  this.client.getAccount().getPlayerSkills().get(0) * 5;
        }
    }

    /**
     * Enables the skills in the current room.
     */
    public void sendShamanRoomSkills() {
        if(this.client.getAccount().isShamanNoSkills()) return;

        var skills = this.client.getAccount().getPlayerSkills();
        if (skills.containsKey(1)) {
            // Big cheese
            this.client.getRoom().sendAll(new C_ShamanEnableSkill(1, new int[] {110, 120, 130, 140, 150}[(Math.min(skills.get(1), 5)) - 1]));
        }

        if (skills.containsKey(2)) {
            // Big shaman
            this.sendShamanEnableSkill(2, new int[] {114, 126, 118, 120, 122}[(Math.min(skills.get(2), 5)) - 1]);
        }

        if (skills.containsKey(4) && !this.client.getRoom().getCurrentMap().isDualShaman) {
            // Unburstable
            this.client.canShamanRespawn = true;
        }

        if (skills.containsKey(20)) {
            // Endurance
            this.sendShamanEnableSkill(20, new int[] {114, 126, 118, 120, 122}[(Math.min(skills.get(20), 5)) - 1]);
        }

        if (skills.containsKey(21)) {
            // Clean mouse
            this.client.bubblesCount = skills.get(21);
        }

        if (skills.containsKey(22)) {
            // Cleats
            this.sendShamanEnableSkill(22, new int[] {25, 30, 35, 40, 45}[(Math.min(skills.get(22), 5)) - 1]);
        }

        if (skills.containsKey(23)) {
            // Diet
            this.sendShamanEnableSkill(23, new int[] {40, 50, 60, 70, 80}[(Math.min(skills.get(23), 5)) - 1]);
        }

        if (skills.containsKey(24)) {
            // Opportunist
            this.client.isOpportunist = true;
        }

        if (skills.containsKey(32)) {
            // Stern mouse
            this.client.iceCount += skills.get(32);
        }

        if (skills.containsKey(40)) {
            // Big spirit
            this.sendShamanEnableSkill(40, new int[] {30, 40, 50, 60, 70}[(Math.min(skills.get(40), 5)) - 1]);
        }

        if (skills.containsKey(42)) {
            // Lead cannon
            this.sendShamanEnableSkill(42, new int[] {240, 230, 220, 210, 200}[(Math.min(skills.get(42), 5)) - 1]);
        }

        if (skills.containsKey(43)) {
            // Super anvil
            this.sendShamanEnableSkill(43, new int[] {240, 230, 220, 210, 200}[(Math.min(skills.get(43), 5)) - 1]);
        }

        if (skills.containsKey(45)) {
            // Aerodynamic balloon
            this.sendShamanEnableSkill(45, new int[] {110, 120, 130, 140, 150}[(Math.min(skills.get(45), 5)) - 1]);
        }

        if (skills.containsKey(49)) {
            this.sendShamanEnableSkill(49, new int[] {110, 120, 130, 140, 150}[(Math.min(skills.get(49), 5)) - 1]);
        }

        if (skills.containsKey(72)) {
            this.sendShamanEnableSkill(72, new int[] {25, 30, 35, 40, 45}[(Math.min(skills.get(72), 5)) - 1]);
        }

        if (skills.containsKey(89)) {
            int count = skills.get(89);
            this.sendShamanEnableSkill(49, new int[] {96, 92, 88, 84, 80}[(Math.min(count, 5)) - 1]);
            this.sendShamanEnableSkill(54, new int[] {96, 92, 88, 84, 80}[(Math.min(count, 5)) - 1]);
        }

        if (skills.containsKey(91)) {
            // Controlled disintegration
            this.client.isDisintegration = true;
        }

        for (int skill : new int[] {5, 8, 9, 11, 12, 26, 28, 29, 31, 41, 46, 48, 51, 52, 53, 60, 62, 65, 66, 67, 69, 71, 74, 80, 81, 83, 85, 88, 90, 93}) {
            if (skills.containsKey(skill) && !(this.client.getRoom().isSurvivor() && skill == 81)) {
                this.sendShamanEnableSkill(skill, skill == 28 || skill == 65 || skill == 74 ? skills.get(skill) * 2 : skills.get(skill));
            }
        }

        for (int skill : new int[] {6, 10, 13, 30, 33, 34, 44, 47, 50, 63, 64, 70, 73, 82, 84, 92}) {
            if (skills.containsKey(skill)) {
                this.sendShamanEnableSkill(skill, skill == 10 || skill == 13 ? 3 : 1);
            }
        }

        for (int skill : new int[] {7, 14, 27, 54, 86, 87, 94}) {
            if (skills.containsKey(skill)) {
                this.sendShamanEnableSkill(skill, skill == 54 ? 130 : 100);
            }
        }
    }

    /**
     * Sends the shaman skills.
     */
    public void sendShamanSkills(boolean refresh) {
        this.client.sendPacket(new C_ShamanSkills(this.client.getAccount().getPlayerSkills(), refresh));
    }

    /**
     * Sends the shaman type.
     */
    public void sendShamanType() {
        this.client.sendPacket(new C_ShamanType(this.client.getAccount().getShamanType(), true, this.client.getAccount().getShamanColor(), this.client.getAccount().isShamanNoSkills()));
    }

    /**
     * Checks if the player is enough close to shaman to apply the skill.
     * @param px The player's X coordinate.
     * @param py The player's Y coordinate.
     * @param player The player object.
     * @return True if player is enough close to shaman or False if it is too far.
     */
    private boolean checkQualifiedPlayer(int px, int py, Client player) {
        if (!player.getPlayerName().equals(this.client.getPlayerName()) && !player.isShaman) {
            if (player.getPosition().getFirst() >= px - 85 && player.getPosition().getFirst() <= px + 85) {
                return player.getPosition().getSecond() >= py - 85 && player.getPosition().getSecond() <= py + 85;
            }
        }

        return false;
    }

    /**
     * Enables the skill.
     * @param skillId The skill id.
     * @param argument The skill arg (value).
     */
    private void sendShamanEnableSkill(int skillId, int argument) {
        this.client.sendPacket(new C_ShamanEnableSkill(skillId, argument));
    }

    /**
     * Handles the player's skills.
     * @param objectId The object id.
     * @param shamanObjectId The shaman object id (skill id).
     * @param posX The X position where skill is going to summon.
     * @param posY The Y position where skill is going to summon.
     * @param angle The angle where skill is going to summon.
     */
    public void handleSkill(int objectId, int shamanObjectId, int posX, int posY, int angle) {
        if (this.client.getAccount().isShamanNoSkills()) return;
        var playerSkills = this.client.getAccount().getPlayerSkills();

        if(shamanObjectId == 36 /* Transformice */) {
            if(!playerSkills.containsKey(31)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    player.canTransform = true;
                    player.sendPacket(new C_EnableTransformation(true));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 37 /* Teleporter */) {
            if(!playerSkills.containsKey(9)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    this.client.getRoom().sendAll(new C_TeleportEffect(36, player.getPosition().getFirst(), player.getPosition().getSecond()));
                    player.sendPacket(new C_MovePlayer(this.client.getPosition().getFirst(), this.client.getPosition().getSecond(), false, 0, 0, false));
                    this.client.getRoom().sendAll(new C_TeleportEffect(37, player.getPosition().getFirst(), player.getPosition().getSecond()));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 38 /* Ambulance */) {
            if(!playerSkills.containsKey(6)) return;
            int count = playerSkills.get(6);
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (count == 0) break;

                if (player.isDead && !player.isHidden && !player.isShaman && !player.isEnteredInHole && !player.isAfk) {
                    count--;
                    this.client.getRoom().respawnPlayer(player.getPlayerName());
                    player.isDead = false;
                    player.sendPacket(new C_MovePlayer(this.client.getPosition().getFirst(), this.client.getPosition().getSecond(), false, 0, 0, false));
                    this.client.getRoom().sendAll(new C_TeleportEffect(37, this.client.getPosition().getFirst(), this.client.getPosition().getSecond()));
                }
            }

            this.client.getRoom().sendAll(new C_Skill(38, 1));
            return;
        }

        if(shamanObjectId == 42 /* Spring */) {
            if(!playerSkills.containsKey(13)) return;
            this.client.getRoom().sendAll(new C_AddBonus(3, posX, posY, angle, 0, true));
            return;
        }

        if(shamanObjectId == 43 /* Speed boost */) {
            if(!playerSkills.containsKey(10)) return;
            this.client.getRoom().sendAll(new C_AddBonus(1, posX, posY, angle, 0, true));
            return;
        }

        if(shamanObjectId == 47 /* Easy victory */) {
            if(!playerSkills.containsKey(5) || this.client.getRoom().getNumCompleted() < 1) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (player.cheeseCount > 0 && this.checkQualifiedPlayer(posX, posY, player)) {
                    player.sendEnterHole(0, -1, -1, -1);
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 55 /* Chief's food */) {
            if(!playerSkills.containsKey(7) || this.client.cheeseCount == 0 || client.getRoom().getNumCompleted() < 1) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (player.cheeseCount == 0 && this.checkQualifiedPlayer(posX, posY, player)) {
                    player.sendGiveCheese(-1, -1, -1, 0);
                    this.client.getRoom().sendAll(new C_PlayerGetCheese(this.client.getSessionId(), 0));
                    this.client.cheeseCount = 0;
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 56 /* Personal teleport */) {
            if(!playerSkills.containsKey(29)) return;
            this.client.getRoom().sendAll(new C_TeleportEffect(36, this.client.getPosition().getFirst(), this.client.getPosition().getSecond()));
            this.client.sendPacket(new C_MovePlayer(posX, posY, false, 0, 0, false));
            this.client.getRoom().sendAll(new C_TeleportEffect(37,posX, posY));
            return;
        }

        if(shamanObjectId == 57 /* Cloud */) {
            if(!playerSkills.containsKey(14)) return;
            if (this.client.getRoom().lastCloudID != -1) {
                this.client.getRoom().sendAll(new C_RemoveObject(this.client.getRoom().lastCloudID, true));
            }
            this.client.getRoom().lastCloudID = objectId;
            return;
        }

        if(shamanObjectId == 61 /* Companion Box */) {
            if(!playerSkills.containsKey(27)) return;
            if (this.client.getRoom().companionBox != -1) {
                this.client.getRoom().sendAll(new C_RemoveObject(this.client.getRoom().companionBox, true));
            }
            this.client.getRoom().companionBox = objectId;
            return;
        }

        if(shamanObjectId == 70 /* Spidermouse */) {
            if(!playerSkills.containsKey(88)) return;
            this.client.getRoom().sendAll(new C_SpiderMouseSkill(posX, posY));
            return;
        }

        if(shamanObjectId == 71 /* Rollout */) {
            if(!playerSkills.containsKey(69)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    this.client.getRoom().sendAll(new C_RolloutSkill(player.getSessionId()));
                    break;
                }
            }

            this.client.getRoom().sendAll(new C_Skill(71, 1));
            return;
        }

        if(shamanObjectId == 73 /* Go through there */) {
            if(!playerSkills.containsKey(71)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    this.client.getRoom().sendAll(new C_PlayerChangeSize(player.getSessionId(), 70, true));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 74 /* Leaf */) {
            if(!playerSkills.containsKey(80)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    this.client.getRoom().sendAll(new C_LeafSkill(player.getSessionId(), true));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 75 /* Nature's return */) {
            if(!playerSkills.containsKey(73)) return;
            this.client.getRoom().sendAll(new C_RemoveAllObjectsSkill());
            return;
        }

        if(shamanObjectId == 76 /* Booster */) {
            if(!playerSkills.containsKey(83)) return;
            this.client.getRoom().sendAll(new C_AddBonus(5, posX, posY, angle, 0, true));
            return;
        }

        if(shamanObjectId == 79 /* Stop */) {
            if(!playerSkills.containsKey(82) || this.client.getRoom().isSurvivor()) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (!player.isDead && !player.isHidden && !player.isShaman && !player.isEnteredInHole) {
                    this.client.getRoom().sendAll(new C_IcedMouseSkill(player.getSessionId(), true, true));
                    new Timer().schedule(() -> {
                        this.client.getRoom().sendAll(new C_IcedMouseSkill(player.getSessionId(), false, false));
                    }, playerSkills.get(82) * 2, TimeUnit.SECONDS);
                    break;
                }
            }

            this.client.getRoom().sendAll(new C_Skill(79, 1));
            return;
        }

        if(shamanObjectId == 81 /* Gravitational Anomaly */) {
            if(!playerSkills.containsKey(63)) return;
            this.client.getRoom().sendAll(new C_GravitationalSkill(0, 0, (this.client.getAccount().getPlayerSkills().get(63) * 2) * 1000));
            return;
        }

        if(shamanObjectId == 83 /* Meep */) {
            if(!playerSkills.containsKey(66)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    player.canMeep = true;
                    player.sendPacket(new C_EnableMeep(true));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 84 /* Grapnel */) {
            if(!playerSkills.containsKey(74)) return;
            this.client.getRoom().sendAll(new C_GrapnelSkill(this.client.getSessionId(), posX, posY));
            return;
        }

        if(shamanObjectId == 86 /* Campfire */) {
            if(!playerSkills.containsKey(86)) return;
            this.client.getRoom().sendAll(new C_BonfireSkill(posX, posY, this.client.getAccount().getPlayerSkills().get(86) * 4));
            return;
        }

        if(shamanObjectId == 92 /* Renewal */) {
            if(!playerSkills.containsKey(92)) return;
            this.client.sendPacket(new C_ResetShamanSkills());
            this.sendShamanRoomSkills();
            return;
        }

        if(shamanObjectId == 93 /* Evolution */) {
            if(!playerSkills.containsKey(93)) return;
            for (Client player : this.client.getRoom().getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    this.client.getRoom().sendAll(new C_EvolutionSkill(player.getSessionId(), 100));
                    break;
                }
            }
            return;
        }

        if(shamanObjectId == 94 /* Shameow */) {
            if(!playerSkills.containsKey(94)) return;
            this.client.getRoom().sendAll(new C_ShameowSkill(this.client.getSessionId(), true));
        }
    }

    /**
     * Handles the player's action skills like superstar.
     * @param actionType The action type.
     */
    public void handleSkillAction(int actionType) {
        if (this.client.getAccount().isShamanNoSkills())
            return;

        int skillId, actionId;
        switch (actionType) {
            case 0:
                skillId = 3;
                actionId = 0;
                break; // Superstar
            case 4:
                skillId = 61;
                actionId = 2;
                break; // Anger
            case 8:
                skillId = 25;
                actionId = 3;
                break; // Chocokiss
            default: return;
        }

        Integer skillLevel = this.client.getAccount().getPlayerSkills().get(skillId);
        if (skillLevel == null)
            return;

        int count = 0;
        for (Client player : this.client.getRoom().getPlayers().values()) {
            if (count >= skillLevel)
                break;

            if (player != this.client && this.checkQualifiedPlayer(player.getPosition().getFirst(), player.getPosition().getSecond(), player)) {
                this.client.getRoom().sendAll(new C_PlayerAction(player.getSessionId(), actionId, "", false));
                count++;
            }
        }
    }
}
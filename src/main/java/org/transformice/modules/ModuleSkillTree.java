package org.transformice.modules;

// Imports
import java.util.concurrent.TimeUnit;
import org.psframework.utils.PSBase64;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.AddBonusType;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.ShamanType;
import org.transformice.data.objects.SkillType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.modules.impl.ModulePlayer;

// Packets
import org.transformice.packets.send.informations.PacketSendShamanType;
import org.transformice.packets.send.legacy.PacketSendPlayerShamanPerformance;
import org.transformice.packets.send.legacy.PacketSendTotem;
import org.transformice.packets.send.player.PacketSendEnableMeep;
import org.transformice.packets.send.player.PacketSendEnableTransformation;
import org.transformice.packets.send.player.PacketSendPlayerAction;
import org.transformice.packets.send.player.PacketSendPlayerMove;
import org.transformice.packets.send.player.PacketSendShamanEarnedExperience;
import org.transformice.packets.send.player.PacketSendShamanEarnedLevel;
import org.transformice.packets.send.player.PacketSendShamanEnableSkill;
import org.transformice.packets.send.player.PacketSendShamanExperience;
import org.transformice.packets.send.player.PacketSendShamanFlySkill;
import org.transformice.packets.send.player.PacketSendShamanGainExperience;
import org.transformice.packets.send.player.PacketSendShamanSkills;
import org.transformice.packets.send.player.PacketSendSkillRedistributionCooldown;
import org.transformice.packets.send.player.PacketSendSkillRedistributionNotEnoughCheese;
import org.transformice.packets.send.room.PacketSendAddBonus;
import org.transformice.packets.send.room.PacketSendAntigravitySkill;
import org.transformice.packets.send.room.PacketSendCampfireSkill;
import org.transformice.packets.send.room.PacketSendConvertSkill;
import org.transformice.packets.send.room.PacketSendDemolitionSkill;
import org.transformice.packets.send.room.PacketSendEvolutionSkill;
import org.transformice.packets.send.room.PacketSendGrapnelSkill;
import org.transformice.packets.send.room.PacketSendHandymouseSkill;
import org.transformice.packets.send.room.PacketSendIcedSkill;
import org.transformice.packets.send.room.PacketSendLeafSkill;
import org.transformice.packets.send.room.PacketSendPlayerChangeSize;
import org.transformice.packets.send.room.PacketSendProjectionSkill;
import org.transformice.packets.send.room.PacketSendRecyclingSkill;
import org.transformice.packets.send.room.PacketSendRemoveAllObjectsSkill;
import org.transformice.packets.send.room.PacketSendRemoveObject;
import org.transformice.packets.send.room.PacketSendRestorativeSkill;
import org.transformice.packets.send.room.PacketSendRolloutSkill;
import org.transformice.packets.send.room.PacketSendSetRoomGravity;
import org.transformice.packets.send.room.PacketSendShamanResetSkills;
import org.transformice.packets.send.room.PacketSendShameowSkill;
import org.transformice.packets.send.room.PacketSendSkillChangeUseState;
import org.transformice.packets.send.room.PacketSendSpawnStaticParticle;
import org.transformice.packets.send.room.PacketSendSpiderMouseSkill;

public final class ModuleSkillTree extends ModulePlayer {
    /**
     * Creates a new spiritual tree (skills) module.
     * @param gameServer The game server.
     * @param gamePlayer The game player.
     */
    public ModuleSkillTree(GameServer gameServer, GamePlayer gamePlayer) {
        super(gameServer, gamePlayer);
    }

    /**
     * Gets XP after winning the round.
     * @param isShaman Is the current player a shaman.
     * @param exp Total xp to gain.
     */
    public void earnExp(boolean isShaman, int exp) {
        if (exp > 0 && !this.player.getIsGuest()) {
            PlayerEntity playerData = this.player.getPlayerData();
            GameRoom room = this.player.getPlayerRoom();
            if (isShaman) {
                int consumable = exp / 7;
                this.player.getParseDailyQuests().increaseMission(MissionType.SAVE_MICE, exp);
                room.broadcastLegacyPacket(new PacketSendPlayerShamanPerformance(this.player.getPlayerName(), exp));
                if (consumable > 0)
                    this.player.getParseInventory().addConsumable(String.valueOf(playerData.shamanSkillsEnabled ? 2253 : 2620), 1, false);
            }

            int level = playerData.shamanLevel;
            int nextLevelXp = playerData.shamanNextLevelXp;
            int multiplier = 1;
            if (isShaman) {
                if (playerData.getShamanType() == ShamanType.NORMAL)
                    multiplier = level < 30 ? 3 : level < 60 ? 5 : 10;
                else
                    multiplier = level < 30 ? 5 : level < 60 ? 10 : 20;
            }

            int gainExp = exp * multiplier;
            int currentXp = playerData.shamanLevelXp + gainExp;
            boolean state = false;
            while (currentXp >= nextLevelXp) {
                currentXp -= nextLevelXp;
                level++;
                state = true;
                nextLevelXp = level < 30 ? 32 + (level - 1) * (level + 2) : level < 60 ? 900 + 5 * (level - 29) * (level + 30) : 14250 + (15 * (level - 59) * (level + 60) / 2);
            }

            playerData.shamanLevel = level;
            playerData.shamanLevelXp = currentXp;
            playerData.shamanNextLevelXp = nextLevelXp;
            if (state) {
                this.player.broadcastPacket(new PacketSendShamanExperience(level - 1, 0, nextLevelXp));
                this.player.broadcastPacket(new PacketSendShamanGainExperience(currentXp));
                if (level >= 20)
                    room.broadcastPacket(new PacketSendShamanEarnedLevel(this.player.getPlayerName(), level));
            } else {
                this.player.broadcastPacket(new PacketSendShamanGainExperience(gainExp));
                this.player.broadcastPacket(new PacketSendShamanExperience(level - 1, currentXp, nextLevelXp));
            }

            if (isShaman) {
                this.player.broadcastPacket(new PacketSendShamanEarnedExperience(gainExp, exp));
                if (playerData.shamanSkillsEnabled) {
                    if (playerData.shamanType == ShamanType.NORMAL) {
                        playerData.savesNormalWithSkill += exp;
                    } else if (playerData.shamanType == ShamanType.HARD) {
                        playerData.savesHardWithSkill += exp;
                    } else {
                        playerData.savesDivineWithSkill += exp;
                    }
                } else {
                    if (playerData.shamanType == ShamanType.NORMAL) {
                        playerData.savesNormalWithoutSkill += exp;
                    } else if (playerData.shamanType == ShamanType.HARD) {
                        playerData.savesHardWithoutSkill += exp;
                    } else {
                        playerData.savesDivineWithoutSkill += exp;
                    }
                }
            }
        }
    }

    /**
     * Gets the default shaman badge depending on the skill tree.
     * @return The default shaman badge.
     */
    public int getShamanBadge() {
        var data = this.player.getPlayerData();
        if (data.currentShamanBadge != 0)
            return data.currentShamanBadge;

        int[] counts = new int[5];
        for (var skill : data.getShamanSkills().entrySet()) {
            int id = skill.getKey().getId();
            int value = skill.getValue();
            if (id >= 0 && id < 14)
                counts[0] += value;
            else if (id > 19 && id < 35)
                counts[1] += value;
            else if (id > 39 && id < 55)
                counts[2] += value;
            else if (id > 79 && id < 95)
                counts[3] += value;
            else if (id > 59 && id < 75)
                counts[4] += value;
        }

        int max = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[max])
                max = i;
        }

        return -max;
    }

    /**
     * Handles the antigravity skill.
     * @param objectId The object's id to apply antigravity.
     */
    public void handleAntigravitySkill(int objectId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_ANTIGRAVITY))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendAntigravitySkill(objectId, 0));
    }

    /**
     * Handles the convert skill.
     * @param objectId The object's id to apply convert.
     */
    public void handleConvertSkill(int objectId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_ARCHITECT))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendConvertSkill(objectId, false));
    }

    /**
     * Handles the demolition skill.
     * @param objectId The object's id to apply demolition.
     */
    public void handleDemolitionSkill(int objectId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_DEMOLISH_WORKER))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendDemolitionSkill(objectId));
    }

    /**
     * Handles the handymouse skill.
     * @param objectId The object's to apply handymouse.
     * @param handyMouseId The handymouse's id.
     */
    public void handleHandymouseSkill(int objectId, int handyMouseId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_HANDYMOUSE))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().getRoomType() == RoomType.SURVIVOR || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        GameRoom myRoom = this.player.getPlayerRoom();
        if (myRoom.lastHandyMouseObjectId[0] == -1)
            myRoom.lastHandyMouseObjectId = new int[] {objectId, handyMouseId};
        else {
            myRoom.broadcastPacket(new PacketSendHandymouseSkill(handyMouseId, objectId, myRoom.lastHandyMouseObjectId[1], myRoom.lastHandyMouseObjectId[0]));
            this.player.broadcastPacket(new PacketSendSkillChangeUseState(77, 1));
            myRoom.lastHandyMouseObjectId = new int[] {-1, -1};
        }
    }

    /**
     * Handles the leaf skill.
     * @param sessionId The player's session id to apply leaf skill.
     */
    public void handleLeafSkill(int sessionId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_LEAF))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().getRoomType() == RoomType.SURVIVOR || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendLeafSkill(true, sessionId));
    }

    /**
     * Handles the projection skill.
     * @param posX The position X.
     * @param posY The position Y.
     * @param distance The projection distance.
     */
    public void handleProjectionSkill(int posX, int posY, int distance) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_PROJECTION))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(this.player, new PacketSendProjectionSkill(posX, posY, distance));
    }

    /**
     * Handles the recycling skill.
     * @param jointId The join's id to apply the recycling skill.
     */
    public void handleRecyclingSkill(int jointId) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_RECYCLING))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendRecyclingSkill(jointId));
    }

    /**
     * Handles the restorative skill.
     * @param objectId The object's id.
     * @param objectId2 The other object's id.
     */
    public void handleRestorativeSkill(int objectId, int objectId2) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_RESTORATIVE))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(new PacketSendRestorativeSkill(objectId, objectId2));
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
        if (!this.player.isShaman || (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills) && shamanObjectId != 44)
            return;

        var room = this.player.getPlayerRoom();
        var skills = this.player.getPlayerData().getShamanSkills();
        boolean isSurvivor =  room.getRoomType() == RoomType.SURVIVOR;
        if (shamanObjectId == 36 && skills.containsKey(SkillType.SKILL_TRANSFORMICE)) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    player.canTransform = true;
                    player.broadcastPacket(new PacketSendEnableTransformation(true));
                    this.player.broadcastPacket(new PacketSendSkillChangeUseState(36, 1));
                    break;
                }
            }
        }

        if (shamanObjectId == 37 && skills.containsKey(SkillType.SKILL_TELEPORTER) && !isSurvivor) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    room.broadcastPacket(new PacketSendSpawnStaticParticle(36, player.positionX, player.positionY));
                    player.broadcastPacket(new PacketSendPlayerMove(this.player.positionX, this.player.positionY, false, 0, 0, false));
                    room.broadcastPacket(new PacketSendSpawnStaticParticle(37, this.player.positionX, this.player.positionY));
                    break;
                }
            }
        }

        if (shamanObjectId == 38 && skills.containsKey(SkillType.SKILL_AMBULANCE)) {
            if (System.currentTimeMillis() - room.getGameStartTimeMillis() < 15000)
                return;

            int ambulanceCount = skills.get(SkillType.SKILL_AMBULANCE);
            for (GamePlayer player : room.getPlayers().values()) {
                if (player.isDead && !player.isHidden && !player.isShaman && !player.isEnteredInHole && !player.isAfk && !player.isNewPlayer) {
                    if (ambulanceCount > 0) {
                        room.sendRespawnPlayer(player);
                        player.broadcastPacket(new PacketSendPlayerMove(this.player.positionX, this.player.positionY, false, 0, 0, false));
                        room.broadcastPacket(new PacketSendSpawnStaticParticle(37, this.player.positionX, this.player.positionY));
                        ambulanceCount--;
                    }
                }
            }

            if (ambulanceCount != skills.get(SkillType.SKILL_AMBULANCE))
                this.player.broadcastPacket(new PacketSendSkillChangeUseState(38, 1));

            return;
        }

        if (shamanObjectId == 42 && skills.containsKey(SkillType.SKILL_SPRING))
            room.sendAddBonus(AddBonusType.BONUS_SPRING, posX, posY, angle, true);

        if (shamanObjectId == 43 && skills.containsKey(SkillType.SKILL_SPEEDBOOST))
            room.sendAddBonus(AddBonusType.BONUS_SPEED, posX, posY, angle, true);

        if (shamanObjectId == 44 && !isSurvivor) {
            if (this.player.canTotem) {
                this.player.broadcastLegacyPacket(new PacketSendTotem(this.player.getSessionId(), posX, posY, PSBase64.decode((String) this.player.getPlayerData().totemInfo[1])));
                this.player.canTotem = false;
            }
        }

        if (shamanObjectId == 47 && skills.containsKey(SkillType.SKILL_EASY_VICTORY) && !isSurvivor) {
            int cnt = room.firstShamanNumCompleted + room.secondShamanNumCompleted;
            if (cnt > 0) {
                for (GamePlayer player : room.getPlayers().values()) {
                    if (player.cheeseCount > 0 && this.checkQualifiedPlayer(posX, posY, player)) {
                        player.sendEnterHole(0, -1, -1, -1);
                        this.player.broadcastPacket(new PacketSendSkillChangeUseState(47, 1));
                        break;
                    }
                }
            }
        }

        if (shamanObjectId == 55 && skills.containsKey(SkillType.SKILL_CHIEFSFOOD)) {
            int cnt = room.firstShamanNumCompleted + room.secondShamanNumCompleted;
            if (cnt > 0 && this.player.cheeseCount > 0) {
                for (GamePlayer player : room.getPlayers().values()) {
                    if (player.cheeseCount == 0 && this.checkQualifiedPlayer(posX, posY, player)) {
                        player.sendGiveCheese(-1, -1, 0, 0, -1);
                        this.player.sendRemoveCheese(1);
                        this.player.broadcastPacket(new PacketSendSkillChangeUseState(55, 1));
                        break;
                    }
                }
            }
        }

        if (shamanObjectId == 56 && skills.containsKey(SkillType.SKILL_PERSONAL_TELEPORTER)) {
            room.broadcastPacket(new PacketSendSpawnStaticParticle(36, this.player.positionX, this.player.positionY));
            this.player.broadcastPacket(new PacketSendPlayerMove(posX, posY, false, 0, 0, false));
            room.broadcastPacket(new PacketSendSpawnStaticParticle(37, this.player.positionX, this.player.positionY));
        }

        if (shamanObjectId == 57 && skills.containsKey(SkillType.SKILL_CLOUD)) {
            if (room.lastCloudObjectId != -1)
                room.broadcastPacket(new PacketSendRemoveObject(room.lastCloudObjectId, true));

            room.lastCloudObjectId = objectId;
        }

        if (shamanObjectId == 61 && skills.containsKey(SkillType.SKILL_COMPANION_BOX) && !isSurvivor) {
            if (room.lastCompanionBoxId != -1)
                room.broadcastPacket(new PacketSendRemoveObject(room.lastCompanionBoxId, true));

            room.lastCompanionBoxId = objectId;
        }

        if (shamanObjectId == 70 && skills.containsKey(SkillType.SKILL_SPIDER_MOUSE))
            room.broadcastPacket(new PacketSendSpiderMouseSkill(posX, posY));

        if (shamanObjectId == 71 && skills.containsKey(SkillType.SKILL_ROLLOUT)) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player) && !player.isShaman) {
                    room.broadcastPacket(new PacketSendRolloutSkill(player.getSessionId()));
                    this.player.broadcastPacket(new PacketSendSkillChangeUseState(71, 1));
                    break;
                }
            }

            return;
        }

        if (shamanObjectId == 73 && skills.containsKey(SkillType.SKILL_GO_THROUGH_THERE)) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    room.broadcastPacket(new PacketSendPlayerChangeSize(player.getSessionId(), 70, true));
                    this.player.broadcastPacket(new PacketSendSkillChangeUseState(73, 1));
                }
            }
        }

        if (shamanObjectId == 74 && skills.containsKey(SkillType.SKILL_LEAF) && !isSurvivor) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    room.broadcastPacket(new PacketSendLeafSkill(true, player.getSessionId()));
                    this.player.broadcastPacket(new PacketSendSkillChangeUseState(74, 1));
                    break;
                }
            }
        }

        if (shamanObjectId == 75 && skills.containsKey(SkillType.SKILL_NATURE_RETURN))
            room.broadcastPacket(new PacketSendRemoveAllObjectsSkill());

        if (shamanObjectId == 76 && skills.containsKey(SkillType.SKILL_BOOSTER))
            room.broadcastPacket(new PacketSendAddBonus(AddBonusType.BONUS_BOOSTER, posX, posY, angle, 0, true));

        if (shamanObjectId == 79 && skills.containsKey(SkillType.SKILL_STOP) && !isSurvivor) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (!player.isDead && !player.isHidden && !player.isShaman && !player.isEnteredInHole) {
                    room.broadcastPacket(new PacketSendIcedSkill(player.getSessionId(), true, true));
                    new PSTimer(this.server.getScheduler()).schedule(() -> room.broadcastPacket(new PacketSendIcedSkill(player.getSessionId(), false, false)), skills.get(SkillType.SKILL_STOP) * 2, TimeUnit.SECONDS);
                    break;
                }
            }

            this.player.broadcastPacket(new PacketSendSkillChangeUseState(79, 1));
        }

        if (shamanObjectId == 81 && skills.containsKey(SkillType.SKILL_GRAVITATIONAL_ANOMALY) && !isSurvivor)
            room.broadcastPacket(new PacketSendSetRoomGravity((skills.get(SkillType.SKILL_GRAVITATIONAL_ANOMALY) * 2) * 1000, 0, 0));

        if (shamanObjectId == 83 && skills.containsKey(SkillType.SKILL_MEEP)) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    player.canMeep = true;
                    player.broadcastPacket(new PacketSendEnableMeep(true));
                    this.player.broadcastPacket(new PacketSendSkillChangeUseState(83, 1));
                    break;
                }
            }
        }

        if (shamanObjectId == 84 && skills.containsKey(SkillType.SKILL_GRAPNEL))
            room.broadcastPacket(new PacketSendGrapnelSkill(this.player.getSessionId(), posX, posY));

        if (shamanObjectId == 86 && skills.containsKey(SkillType.SKILL_CAMPFIRE))
            room.broadcastPacket(new PacketSendCampfireSkill(posX, posY, skills.get(SkillType.SKILL_CAMPFIRE) * 4));

        if (shamanObjectId == 92 && skills.containsKey(SkillType.SKILL_RENEVAL)) {
            this.player.broadcastPacket(new PacketSendShamanResetSkills());
            this.sendShamanRoomSkills();
        }

        if (shamanObjectId == 93  && skills.containsKey(SkillType.SKILL_EVOLUTION)) {
            for (GamePlayer player : room.getPlayers().values()) {
                if (this.checkQualifiedPlayer(posX, posY, player)) {
                    room.broadcastPacket(new PacketSendEvolutionSkill(player.getSessionId(), 100));
                    break;
                }
            }
        }

        if (shamanObjectId == 94 && skills.containsKey(SkillType.SKILL_SHAMEOW))
            room.broadcastPacket(new PacketSendShameowSkill(this.player.getSessionId(), true));
    }

    /**
     * Handles the player's action skills like superstar.
     * @param actionType The action type.
     */
    public void handleSkillByAction(int actionType) {
        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        int actionId = -1;
        SkillType skillId = null;
        actionId = switch (actionType) {
            case 0 -> {
                skillId = SkillType.SKILL_SUPERSTAR;
                yield 0;
            }
            case 4 -> {
                skillId = SkillType.SKILL_ANGER;
                yield 2;
            }
            case 8 -> {
                skillId = SkillType.SKILL_CHOCOKISS;
                yield 3;
            }
            default -> actionId;
        };

        Integer skillLevel = this.player.getPlayerData().getShamanSkills().get(skillId);
        if (skillLevel == null)
            return;

        int count = 0;
        for (GamePlayer player : this.player.getPlayerRoom().getPlayers().values()) {
            if (count >= skillLevel)
                break;

            if (player == this.player || (player.positionY < this.player.positionY - 100 || player.positionY > this.player.positionY + 100) || (player.positionX < this.player.positionX - 100 || player.positionX > this.player.positionX + 100))
                continue;

            this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(player.getSessionId(), actionId, "", false));
            count++;
        }
    }

    /**
     * Handles the angel skill.
     * @param isFlying Is the player flying right now.
     */
    public void handleShamanFlySkill(boolean isFlying) {
        if (!this.player.isShaman || !this.player.getPlayerData().getShamanSkills().containsKey(SkillType.SKILL_ANGEL))
            return;

        if (!this.player.getPlayerData().shamanSkillsEnabled || this.player.getPlayerRoom().disableAllShamanSkills)
            return;

        this.player.getPlayerRoom().broadcastPacket(this.player, new PacketSendShamanFlySkill(this.player.getSessionId(), isFlying));
    }

    /**
     * Initializes the shaman packets on login.
     */
    public void sendLoginShamanSkillsInitialization() {
        if (this.player.getIsGuest())
            return;

        this.sendShamanSkills(false);
        this.player.broadcastPacket(new PacketSendShamanExperience(this.player.getPlayerData().shamanLevel - 1, this.player.getPlayerData().shamanLevelXp, this.player.getPlayerData().shamanNextLevelXp));
        if (this.player.getPlayerData().savesNormalWithSkill >= 1000)
            this.sendShamanType();
    }

    /**
     * Redistributes the skills.
     */
    public void sendRedistributeSkills() {
        if (this.player.getPlayerData().getShamanSkills().isEmpty())
            return;

        if (this.player.getPlayerData().shopCheese < 10) {
            this.player.broadcastPacket(new PacketSendSkillRedistributionNotEnoughCheese());
            return;
        }

        if (this.server.getSkillRedistributionTimer(this.player.getPlayerName()).getRemainingTime() > 0) {
            this.player.broadcastPacket(new PacketSendSkillRedistributionCooldown());
            return;
        }

        this.server.getSkillRedistributionTimer(this.player.getPlayerName()).schedule();
        this.player.getPlayerData().shopCheese -= 10;
        this.player.getPlayerData().getShamanSkills().clear();
        this.sendShamanSkills(true);
        this.player.getPlayerData().totemInfo = new String[]{"0", ""};
    }

    /**
     * Enables the skills in the current room.
     */
    public void sendShamanRoomSkills() {
        if (!this.player.getPlayerData().shamanSkillsEnabled)
            return;

        var skills = this.player.getPlayerData().getShamanSkills();
        boolean isSurvivor =  this.player.getPlayerRoom().getRoomType() == RoomType.SURVIVOR;
        if (skills.containsKey(SkillType.SKILL_BIG_CHEESE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_BIG_CHEESE, 100 + Math.min(skills.getOrDefault(SkillType.SKILL_BIG_CHEESE, 0), 5) * 10));

        if (skills.containsKey(SkillType.SKILL_BIG_SHAMAN))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_BIG_SHAMAN, new int[] {114, 126, 118, 120, 122}[skills.get(SkillType.SKILL_BIG_SHAMAN) - 1]));

        if (skills.containsKey(SkillType.SKILL_UNBURSTABLE) && !this.player.getPlayerRoom().getCurrentMap().isDualShaman && !isSurvivor)
            this.player.canShamanRespawn = true;

        if (skills.containsKey(SkillType.SKILL_EASY_VICTORY) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_EASY_VICTORY, skills.get(SkillType.SKILL_EASY_VICTORY)));

        if (skills.containsKey(SkillType.SKILL_CHIEFSFOOD))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_CHIEFSFOOD, (this.player.getPlayerData().getShamanType() == ShamanType.DIVINE ? 5 : 100)));

        if (skills.containsKey(SkillType.SKILL_AMBULANCE)) {
            this.player.ambulanceCount = skills.get(SkillType.SKILL_AMBULANCE);
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_AMBULANCE, 1));
        }

        if (skills.containsKey(SkillType.SKILL_WEIGHTLESS))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_WEIGHTLESS, skills.get(SkillType.SKILL_WEIGHTLESS)));

        if (skills.containsKey(SkillType.SKILL_TELEPORTER) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_TELEPORTER, skills.get(SkillType.SKILL_TELEPORTER)));

        if (skills.containsKey(SkillType.SKILL_SPEEDBOOST))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SPEEDBOOST, 3));

        if (skills.containsKey(SkillType.SKILL_APPLE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_APPLE, skills.get(SkillType.SKILL_APPLE)));

        if (skills.containsKey(SkillType.SKILL_ANCESTRAL_SPIRIT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ANCESTRAL_SPIRIT, skills.get(SkillType.SKILL_ANCESTRAL_SPIRIT)));

        if (skills.containsKey(SkillType.SKILL_SPRING))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SPRING, (this.player.getPlayerData().getShamanType() == ShamanType.DIVINE ? 1 : 3)));

        if (skills.containsKey(SkillType.SKILL_CLOUD))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_CLOUD, 100));

        if (skills.containsKey(SkillType.SKILL_ENDURANCE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ENDURANCE, new int[] {114, 126, 118, 120, 122}[skills.get(SkillType.SKILL_ENDURANCE) - 1]));

        if (skills.containsKey(SkillType.SKILL_CLEAN_MOUSE))
            this.player.bubbleCount = skills.get(SkillType.SKILL_CLEAN_MOUSE);

        if (skills.containsKey(SkillType.SKILL_CLEATS))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_CLEATS, new int[] {25, 30, 35, 40, 45}[skills.get(SkillType.SKILL_CLEATS) - 1]));

        if (skills.containsKey(SkillType.SKILL_DIET))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_DIET, new int[] {40, 50, 60, 70, 80}[skills.get(SkillType.SKILL_DIET) - 1]));

        if (skills.containsKey(SkillType.SKILL_OPPORTUNIST))
            this.player.isOpportunist = true;

        if (skills.containsKey(SkillType.SKILL_ROCKET_SCIENTIST))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ROCKET_SCIENTIST, skills.get(SkillType.SKILL_ROCKET_SCIENTIST)));

        if (skills.containsKey(SkillType.SKILL_COMPANION_BOX) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_COMPANION_BOX, 100));

        if (skills.containsKey(SkillType.SKILL_TIGHT_BUDGET))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_TIGHT_BUDGET, skills.get(SkillType.SKILL_TIGHT_BUDGET) * 2));

        if (skills.containsKey(SkillType.SKILL_PERSONAL_TELEPORTER))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_PERSONAL_TELEPORTER, skills.get(SkillType.SKILL_PERSONAL_TELEPORTER)));

        if (skills.containsKey(SkillType.SKILL_PROJECTION))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_PROJECTION, 1));

        if (skills.containsKey(SkillType.SKILL_TRANSFORMICE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_TRANSFORMICE, skills.get(SkillType.SKILL_TRANSFORMICE)));

        if (skills.containsKey(SkillType.SKILL_STERN_MOUSE))
            this.player.iceCount += skills.get(SkillType.SKILL_STERN_MOUSE);

        if (skills.containsKey(SkillType.SKILL_ACROBAT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ACROBAT, 1));

        if (skills.containsKey(SkillType.SKILL_ANGEL))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ANGEL, 1));

        if (skills.containsKey(SkillType.SKILL_BIG_SPIRIT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_BIG_SPIRIT, new int[] {30, 40, 50, 60, 70}[skills.get(SkillType.SKILL_BIG_SPIRIT) - 1]));

        if (skills.containsKey(SkillType.SKILL_MULTIPLE_BALLS))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_MULTIPLE_BALLS, skills.get(SkillType.SKILL_MULTIPLE_BALLS)));

        if (skills.containsKey(SkillType.SKILL_LEAD_CANNON) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_LEAD_CANNON, new int[] {240, 230, 220, 210, 200}[skills.get(SkillType.SKILL_LEAD_CANNON) - 1]));

        if (skills.containsKey(SkillType.SKILL_SUPER_ANVIL))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SUPER_ANVIL, new int[] {240, 230, 220, 210, 200}[skills.get(SkillType.SKILL_SUPER_ANVIL) - 1]));

        if (skills.containsKey(SkillType.SKILL_ICE_PLANK))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ICE_PLANK, 1));

        if (skills.containsKey(SkillType.SKILL_AERODYNAMIC_BALLOON))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_AERODYNAMIC_BALLOON, new int[] {110, 120, 130, 140, 150}[skills.get(SkillType.SKILL_AERODYNAMIC_BALLOON) - 1]));

        if (skills.containsKey(SkillType.SKILL_STABLE_RUNE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_STABLE_RUNE, skills.get(SkillType.SKILL_STABLE_RUNE)));

        if (skills.containsKey(SkillType.SKILL_CHOCOPLANK))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_CHOCOPLANK, 1));

        if (skills.containsKey(SkillType.SKILL_PRE_INFLATED_BALLOON))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_PRE_INFLATED_BALLOON, skills.get(SkillType.SKILL_PRE_INFLATED_BALLOON)));

        if (skills.containsKey(SkillType.SKILL_MASTER_SUMMONER) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_MASTER_SUMMONER, new int[] {110, 120, 130, 140, 150}[skills.get(SkillType.SKILL_MASTER_SUMMONER) - 1]));

        if (skills.containsKey(SkillType.SKILL_ANTISPIRIT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ANTISPIRIT, 1));

        if (skills.containsKey(SkillType.SKILL_DEMOLISH_WORKER))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_DEMOLISH_WORKER, skills.get(SkillType.SKILL_DEMOLISH_WORKER)));

        if (skills.containsKey(SkillType.SKILL_ARCHITECT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ARCHITECT, skills.get(SkillType.SKILL_ARCHITECT)));

        if (skills.containsKey(SkillType.SKILL_SHEEP))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SHEEP, skills.get(SkillType.SKILL_SHEEP)));

        if (skills.containsKey(SkillType.SKILL_EAGLE_EYE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_EAGLE_EYE, 130));

        if (skills.containsKey(SkillType.SKILL_RESTORATIVE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_RESTORATIVE, skills.get(SkillType.SKILL_RESTORATIVE)));

        if (skills.containsKey(SkillType.SKILL_RECYCLING))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_RECYCLING, skills.get(SkillType.SKILL_RECYCLING)));

        if (skills.containsKey(SkillType.SKILL_GRAVITATIONAL_ANOMALY) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_GRAVITATIONAL_ANOMALY, 1));

        if (skills.containsKey(SkillType.SKILL_MAD_SCIENTIST))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_MAD_SCIENTIST, 1));

        if (skills.containsKey(SkillType.SKILL_ANTIGRAVITY))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ANTIGRAVITY, skills.get(SkillType.SKILL_ANTIGRAVITY) * 2));

        if (skills.containsKey(SkillType.SKILL_MEEP))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_MEEP, skills.get(SkillType.SKILL_MEEP)));

        if (skills.containsKey(SkillType.SKILL_SOLITARY))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SOLITARY, skills.get(SkillType.SKILL_SOLITARY)));

        if (skills.containsKey(SkillType.SKILL_ROLLOUT))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_ROLLOUT, skills.get(SkillType.SKILL_ROLLOUT)));

        if (skills.containsKey(SkillType.SKILL_GREGARIOUS_SPIRIT) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_GREGARIOUS_SPIRIT, 1));

        if (skills.containsKey(SkillType.SKILL_GO_THROUGH_THERE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_GO_THROUGH_THERE, skills.get(SkillType.SKILL_GO_THROUGH_THERE)));

        if (skills.containsKey(SkillType.SKILL_THORNS))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_THORNS, new int[] {25, 30, 35, 40, 45}[skills.get(SkillType.SKILL_THORNS) - 1]));

        if (skills.containsKey(SkillType.SKILL_NATURE_RETURN))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_NATURE_RETURN, 1));

        if (skills.containsKey(SkillType.SKILL_GRAPNEL))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_GRAPNEL, skills.get(SkillType.SKILL_GRAPNEL) * 2));

        if (skills.containsKey(SkillType.SKILL_LEAF) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_LEAF, skills.get(SkillType.SKILL_LEAF)));

        if (skills.containsKey(SkillType.SKILL_LANDSLIDE) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_LANDSLIDE, skills.get(SkillType.SKILL_LANDSLIDE)));

        if (skills.containsKey(SkillType.SKILL_STOP) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_STOP, 1));

        if (skills.containsKey(SkillType.SKILL_BOOSTER))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_BOOSTER, skills.get(SkillType.SKILL_BOOSTER)));

        if (skills.containsKey(SkillType.SKILL_EARTH_SYMBOL) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_EARTH_SYMBOL, 1));

        if (skills.containsKey(SkillType.SKILL_HANDYMOUSE) && !isSurvivor)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_HANDYMOUSE, skills.get(SkillType.SKILL_HANDYMOUSE)));

        if (skills.containsKey(SkillType.SKILL_CAMPFIRE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_CAMPFIRE, 100));

        if (skills.containsKey(SkillType.SKILL_S))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_S, 100));

        if (skills.containsKey(SkillType.SKILL_SPIDER_MOUSE))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SPIDER_MOUSE, skills.get(SkillType.SKILL_SPIDER_MOUSE)));

        if (skills.containsKey(SkillType.SKILL_BIG_PLANK))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_BIG_PLANK, skills.get(SkillType.SKILL_BIG_PLANK)));

        if (skills.containsKey(SkillType.SKILL_REFLEX) && !isSurvivor) {
            int count = skills.get(SkillType.SKILL_REFLEX);
            int value = new int[] {96, 92, 88, 84, 80}[count - 1];
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_MASTER_SUMMONER, value));
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_EAGLE_EYE, value));
        }

        if (skills.containsKey(SkillType.SKILL_CONTROLLED_DISINTEGRATION))
            this.player.isDisintegration = true;

        if (skills.containsKey(SkillType.SKILL_RENEVAL))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_RENEVAL, 1));

        if (skills.containsKey(SkillType.SKILL_EVOLUTION))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_EVOLUTION, skills.get(SkillType.SKILL_EVOLUTION)));

        if (skills.containsKey(SkillType.SKILL_SHAMEOW))
            this.player.getPlayerRoom().broadcastPacket(new PacketSendShamanEnableSkill(SkillType.SKILL_SHAMEOW, 100));
    }

    /**
     * Sends the shaman skills.
     */
    public void sendShamanSkills(boolean refresh) {
        this.player.broadcastPacket(new PacketSendShamanSkills(this.player.getPlayerData().getShamanSkills(), refresh));
    }

    /**
     * Sends the shaman type.
     */
    public void sendShamanType() {
        this.player.broadcastPacket(new PacketSendShamanType(this.player.getPlayerData().getShamanType().ordinal(), (this.player.getPlayerData().savesNormalWithSkill >= 5000 && this.player.getPlayerData().savesHardWithSkill >= 2000), this.player.getPlayerData().shamanColor, !this.player.getPlayerData().shamanSkillsEnabled));
    }

    /**
     * Checks if the player is enough close to shaman to apply the skill.
     * @param px The player's X coordinate.
     * @param py The player's Y coordinate.
     * @param player The player object.
     * @return True if player is enough close to shaman or False if it is too far.
     */
    private boolean checkQualifiedPlayer(int px, int py, GamePlayer player) {
        return !player.getPlayerName().equals(this.player.getPlayerName()) && !player.isShaman && player.positionX >= px - 85 && player.positionX <= px + 85 && player.positionY >= py - 85 && player.positionY <= py + 85;
    }
}
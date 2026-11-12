package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

public final class PacketSendPlayerProfile extends PSSendPacket {
    private final PlayerEntity playerEntity;
    private final GameConfig.RoomStats roomStats;

    /**
     * Creates a new packet of SEND_PLAYER_PROFILE.
     * @param playerEntity The player's database.
     * @param roomStats The profile room stats.
     */
    public PacketSendPlayerProfile(PlayerEntity playerEntity, GameConfig.RoomStats roomStats) {
        super(PacketOpcodes.SEND_PLAYER_PROFILE);
        this.playerEntity = playerEntity;
        this.roomStats = roomStats;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerEntity.getUsername(), true);
        packet.writeInt(playerEntity.getAccountId());
        packet.writeInt(this.playerEntity.getRegistrationDate());
        packet.writeByte(Utils.getProfileColorId(this.playerEntity.getStaffRoles()));
        packet.writeByte(this.playerEntity.playerGender.ordinal());
        packet.writeUTF(this.playerEntity.tribeName, true);
        packet.writeUTF(this.playerEntity.soulmate, true);
        packet.writeInt(this.playerEntity.savesNormalWithSkill);
        packet.writeInt(this.playerEntity.shamanCheeseCount);
        packet.writeInt(this.playerEntity.firstCount);
        packet.writeInt(this.playerEntity.cheeseCount);
        packet.writeInt(this.playerEntity.savesHardWithSkill);
        packet.writeInt(this.playerEntity.bootcampCount);
        packet.writeInt(this.playerEntity.savesDivineWithSkill);
        packet.writeInt(this.playerEntity.savesNormalWithoutSkill);
        packet.writeInt(this.playerEntity.savesHardWithoutSkill);
        packet.writeInt(this.playerEntity.savesDivineWithoutSkill);
        packet.writeShort(this.playerEntity.currentTitle.titleId);
        packet.writeShort((short) this.playerEntity.getUnlockedTitles().size());
        for (PlayerTitleInfo title : this.playerEntity.getUnlockedTitles()) {
            packet.writeShort(title.titleId);
            packet.writeByte(title.titleStars);
        }

        packet.writeUTF(this.playerEntity.playerLook + ";" + Integer.toHexString(this.playerEntity.mouseColor), true);
        packet.writeShort((short) this.playerEntity.shamanLevel);
        packet.writeUnsignedShort(this.playerEntity.getPlayerBadges().size() * 2);
        for (var badge : this.playerEntity.getPlayerBadges().entrySet()) {
            packet.writeUnsignedShort(badge.getKey());
            packet.writeUnsignedShort(badge.getValue());
        }

        packet.writeByte(11);
        packet.writeUnsignedByte(30);
        packet.writeInt(this.playerEntity.getRacingStats().roundsCount);
        packet.writeInt(this.roomStats.getRacingPlayRounds());
        packet.writeShort((short)124);
        packet.writeUnsignedByte(31);
        packet.writeInt(this.playerEntity.getRacingStats().roundsCompleted);
        packet.writeInt(this.roomStats.getRacingCompleteRounds());
        packet.writeShort((short)125);
        packet.writeUnsignedByte(33);
        packet.writeInt(this.playerEntity.getRacingStats().roundsCompletedFirstSecondThird);
        packet.writeInt(this.roomStats.getRacingGetFirstSecondThird());
        packet.writeShort((short)127);
        packet.writeUnsignedByte(32);
        packet.writeInt(this.playerEntity.getRacingStats().roundsCompletedFirst);
        packet.writeInt(this.roomStats.getRacingGetFirst());
        packet.writeShort((short)126);
        packet.writeUnsignedByte(26);
        packet.writeInt(this.playerEntity.getSurvivorStats().roundsCount);
        packet.writeInt(this.roomStats.getSurvivorPlayRounds());
        packet.writeShort((short)120);
        packet.writeUnsignedByte(27);
        packet.writeInt(this.playerEntity.getSurvivorStats().roundsShaman);
        packet.writeInt(this.roomStats.getSurvivorBeShaman());
        packet.writeShort((short)121);
        packet.writeUnsignedByte(28);
        packet.writeInt(this.playerEntity.getSurvivorStats().roundsKilledMice);
        packet.writeInt(this.roomStats.getSurvivorKillMice());
        packet.writeShort((short)122);
        packet.writeUnsignedByte(29);
        packet.writeInt(this.playerEntity.getSurvivorStats().roundsSurvived);
        packet.writeInt(this.roomStats.getSurvivorSurvive());
        packet.writeShort((short)123);
        packet.writeUnsignedByte(42);
        packet.writeInt(this.playerEntity.getDefilanteStats().roundsCount);
        packet.writeInt(this.roomStats.getDefilantePlayRounds());
        packet.writeShort((short)288);
        packet.writeUnsignedByte(43);
        packet.writeInt(this.playerEntity.getDefilanteStats().roundsCompletedFirstSecondThird);
        packet.writeInt(this.roomStats.getDefilanteCompleteRoundsFirstSecondThird());
        packet.writeShort((short)287);
        packet.writeUnsignedByte(44);
        packet.writeInt(this.playerEntity.getDefilanteStats().defilantePointsCount);
        packet.writeInt(this.roomStats.getDefilanteCollectPoints());
        packet.writeShort((short)286);
        packet.writeUnsignedByte(this.playerEntity.currentShamanBadge);
        packet.writeUnsignedByte(this.playerEntity.getShamanBadges().size());
        for (int badge : this.playerEntity.getShamanBadges())
            packet.writeUnsignedByte(badge);

        packet.writeBoolean(true);
        packet.writeInt(this.playerEntity.adventurePoints);
        packet.writeUnsignedShort(this.playerEntity.currentAdventureBanner);
        return packet;
    }
}
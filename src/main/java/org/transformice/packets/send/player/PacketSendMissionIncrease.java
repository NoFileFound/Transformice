package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.MissionType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMissionIncrease extends PSSendPacket {
    private final PlayerEntity.Mission missionInfo;

    /**
     * Creates a new packet of SEND_MISSION_INCREASE.
     * @param missionInfo The mission's info.
     */
    public PacketSendMissionIncrease(PlayerEntity.Mission missionInfo) {
        super(PacketOpcodes.SEND_MISSION_INCREASE);
        this.missionInfo = missionInfo;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        boolean isStrawberry = missionInfo.getMissionType() == MissionType.FINISH_20_DAILY_QUESTS;
        boolean isSaveMice = missionInfo.getMissionType() == MissionType.SAVE_MICE;
        return new PSByteArray().writeShort((short) this.missionInfo.getMissionType().getId()).writeByte(isSaveMice ? 1 : 0).writeShort((short)this.missionInfo.missionProgress).writeShort((short) this.missionInfo.getMissionRequirement()).writeShort((short) (isStrawberry ? 0 : this.missionInfo.getMissionAward())).writeShort((short) (isStrawberry ? this.missionInfo.getMissionAward() : 0));
    }
}
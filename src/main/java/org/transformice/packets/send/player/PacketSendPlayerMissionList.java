package org.transformice.packets.send.player;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.MissionType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerMissionList extends PSSendPacket {
    private final List<PlayerEntity.Mission> missionList;
    private final boolean canChangeMission;

    /**
     * Creates a new packet of SEND_PLAYER_MISSION_LIST.
     * @param missionList The player's mission list.
     * @param canChangeMission Can the player change a mission.
     */
    public PacketSendPlayerMissionList(List<PlayerEntity.Mission> missionList, boolean canChangeMission) {
        super(PacketOpcodes.SEND_PLAYER_MISSION_LIST);
        this.missionList = missionList;
        this.canChangeMission = canChangeMission;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.missionList.size());
        for (var info : this.missionList) {
            boolean isStrawberry = (info.getMissionType() == MissionType.FINISH_20_DAILY_QUESTS);
            boolean isSaveMice = (info.getMissionType() == MissionType.SAVE_MICE);
            packet.writeShort((short)info.getMissionType().getId());
            packet.writeByte((isSaveMice ? 1 : 0));
            packet.writeShort((short)info.missionProgress);
            packet.writeShort((short) info.getMissionRequirement());
            packet.writeShort((short) (isStrawberry ? 0 : info.getMissionAward()));
            packet.writeShort((short) (isStrawberry ? info.getMissionAward() : 0));
            if (isStrawberry)
                packet.writeBoolean(false);
            else
                packet.writeBoolean(this.canChangeMission);
        }

        return packet;
    }
}
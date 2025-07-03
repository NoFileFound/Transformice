package org.transformice.packets.send.newpackets;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.database.embeds.Quest;
import org.transformice.packets.SendPacket;

public final class C_PlayerSendMissionList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerSendMissionList(List<Quest> playerMissions, boolean canChangeMission) {
        this.byteArray.writeByte(playerMissions.size());
        for (Quest quest : playerMissions) {
            this.byteArray.writeShort((short)quest.getId());
            this.byteArray.writeByte(quest.getMissionType());
            this.byteArray.writeShort((short)quest.getMissionCollected());
            this.byteArray.writeShort((short)quest.getMissionTotal());
            this.byteArray.writeShort(quest.getId() == 60801 ? 0 : (short)quest.getMissionPrize());
            this.byteArray.writeShort(quest.getId() == 60801 ? (short)quest.getMissionPrize() : 0);
            this.byteArray.writeBoolean(quest.getId() != 60801 && canChangeMission);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
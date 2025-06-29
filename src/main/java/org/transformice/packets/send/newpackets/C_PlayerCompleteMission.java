package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.database.embeds.Quest;
import org.transformice.packets.SendPacket;

public final class C_PlayerCompleteMission implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerCompleteMission(Quest playerMission) {
        this.byteArray.writeShort((short)playerMission.getId());
        this.byteArray.writeByte(playerMission.getMissionType());
        this.byteArray.writeShort((short)playerMission.getMissionCollected());
        this.byteArray.writeShort((short)playerMission.getMissionTotal());
        this.byteArray.writeShort(playerMission.getId() == 60801 ? 0 : (short)playerMission.getMissionPrize());
        this.byteArray.writeShort(playerMission.getId() == 60801 ? (short)playerMission.getMissionPrize() : 0);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.level;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OnlineStaffTeam implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OnlineStaffTeam(int staffColorId, List<String> staffInfo) {
        this.byteArray.writeUnsignedByte(staffColorId);
        this.byteArray.writeUnsignedByte(staffInfo.size());
        for(String info : staffInfo) {
            for(String playerInfo : info.split("_")) {
                this.byteArray.writeString(playerInfo);
            }
        }
        this.byteArray.writeBoolean(true);
    }

    @Override
    public int getC() {
        return 24;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ViewStaffLog implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ViewStaffLog(String playerName, String header, String footer, int givenSanctions, byte[] sanctionData, int cancelledSanctions, byte[] sanctionData2) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(header);
        this.byteArray.writeString(footer);
        this.byteArray.writeUnsignedInt(givenSanctions);
        this.byteArray.writeInt(sanctionData.length);
        this.byteArray.writeBytes(sanctionData);
        this.byteArray.writeUnsignedInt(cancelledSanctions);
        this.byteArray.writeInt(sanctionData2.length);
        this.byteArray.writeBytes(sanctionData2);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 105;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
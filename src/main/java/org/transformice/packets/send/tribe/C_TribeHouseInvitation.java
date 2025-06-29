package org.transformice.packets.send.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TribeHouseInvitation implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TribeHouseInvitation(String inviter, String inviterTribe) {
        this.byteArray.writeString(inviter);
        this.byteArray.writeString(inviterTribe);
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
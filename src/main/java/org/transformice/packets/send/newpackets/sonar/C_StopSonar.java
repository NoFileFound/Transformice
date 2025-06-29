package org.transformice.packets.send.newpackets.sonar;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_StopSonar implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_StopSonar(int playerSession) {
        this.byteArray.writeInt(playerSession);
    }

    @Override
    public int getC() {
        return 145;
    }

    @Override
    public int getCC() {
        return 181;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LaunchHotAirBalloon implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LaunchHotAirBalloon(int sessionId, int badgeId) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeUnsignedShort(badgeId);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 71;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
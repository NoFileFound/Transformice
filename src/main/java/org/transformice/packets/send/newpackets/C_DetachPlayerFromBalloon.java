package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DetachPlayerFromBalloon implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DetachPlayerFromBalloon(int playerSession) {
        this.byteArray.writeInt(playerSession);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
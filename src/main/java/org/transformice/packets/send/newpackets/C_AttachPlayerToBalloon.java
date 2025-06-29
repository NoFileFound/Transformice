package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AttachPlayerToBalloon implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AttachPlayerToBalloon(int playerSession, int objId, int speed) {
        this.byteArray.writeInt(playerSession);
        this.byteArray.writeInt(objId);
        this.byteArray.writeInt(speed);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
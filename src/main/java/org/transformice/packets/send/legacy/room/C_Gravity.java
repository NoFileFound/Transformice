package org.transformice.packets.send.legacy.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Gravity implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Gravity(int wind, int gravity) {
        this.byteArray.writeByte(wind);
        this.byteArray.writeByte(1);
        this.byteArray.writeByte(gravity);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
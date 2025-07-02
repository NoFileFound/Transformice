package org.transformice.packets.send.legacy;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChangeTribeHouseResult implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeTribeHouseResult(int errorCode) {
        this.byteArray.writeByte(errorCode);
    }

    @Override
    public int getC() {
        return 16;
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